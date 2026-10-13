package endo.kes;

import java.time.LocalDateTime;

public record Task(
    Long id,
    Long creatorId,       // id пользователя, создавшего задачу
    Long assignedUserId,  // id исполнителя
    TaskStatus status,
    LocalDateTime createDateTime,
    LocalDateTime deadlineDate ,
    TaskPriority priority

) {
}
