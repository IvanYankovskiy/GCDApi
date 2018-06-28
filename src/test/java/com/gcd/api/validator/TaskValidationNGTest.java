package com.gcd.api.validator;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gcd.api.exceptions.InvalidTaskRequestException;
import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SpringBootTest
public class TaskValidationNGTest extends AbstractTestNGSpringContextTests {
    
    @DataProvider(name = "invalidInputTasks")
    public Iterator<Object[]> setupJSONTasksWithExceptions() {
        ArrayList<Object[]> cases = new ArrayList<>();
        
        String def_1_1 = "Invalid input tasks. Case 1. Missed \"first\" field";
        ObjectNode json_1_1 = new ObjectNode(JsonNodeFactory.instance);
        json_1_1.put("second", 2);
        cases.add(new Object[]{def_1_1, json_1_1});
        
        
        String def_1_2 = "Invalid input tasks. Case 2. Missed \"second\" field";
        ObjectNode json_1_2 = new ObjectNode(JsonNodeFactory.instance);
        json_1_2.put("first", 2);
        cases.add(new Object[]{def_1_2, json_1_2});
        
        
        String def_1_3 = "Invalid input tasks. Case 3. Incorrect negative value.";
        ObjectNode json_1_3 = new ObjectNode(JsonNodeFactory.instance);
        json_1_3.put("first", -2)
                .put("second", 2);
        cases.add(new Object[]{def_1_3, json_1_3});
                
        String def_1_4 = "Invalid input tasks. Case 4. Incorrect negative value.";
        ObjectNode json_1_4 = new ObjectNode(JsonNodeFactory.instance);
        json_1_4.put("first", -2)
                .put("second", 2);
        cases.add(new Object[]{def_1_4, json_1_4});
        
        String def_1_5 = "Invalid input tasks. Case 5. Incorrect 0 value.";
        ObjectNode json_1_5 = new ObjectNode(JsonNodeFactory.instance);
        json_1_5.put("first", 2)
                .put("second", 0);
        cases.add(new Object[]{def_1_5, json_1_5});
        
        String def_1_6 = "Invalid input tasks. Case 6. Non-integer field type.";
        ObjectNode json_1_6 = new ObjectNode(JsonNodeFactory.instance);
        json_1_6.put("first", "2")
                .put("second", 18);
        cases.add(new Object[]{def_1_6, json_1_6});
        
        
        String def_1_7 = "Invalid input tasks. Case 7. Non-integer field type.";
        ObjectNode json_1_7 = new ObjectNode(JsonNodeFactory.instance);
        json_1_7.put("first", 25)
                .put("second", 12.3);
        cases.add(new Object[]{def_1_7, json_1_7});
        
        return cases.iterator();        
    }
    
    @Test(dataProvider = "invalidInputTasks",
            expectedExceptions = {InvalidTaskRequestException.class})
    public void testIsTaskValidWithExceptions(String description, ObjectNode task) {
        System.out.println(description);
        TaskValidation validator = new TaskValidation(task);
        validator.isTaskValid();
    }
    
    @DataProvider(name = "validInputTasks")
    public Iterator<Object[]> setupJSONTasks() {
        ArrayList<Object[]> cases = new ArrayList<>();
        
        String def_1_1 = "Valid input tasks. Case 1.";
        ObjectNode json_1_1 = new ObjectNode(JsonNodeFactory.instance);
        json_1_1.put("first", 1)
                .put("second", 2);
        cases.add(new Object[]{def_1_1, json_1_1});
        
        
        String def_1_2 = "Valid input tasks. Case 2.";
        ObjectNode json_1_2 = new ObjectNode(JsonNodeFactory.instance);
        json_1_2.put("first", 2);
        json_1_2.put("second", 100);
        cases.add(new Object[]{def_1_2, json_1_2});
        
        return cases.iterator();        
    }
    
    @Test(dataProvider = "validInputTasks")
    public void testIsTaskValid(String description, ObjectNode task) {
        System.out.println(description);
        TaskValidation validator = new TaskValidation(task);
        assertTrue(validator.isTaskValid(), description);
    }
    
}
