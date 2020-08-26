package pl.programmersrest.blog.model.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pl.programmersrest.blog.controllers.request.CreatePostRequest;
import pl.programmersrest.blog.controllers.request.UpdatePostRequest;
import pl.programmersrest.blog.controllers.response.PagePost;
import pl.programmersrest.blog.model.entity.Post;
import pl.programmersrest.blog.model.exceptions.custom.PostNotFoundException;
import pl.programmersrest.blog.model.exceptions.custom.TitleTakenException;
import pl.programmersrest.blog.model.mapper.Mapper;
import pl.programmersrest.blog.model.repository.PostRepository;
import pl.programmersrest.blog.model.service.PostService;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImp implements PostService {
    private final int POST_ON_SINGLE_PAGE = 4;
    private PostRepository postRepository;
    private Mapper<List<Post>, List<PagePost>> postListToPagePostList;

    @Autowired
    public PostServiceImp(PostRepository postRepository, Mapper<List<Post>, List<PagePost>> postListToPagePostList) {
        this.postRepository = postRepository;
        this.postListToPagePostList = postListToPagePostList;
    }


    @Override
    public List<PagePost> getPosts(int page) {
        List<Post> postList = postRepository.findAllPosts(
                PageRequest.of(page, POST_ON_SINGLE_PAGE, Sort.by(Sort.Direction.DESC, "id"))
        );
        return postListToPagePostList.converter(postList);
    }

    @Override
    public Post getSpecificPost(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    @Override
    public void updateSpecificPost(long id, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found"));

        if (post.getTitle().equals(updatePostRequest.getTitle())) {
            throw new TitleTakenException("Title is taken");
        }
        post.setTitle(updatePostRequest.getTitle());
        post.setShortDescription(updatePostRequest.getShortDescription());
        post.setFullDescription(updatePostRequest.getFullDescription());

        postRepository.save(post);
    }

    @Override
    public void deletePost(long id) {
        try{
            postRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex){
            throw new PostNotFoundException("Post Not Found");
        }
    }
}
