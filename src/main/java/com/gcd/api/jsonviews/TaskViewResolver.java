package com.gcd.api.jsonviews;

import com.gcd.api.entities.Task;
import com.gcd.api.service.TaskStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

@Component
public class TaskViewResolver {

    public TaskViewResolver() { }
    
    public ResponseEntity<MappingJacksonValue> resolveTaskView(Task task) {
        MappingJacksonValue jacksonValue = new MappingJacksonValue(task);
        switch(task.getStatus()){
            case COMPLETED:
                jacksonValue.setSerializationView(TaskView.PersistedCompleted.class);
                break;
            case NOT_COMPLETED:
                jacksonValue.setSerializationView(TaskView.Persisted.class);
                break;
            case ERROR:
                jacksonValue.setSerializationView(TaskView.Persisted.class);
                return new ResponseEntity<MappingJacksonValue>(jacksonValue, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<MappingJacksonValue>(jacksonValue, HttpStatus.OK);
    }
    
    public ResponseEntity<MappingJacksonValue> resolveNotFoundTaskView(long id) {
        Task task = new Task();
        task.setId(id);
        task.setStatus(TaskStatus.ERROR);
        task.setError("Requested task with id=" + id + " does not exist");
        MappingJacksonValue jacksonValue = new MappingJacksonValue(task);
        jacksonValue.setSerializationView(TaskView.NotFound.class);
        return new ResponseEntity<MappingJacksonValue>(jacksonValue, HttpStatus.NOT_FOUND);
    }
}
