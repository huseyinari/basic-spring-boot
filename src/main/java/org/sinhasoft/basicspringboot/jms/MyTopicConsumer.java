package org.sinhasoft.basicspringboot.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class MyTopicConsumer implements MessageListener {
    @JmsListener(destination = "${active-mq.my-topic-name}", containerFactory = "jmsTopicListenerContainerFactory")
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();

            System.out.println("MyTopicConsumer - Alınan mesaj : " + text);
        } catch (Exception e) {
            System.out.println("MyTopicConsumer - Mesaj alınamadı.");
        }
    }
}
