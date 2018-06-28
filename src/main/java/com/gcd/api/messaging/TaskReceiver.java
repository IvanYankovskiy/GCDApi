package com.gcd.api.messaging;

import com.gcd.api.entities.Task;
import com.gcd.api.service.GCDService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class TaskReceiver {

    @Autowired
    @Qualifier(value = "gCDServiceJpaImplementation")
    GCDService gCDService;
    
    public TaskReceiver() {
       super();
    }    
    
    @RabbitListener(containerFactory = "myRabbitListenerContainerFactory", queues = "resultQueue")
    public void updateTaskWithRecievedResult(Task result) {
        gCDService.updateTaskWithRecievedResult(result);
    }
}
