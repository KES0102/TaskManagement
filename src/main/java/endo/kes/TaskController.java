package endo.kes;

import endo.kes.service.TaskService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    TaskService service;

    public TaskController(TaskService service) {
        this.service = service;

        System.out.println(service.db_Task);

    }
}
