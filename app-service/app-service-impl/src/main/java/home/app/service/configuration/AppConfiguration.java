package home.app.service.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "home.app.service.*")
@EnableAutoConfiguration
public class AppConfiguration {

}
