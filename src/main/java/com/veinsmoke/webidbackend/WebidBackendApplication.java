package com.veinsmoke.webidbackend;

import com.veinsmoke.webidbackend.config.CloudinaryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WebidBackendApplication implements CommandLineRunner {

    private final CloudinaryConfig configuration;

    @Value("${spring.mail.password}")
    private String password;

    public WebidBackendApplication(CloudinaryConfig configuration) {
        this.configuration = configuration;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebidBackendApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Logger logger = LoggerFactory.getLogger(WebidBackendApplication.class);

        logger.info("----------------------------------------");
        logger.info("Configuration properties");
        logger.info("   cloudinary.apisecret is {}", configuration.getApisecret());
        logger.info("   cloudinary.cloudname is {}", configuration.getCloudname());
        logger.info("   mail.password is {}", password);
        logger.info("----------------------------------------");
    }

}
