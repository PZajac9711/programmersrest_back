package pl.programmersrest.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.programmersrest.blog.controllers.request.CreateTagRequest;
import pl.programmersrest.blog.controllers.request.UpdateTagNameRequest;
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
        if(createTagRequest.getName() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        tagManager.createNewTag(createTagRequest.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{name}")
    public ResponseEntity<Void> deleteTag(@PathVariable String name){
        tagManager.deleteTag(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "{name}")
    public ResponseEntity<Void> updateTagName(@RequestBody UpdateTagNameRequest nameRequest, @PathVariable(value = "name") String oldName){
        if(nameRequest.getName() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        tagManager.updateTagName(oldName, nameRequest.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
