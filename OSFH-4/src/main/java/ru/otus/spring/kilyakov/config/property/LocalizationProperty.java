package ru.otus.spring.kilyakov.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.localization")
public class LocalizationProperty {
    private Locale locale;
    private String defaultPath;
    private Map<String, String> localePaths;
}
