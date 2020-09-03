package pl.programmersrest.blog.model.service;

import pl.programmersrest.blog.model.enums.AuthorityEnum;

public interface SubCommentService {
    void addNewSubComment(long postId, long commentId, String username, String contents);

    void deleteSubComment(long postId, long commentId, long subCommentId, String username, AuthorityEnum authority);

    void updateSubComment(long postId, long commentId, long subCommentId, String username, String contest);
}
