package com.sda.store.config;

import com.sda.store.faker.CustomFaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CustomFaker customFaker() {
        return new CustomFaker();
    }


}
