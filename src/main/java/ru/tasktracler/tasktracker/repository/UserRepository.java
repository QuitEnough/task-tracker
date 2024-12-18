package ru.tasktracler.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tasktracler.tasktracker.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = """
            SELECT exists(
                           SELECT 1
                           FROM users_tasks
                           WHERE user_id = :userId
                             AND task_id = :taskId)
            """, nativeQuery = true)
    Boolean isTaskOwner(@Param("userId") Long userId,
                        @Param("taskId") Long taskId);

    @Query(value = """
            SELECT u.id as id,
            u.name as name,
            u.email as email,
            u.password as password
            FROM users_tasks ut
            JOIN users u ON ut.user_id = u.id
            WHERE ut.task_id = :taskId
            """, nativeQuery = true)
    Optional<User> findTaskAuthor(@Param("taskId") Long taskId);

    @Modifying
    @Query("update User u set u.name = ?1, u.email = ?2, u.password = ?3 where u.id = ?4")
    void updateUserById(String userName, String email, String password, Long userId);

}
