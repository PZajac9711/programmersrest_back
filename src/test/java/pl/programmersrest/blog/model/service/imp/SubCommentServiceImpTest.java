package pl.programmersrest.blog.model.service.imp;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.programmersrest.blog.model.entity.Comment;
import pl.programmersrest.blog.model.entity.SubComment;
import pl.programmersrest.blog.model.enums.AuthorityEnum;
import pl.programmersrest.blog.model.exceptions.custom.CommentNotFoundException;
import pl.programmersrest.blog.model.exceptions.custom.NoAuthException;
import pl.programmersrest.blog.model.repository.CommentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubCommentServiceImpTest {
    @InjectMocks
    SubCommentServiceImp subCommentServiceImp;
    @Mock
    CommentRepository commentRepository;

    private Comment comment;

    @Before
    public void setUp(){
        comment = Comment.builder()
                .createDate(java.time.LocalDateTime.now())
                .author("janek")
                .description("desc")
                .postId(1L)
                .subCommentList(new ArrayList<SubComment>())
                .id(1L)
                .build();
        SubComment subComment = SubComment.builder()
                .author("janek")
                .commentId(1L)
                .id(1L)
                .createDate(java.time.LocalDateTime.now())
                .description("sub")
                .build();
        comment.getSubCommentList().add(subComment);
    }

    @Test
    public void addNewSubCommentShouldBeSuccessfully() {
        //given
        long postId = 1;
        long commentId = 1;
        String username = "admin";
        String contents = "sub com";
        int subCommentSizeBefore = comment.getSubCommentList()
                .size();
        int expectedSizeAfter = subCommentSizeBefore + 1;
        //when
        when(commentRepository.findCommentByPostIdAndId(postId, commentId)).thenReturn(Optional.of(comment));
        subCommentServiceImp.addNewSubComment(postId,commentId,username,contents);
        //then
        verify(commentRepository,times(1)).save(any());
        assertEquals(comment.getSubCommentList().size(), expectedSizeAfter);
    }

    @Test(expected = CommentNotFoundException.class)
    public void addNewSubCommentShouldThrowCommentNotFoundException(){
        //given
        long postId = -1;
        int expectedListSize = comment.getSubCommentList().size();
        //when
        when(commentRepository.findCommentByPostIdAndId(postId,1L)).thenReturn(Optional.empty());
        subCommentServiceImp.addNewSubComment(postId, 1L, "", "");
        //then
        verify(commentRepository, times(0)).save(any());
        assertEquals(expectedListSize, expectedListSize);
    }

    @Test
    public void deleteSubCommentTestShouldBeSuccessfully(){
        //given
        AuthorityEnum authorityEnum = AuthorityEnum.valueOf("ADMIN");
        long postId = 1;
        long commentId = 1;
        long subCommentId = 1;
        int sizeBeforeDelete = comment.getSubCommentList().size();
        //when
        when(commentRepository.findCommentByPostIdAndId(postId,commentId)).thenReturn(Optional.of(comment));
        subCommentServiceImp.deleteSubComment(postId,commentId,subCommentId,"admin", authorityEnum);
        //then
        verify(commentRepository, times(1)).save(comment);
        assertEquals(sizeBeforeDelete - 1, comment.getSubCommentList().size());
    }

    @Test
    public void deleteSubCommentTestShouldBeSuccessfullyNormalUser(){
        //ToDo: refactor this test to parametrized
        //given
        AuthorityEnum authorityEnum = AuthorityEnum.valueOf("USER");
        long postId = 1;
        long commentId = 1;
        long subCommentId = 1;
        int sizeBeforeDelete = comment.getSubCommentList().size();
        //when
        when(commentRepository.findCommentByPostIdAndId(postId,commentId)).thenReturn(Optional.of(comment));
        subCommentServiceImp.deleteSubComment(postId,commentId,subCommentId,"JaNeK", authorityEnum);
        //then
        verify(commentRepository, times(1)).save(comment);
        assertEquals(sizeBeforeDelete - 1, comment.getSubCommentList().size());
    }

    @Test(expected = NoAuthException.class)
    public void deleteSubCommentTestShouldThrowNoAuthExceptionWrongUser(){
        //given
        AuthorityEnum authorityEnum = AuthorityEnum.valueOf("USER");
        long postId = 1;
        long commentId = 1;
        long subCommentId = 1;
        //when
        when(commentRepository.findCommentByPostIdAndId(postId,commentId)).thenReturn(Optional.of(comment));
        subCommentServiceImp.deleteSubComment(postId,commentId,subCommentId,"admin", authorityEnum);
    }

    @Test(expected = CommentNotFoundException.class)
    public void deleteSubCommentTestShouldThrowCommentNotFoundException(){
        //ToDo: refactor this test to parametrized
        //given
        AuthorityEnum authorityEnum = AuthorityEnum.valueOf("USER");
        long postId = 1;
        long commentId = 1;
        long subCommentId = 1;
        //when
        when(commentRepository.findCommentByPostIdAndId(postId,commentId)).thenReturn(Optional.empty());
        subCommentServiceImp.deleteSubComment(postId,commentId,subCommentId,"admin", authorityEnum);
    }

    @Test(expected = CommentNotFoundException.class)
    public void deleteSubCommentTestShouldThrowCommentNotFoundExceptionNoSubCommentWithThisId(){
        //given
        AuthorityEnum authorityEnum = AuthorityEnum.valueOf("USER");
        long postId = 1;
        long commentId = 1;
        long subCommentId = -1;
        //when
        when(commentRepository.findCommentByPostIdAndId(postId,commentId)).thenReturn(Optional.of(comment));
        subCommentServiceImp.deleteSubComment(postId,commentId,subCommentId,"admin", authorityEnum);
    }

}
