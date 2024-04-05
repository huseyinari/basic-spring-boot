package org.sinhasoft.basicspringboot.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import java.util.Arrays;

@Configuration
public class ActiveMqConfig {

    @Value("${active-mq.broker-url}")
    private String brokerUrl;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(this.brokerUrl);
        connectionFactory.setTrustedPackages(Arrays.asList("org.sinhasoft.basicspringboot")); // Object Message gönderirken hangi paket altındaki sınıfların nesnelerinin gönderilmesine izin verildiğini belirtir.
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsQueueTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(this.connectionFactory());
        return jmsTemplate;
    }

    @Bean
    public JmsTemplate jmsTopicTemplate() {
        //...
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(this.connectionFactory());
        jmsTemplate.setPubSubDomain(true);  // JmsTemplate'in Queue değil Topic'e mesaj göndereceğini belirtir.
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(this.connectionFactory());
        return factory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsTopicListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(this.connectionFactory());
        factory.setPubSubDomain(true);  // Topic'lere gelen mesajları dinlemek için kullanılacak
        return factory;
    }
}
