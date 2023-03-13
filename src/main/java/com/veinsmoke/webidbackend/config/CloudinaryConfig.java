package com.veinsmoke.webidbackend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("cloudinary")
@Getter
@Setter
public class CloudinaryConfig {

    private String cloudname;
    private String apikey;
    private String apisecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudname,
                "api_key", apikey,
                "api_secret", apisecret));
    }
}