package com.ocoelhogabriel.microauth.infrastructure.amqp;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Define o nome do nosso roteador de eventos de company
    public static final String EXCHANGE_COMPANY_EVENTS = "company.events";

    /**
     * Cria uma Topic Exchange. Uma Topic Exchange é poderosa porque permite rotear mensagens baseadas em um padrão (routing key). Ex:
     * "company.created", "company.updated", etc.
     */
    @Bean
    public Exchange companyEventsExchange() {
        return new TopicExchange(EXCHANGE_COMPANY_EVENTS);
    }

    /**
     * Define o conversor de mensagens para o formato JSON. Isso garante que nossos objetos Java (como o CompanyEvent) sejam enviados como JSON,
     * facilitando a interoperabilidade com outros possíveis serviços em outras linguagens.
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}