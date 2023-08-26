package com.veinsmoke.webidbackend.config;

import io.ably.lib.rest.AblyRest;
import io.ably.lib.types.AblyException;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("ably")
@Setter
public class AblyConfig {


    private String apikey;

    @Bean
    AblyRest setAblyRest() throws AblyException {
        return new AblyRest(apikey);
    }
}
