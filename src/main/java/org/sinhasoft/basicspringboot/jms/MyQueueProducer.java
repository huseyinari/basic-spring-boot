package org.sinhasoft.basicspringboot.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyQueueProducer {
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    @Value("${active-mq.my-queue-name}")
    private String queueName;

    public void sendMessage(String message) {
        try {
            System.out.println("Mesaj queue'ya gönderiliyor. Gönderilen Mesaj: " + message);
            jmsTemplate.convertAndSend(queueName, message);
        } catch (Exception e) {
            System.out.println("Mesaj queue'ya gönderilemedi.");
        }
    }
}
