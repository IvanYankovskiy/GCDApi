package com.gcd.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcd.api.entities.Task;
import com.gcd.api.jsonviews.TaskView;
import com.gcd.api.messaging.TaskSender;
import com.gcd.api.service.GCDService;
import com.gcd.api.validator.TaskValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    TaskSender taskSender;
    
    @RequestMapping(method = RequestMethod.POST, path = "/calculate-gcd")
    @JsonView(TaskView.SuccesfulAdded.class)
    public @ResponseBody Task calculateGCD(@RequestBody JsonNode pair) {
        logger.info("Retrieved task: " + pair.toString());
        TaskValidation validator = new TaskValidation(pair);
        validator.isTaskValid();
        ObjectMapper mapper = new ObjectMapper();
        Task validPair = mapper.convertValue(pair, Task.class);
        Task task = gCDService.addTask(validPair);
        taskSender.sendTask(task);
        return task;
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/get-result/{id}")
    public ResponseEntity<Task> gerResultById(@PathVariable("id") long id) {
        return new ResponseEntity<Task>(gCDService.getTaskById(id), HttpStatus.OK);
    }
}
