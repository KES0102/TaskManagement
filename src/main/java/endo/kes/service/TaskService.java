package endo.kes.service;

import endo.kes.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final static Logger log = LoggerFactory.getLogger(TaskService.class);
    TaskRepository db_task;

    public TaskService(TaskRepository db_task) {

        this.db_task = db_task;
        log.info("End constructor TaskService" );
    }


    public List<Task> getAllTasks() {
        log.info("Start TaskService.getAllTasks()");

        List<TaskEntity> allEntities = db_task.findAll();
        return allEntities.stream().map( it->
            toTaskOfTaskEntity(it)).toList();

    }


    public Task getTaskById(Long id) {
        log.info("Start TaskService.getTaskById(): id={}", id );

        TaskEntity taskEntity = db_task.findById(id).orElseThrow(()->new EntityNotFoundException("Task not found by id = "+id));

        return toTaskOfTaskEntity(taskEntity);
    }


    public Task createTask(Task taskOfClient) {

        log.info("Start TaskService.createTask(): taskOfClient={}", taskOfClient );

        if( taskOfClient.id()!=null){
            throw new IllegalArgumentException("Id should be empty");
        }
        if( taskOfClient.status()!=null){
            throw new IllegalArgumentException("Status should be empty");
        }

        TaskEntity taskEntity = new TaskEntity(taskOfClient.creatorId(), null, taskOfClient.assignedUserId(), TaskStatus.CREATED, taskOfClient.priority(), taskOfClient.deadlineDate(), taskOfClient.createDateTime(),taskOfClient.doneDateTime());

        return toTaskOfTaskEntity( db_task.save(taskEntity) );
    }


    public void deleteTaskById(Long id) {

        log.info("Start TaskService.deleteTaskById(): id={}", id );

        if(db_task.existsById(id)){
            db_task.deleteById(id);
        }else{
            throw new EntityNotFoundException("Task not found by id = " + id);
        }

    }


    public Task updateTaskById(Long id, Task taskOfClient) {

        log.info("Start TaskService.updateTaskById(): id={}", id );

        if( db_task.findById(id).get().getStatus()==TaskStatus.DONE){
            throw new IllegalArgumentException("Task of status Done cannot be updated ");
        }

        if(!db_task.existsById(id)) {
            throw new IllegalArgumentException("Not found id= "+ id);
        }

        if(taskOfClient.status()!=  db_task.findById(id).get().getStatus() ){
            throw new IllegalArgumentException("Status cant be change");
        }

        Task taskForUpdate=new Task(
                id,
                taskOfClient.creatorId(),
                taskOfClient.assignedUserId(),
                taskOfClient.status(),
                taskOfClient.createDateTime(),
                taskOfClient.deadlineDate(),
                taskOfClient.priority(),
                taskOfClient.doneDateTime()
        );
        db_task.save(toEntityOfTask(taskForUpdate));

        log.info("End TaskService.updateTaskById(): id={}", id );

        return toTaskOfTaskEntity(db_task.findById(id).get() );
    }


    @Transactional
    public void canselInProgressOfDone(Long id) {
        log.info("Start TaskService.backStatusDone(): id={}", id );

        TaskEntity taskOld = db_task.findById(id).orElseThrow(()->new EntityNotFoundException("Task not found by id = "+ id));
        log.info("TaskService.backStatusDone() find entity by id={}", id );

        if (taskOld.getStatus() != TaskStatus.DONE) {
            throw new IllegalArgumentException("Task should be Done");
        }

        db_task.setStatusById(id, TaskStatus.IN_PROGRESS);

        log.info("End TaskService.backStatusDone(): id={}", id );
    }

    @Transactional
    public void taskStart(Long id) {

        TaskEntity entity = db_task.findById(id).orElseThrow(() ->  new EntityNotFoundException("Not found id=" + id));

        if( !(entity.getAssignedUserId()>0) ){
            throw  new IllegalArgumentException("AssignedUserId is not number:" + entity.getAssignedUserId());
        }

        if( entity.getStatus()!=TaskStatus.CREATED ){
            throw  new IllegalStateException("Status should be Create");
        }

        db_task.setStatusById(id, TaskStatus.IN_PROGRESS);

    }

    @Transactional
    public void taskComplete(Long id) {

        TaskEntity entity = db_task.findById(id).orElseThrow(() ->  new EntityNotFoundException("Not found id=" + id));

        if( !(entity.getAssignedUserId()>0) ){
            throw  new IllegalArgumentException("AssignedUserId is not number:" + entity.getAssignedUserId());
        }

        if( entity.getStatus()!=TaskStatus.IN_PROGRESS ){
            throw  new IllegalStateException("Status should be IN_PROGRESS");
        }
        if( entity.getDeadlineDate()==null ){
            throw  new IllegalArgumentException("DeadlineDate should be not null");
        }

        db_task.setStatusById(id, TaskStatus.DONE);
        db_task.setTimeDone(LocalDateTime.now(), id);
    }


    private Task toTaskOfTaskEntity(TaskEntity taskEntity){
        return new Task(
                taskEntity.getId(),
                taskEntity.getCreatorId(),
                taskEntity.getAssignedUserId(),
                taskEntity.getStatus(),
                taskEntity.getCreateDateTime(),
                taskEntity.getDeadlineDate(),
                taskEntity.getPriority(),
                taskEntity.getDoneDateTime()
        );
    }


    private TaskEntity toEntityOfTask(Task task){
        return new TaskEntity(
                task.creatorId(),
                task.id(),
                task.assignedUserId(),
                task.status(),
                task.priority(),
                task.deadlineDate(),
                task.createDateTime(),
                task.doneDateTime()

        );
    }


}
