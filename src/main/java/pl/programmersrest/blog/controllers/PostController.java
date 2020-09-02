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
import pl.programmersrest.blog.model.service.PostService;

import java.util.List;

@RestController
@RequestMapping(value = "posts")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PagePost> getPosts(@RequestParam(defaultValue = "0") int page) {
        page = page < 0 ? 0 : page;
        return this.postService.getPosts(page);
    }

    @GetMapping(value = "{id}")
    public Post getSpecificPost(@PathVariable long id) {
        return postService.getSpecificPost(id);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Void> updatePost(@PathVariable long id, @RequestBody UpdatePostRequest updatePostRequest) {
        if (updatePostRequest.getFullDescription() == null || updatePostRequest.getTitle() == null || updatePostRequest.getShortDescription() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        postService.updateSpecificPost(id, updatePostRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteSpecificPost(@PathVariable long id) {
        postService.deletePost(id);
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
        postService.createPost(username, createPostRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}/title")
    public ResponseEntity<Void> updateTitle(@PathVariable Long id, @RequestBody UpdateSpecificFieldRequest title){
        if(title.getContest() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        postService.updateTitle(id,title.getContest());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "{id}/short")
    public ResponseEntity<Void> updateShortDescription(@PathVariable Long id, @RequestBody UpdateSpecificFieldRequest shortDescription){
        if(shortDescription.getContest() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        postService.updateShortDescription(id, shortDescription.getContest());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "{id}/full")
    public ResponseEntity<Void> updateFullDescription(@PathVariable Long id, @RequestBody UpdateSpecificFieldRequest fullDescription){
        if(fullDescription.getContest() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        postService.updateFullDescription(id, fullDescription.getContest());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "{id}/image")
    public ResponseEntity<Void> updateImage(@PathVariable Long id, @RequestBody UpdateSpecificFieldRequest imagePath){
        if(imagePath.getContest() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        postService.updateImagePath(id, imagePath.getContest());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id){
        postService.changePostStatus(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
