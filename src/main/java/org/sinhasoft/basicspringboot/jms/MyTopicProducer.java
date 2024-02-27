package org.sinhasoft.basicspringboot.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyTopicProducer {
    @Autowired
    @Qualifier("jmsTopicTemplate")
    private JmsTemplate jmsTemplate;

    @Value("${active-mq.my-topic-name}")
    private String topicName;

    public void sendMessage(String message) {
        try {
            System.out.println("Mesaj topic'e gönderiliyor. Gönderilen Mesaj: " + message);
            jmsTemplate.convertAndSend(topicName, message);
        } catch (Exception e) {
            System.out.println("Mesaj topic'e gönderilemedi.");
        }
    }
}
