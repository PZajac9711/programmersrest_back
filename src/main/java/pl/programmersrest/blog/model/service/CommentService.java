package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.controllers.request.CommentRequest;
import pl.programmersrest.blog.model.enums.AuthorityEnum;

public interface CommentService {
    void addComment(Long id, CommentRequest commentRequest, String username);

    void deleteComment(Long postId, Long commentId, AuthorityEnum authority, String username);

    void updateComment(Long postId, Long commentId, String username, String contents);
}
