package endo.kes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Modifying
    @Query("""
            UPDATE TaskEntity r
            set r.status=:status
            where r.id = :id
            """)
    void canselInProgressOfDone(
            @Param("id") Long id,
            @Param("status") TaskStatus status);
}