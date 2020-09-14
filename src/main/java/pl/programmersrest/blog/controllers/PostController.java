package pl.programmersrest.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.programmersrest.blog.controllers.request.CreatePostRequest;
import pl.programmersrest.blog.controllers.request.UpdatePostRequest;
import pl.programmersrest.blog.controllers.request.UpdateSpecificFieldRequest;
import pl.programmersrest.blog.controllers.response.PagePost;
import pl.programmersrest.blog.model.entity.Post;
import pl.programmersrest.blog.model.service.PostServiceManager;

import java.util.List;

@RestController
@RequestMapping(value = "posts")
public class PostController {
    private PostServiceManager postServiceManager;

    @Autowired
    public PostController(PostServiceManager postServiceManager) {
        this.postServiceManager = postServiceManager;
    }

    @GetMapping
    public List<PagePost> getPosts(@RequestParam(defaultValue = "0") int page) {
        page = page < 0 ? 0 : page;
        return this.postServiceManager.getPosts(page);
    }

    @GetMapping(value = "{id}")
    public Post getSpecificPost(@PathVariable long id) {
        return postServiceManager.getSpecificPost(id);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Void> updatePost(@PathVariable long id, @RequestBody UpdatePostRequest updatePostRequest) {
        if (updatePostRequest.getFullDescription() == null || updatePostRequest.getTitle() == null || updatePostRequest.getShortDescription() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        postServiceManager.updateSpecificPost(id, updatePostRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteSpecificPost(@PathVariable long id) {
        postServiceManager.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createNewPost(@RequestBody CreatePostRequest createPostRequest){
        if (createPostRequest.getFullDescription() == null
                || createPostRequest.getTitle() == null
                || createPostRequest.getShortDescription() == null
                || createPostRequest.getImaginePath() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        postServiceManager.createPost(username, createPostRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id){
        postServiceManager.changePostStatus(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
