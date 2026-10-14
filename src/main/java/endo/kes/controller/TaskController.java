package endo.kes.controller;

import endo.kes.Task;
import endo.kes.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    TaskService service;


    public TaskController(TaskService service) {
        this.service = service;

        System.out.println(service.db_Task);

    }


    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        log.info("Start TaskController.getAllTasks()");
        return ResponseEntity.status(200).body(service.getAllTasks());
    }


    @GetMapping("/task/{id}")
    public ResponseEntity getTaskById(@PathVariable Long id) {

        log.info("Start TaskController.getTaskById(): id={}", id);
        try {
            return ResponseEntity.status(200).body(service.getTaskById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @PostMapping("/tasks")
    public ResponseEntity createTask( @RequestBody Task task){
        log.info("Start TaskController.createTask()");

        try {
            return  ResponseEntity.status(200).body( service.createTask(task) );
        } catch (IllegalArgumentException e) {
           return ResponseEntity.status(403).body(e.getMessage());
        }
    }

}
