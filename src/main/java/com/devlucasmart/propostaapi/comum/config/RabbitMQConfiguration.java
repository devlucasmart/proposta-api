package com.devlucasmart.propostaapi.comum.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchangePropostaPendete;

    @Value("${rabbitmq.propostaconcluida.exchange}")
    private String exchangePropostaConcluida;

    @Bean
    public Queue criarFilaPropostaPendenteMsAnaliseCredito() {
        return QueueBuilder.durable("proposta-pendente.ms-analise-credito")
                .deadLetterExchange("proposta-pendente-dlx.ex")
                .build();
    }

    @Bean
    public Queue criarFilaPropostaPendenteNoticacaoMsNotificacao() {
        return QueueBuilder.durable("proposta-pendente.ms-notificacao")
                .deadLetterExchange("proposta-pendente-dlx.ex")
                .build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsProposta() {
        return QueueBuilder.durable("proposta-concluida.ms-proposta")
                .deadLetterExchange("proposta-concluida-dlx.ex")
                .build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaNoticacaoMsNotificacao() {
        return QueueBuilder.durable("proposta-concluida.ms-notificacao")
                .deadLetterExchange("proposta-pendente-ms-notificacao-dlx.ex")
                .build();
    }
    @Bean
    public Queue criarFilaPropostaPendenteDlq() {
        return QueueBuilder.durable("proposta-pendente.dlq").build();
    }

    @Bean
    public Queue criarFilaPropostaPendenteNoticacaoMsNotificacaoDlq() {
        return QueueBuilder.durable("proposta-pendente-ms-notificacao.dlq").build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaDlq() {
        return QueueBuilder.durable("proposta-concluida.dlq").build();
    }

    @Bean
    public FanoutExchange deadLetterExchangePropostaPendente() {
        return ExchangeBuilder.fanoutExchange("proposta-pendente-dlx.ex").build();
    }

    @Bean
    public FanoutExchange deadLetterExchangePropostaPendenteMsNotificacao() {
        return ExchangeBuilder.fanoutExchange("proposta-pendente-ms-notificacao-dlx.ex").build();
    }

    @Bean
    public FanoutExchange deadLetterExchangePropostaConcluida() {
        return ExchangeBuilder.fanoutExchange("proposta-concluida-dlx.ex").build();
    }

    @Bean
    public Binding criarBindingPropostaPendente(){
        return BindingBuilder.bind(criarFilaPropostaPendenteDlq()).to(deadLetterExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaConcluida(){
        return BindingBuilder.bind(criarFilaPropostaConcluidaDlq()).to(deadLetterExchangePropostaConcluida());
    }

    @Bean
    public Binding criarBindingPropostaPendenteMsNotificacao(){
        return BindingBuilder.bind(criarFilaPropostaPendenteNoticacaoMsNotificacaoDlq())
                .to(deadLetterExchangePropostaPendenteMsNotificacao());
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaPendente() {
        return ExchangeBuilder.fanoutExchange(exchangePropostaPendete).build();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaConcluida() {
        return ExchangeBuilder.fanoutExchange(exchangePropostaConcluida).build();
    }

    @Bean
    public Binding criarBindingPropostaPendenteMSAnaliseCredito() {
        return BindingBuilder.bind(criarFilaPropostaPendenteMsAnaliseCredito())
                .to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaPendenteMSNotificacao() {
        return BindingBuilder.bind(criarFilaPropostaPendenteNoticacaoMsNotificacao())
                .to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMSPropostaApp() {
        return BindingBuilder.bind(criarFilaPropostaConcluidaMsProposta())
                .to(criarFanoutExchangePropostaConcluida());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMSNotificacao() {
        return BindingBuilder.bind(criarFilaPropostaConcluidaNoticacaoMsNotificacao())
                .to(criarFanoutExchangePropostaConcluida());
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

        return rabbitTemplate;
    }
}
