package pl.programmersrest.blog.model.service.imp;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.InstanceOf;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import pl.programmersrest.blog.controllers.request.UpdatePostRequest;
import pl.programmersrest.blog.controllers.response.PagePost;
import pl.programmersrest.blog.model.entity.Comment;
import pl.programmersrest.blog.model.entity.Post;
import pl.programmersrest.blog.model.exceptions.custom.PostNotFoundException;
import pl.programmersrest.blog.model.exceptions.custom.TitleTakenException;
import pl.programmersrest.blog.model.mapper.Mapper;
import pl.programmersrest.blog.model.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImpTest {
    @InjectMocks
    PostServiceImp postService;
    @Mock
    PostRepository postRepository;
    @Mock
    Mapper<List<Post>, List<PagePost>> postListToPagePostList;

    private List<Post> postList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postList = new ArrayList<>();
        postList.add(
                Post.builder()
                        .title("title1")
                        .createDate(java.time.LocalDateTime.now())
                        .author("admin")
                        .commentList(new ArrayList<Comment>())
                        .imaginePath("path")
                        .fullDescription("full")
                        .shortDescription("short")
                        .id(1L)
                        .lastModified(java.time.LocalDateTime.now())
                        .build()
        );
    }

    @Test
    public void getPostsTest() {
        //given
        List<PagePost> pagePosts = new ArrayList<>();
        pagePosts.add(
                PagePost.builder()
                        .date(java.time.LocalDateTime.now())
                        .title("title1")
                        .author("admin")
                        .id(1L)
                        .imagePath("path")
                        .shortDescription("short")
                        .build()
        );
        int page = 3;
        when(postRepository.findAllPosts(any())).thenReturn(postList);
        when(postListToPagePostList.converter(postList)).thenReturn(pagePosts);
        //when
        List<PagePost> result = postService.getPosts(page);
        //then
        assertEquals("PagePost", result.get(0).getClass().getSimpleName());
        assertEquals(1, result.size());
        assertEquals(postList.get(0).getAuthor(), result.get(0).getAuthor());
        assertEquals(postList.get(0).getId(), result.get(0).getId());
        verify(postRepository, times(1)).findAllPosts(any());
        verify(postListToPagePostList, times(1)).converter(postList);
    }

    @Test
    public void getSpecificPostTest() {
        //given
        long id = 1;
        //when
        when(postRepository.findById(id)).thenReturn(Optional.of(postList.get(0)));
        Post post = postService.getSpecificPost(id);
        //then
        verify(postRepository, times(1)).findById(id);
        assertEquals(post,postList.get(0));
        assertNotNull(post);
    }

    @Test(expected = PostNotFoundException.class)
    public void failGetSpecificPostTestShouldThrowPostNotFoundException(){
        //given
        long id = 1;
        //when
        when(postRepository.findById(id)).thenReturn(Optional.empty());
        Post post = postService.getSpecificPost(id);
        //then
        verify(postRepository, times(1)).findById(id);
        assertNull(post);
    }

    @Test
    public void updateSpecificPost(){
        //given
        ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);
        long id = 1;
        UpdatePostRequest updatePostRequest = new UpdatePostRequest("title","shortDescription","fullDesc");
        //when
        when(postRepository.findById(id)).thenReturn(Optional.of(postList.get(0)));
        postService.updateSpecificPost(id,updatePostRequest);
        //then
        verify(postRepository).save(captor.capture());
        assertEquals(captor.getValue().getTitle(), updatePostRequest.getTitle());
        assertEquals(captor.getValue().getShortDescription(), updatePostRequest.getShortDescription());
        assertEquals(captor.getValue().getFullDescription(), updatePostRequest.getFullDescription());
    }

    @Test(expected = PostNotFoundException.class)
    public void updateSpecificPostTestFailPostNotExist(){
        //given
        long id = 1;
        UpdatePostRequest updatePostRequest = new UpdatePostRequest("title","shortDescription","fullDesc");
        //when
        when(postRepository.findById(id)).thenThrow(new PostNotFoundException("Post not found"));
        postService.updateSpecificPost(id,updatePostRequest);
        //then
        verify(postRepository,times(0)).save(any());
    }

    @Test(expected = TitleTakenException.class)
    public void updateSpecificPostTestFailTitleTakenException(){
        //given
        long id = 1;
        UpdatePostRequest updatePostRequest = new UpdatePostRequest("title1","shortDescription","fullDesc");
        //when
        when(postRepository.findById(id)).thenReturn(Optional.of(postList.get(0)));
        postService.updateSpecificPost(id,updatePostRequest);
        //then
        verify(postRepository,times(0)).save(any());
    }

    @Test
    public void deletePostTest(){
        //given
        long id = 1;
        //when
        postService.deletePost(id);
        //then
        verify(postRepository, times(1)).deleteById(id);
    }

    @Test(expected = PostNotFoundException.class)
    public void deletePostTestFailShouldThrowPostNotFoundException(){
        //given
        long id = 1;
        //when
        doThrow(new EmptyResultDataAccessException(1)).when(postRepository).deleteById(id);
        postService.deletePost(1);
    }
}
