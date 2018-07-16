package com.gcd.api.service;

import com.gcd.api.entities.Task;
import java.util.Optional;

/**
 *
 * @author Ivan
 */
public interface GCDService {
    Task addTask(Task task);
    Optional<Task> getTaskById(long id);
    void updateTaskWithRecievedResult(Task result);
    /**
     * Save task 
     * @param task
     * @return 
     *//*
    long saveTask(Task task);
    long saveResult(Task task);
    long calculateResult(Task task);
    Task readResult(long id);*/
}
