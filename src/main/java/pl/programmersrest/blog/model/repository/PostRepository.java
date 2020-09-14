package pl.programmersrest.blog.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.programmersrest.blog.model.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("SELECT p FROM Post p WHERE p.active = true")
    List<Post> findAllPosts(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE LOWER(p.title) = LOWER(?1) ")
    Optional<Post> findByTitle(String title);
}
