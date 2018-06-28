package com.gcd.api.messaging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcd.api.entities.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskSender {
    private static Logger logger = LoggerFactory.getLogger(TaskSender.class);
    
    @Autowired
    private DirectExchange exchange;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private Queue taskQueue;
    
    public void sendTask(Task task) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode taskNode = mapper.valueToTree(task);
        rabbitTemplate.convertAndSend(exchange.getName(), "task", taskNode);
        logger.debug("[x] Task: " + task.toString() 
                + " was sent to topic \"" + exchange.getName() + "\"" 
                + " with routing key \"task\"");
    }
}
