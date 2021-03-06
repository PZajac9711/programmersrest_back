package pl.programmersrest.blog.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.programmersrest.blog.model.entity.TagDetails;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagDetails, Long> {
    Optional<TagDetails> findTagByName(String name);

    @Query("SELECT u FROM TagDetails u, Tag z WHERE u.id = z.tagId AND z.postId = ?1")
    List<TagDetails> findByPostId(long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tag(tag_id, post_id) VALUES ( ?1, ?2 );" ,nativeQuery = true)
    void assignTagToPost(long tagId, long postId);


}
