package pl.programmersrest.blog.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.programmersrest.blog.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(?1)")
    Optional<User> findUserByUsername(String username);
}
