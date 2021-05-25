package codeworld.health.service.listener;

import javax.jms.Queue;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import codeworld.health.service.jms.JMSMessage;
import codeworld.health.service.jms.JMSOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JpaChangeEntityListener {

    @PostPersist
    public void postPersist(Object entity) {
        log.info("persiting object: {}", entity.getClass()
                .getName());
        sendMessage(entity, JMSOperation.PERSIST);
    }

    @PostRemove
    public void postDelete(Object entity) {
        log.info("deleting object: {}", entity.getClass()
                .getName());
        sendMessage(entity, JMSOperation.DELETE);
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        log.info("persiting object: {}", entity.getClass()
                .getName());
        sendMessage(entity, JMSOperation.UPDATE);
    }

    private void sendMessage(Object entity, JMSOperation operation) {
        JMSMessage jmsMessage = JPAListenerUtil.createJMSMessage(entity, operation);
        JpaChangeEntityListenerSpringConnector connector = JpaChangeEntityListenerSpringConnector.instance;
        connector.jmsTemplate.convertAndSend(connector.queue, jmsMessage);
    }

    @Service
    public static class JpaChangeEntityListenerSpringConnector {

        @Autowired
        private JmsTemplate jmsTemplate;

        @Autowired
        private Queue queue;

        private static JpaChangeEntityListenerSpringConnector instance;

        public JpaChangeEntityListenerSpringConnector() {
            instance = this;
        }
    }
}
