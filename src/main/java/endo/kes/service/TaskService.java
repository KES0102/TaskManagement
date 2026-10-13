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

        db_Task.put(incrementHashMapKey++, new Task(incrementTaskId++, 1L, 1L, TaskStatus.CREATED, LocalDateTime.now(), LocalDateTime.parse("2026-10-15T08:00") , TaskPriority.Low) );
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
}
