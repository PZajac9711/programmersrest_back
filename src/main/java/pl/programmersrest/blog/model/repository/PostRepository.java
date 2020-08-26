package pl.programmersrest.blog.model.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.programmersrest.blog.model.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("SELECT p FROM Post p")
    List<Post> findAllPosts(Pageable pageable);

    Optional<Post> findByTitle(String title);
}
