package codeworld.app.ui.configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.vaadin.spring.events.config.EventBusConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import codeworld.health.service.api.BillService;
import codeworld.health.service.api.CityService;
import codeworld.health.service.api.CountryService;
import codeworld.health.service.api.DepartmentService;
import codeworld.health.service.api.HospitalService;
import codeworld.health.service.api.PatientService;
import codeworld.health.service.api.StaffService;
import codeworld.health.service.api.StateService;

@Configuration
@ComponentScan(basePackages = "codeworld.app.ui.*")
@Import(EventBusConfiguration.class)
public class ClientConfiguration {

    private <C> C createClient(Class<C> service, String url) {
        ClientConfig cc = new ClientConfig().register(ObjectMapper.class);
        Client resource = ClientBuilder.newClient(cc);
        return WebResourceFactory.newResource(service, resource.target(url));
    }

    @Bean
    public CountryService countryService() {
        return createClient(CountryService.class, "http://localhost:8077/");
    }

    @Bean
    public StateService stateService() {
        return createClient(StateService.class, "http://localhost:8077/");
    }

    @Bean
    public CityService cityService() {
        return createClient(CityService.class, "http://localhost:8077/");
    }

    @Bean
    public HospitalService hospitalService() {
        return createClient(HospitalService.class, "http://localhost:8077/");
    }

    @Bean
    public DepartmentService departmentService() {
        return createClient(DepartmentService.class, "http://localhost:8077/");
    }

    @Bean
    public StaffService staffService() {
        return createClient(StaffService.class, "http://localhost:8077/");
    }

    @Bean
    public BillService billService() {
        return createClient(BillService.class, "http://localhost:8077/");
    }

    @Bean
    public PatientService patientService() {
        return createClient(PatientService.class, "http://localhost:8077/");
    }

}
