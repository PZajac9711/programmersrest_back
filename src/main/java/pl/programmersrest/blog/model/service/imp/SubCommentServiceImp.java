package pl.programmersrest.blog.model.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.programmersrest.blog.model.entity.Comment;
import pl.programmersrest.blog.model.entity.SubComment;
import pl.programmersrest.blog.model.enums.AuthorityEnum;
import pl.programmersrest.blog.model.exceptions.custom.CommentNotFoundException;
import pl.programmersrest.blog.model.exceptions.custom.NoAuthException;
import pl.programmersrest.blog.model.repository.CommentRepository;
import pl.programmersrest.blog.model.service.SubCommentService;

import static pl.programmersrest.blog.model.enums.AuthorityEnum.ADMIN;

@Service
public class SubCommentServiceImp implements SubCommentService {
    private CommentRepository commentRepository;

    @Autowired
    public SubCommentServiceImp(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addNewSubComment(long postId, long commentId, String username, String contents) {
        Comment comment = getCommentFromDatabase(postId, commentId);
        SubComment subComment = SubComment.builder()
                .commentId(commentId)
                .author(username)
                .createDate(java.time.LocalDateTime.now())
                .description(contents)
                .build();
        comment.getSubCommentList().add(subComment);
        commentRepository.save(comment);
    }

    @Override
    public void deleteSubComment(long postId, long commentId, long subCommentId, String username, AuthorityEnum authority) {
        Comment comment = getCommentFromDatabase(postId, commentId);
        SubComment subComment = getSubCommentFromComment(comment, subCommentId);
        if (authority.equals(ADMIN)) {
            comment.getSubCommentList().remove(subComment);
            commentRepository.save(comment);
            return;
        }
        if(!username.equals(subComment.getAuthor())){
            throw new NoAuthException("It's not your comment, you cant delete it");
        }
        comment.getSubCommentList().remove(subComment);
        commentRepository.save(comment);
    }

    @Override
    public void updateSubComment(long postId, long commentId, long subCommentId, String username, String contest) {
        Comment comment = getCommentFromDatabase(postId,commentId);
        SubComment subComment = getSubCommentFromComment(comment, subCommentId);
        if(!subComment.getAuthor().equals(username)){
            throw new NoAuthException("It's not your comment, you cant update it");
        }
        subComment.setDescription(contest);
        commentRepository.save(comment);
    }

    private Comment getCommentFromDatabase(long postId, long commentId) {
        return commentRepository.findCommentByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment with this id not exist or not belong to this post"));
    }

    private SubComment getSubCommentFromComment(Comment comment, long subCommentId) {
        return comment.getSubCommentList()
                .stream()
                .filter(sub -> sub.getId() == subCommentId)
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException("Can't find sub comment wit this id"));
    }
}
