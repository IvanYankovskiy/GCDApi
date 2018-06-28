package com.gcd.api.service;

import com.gcd.api.dao.TaskRepository;
import com.gcd.api.entities.Task;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("gCDServiceJpaImplementation")
@Transactional
public class GCDServiceJpaImplementation implements GCDService {
    private TaskRepository repository;

    @Autowired
    public void setRepository(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task addTask(Task task) {
        task.setStatus(TaskStatus.NOT_COMPLETED);
        task = repository.saveAndFlush(task);
        return task;
    }

    @Override
    public Task getTaskById(long id) {
        Task task;
        Optional<Task> taskOpt = repository.findById(id);
        if (!taskOpt.isPresent()){
            task = new Task();
            task.setId(id);
            task.setStatus(TaskStatus.ERROR);
            task.setError("Requested task(with id=" + id + ") does not exist");
        } else {
            task = taskOpt.get();
        }
        return task;
    }

    @Override
    public void updateTaskWithRecievedResult(Task result) {
        repository.save(result);
    }
    
    
    
}
