package codeworld.elastic.service.jmslistener;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codeworld.elastic.service.document.ECountry;
import codeworld.elastic.service.document.PatientDoc;
import codeworld.elastic.service.impl.ECountryServiceImpl;
import codeworld.elastic.service.impl.EPatientServiceImpl;
import codeworld.elastic.service.repository.ECountryRepository;
import codeworld.elastic.service.repository.PatientRepository;
import codeworld.health.service.api.CountryService;
import codeworld.health.service.api.PatientService;
import codeworld.health.service.jms.EntityType;
import codeworld.health.service.jms.JMSMessage;
import codeworld.health.service.model.CountryDto;
import codeworld.health.service.model.PatientDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ECountryJmsListener {

    @Resource
    private CountryService countryService;

    @Resource
    private PatientService patientService;

    @Resource
    private ECountryRepository eCountryRepository;

    @Resource
    private PatientRepository patientRepository;

    @JmsListener(destination = "queue.health.country")
    public void consume(JMSMessage message) {
        System.out.println("Received Message: " + message);
        if (message.getId() == null || message.getJmsOperation() == null || message.getEntityType() == null) {
            throw new IllegalArgumentException("there are some missing input values");
        }

        switch (message.getJmsOperation()) {
            case PERSIST:
                AfterPersist(message.getId(), message.getEntityType());
                break;
            case UPDATE:
                AfterUpdate(message.getId(), message.getEntityType());
                break;
            case DELETE:
                AfterDelete(message.getId(), message.getEntityType());
                break;
            default:
                break;
        }
    }

    private void AfterPersist(Long id, EntityType entityType) {
        switch (entityType) {
            case COUNTRY:
                saveOrUpdateCountry(id);
                break;
            case PATIENT:
                saveOrUpdatePatient(id);
                break;
            default:
                break;
        }

    }

    private void AfterUpdate(Long id, EntityType entityType) {
        switch (entityType) {
            case COUNTRY:
                saveOrUpdateCountry(id);
                break;
            case PATIENT:
                saveOrUpdatePatient(id);
                break;
            default:
                break;
        }
    }

    private void AfterDelete(Long id, EntityType entityType) {
        switch (entityType) {
            case COUNTRY:
                Optional<ECountry> country = eCountryRepository.findById(id);
                if (country.isPresent()) {
                    eCountryRepository.delete(country.get());
                }
                break;
            case PATIENT:
                patientRepository.deleteById(id);
                break;
            default:
                break;
        }
    }

    private void saveOrUpdateCountry(Long id) {
        CountryDto countryDto = countryService.findById(id);
        if (countryDto != null) {
            ECountry countryDocument = ECountryServiceImpl.convertToDocument(countryDto);
            eCountryRepository.save(countryDocument);
        }
    }

    private void saveOrUpdatePatient(Long id) {
        log.info("rquesting Patient Entity from elastic search listener " + id);
        PatientDto patientDto = patientService.findById(id);
        if (patientDto != null) {
            PatientDoc patientDoc = EPatientServiceImpl.convertToDocument(patientDto);
            PatientDoc save = patientRepository.save(patientDoc);
            log.info("Patient Document is saving in elastic search database and saved ID is " + save.getId());
        }
    }

}
