package codeworld.health.service.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author h.kanure
 *
 */
@Configuration
@ComponentScan(basePackages = "codeworld.health.service.*")
@EnableAutoConfiguration
public class AppConfiguration {

}
