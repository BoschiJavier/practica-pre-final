package com.spotify.sugeridos;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableMongoRepositories
@EnableRabbit
public class ApiSugeridosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiSugeridosApplication.class, args);
    }

}
