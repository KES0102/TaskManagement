package endo.kes.service;

import endo.kes.Task;
import endo.kes.TaskPriority;
import endo.kes.TaskStatus;
import endo.kes.controller.TaskController;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TaskService {

    private final static Logger log = LoggerFactory.getLogger(TaskService.class);
    public  HashMap<Long, Task> db_Task;
    Long incrementHashMapKey, incrementTaskId;

    public TaskService() {

        db_Task= new HashMap<>();
        incrementHashMapKey=0L;
        incrementTaskId=0L;

        db_Task.put(incrementHashMapKey++, new Task(incrementTaskId++, 1L, 1L, TaskStatus.DONE, LocalDateTime.now(), LocalDateTime.parse("2026-10-15T08:00") , TaskPriority.Low) );
        db_Task.put(incrementHashMapKey++, new Task(incrementTaskId++, 2L, 2L, TaskStatus.CREATED, LocalDateTime.now(), LocalDateTime.parse("2026-10-15T08:00") , TaskPriority.Medium) );
        db_Task.put(incrementHashMapKey++, new Task(incrementTaskId++, 3L, 3L, TaskStatus.CREATED, LocalDateTime.now(), LocalDateTime.parse("2026-10-15T08:00") , TaskPriority.High) );
        db_Task.put(incrementHashMapKey++, new Task(incrementTaskId++, 4L, 4L, TaskStatus.CREATED, LocalDateTime.now(), LocalDateTime.parse("2026-10-15T08:00") , TaskPriority.Low) );

        log.info("End constructor TaskService" );
    }


    public List<Task> getAllTasks() {
        log.info("Start TaskService.getAllTasks()" );
        return new ArrayList<Task>(db_Task.values());
    }


    public Task getTaskById(Long id) {
        log.info("Start TaskService.getTaskById(): id={}", id );
        if(!db_Task.containsKey(id)) {
            throw new EntityNotFoundException("Not found Task by id = "+id);
        }
        return db_Task.get(id);
    }


    public Task createTask(Task taskOfClient) {
        log.info("Start TaskService.createTask(): taskOfClient={}", taskOfClient );
        if( taskOfClient.id()!=null){
            throw new IllegalArgumentException("Id should be empty");
        }
        if( taskOfClient.status()!=null){
            throw new IllegalArgumentException("Status should be empty");
        }

        Task taskForCreate=new Task(
                incrementTaskId++,
                taskOfClient.creatorId(),
                taskOfClient.assignedUserId(),
                TaskStatus.CREATED,
                taskOfClient.createDateTime(),
                taskOfClient.deadlineDate(),
                taskOfClient.priority()
        );
        db_Task.put(incrementHashMapKey++, taskForCreate);

        log.info("End TaskService.createTask(): task={}", taskForCreate );

        return db_Task.get(incrementTaskId);
    }


    public void deleteTaskById(Long id) {
        log.info("Start TaskService.deleteTaskById(): id={}", id );
        if(!db_Task.containsKey(id)) {
            throw new EntityNotFoundException("Not found Task by id = "+id);
        }
        db_Task.remove(id);
        return ;
    }


    public Task updateTaskById(Long id, Task taskOfClient) {
        log.info("Start TaskService.updateTaskById(): id={}", id );
        if( taskOfClient.status()==TaskStatus.DONE){
            throw new IllegalArgumentException("Status cant be Done");
        }
        if(!db_Task.containsKey(id)) {
            throw new IllegalArgumentException("Not found id= "+ id);
        }
        if(taskOfClient.status()!=db_Task.get(id).status()){
            throw new IllegalArgumentException("Status cant be change");
        }
        Task taskForUpdate=new Task(
                taskOfClient.id(),
                taskOfClient.creatorId(),
                taskOfClient.assignedUserId(),
                taskOfClient.status(),
                taskOfClient.createDateTime(),
                taskOfClient.deadlineDate(),
                taskOfClient.priority()
        );
        db_Task.put(id, taskForUpdate);
        log.info("End TaskService.updateTaskById(): id={}", id );
        return db_Task.get(id);
    }


    public Task backStatusDone(Long id) {
        log.info("Start TaskService.backStatusDone(): id={}", id );
        Task taskOld = db_Task.get(id);
        if (taskOld.status() != TaskStatus.DONE) {
            throw new IllegalArgumentException("Task should be Done");
        }
        Task taskForUpdate = new Task(taskOld.id(), taskOld.creatorId(), taskOld.assignedUserId(), TaskStatus.IN_PROGRESS, taskOld.createDateTime(), taskOld.deadlineDate(), taskOld.priority());
        db_Task.put(id, taskForUpdate);
        log.info("End TaskService.backStatusDone(): id={}", id );
        return db_Task.get(id);
    }
}
