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
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @PostMapping("/tasks")
    public ResponseEntity createTask(@RequestBody Task task) {
        log.info("Start TaskController.createTask()");

        try {
            service.createTask(task);
            return ResponseEntity.status(201).body("Task created");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long id) {
        log.info("Start TaskController.deleteTaskById(): id={}", id);
        try {
            service.deleteTaskById(id);
            return ResponseEntity.status(200).body("Task deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTaskById(@PathVariable Long id, @RequestBody Task task) {
        log.info("Start TaskController.updateTaskById(): id={}", id);

        try {
            Task taskUpdate = service.updateTaskById(id, task);
            return ResponseEntity.status(200).body(taskUpdate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).header("MyError", e.getMessage()).build();
        }
    }

    @PatchMapping("/task/backInProgress/{id}")
    public ResponseEntity<Task> backStatusDone(@PathVariable Long id) {
        log.info("Start TaskController.backStatusDone(): id={}", id);

        try {
            Task taskUpdate = service.backStatusDone(id);
            return ResponseEntity.status(200).body(taskUpdate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).header("MyError", e.getMessage()).build();
        }
    }
}
