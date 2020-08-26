package pl.programmersrest.blog.model.mapper.imp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.programmersrest.blog.controllers.response.PagePost;
import pl.programmersrest.blog.model.entity.Comment;
import pl.programmersrest.blog.model.entity.Post;
import pl.programmersrest.blog.model.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostListToPagePostListTest {
    private Mapper<List<Post>, List<PagePost>> postListToPagePostList = new PostListToPagePostList();
    private List<Post> postList;


    @BeforeEach
    public void setUp() {
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
        postList.add(
                Post.builder()
                        .title("title2")
                        .createDate(java.time.LocalDateTime.now())
                        .author("admin2")
                        .commentList(new ArrayList<Comment>())
                        .imaginePath("path")
                        .fullDescription("full")
                        .shortDescription("short")
                        .id(2L)
                        .lastModified(java.time.LocalDateTime.now())
                        .build()
        );
    }

    @Test
    public void converterTest() {
        //when
        List<PagePost> pagePostList = postListToPagePostList.converter(postList);
        //then
        assertEquals(pagePostList.size(), postList.size());
        IntStream.range(0,pagePostList.size())
                .forEach(index -> {
                    assertEquals(pagePostList.get(index).getAuthor(), postList.get(index).getAuthor());
                    assertEquals(pagePostList.get(index).getImagePath(), postList.get(index).getImaginePath());
                    assertEquals(pagePostList.get(index).getId(), postList.get(index).getId());
                    assertEquals(pagePostList.get(index).getTitle(), postList.get(index).getTitle());
                    assertEquals(pagePostList.get(index).getDate(), postList.get(index).getCreateDate());
                    assertEquals(pagePostList.get(index).getShortDescription(), postList.get(index).getShortDescription());
                });
    }

}
