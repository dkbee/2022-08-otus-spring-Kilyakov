package ru.otus.spring.kilyakov.security.service.impl;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.otus.spring.kilyakov.domain.CustomUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDetailsServiceImpl extends JdbcDaoSupport implements UserDetailsService {

    public static final String DEF_USERS_BY_USERNAME_QUERY = "select id, username, password, enabled "
            + "from users "
            + "where username = ?";

    public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY = "select username,authority "
            + "from authorities "
            + "where username = ?";

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private String usersByUsernameQuery;

    private String authoritiesByUsernameQuery;

    public void setAuthoritiesByUsernameQuery(String authoritiesByUsernameQuery) {
        this.authoritiesByUsernameQuery = authoritiesByUsernameQuery;
    }

    public String getAuthoritiesByUsernameQuery() {
        return authoritiesByUsernameQuery;
    }

    public UserDetailsServiceImpl() {
        this.usersByUsernameQuery = DEF_USERS_BY_USERNAME_QUERY;
        this.authoritiesByUsernameQuery = DEF_AUTHORITIES_BY_USERNAME_QUERY;
    }

    public String getUsersByUsernameQuery() {
        return this.usersByUsernameQuery;
    }

    public void setUsersByUsernameQuery(String usersByUsernameQueryString) {
        this.usersByUsernameQuery = usersByUsernameQueryString;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<CustomUser> customUsers = loadUsersByUsername(username);
        if (customUsers.size() == 0) {
            this.logger.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound",
                    new Object[]{username}, "Username {0} not found"));
        }
        CustomUser customUser = customUsers.get(0);
        if (!customUser.isEnabled()) {
            this.logger.debug("User '" + username + "' has been disabled");
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound",
                    new Object[]{username}, "Username {0} has been disabled"));
        }
        Set<GrantedAuthority> dbAuthsSet = new HashSet<>(loadUserAuthorities(customUser.getUsername()));
        List<GrantedAuthority> dbAuths = new ArrayList<>(dbAuthsSet);
        return createUserDetails(customUser, dbAuths);
    }

    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        if (getJdbcTemplate() != null) {
            return getJdbcTemplate().query(this.authoritiesByUsernameQuery, (rs, rowNum) -> {
                String roleName = rs.getString(2);
                return new SimpleGrantedAuthority(roleName);
            }, (Object) new String[]{username});
        }
        return new ArrayList<>();
    }

    protected List<CustomUser> loadUsersByUsername(String username) {
        // @formatter:off
        RowMapper<CustomUser> mapper = (rs, rowNum) -> {
            Long userId = rs.getLong(1);
            String username1 = rs.getString(2);
            String password = rs.getString(3);
            boolean enabled = rs.getBoolean(4);
            return new CustomUser(userId, username1, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
        };
        // @formatter:on
        if (getJdbcTemplate() != null) {
            return getJdbcTemplate().query(this.usersByUsernameQuery, mapper, username);
        }
        return new ArrayList<>();
    }

    protected UserDetails createUserDetails(CustomUser customUser, List<GrantedAuthority> combinedAuthorities) {
        return new CustomUser(customUser.getId(), customUser.getUsername(), customUser.getPassword(),
                customUser.isEnabled(),
                customUser.isAccountNonExpired(),
                customUser.isCredentialsNonExpired(),
                customUser.isAccountNonLocked(),
                combinedAuthorities);
    }
}
