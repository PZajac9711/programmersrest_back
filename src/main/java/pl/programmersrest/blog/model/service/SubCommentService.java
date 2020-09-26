package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.model.enums.AuthorityEnum;
import pl.programmersrest.blog.model.exceptions.custom.CommentNotFoundException;
import pl.programmersrest.blog.model.exceptions.custom.NoAuthException;

/**
 * Provides basic operation on sub comments.
 *
 * @author Patryk
 */
public interface SubCommentService {
    /**
     * Add new sub comment to existing comment.
     *
     * @param postId    Specified post which comment belong.
     * @param commentId Specified comment which to we are trying add sub comment
     * @param username  Specified username that are adding new sub comment
     * @param contents  Specified contents
     * @throws CommentNotFoundException when comment which id not exists or it not belong to specified post.
     */
    void addNewSubComment(long postId, long commentId, String username, String contents) throws CommentNotFoundException;

    /**
     * Delete existing sub comment. note that user with ADMIN authority should can delete all comments
     *
     * @param postId       Specified post which comment belong.
     * @param commentId    Specified comment which from we are trying delete sub comment.
     * @param subCommentId Specified sub comment which are we trying delete
     * @param username     Specified username which are trying delete sub comment
     * @param authority    Specified user authority
     * @throws CommentNotFoundException when comment or sub comment are not exists.
     * @throws NoAuthException          when user which sub comment not belong try to delete it
     * @see AuthorityEnum for more clarification about Authority.
     */
    void deleteSubComment(long postId, long commentId, long subCommentId, String username, AuthorityEnum authority) throws CommentNotFoundException, NoAuthException;

    /**
     * Update existing sub comment. note that even user with ADMIN authority should not be able to update other comments
     *
     * @param postId       Specified post which comment belong.
     * @param commentId    Specified comment which to we are trying update sub comment
     * @param subCommentId Specified sub comment which are we trying update
     * @param username     Specified username which are trying update sub comment
     * @param contest      Specified contest
     * @throws CommentNotFoundException when comment or sub comment are not exists.
     * @throws NoAuthException          when user which sub comment not belong try to update it
     */
    void updateSubComment(long postId, long commentId, long subCommentId, String username, String contest) throws CommentNotFoundException, NoAuthException;
}
