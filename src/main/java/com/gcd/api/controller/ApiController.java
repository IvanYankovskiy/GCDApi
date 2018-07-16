package com.gcd.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gcd.api.entities.Task;
import com.gcd.api.jsonviews.TaskView;
import com.gcd.api.jsonviews.TaskViewResolver;
import com.gcd.api.messaging.TaskSender;
import com.gcd.api.service.GCDService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ApiController {
    Logger logger = LoggerFactory.getLogger(ApiController.class);
    
    @Autowired
    @Qualifier(value = "gCDServiceJpaImplementation")
    GCDService gCDService;
    
    @Autowired
    TaskViewResolver taskViewResolver; 
   
    @Autowired
    TaskSender taskSender;
    
    @RequestMapping(method = RequestMethod.POST, path = "/calculate-gcd")
    @JsonView(TaskView.SuccesfulAdded.class)
    public @ResponseBody Task calculateGCD(@Validated(Task.NewTask.class) @RequestBody Task pair) {
        logger.info("Retrieved task: " + pair.toString());
        Task task = gCDService.addTask(pair);
        taskSender.sendTask(task);
        return task;
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/get-result/{id}")
    public ResponseEntity<MappingJacksonValue> gerResultById(@PathVariable("id") long id) {
        Optional<Task> task = gCDService.getTaskById(id);
        if (task.isPresent())
            return taskViewResolver.resolveTaskView(task.get());
        else
            return taskViewResolver.resolveNotFoundTaskView(id);
    }
}
