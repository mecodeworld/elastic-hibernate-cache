package codeworld.health.service.listener;

import codeworld.health.service.entity.CityEntity;
import codeworld.health.service.entity.CountryEntity;
import codeworld.health.service.entity.HospitalEntity;
import codeworld.health.service.entity.PatientEntity;
import codeworld.health.service.entity.StateEntity;
import codeworld.health.service.jms.EntityType;
import codeworld.health.service.jms.JMSMessage;
import codeworld.health.service.jms.JMSOperation;

public class JPAListenerUtil {

    public static JMSMessage createJMSMessage(Object entity, JMSOperation operation) {

        JMSMessage jmsMessage = JMSMessage.builder()
                .jmsOperation(operation)
                .build();

        if (entity instanceof CountryEntity) {
            CountryEntity countryEntity = (CountryEntity) entity;
            jmsMessage.setEntityType(EntityType.COUNTRY);
            jmsMessage.setId(countryEntity.getId());
        }
        else if (entity instanceof StateEntity) {
            StateEntity stateEntity = (StateEntity) entity;
            jmsMessage.setEntityType(EntityType.STATE);
            jmsMessage.setId(stateEntity.getId());
        }
        else if (entity instanceof CityEntity) {
            CityEntity cityEntity = (CityEntity) entity;
            jmsMessage.setEntityType(EntityType.CITY);
            jmsMessage.setId(cityEntity.getId());
        }
        else if (entity instanceof HospitalEntity) {
            HospitalEntity hospitalEntity = (HospitalEntity) entity;
            jmsMessage.setEntityType(EntityType.HOSPITAL);
            jmsMessage.setId(hospitalEntity.getId());
        }
        else if (entity instanceof PatientEntity) {
            PatientEntity patientEntity = (PatientEntity) entity;
            jmsMessage.setEntityType(EntityType.PATIENT);
            jmsMessage.setId(patientEntity.getId());
        }

        return jmsMessage;
    }

}
