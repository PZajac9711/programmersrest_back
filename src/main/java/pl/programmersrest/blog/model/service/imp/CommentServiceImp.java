package pl.programmersrest.blog.model.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.programmersrest.blog.controllers.request.CreateCommentRequest;
import pl.programmersrest.blog.model.entity.Comment;
import pl.programmersrest.blog.model.entity.Post;
import pl.programmersrest.blog.model.enums.AuthorityEnum;
import pl.programmersrest.blog.model.exceptions.custom.CommentNotFoundException;
import pl.programmersrest.blog.model.exceptions.custom.DeleteCommentException;
import pl.programmersrest.blog.model.exceptions.custom.PostNotFoundException;
import pl.programmersrest.blog.model.repository.PostRepository;
import pl.programmersrest.blog.model.service.CommentService;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {
    private PostRepository postRepository;

    @Autowired
    public CommentServiceImp(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void addComment(Long id, CreateCommentRequest createCommentRequest, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
        Comment comment = Comment.builder()
                .author(username)
                .createDate(java.time.LocalDateTime.now())
                .description(createCommentRequest.getContest())
                .postId(id)
                .build();
        post.getCommentList().add(comment);
        postRepository.save(post);
    }

    @Override
    public void deleteComment(Long postId, Long commentId, AuthorityEnum authority, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
        Comment comment = post.getCommentList()
                .stream()
                .filter(item -> item.getId() == commentId)
                .findAny()
                .orElseThrow(() -> new CommentNotFoundException("Comment with this id is not present"));

        System.out.println("a: " + post.getCommentList().size());
        if(authority.equals(AuthorityEnum.ADMIN)){
            post.getCommentList().remove(comment);
            System.out.println(post.getCommentList().size());
            postRepository.save(post);
            return;
        }
        if(!username.equals(comment.getAuthor())){
            throw new DeleteCommentException("It's not your comment, so you cant delete id");
        }
        post.getCommentList().remove(comment);
        System.out.println(post.getCommentList().size());
        postRepository.save(post);
    }
}
