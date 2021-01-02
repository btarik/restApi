package com.tarik.rest.api.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.tarik.rest.api.model.CommentModel;
import com.tarik.rest.api.model.PostModel;
import com.tarik.rest.api.service.CommentService;
import com.tarik.rest.api.service.PostService;
import com.tarik.rest.api.utls.TestUtils;

/**
 * ApiControllerImplTest
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiControllerImplTest {

  @InjectMocks
  private ApiControllerImpl apiControllerImpl;

  @Mock
  private PostService postService;

  @Mock
  private CommentService commentService;

  @Test
  public void shouldReturnAllPostsStatusOk() throws IOException {

    Mockito.when(postService.getPosts()).thenReturn(List.of(TestUtils.post));

    ResponseEntity<List<PostModel>> response = apiControllerImpl.getAllPosts();

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldReturnNoContentWhenAllPostReturnEmptyList() throws IOException {

    Mockito.when(postService.getPosts()).thenReturn(List.of());

    ResponseEntity<List<PostModel>> response = apiControllerImpl.getAllPosts();

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

  }

  @Test
  public void shouldReturnNotFoundWhenGetSpecificPostReturnNullObject() {

    Mockito.when(postService.getSpecificPost(1)).thenReturn(null);

    ResponseEntity<PostModel> response = apiControllerImpl.getSpecificPost(1);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void shouldReturnSpecificPostStatusOk() {

    Mockito.when(postService.getSpecificPost(1)).thenReturn(TestUtils.post);

    ResponseEntity<PostModel> response = apiControllerImpl.getSpecificPost(1);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldReturnNoContentWhenGetCommentsReturnEmptyList() {

    Mockito.when(commentService.getComment()).thenReturn(List.of());

    ResponseEntity<List<CommentModel>> response = apiControllerImpl.getComment();

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

  }

  @Test
  public void shouldReturnCommentsStatusOk() {

    Mockito.when(commentService.getComment()).thenReturn(List.of(TestUtils.comment));

    ResponseEntity<List<CommentModel>> response = apiControllerImpl.getComment();

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldReturnNotFoundWhenGetSpecificCommentReturnEmptyList() {

    Mockito.when(commentService.getSpecificComments(Mockito.anyInt())).thenReturn(null);

    ResponseEntity<List<CommentModel>> response = apiControllerImpl.getSpecificComments(1);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void shouldReturnSpecificCommentStatusOk() {

    Mockito.when(commentService.getSpecificComments(1)).thenReturn(List.of(TestUtils.comment));

    ResponseEntity<List<CommentModel>> response = apiControllerImpl.getSpecificComments(1);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldReturnNoContentWhenAddPostResponseReturnNull() {

    Mockito.when(postService.addPost(Mockito.any(PostModel.class))).thenReturn(null);

    ResponseEntity<PostModel> response = apiControllerImpl.addPost(TestUtils.post);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

  }

  @Test
  public void shouldAddPostWithStatusOk() {

    Mockito.when(postService.addPost(Mockito.any(PostModel.class))).thenReturn(TestUtils.post);

    ResponseEntity<PostModel> response = apiControllerImpl.addPost(TestUtils.post);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldReturnNoFoundWhenUpdatePostIdIsNotExists() {

    Mockito.when(postService.updatePost(Mockito.any(PostModel.class), Mockito.anyInt()))
        .thenThrow(RuntimeException.class);

    ResponseEntity<PostModel> response = apiControllerImpl.updatePost(TestUtils.post, 1);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

  }

  @Test
  public void shouldUpdatePostWithStatusOk() {

    Mockito.when(postService.updatePost(Mockito.any(PostModel.class), Mockito.anyInt())).thenReturn(TestUtils.post);

    ResponseEntity<PostModel> response = apiControllerImpl.updatePost(TestUtils.post, 1);

    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  public void shouldReturnNotFoundWhenDeletePostIdIsNotExists() {

    Mockito.when(postService.getSpecificPost(Mockito.anyInt())).thenThrow(HttpClientErrorException.class);

    ResponseEntity<Integer> response = apiControllerImpl.deletePost(1);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void shouldDeletePostWithStatusOk() {

    Mockito.when(postService.getSpecificPost(Mockito.anyInt())).thenReturn(TestUtils.post);

    Mockito.when(postService.deletePost(1)).thenReturn(1);

    ResponseEntity<Integer> response = apiControllerImpl.deletePost(1);

    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

}
