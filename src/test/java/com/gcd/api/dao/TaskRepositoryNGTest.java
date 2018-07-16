package com.gcd.api.dao;

import com.gcd.api.ApiApplication;
import com.gcd.api.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

@EnableAutoConfiguration
@SpringBootTest(classes = {ApiApplication.class, TaskRepository.class, Task.class})
@DataJpaTest
public class TaskRepositoryNGTest extends AbstractTestNGSpringContextTests{
    
    @Autowired
    TaskRepository taskRepository;
    
    @Test
    public void saveTask() {
        System.out.println("Save task case.");
        long first = 20;
        long second = 10;
        Task task = new Task(first, second);
        System.out.println("Saved task: " + task.toString());
        Task persisted = taskRepository.save(task);
        assertEquals(persisted.getFirst(), first);
        assertEquals(persisted.getSecond(), second);
    }
}
