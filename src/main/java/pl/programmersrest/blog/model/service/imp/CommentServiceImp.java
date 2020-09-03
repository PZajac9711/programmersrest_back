package pl.programmersrest.blog.model.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.programmersrest.blog.controllers.request.CommentRequest;
import pl.programmersrest.blog.model.entity.Comment;
import pl.programmersrest.blog.model.entity.Post;
import pl.programmersrest.blog.model.enums.AuthorityEnum;
import pl.programmersrest.blog.model.exceptions.custom.CommentNotFoundException;
import pl.programmersrest.blog.model.exceptions.custom.DeleteCommentException;
import pl.programmersrest.blog.model.exceptions.custom.NoAuthException;
import pl.programmersrest.blog.model.exceptions.custom.PostNotFoundException;
import pl.programmersrest.blog.model.repository.PostRepository;
import pl.programmersrest.blog.model.service.CommentService;

@Service
public class CommentServiceImp implements CommentService {
    private PostRepository postRepository;

    @Autowired
    public CommentServiceImp(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void addComment(Long id, CommentRequest commentRequest, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
        Comment comment = Comment.builder()
                .author(username)
                .createDate(java.time.LocalDateTime.now())
                .description(commentRequest.getContents())
                .postId(id)
                .build();
        post.getCommentList().add(comment);
        postRepository.save(post);
    }

    @Override
    public void deleteComment(Long postId, Long commentId, AuthorityEnum authority, String username) {
        PostAndComment postAndComment = getPostAndComment(postId,commentId);
        Post post = postAndComment.post;
        Comment comment = postAndComment.comment;
        if(authority.equals(AuthorityEnum.ADMIN)){
            post.getCommentList().remove(comment);
            postRepository.save(post);
            return;
        }
        if(!username.equals(comment.getAuthor())){
            throw new NoAuthException("It's not your comment, so you cant delete it");
        }
        post.getCommentList().remove(comment);
        System.out.println(post.getCommentList().size());
        postRepository.save(post);
    }

    @Override
    public void updateComment(Long postId, Long commentId, String username, String contents) {
        PostAndComment postAndComment = getPostAndComment(postId,commentId);
        Post post = postAndComment.post;
        Comment comment = postAndComment.comment;
        if(!username.equals(comment.getAuthor())){
            throw new NoAuthException("It's not your comment, so you can update it");
        }
        comment.setDescription(contents);
        postRepository.save(post);
    }
    private PostAndComment getPostAndComment(long postId, long commentId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
        Comment comment = post.getCommentList()
                .stream()
                .filter(item -> item.getId() == commentId)
                .findAny()
                .orElseThrow(() -> new CommentNotFoundException("Comment with this id is not present"));
        return new PostAndComment(post,comment);
    }
    private class PostAndComment{
        private Post post;
        private Comment comment;

        public PostAndComment(Post post, Comment comment) {
            this.post = post;
            this.comment = comment;
        }
    }
}
