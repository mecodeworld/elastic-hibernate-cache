package home.app.ui.configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.vaadin.spring.events.config.EventBusConfiguration;

import home.app.api.configuration.ObjectMapperProvider;
import home.app.api.service.FilmService;

@Configuration
@ComponentScan(basePackages = "home.app.ui")
@Import(EventBusConfiguration.class)
public class ClientConfiguration {

    private <C> C createClient(Class<C> service, String url) {
        ClientConfig cc = new ClientConfig().register(ObjectMapperProvider.class);
        Client resource = ClientBuilder.newClient(cc);
        return WebResourceFactory.newResource(service, resource.target(url));
    }

    @Bean
    public FilmService filmService() {
        return createClient(FilmService.class, "http://localhost:8077/");
    }

}
