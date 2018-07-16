package com.gcd.api.entities;

import com.gcd.api.service.TaskStatus;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TaskNGTest {
    
    private static Validator validator;
    
    public TaskNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
    
    @Test
    public void testNewTaskGroupNegativeFirst() {
        System.out.println("NewTask validation group. Negative \"first\"");
        long first = -5L;
        long second = 10;
        Task task = new Task();
        task.setFirst(first);
        task.setSecond(second);
        Set<ConstraintViolation<Task>> constraintViolations =
        validator.validate(task, Task.NewTask.class);
        assertEquals(constraintViolations.size(), 1);
        assertEquals(
                constraintViolations.iterator().next().getMessage(),
                "Provide correct positive, non-zero field with name \"first\""
        );       
    }
    
    @Test
    public void testNewTaskGroupZeroFirst() {
        System.out.println("NewTask validation group. Zero \"first\"");
        long first = 0L;
        long second = 10;
        Task task = new Task();
        task.setFirst(first);
        task.setSecond(second);
        Set<ConstraintViolation<Task>> constraintViolations =
        validator.validate(task, Task.NewTask.class);
        assertEquals(constraintViolations.size(), 1);
        assertEquals(
                constraintViolations.iterator().next().getMessage(),
                "Provide correct positive, non-zero field with name \"first\""
        );       
    }
    
    @Test
    public void testNewTaskGroupNegativeSecond() {
        System.out.println("NewTask validation group. Negative \"second\"");
        long first = 20L;
        long second = -4;
        Task task = new Task();
        task.setFirst(first);
        task.setSecond(second);
        Set<ConstraintViolation<Task>> constraintViolations =
        validator.validate(task, Task.NewTask.class);
        assertEquals(constraintViolations.size(), 1);
        assertEquals(
                constraintViolations.iterator().next().getMessage(),
                "Provide correct positive, non-zero field with name \"second\""
        );       
    }
    
    @Test
    public void testNewTaskGroupZeroSecond() {
        System.out.println("NewTask validation group. Zero \"second\"");
        long first = 10L;
        long second = 0;
        Task task = new Task();
        task.setFirst(first);
        task.setSecond(second);
        Set<ConstraintViolation<Task>> constraintViolations =
        validator.validate(task, Task.NewTask.class);
        assertEquals(constraintViolations.size(), 1);
        assertEquals(
                constraintViolations.iterator().next().getMessage(),
                "Provide correct positive, non-zero field with name \"second\""
        );       
    }
}
