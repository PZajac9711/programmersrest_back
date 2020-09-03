package pl.programmersrest.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.programmersrest.blog.controllers.request.CommentRequest;
import pl.programmersrest.blog.model.enums.AuthorityEnum;
import pl.programmersrest.blog.model.service.CommentService;

@RestController
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/posts/{id}/comments")
    public ResponseEntity<Void> addComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest){
        if(commentRequest.getContents() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.addComment(id, commentRequest,username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/posts/{id}/comments/{comment-id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id, @PathVariable(name = "comment-id") long commentId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthorityEnum authority = AuthorityEnum.valueOf(authentication.getAuthorities().iterator().next().getAuthority());
        commentService.deleteComment(id,commentId,authority,authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping(value = "/posts/{id}/comments/{comment-id}")
    public ResponseEntity<Void> updateComment(@PathVariable long id, @PathVariable(name = "comment-id") long commentId,@RequestBody CommentRequest commentRequest){
        if(commentRequest.getContents() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        commentService.updateComment(id,commentId,username,commentRequest.getContents());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
