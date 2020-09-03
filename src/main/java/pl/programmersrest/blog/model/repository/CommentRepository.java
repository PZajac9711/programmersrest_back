package pl.programmersrest.blog.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.programmersrest.blog.model.entity.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findCommentByPostIdAndId(Long postId, Long id);
}
