package endo.kes.controller;

import endo.kes.Task;
import endo.kes.TaskEntity;
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
            Task createtask = service.createTask(task);
            return ResponseEntity.status(201).body(createtask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @DeleteMapping("/tasks/{id}")
    public ResponseEntity deleteTaskById(@PathVariable Long id) {
        log.info("Start TaskController.deleteTaskById(): id={}", id);

        try {
            service.deleteTaskById(id);
            return ResponseEntity.status(200).body("Deleted Task successfully");
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
    public ResponseEntity<Void> backStatusDone(@PathVariable Long id) {
        log.info("Start TaskController.backStatusDone(): id={}", id);

        try {
            service.canselInProgressOfDone(id);
            return ResponseEntity.status(200).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).header("MyError", e.getMessage()).build();
        }
    }


    @PostMapping("tasks/{id}/start")
    public ResponseEntity<Void> taskStart(@PathVariable Long id){
        log.info("Start TaskController.taskStart(): id={}", id);

        try {
            service.taskStart(id);
            log.info("Start TaskController.taskStart(): try");
            return ResponseEntity.status(200).build();
        } catch (EntityNotFoundException e) {
            log.info("Start TaskController.taskStart(): catch_1");
            return  ResponseEntity.status(404).header("myHeader", e.getMessage()).build();
        }
        catch (IllegalStateException e){
            log.info("Start TaskController.taskStart(): catch_2");
            return  ResponseEntity.status(404).header("myHeader", e.getMessage()).build();
        }


    }
}
