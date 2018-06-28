package com.gcd.api.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.gcd.api.exceptions.InvalidTaskRequestException;

public class TaskValidation {
    private static final String FIRST_NUM_NAME = "first";
    private static final String SECOND_NUM_NAME = "second";
    private final JsonNode task;

    public TaskValidation(JsonNode task) {
        this.task = task;
    }  
    
    public boolean isTaskValid() {
        boolean first, second;
        first = hasValidNumberField(FIRST_NUM_NAME);
        second = hasValidNumberField(SECOND_NUM_NAME);
        return first & second;
    }
    
    private boolean isFieldIntegerType(String fieldName) {
        final JsonNode fieldNode = task.get(fieldName);
        if (!fieldNode.getNodeType().equals(JsonNodeType.NUMBER))
            return false;
        if (!fieldNode.canConvertToLong())
            return false;    
        return fieldNode.isInt() || fieldNode.isLong();
    }
    
    private boolean isFieldExists(String fieldName){
        return task.has(fieldName);
    }
    
    private boolean isValidFieldValue(final JsonNode longField){
        long number = longField.asLong();
        return number > 0;
    }
    
    private boolean hasValidNumberField(String name) {
        if (!isFieldExists(name))
            throw new InvalidTaskRequestException("Field \"" + name + "\" is not provided");
        if (!isFieldIntegerType(name))
            throw new InvalidTaskRequestException("Field \"" + name + "\" is not integer-type value");
        if (!isValidFieldValue(task.get(name)))
            throw new InvalidTaskRequestException("Field \"" + name + "\" must be strictly greater than 0");
        return true;
    }
}
