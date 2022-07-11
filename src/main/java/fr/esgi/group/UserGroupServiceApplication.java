package fr.esgi.group;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserGroupServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserGroupServiceApplication.class, args);
    }

}
