package pl.programmersrest.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.programmersrest.blog.controllers.request.CreatePostRequest;
import pl.programmersrest.blog.controllers.request.UpdatePostRequest;
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
        if (createPostRequest.getFullDescription() == null || createPostRequest.getTitle() == null || createPostRequest.getShortDescription() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //ToDo: zaimplementowac dopiero jak bedzie juz logowanie i uwierzytelnianie z tokenem
        //ToDo: dlatego ,ze author bedzie zczytywany z claimow
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
