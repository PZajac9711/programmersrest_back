package pl.programmersrest.blog.model.service.imp;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.programmersrest.blog.controllers.request.CommentRequest;
import pl.programmersrest.blog.model.entity.Comment;
import pl.programmersrest.blog.model.entity.Post;
import pl.programmersrest.blog.model.entity.SubComment;
import pl.programmersrest.blog.model.enums.AuthorityEnum;
import pl.programmersrest.blog.model.exceptions.custom.CommentNotFoundException;
import pl.programmersrest.blog.model.exceptions.custom.NoAuthException;
import pl.programmersrest.blog.model.exceptions.custom.PostNotFoundException;
import pl.programmersrest.blog.model.repository.PostRepository;
import pl.programmersrest.blog.model.service.CommentService;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImpTest {
    @InjectMocks
    CommentServiceImp commentService;

    @Mock
    PostRepository postRepository;

    Post post;

    @Before
    public void setUp() {
        post = Post.builder()
                .imaginePath("path")
                .createDate(java.time.LocalDateTime.now())
                .author("admin")
                .fullDescription("full")
                .shortDescription("short")
                .title("title")
                .commentList(new ArrayList<Comment>())
                .id(1L)
                .active(true)
                .build();
        post.getCommentList().add(
                Comment.builder()
                        .createDate(java.time.LocalDateTime.now())
                        .author("admin")
                        .description("desc")
                        .postId(1L)
                        .subCommentList(new ArrayList<SubComment>())
                        .id(1L)
                        .build()
        );
    }

    @Test
    public void addCommentTestShouldBeSuccessfully() {
        //given
        long id = 1;
        CommentRequest commentRequest = new CommentRequest("comment");
        String username = "admin";
        int commentNumberBeforeAddComment = post.getCommentList().size();
        //when
        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        commentService.addComment(id, commentRequest, username);
        //then
        assertEquals(post.getCommentList().size(), commentNumberBeforeAddComment + 1);
        Optional<Comment> comment = post.getCommentList().stream()
                .filter(item -> item.getAuthor().equals(username))
                .filter(item -> item.getDescription().equals(commentRequest.getContents()))
                .findFirst();
        assertTrue(comment.isPresent());
    }

    @Test(expected = PostNotFoundException.class)
    public void addCommentTestShouldThrowPostNotFoundException() {
        //given
        long id = 1;
        CommentRequest commentRequest = new CommentRequest("comment");
        String username = "admin";
        //when
        when(postRepository.findById(id)).thenReturn(Optional.empty());
        commentService.addComment(id, commentRequest, username);
        //then
        verify(postRepository, times(0)).save(any());
    }

    @Test
    public void deleteCommentByAdminTestShouldBeSuccessfully() {
        //given
        AuthorityEnum auth = AuthorityEnum.valueOf("ADMIN");
        long postId = 1;
        long commentId = 1;
        int commentListSizeBeforeDelete = post.getCommentList().size();
        //when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        commentService.deleteComment(postId, commentId, auth, "aDmIn");
        //then
        verify(postRepository, times(1)).save(post);
        assertEquals(commentListSizeBeforeDelete - 1, post.getCommentList().size());
    }

    @Test
    public void deleteCommentByNormalUserTestShouldBeSuccessfully() {
        //given
        AuthorityEnum auth = AuthorityEnum.valueOf("USER");
        long postId = 1;
        long commentId = 1;
        int commentListSizeBeforeDelete = post.getCommentList().size();
        //when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        commentService.deleteComment(postId, commentId, auth, "adMiN");
        //then
        verify(postRepository, times(1)).save(post);
        assertEquals(commentListSizeBeforeDelete - 1, post.getCommentList().size());
    }

    @Test(expected = NoAuthException.class)
    public void deleteCommentByNormalUserShouldThrowNoAuthException() {
        //given
        AuthorityEnum auth = AuthorityEnum.valueOf("USER");
        long postId = 1;
        long commentId = 1;
        int commentListSizeBeforeDelete = post.getCommentList().size();
        //when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        commentService.deleteComment(postId, commentId, auth, "user");
        //then
        verify(postRepository, times(0)).save(post);
        assertEquals(commentListSizeBeforeDelete, post.getCommentList().size());
    }

    @Test(expected = PostNotFoundException.class)
    public void deleteCommentByNormalUserShouldThrowPostNotFound() {
        //given
        AuthorityEnum auth = AuthorityEnum.valueOf("USER");
        long postId = 1;
        long commentId = 1;
        int commentListSizeBeforeDelete = post.getCommentList().size();
        //when
        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        commentService.deleteComment(postId, commentId, auth, "user");
        //then
        verify(postRepository, times(0)).save(post);
        assertEquals(commentListSizeBeforeDelete, post.getCommentList().size());
    }

    @Test(expected = CommentNotFoundException.class)
    public void deleteCommentByNormalUserShouldThrowCommentNotFound() {
        //given
        AuthorityEnum auth = AuthorityEnum.valueOf("USER");
        long postId = 1;
        long commentId = 1;
        post.getCommentList().remove(0);
        int commentListSizeBeforeDelete = post.getCommentList().size();
        //when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        commentService.deleteComment(postId, commentId, auth, "user");
        //then
        verify(postRepository, times(0)).save(post);
        assertEquals(commentListSizeBeforeDelete, post.getCommentList().size());
    }

    @Test
    public void updateCommentTestShouldBeSuccessfully() {
        //given
        ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);
        long postId = 1;
        long commentId = 1;
        String username = "admin";
        String contents = "update";
        //when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        commentService.updateComment(postId, commentId, username, contents);
        //then
        verify(postRepository).save(captor.capture());
        String afterUpdate = captor.getValue().getCommentList().get(0).getDescription();
        assertEquals(afterUpdate, contents);
    }

    @Test(expected = NoAuthException.class)
    public void updateCommentTestShouldThrowNoAuthException() {
        //given
        long postId = 1;
        long commentId = 1;
        String username = "user";
        String contents = "update";
        //when
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        commentService.updateComment(postId, commentId, username, contents);
        //then
        verify(postRepository, times(0)).save(any());
    }
}
