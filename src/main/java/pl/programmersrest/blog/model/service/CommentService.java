package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.controllers.request.CommentRequest;
import pl.programmersrest.blog.model.enums.AuthorityEnum;
import pl.programmersrest.blog.model.exceptions.custom.CommentNotFoundException;
import pl.programmersrest.blog.model.exceptions.custom.NoAuthException;
import pl.programmersrest.blog.model.exceptions.custom.PostNotFoundException;

/**
 * Provides basic operations for comments
 *
 * @author Patryk
 */
public interface CommentService {
    /**
     * Add new comment to existing post.
     *
     * @param id             Specified post to which comment should be added.
     * @param commentRequest Specified information about new comment.
     * @param username       Specified username which trying to add post
     * @throws PostNotFoundException when post not exists in database.
     */
    void addComment(Long id, CommentRequest commentRequest, String username) throws PostNotFoundException;

    /**
     * Delete existing comment from post.
     *
     * @param postId    Specific post id from which comment should be deleted.
     * @param commentId Specified comment which should we delete.
     * @param authority Determines user authority.
     * @param username  Determines user who are trying to delete comment.
     * @throws PostNotFoundException    when post is not present in database.
     * @throws CommentNotFoundException when comment is not present in database or not exist to specified post.
     * @throws NoAuthException          when user who are not have authority to delete comment trying to do it.
     */
    void deleteComment(Long postId, Long commentId, AuthorityEnum authority, String username) throws PostNotFoundException, CommentNotFoundException, NoAuthException;

    /**
     * Update existing comment.
     *
     * @param postId    Specific post id from which comment should be update.
     * @param commentId Specified comment which should we update.
     * @param username  Determines user who are trying to update comment.
     * @param contents  Determines new contents
     * @throws PostNotFoundException    when post is not present in database.
     * @throws CommentNotFoundException when comment is not present in database or not exist to specified post.
     * @throws NoAuthException          when user who are not have authority to update comment trying to do it.
     */
    void updateComment(Long postId, Long commentId, String username, String contents) throws PostNotFoundException, CommentNotFoundException, NoAuthException;
}
