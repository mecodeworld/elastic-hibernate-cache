package codeworld.elastic.service.configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import codeworld.health.service.api.CountryService;
import codeworld.health.service.api.PatientService;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "codeworld.elastic.service.*" })
public class ClientConfiguration {

    private <C> C createClient(Class<C> service, String url) {
        ClientConfig clientConfig = new ClientConfig().register(ObjectMapper.class);
        Client client = ClientBuilder.newClient(clientConfig);
        client.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
        return WebResourceFactory.newResource(service, client.target(url));
    }

    @Bean
    public CountryService countryService() {
        return createClient(CountryService.class, "http://localhost:8077/");
    }

    @Bean
    public PatientService patientService() {
        return createClient(PatientService.class, "http://localhost:8077/");
    }

}
