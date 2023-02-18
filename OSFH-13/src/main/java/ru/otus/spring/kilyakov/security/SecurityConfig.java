package ru.otus.spring.kilyakov.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.kilyakov.security.service.impl.UserDetailsServiceImpl;

import javax.sql.DataSource;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "/book", "/book/").hasRole("GUEST")
                .and()
                .authorizeRequests().antMatchers("/book/get").hasAnyRole("USER", "ADMIN")
                .and()
                .authorizeRequests().antMatchers("/book/edit").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/book/add").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/book/delete").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout().logoutUrl("/logout");
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        UserDetailsServiceImpl dao = new UserDetailsServiceImpl();
        dao.setDataSource(dataSource);
        return dao;
    }

}
