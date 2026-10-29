package endo.kes;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record Task(

        @Null
        Long id,

        @NotNull
        Long creatorId,

        @NotNull
        Long assignedUserId,


        TaskStatus status,

        @NotNull
        LocalDateTime createDateTime,

        @NotNull
        @Future
        LocalDateTime deadlineDate,


        @NotNull
        TaskPriority priority,

        LocalDateTime doneDateTime

) {
}
