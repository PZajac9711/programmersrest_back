package pl.programmersrest.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.programmersrest.blog.controllers.request.CommentRequest;
import pl.programmersrest.blog.model.enums.AuthorityEnum;
import pl.programmersrest.blog.model.service.SubCommentService;

@RestController
public class SubCommentsController {
    private SubCommentService subCommentService;

    @Autowired
    public SubCommentsController(SubCommentService subCommentService) {
        this.subCommentService = subCommentService;
    }

    @PostMapping(value = "/posts/{post-id}/comments/{comment-id}/sub-comments")
    public ResponseEntity<Void> addNewSubComment(@PathVariable(name = "post-id") long postId,
                                                 @PathVariable(name = "comment-id") long commentId,
                                                 @RequestBody CommentRequest commentRequest){
        if(commentRequest.getContents() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String contents = commentRequest.getContents();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        subCommentService.addNewSubComment(postId,commentId,username,contents);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/posts/{post-id}/comments/{comment-id}/sub-comments/{sub-id}")
    public ResponseEntity<Void> deleteSubComment(@PathVariable(name = "post-id") long postId,
                                                 @PathVariable(name = "comment-id") long commentId,
                                                 @PathVariable(name = "sub-id") long subCommentId){
        String authority = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        subCommentService.deleteSubComment(postId,commentId,subCommentId,username,AuthorityEnum.valueOf(authority));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/posts/{post-id}/comments/{comment-id}/sub-comments/{sub-id}")
    public ResponseEntity<Void> updateSubComment(@PathVariable(name = "post-id") long postId,
                                                 @PathVariable(name = "comment-id") long commentId,
                                                 @PathVariable(name = "sub-id") long subId,
                                                 @RequestBody CommentRequest commentRequest){
        if(commentRequest.getContents() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        subCommentService.updateSubComment(postId,commentId,subId,username,commentRequest.getContents());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
