package com.gcd.api.entities;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcd.api.jsonviews.TaskView;
import com.gcd.api.service.TaskStatus;
import java.io.Serializable;
import java.util.logging.Level;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import org.slf4j.Logger;

@Entity
@Table(name = "Task")
public class Task implements Serializable {
    @Id
    @GeneratedValue
    @JsonView(value = {TaskView.SuccesfulAdded.class, TaskView.Base.class})
    private long id;
    
    @Column(name = "first", nullable = false, updatable = false)
    @Positive(message = "Provide correct positive, non-zero field with name \"first\"", groups = {NewTask.class})
    @JsonView(value = {TaskView.Persisted.class})
    private long first;
    
    @Column(name = "second", nullable = false, updatable = false)
    @Positive(message = "Provide correct positive, non-zero field with name \"second\"", groups = {NewTask.class})
    @JsonView(value = {TaskView.Persisted.class})
    private long second;
    
    @Column(name = "status", nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    @JsonView(value = {TaskView.Base.class})
    private TaskStatus status;
    
    @Column(name = "result", updatable = true)
    @PositiveOrZero(message = "\"result\" must be strictly greater than 0" )
    @JsonView(value = {TaskView.PersistedCompleted.class})
    private long result;
    
    @Column(name = "error")
    @JsonView(value = {TaskView.PersistedError.class, TaskView.NotFound.class})
    private String error;

    public Task() {
        super();
        this.status = TaskStatus.NOT_COMPLETED;
        this.error = "";
    }
    
    public Task(long first, long second) {
        this.first = first;
        this.second = second;
        this.status = TaskStatus.NOT_COMPLETED;
        this.result = 0L;
        this.error = "";
    }
    
    public long getFirst() {
        return first;
    }

    public long getSecond() {
        return second;
    }
    
    public long getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public long getResult() {
        return result;
    }
    
    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(Task.class.getName()).log(Level.SEVERE, "Serializing of object as string was failed", ex);
        }
        return "";
    }

    public void setFirst(long first) {
        this.first = first;
    }

    public void setSecond(long second) {
        this.second = second;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setResult(long result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    /**
     * Marker interface for new Task validation
     */
    public interface NewTask {}
}
