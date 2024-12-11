package ru.tasktracler.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tasktracler.tasktracker.domain.entity.Task;

import java.sql.Timestamp;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findTaskByUserId(Long userId);

//    @Query(value = """
//            SELECT * FROM task t
//            WHERE t.expiration_date is not null
//            AND t.expiration_date between :start and :end
//            """, nativeQuery = true)
    List<Task> findAllSoonTasks(@Param("start") Timestamp start,
                                @Param("end") Timestamp end);

}
