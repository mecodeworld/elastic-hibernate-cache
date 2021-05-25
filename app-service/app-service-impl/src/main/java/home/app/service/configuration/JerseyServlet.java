package home.app.service.configuration;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JerseyServlet extends ResourceConfig {

    @Resource
    private ApplicationContext context;

    public JerseyServlet() {
        // empty
    }

    @PostConstruct
    public void postConstruct() {
        log.info("Starting rest-service context");

        for (final Map.Entry<String, Object> entry : context.getBeansWithAnnotation(Service.class)
                .entrySet()) {
            log.info("registered rest-service " + entry.getValue()
                    .getClass());
            register(entry.getValue());
        }
        // register(ObjectMapperProvider.class);
        // register(LocalDateConverterProvider.class);
        // register(ZonedDateTimeParamConverterProvider.class);
        register(SerializationFeature.class);
        register(DeserializationFeature.class);
        // property(ServletProperties.FILTER_FORWARD_ON_404, true);

    }
}