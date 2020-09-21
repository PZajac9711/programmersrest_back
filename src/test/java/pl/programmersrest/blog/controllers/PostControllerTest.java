package pl.programmersrest.blog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.programmersrest.blog.controllers.request.UpdatePostRequest;
import pl.programmersrest.blog.controllers.response.PagePost;
import pl.programmersrest.blog.model.entity.Comment;
import pl.programmersrest.blog.model.entity.Post;
import pl.programmersrest.blog.model.exceptions.custom.PostNotFoundException;
import pl.programmersrest.blog.model.service.PostServiceManager;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    //ToDo: refactor after adding authentication !!!
    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    PostServiceManager postService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    private List<PagePost> pagePostList;
    private Post post;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        post = Post.builder()
                .title("title1")
                .createDate(java.time.LocalDateTime.now())
                .author("admin")
                .commentList(new ArrayList<Comment>())
                .imaginePath("path")
                .fullDescription("full")
                .shortDescription("short")
                .id(1L)
                .lastModified(java.time.LocalDateTime.now())
                .build();

        //********************************************************//

        pagePostList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            pagePostList.add(
                    PagePost.builder()
                            .shortDescription("short" + i)
                            .imagePath("path" + i)
                            .id((long) i)
                            .author("author" + i)
                            .title("title" + i)
                            .date(java.time.LocalDateTime.now())
                            .build()
            );
        }
    }

    @Test
    public void getPostsTest() throws Exception {
        //given
        String page = "2";
        //when
        when(postService.getPosts(Integer.parseInt(page))).thenReturn(pagePostList);
        mockMvc.perform(get("/posts")
                .param("page", page))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(4)));
        //then
        verify(postService, times(1)).getPosts((Integer.parseInt(page)));
    }

    @Test
    public void getSpecificPostTest() throws Exception {
        //given
        long id = 1;
        //when
        when(postService.getSpecificPost(id)).thenReturn(post);
        mockMvc.perform(get("/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.author").value(post.getAuthor()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.id").value(post.getId()));
        //then
        verify(postService, times(1)).getSpecificPost(id);
    }

    @Test
    public void failGetSpecificPostTest() throws Exception {
        //given
        long id = 1;
        //when
        when(postService.getSpecificPost(id)).thenThrow(new PostNotFoundException("Post not found"));
        mockMvc.perform(get("/posts/" + id))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.message").value("There's no post with this id"))
                .andExpect(jsonPath("$.debugMessage").value("Post not found"));
        //then
        verify(postService, times(1)).getSpecificPost(id);
    }

    @Test
    public void updatePostTest() throws Exception {
        //given
        long id = 1;
        UpdatePostRequest updatePostRequest = new UpdatePostRequest("title", "short", "full");
        //when
        doNothing().when(postService).updateSpecificPost(id, updatePostRequest);
        mockMvc.perform(put("/posts/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        //then
        verify(postService, times(1)).updateSpecificPost(id, updatePostRequest);
    }

    @Test
    public void updatePostTestEmptyTitleShouldReturnStatusCodeBadRequest() throws Exception{
        //given
        long id = 1;
        UpdatePostRequest updatePostRequest = new UpdatePostRequest(null, "short", "full");
        //when
        doNothing().when(postService).updateSpecificPost(id, updatePostRequest);
        mockMvc.perform(put("/posts/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
        //then
        verify(postService, times(0)).updateSpecificPost(id,updatePostRequest);
    }

    @Test
    public void deleteSpecificPostTest() throws Exception {
        //given
        long id = 1;
        //when
        doNothing().when(postService).deletePost(id);
        mockMvc.perform(delete("/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        //then
        verify(postService, times(1)).deletePost(id);
    }

    @Test
    public void deleteSpecificPostTestFailShouldThrowPostNotFoundException() throws Exception {
        //given
        long id = 1;
        doThrow(new PostNotFoundException("Post not found")).when(postService).deletePost(id);
        mockMvc.perform(delete("/posts/" + id))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.message").value("There's no post with this id"))
                .andExpect(jsonPath("$.debugMessage").value("Post not found"));
        //then
        verify(postService, times(1)).deletePost(id);
    }
}
