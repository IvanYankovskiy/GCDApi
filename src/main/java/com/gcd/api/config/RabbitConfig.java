package com.gcd.api.config;

import com.gcd.api.messaging.TaskReceiver;
import com.gcd.api.messaging.TaskSender;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {
    
    @Value("${messaging.rabbit.host.url}")
    String hostUrl;
    
    @Value("${messaging.rabbit.host.port}")
    int hostPort;
    
    @Value("${messaging.rabbit.host.user}")
    String hostUser;
    
    @Value("${messaging.rabbit.host.password}")
    String hostPassword;
    
    @Value("${messaging.exchange.name}")
    String exchangeName;
    
    @Value("${messaging.exchange.durable}")
    boolean exchangeDurable;
    
    @Value("${messaging.exchange.autodelete}")
    boolean exchangeAutoDelete;
    
    @Value("${messaging.gueue.task.name}")
    String taskQueueName;
    
    @Value("${messaging.gueue.task.durable}")
    boolean taskQueueDurable;
    
    @Value("${messaging.gueue.task.binding.key}")
    String taskQueueBindingKey;
    
    
    @Value("${messaging.gueue.result.name}")
    String resultQueueName;
    
    @Value("${messaging.gueue.result.durable}")
    boolean resultQueueDurable;
    
    @Value("${messaging.gueue.result.binding.key}")
    String resultQueueBindingKey;
    
    @Value("${messaging.reciever.maxConcurrentConsumers}")
    int recieverMaxConcurrentConsumers;
    
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
            new CachingConnectionFactory(hostUrl);
        connectionFactory.setPort(hostPort);
        connectionFactory.setUsername(hostUser);
        connectionFactory.setPassword(hostPassword);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
    
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName, exchangeDurable, exchangeAutoDelete);
    } 
    

    @Bean
    public Queue taskQueue() {
       return new Queue(taskQueueName, taskQueueDurable);
    }
    
    @Bean
    public Queue resultQueue() {
        return new Queue(resultQueueName, resultQueueDurable);
    }
    
    @Bean
    public TaskSender taskSender() {
        return new TaskSender();
    }
    
    @Bean
    public TaskReceiver taskReceiver() {
        return new TaskReceiver();
    }
    
    @Bean
    public Binding bindingResult(DirectExchange exchange, Queue resultQueue) {
        return BindingBuilder.bind(resultQueue)
                .to(exchange)
                .with(resultQueueBindingKey);
    }
    
    @Bean
    public Binding bindingTask(DirectExchange exchange, Queue taskQueue) {
        return BindingBuilder.bind(taskQueue)
                .to(exchange)
                .with(taskQueueBindingKey);
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory 
                = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMaxConcurrentConsumers(recieverMaxConcurrentConsumers);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}
