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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableRabbit
public class RabbitConfig {
    @Autowired
    Environment environment;
       
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
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
        return new DirectExchange("gcd.messages", true, false);
    } 
    

    @Bean
    public Queue taskQueue() {
       return new Queue("taskQueue",true);
    }
    
    @Bean
    public Queue resultQueue() {
        return new Queue("resultQueue", true);
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
                .with("result");
    }
    
    @Bean
    public Binding bindingTask(DirectExchange exchange, Queue taskQueue) {
        return BindingBuilder.bind(taskQueue)
                .to(exchange)
                .with("task");
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory 
                = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMaxConcurrentConsumers(environment.
                getRequiredProperty("messaging.reciever.maxConcurrentConsumers", int.class));
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}
