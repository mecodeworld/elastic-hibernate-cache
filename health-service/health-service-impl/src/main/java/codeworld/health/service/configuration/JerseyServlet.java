package codeworld.health.service.configuration;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JerseyServlet extends ResourceConfig {

    @Resource
    private ApplicationContext context;

    @PostConstruct
    public void postConstruct() {
        for (final Map.Entry<String, Object> entry : context.getBeansWithAnnotation(Service.class)
                .entrySet()) {
            register(entry.getValue());
            log.info("Registering service " + entry.getValue()
                    .getClass()
                    .getSimpleName());
        }
        register(Jdk8Module.class);
    }
}