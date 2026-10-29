package endo.kes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Modifying
    @Query("""
            UPDATE TaskEntity r
            set r.status=:status
            where r.id = :id
            """)
    void setStatusById(
            @Param("id") Long id,
            @Param("status") TaskStatus status);


    @Modifying
    @Query("""
            UPDATE TaskEntity r
            set r.doneDateTime=:time
            where r.id = :id
    """)
    void setTimeDone(
            @Param("time") LocalDateTime time,
            @Param("id")Long id
    );
}