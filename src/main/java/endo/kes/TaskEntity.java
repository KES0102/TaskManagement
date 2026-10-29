package endo.kes;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Table(name = "tasks")
@Entity
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;       // id пользователя, создавшего задачу

    @Column(name = "assigned_user_id", nullable = false)
    private Long assignedUserId;  // id исполнителя

    @Column(name = "create_date_time", nullable = false)
    private LocalDateTime createDateTime;


    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;


    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;


    @Column(name = "dead_line_date", nullable = false)
    private LocalDateTime deadLineDate;



    @Column(name = "done_date_time")
    private LocalDateTime doneDateTime;


    public TaskEntity() {
    }

    public TaskEntity(Long creatorId, Long id, Long assignedUserId, TaskStatus status, TaskPriority priority, LocalDateTime deadlineDate, LocalDateTime createDateTime, LocalDateTime doneDateTime) {
        this.id = id;
        this.creatorId = creatorId;
        this.assignedUserId = assignedUserId;
        this.createDateTime = createDateTime;
        this.status = status;
        this.priority = priority;
        this.deadLineDate = deadlineDate;
        this.doneDateTime = doneDateTime;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public LocalDateTime getDeadlineDate() {
        return deadLineDate;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDoneDateTime() {
        return doneDateTime;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public void setDeadlineDate(LocalDateTime deadlineDate) {
        this.deadLineDate = deadlineDate;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}
