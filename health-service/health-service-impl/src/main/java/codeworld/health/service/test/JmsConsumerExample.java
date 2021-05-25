package codeworld.health.service.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JmsConsumerExample {

    // @JmsListener(destination = "queue.health.country")
    public void consume(String message) {
        System.out.println("Received Message: " + message);
    }

}
