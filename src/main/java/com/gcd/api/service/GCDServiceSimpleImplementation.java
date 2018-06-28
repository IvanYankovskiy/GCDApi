package com.gcd.api.service;

import com.gcd.api.dao.ServiceHibernateDAO;
import com.gcd.api.entities.Task;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("gCDServiceSimpleImplementation")
@Transactional
public class GCDServiceSimpleImplementation extends ServiceHibernateDAO implements GCDService{

    public GCDServiceSimpleImplementation() {
        super();
    }
    
    @PostConstruct()
    private void init(){
        System.out.println("Service with class name " + this.getClass() + " was created");
    }
    
    @PreDestroy()
    private void destroy() {
        System.out.println("Service with class name " + this.getClass() + " is shutting down");
    }
    
    @Override
    public Task addTask(Task task) {
        task.setStatus(TaskStatus.NOT_COMPLETED);
        Session session = getCurrentSession();
        Long id = (Long)session.save(task);
        session.evict(task);
        return task;
    }

    @Override
    public Task getTaskById(long id) {
        Session session = getCurrentSession();
        Task task = (Task)session.get(Task.class, (Long)id);
        if (task == null){
            task = new Task();
            task.setId(id);
            task.setStatus(TaskStatus.ERROR);
            task.setError("Requested task(with id=" + id + ") does not exist");
        }
        return task;
    }
    
    @Override
    public void updateTaskWithRecievedResult(Task result) {
        Session session = getCurrentSession();
        session.update(result);
    }
    
}
