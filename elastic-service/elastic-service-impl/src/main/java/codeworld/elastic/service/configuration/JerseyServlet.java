package codeworld.elastic.service.configuration;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@Configuration
public class JerseyServlet extends ResourceConfig {

    @Resource
    private ApplicationContext context;

    @PostConstruct
    public void postConstruct() {
        for (final Map.Entry<String, Object> entry : context.getBeansWithAnnotation(Service.class)
                .entrySet()) {
            register(entry.getValue());
        }
        register(Jdk8Module.class);
    }
}