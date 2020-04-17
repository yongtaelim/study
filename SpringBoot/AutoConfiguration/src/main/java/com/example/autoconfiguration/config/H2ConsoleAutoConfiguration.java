package com.example.autoconfiguration.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(H2ConsoleProperties.class)
@ConditionalOnProperty(prefix = "spring.h2.console", name = "enabled", havingValue = "true", matchIfMissing = false)
//@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(H2ConsoleProperties.class)
public class H2ConsoleAutoConfiguration {

    private final String H2_CONSOLE_AUTO_CONFIGURATION_BEAN_NAME = "h2-configuration-bean-name";

    @Bean(name = H2_CONSOLE_AUTO_CONFIGURATION_BEAN_NAME)
    public void h2Console() {
        System.out.println("create a bean " + H2_CONSOLE_AUTO_CONFIGURATION_BEAN_NAME);
    }
}
