package com.gcd.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gcd.api.service.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcd.api.jsonviews.TaskView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue
    @JsonView(TaskView.SuccesfulAdded.class)
    private long id;
    
    @Column(name = "first", nullable = false, updatable = false)
    private long first;
    
    @Column(name = "second", nullable = false, updatable = false)
    private long second;
    
    @Column(name = "status", nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    
    @Column(name = "result", updatable = true)
    private long result;
    
    @Column(name = "error")
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
    @JsonIgnore
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
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, "Serializing of object as string was failed", ex);
        }
        return "";
    }

    @JsonProperty(required = true)
    public void setFirst(long first) {
        this.first = first;
    }

    @JsonProperty(required = true)
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
    
    
}
