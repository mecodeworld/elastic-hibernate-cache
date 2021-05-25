package codeworld.app.ui.configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import codeworld.elastic.service.api.ECountryService;
import codeworld.elastic.service.api.EPatientService;

@Configuration
@ComponentScan(basePackages = "codeworld.app.ui.*")
public class JmsServiceClientConfig {

    private <C> C createClient(Class<C> service, String url) {
        ClientConfig cc = new ClientConfig().register(ObjectMapper.class);
        Client resource = ClientBuilder.newClient(cc);
        return WebResourceFactory.newResource(service, resource.target(url));
    }

    @Bean
    public ECountryService eCountryService() {
        return createClient(ECountryService.class, "http://localhost:8088/");
    }

    @Bean
    public EPatientService ePatientService() {
        return createClient(EPatientService.class, "http://localhost:8088/");
    }

}
