package codeworld.app.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class HealthUIApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HealthUIApplication.class, args);
    }

}
