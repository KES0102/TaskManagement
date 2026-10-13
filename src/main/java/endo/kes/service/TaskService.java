package endo.kes.service;

import endo.kes.Task;
import endo.kes.TaskPriority;
import endo.kes.TaskStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class TaskService {
    public  HashMap<Long, Task> db_Task;
    Long incrementHashMapKey, incrementTaskId;

    public TaskService() {
        db_Task= new HashMap<>();
        incrementHashMapKey=0L; incrementTaskId=0L;
        db_Task.put(incrementHashMapKey++, new Task(incrementTaskId++, 1L, 1L, TaskStatus.CREATED, LocalDateTime.now(), LocalDateTime.parse("2026-10-15T08:00") , TaskPriority.Low) );
        db_Task.put(incrementHashMapKey++, new Task(incrementTaskId++, 2L, 2L, TaskStatus.CREATED, LocalDateTime.now(), LocalDateTime.parse("2026-10-15T08:00") , TaskPriority.Medium) );
        db_Task.put(incrementHashMapKey++, new Task(incrementTaskId++, 3L, 3L, TaskStatus.CREATED, LocalDateTime.now(), LocalDateTime.parse("2026-10-15T08:00") , TaskPriority.High) );
        db_Task.put(incrementHashMapKey++, new Task(incrementTaskId++, 4L, 4L, TaskStatus.CREATED, LocalDateTime.now(), LocalDateTime.parse("2026-10-15T08:00") , TaskPriority.Low) );
    }
}
