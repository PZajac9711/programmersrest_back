package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.controllers.request.CreateCommentRequest;
import pl.programmersrest.blog.model.enums.AuthorityEnum;

public interface CommentService {
    void addComment(Long id, CreateCommentRequest createCommentRequest,String username);

    void deleteComment(Long postId, Long commentId, AuthorityEnum authority, String username);
}
