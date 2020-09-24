package pl.programmersrest.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.programmersrest.blog.controllers.request.CreateTagRequest;
import pl.programmersrest.blog.model.entity.TagDetails;
import pl.programmersrest.blog.model.service.TagDetailsManager;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")
public class TagController {
    private TagDetailsManager tagManager;

    @Autowired
    public TagController(TagDetailsManager tagManager) {
        this.tagManager = tagManager;
    }
    //ToDo: POST /tags add list of tags
    //ToDo: DELETE /tags delete list of tags

    //ToDo: put /tags/{id} update tag

    @GetMapping
    public List<TagDetails> getAllTags(){
        return tagManager.loadAllTags();
    }
    @PostMapping
    public ResponseEntity<Void> createNewTag(@RequestBody CreateTagRequest createTagRequest){
        tagManager.createNewTag(createTagRequest.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
