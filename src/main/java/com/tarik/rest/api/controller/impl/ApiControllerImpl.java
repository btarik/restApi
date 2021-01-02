package com.tarik.rest.api.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.tarik.rest.api.controller.ApiController;
import com.tarik.rest.api.model.CommentModel;
import com.tarik.rest.api.model.PostModel;
import com.tarik.rest.api.service.CommentService;
import com.tarik.rest.api.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiControllerImpl implements ApiController {

  @Autowired
  private PostService postService;

  @Autowired
  private CommentService commentService;

  @Override
  public ResponseEntity<List<PostModel>> getAllPosts() throws IOException {

    log.info("Get all Posts");

    List<PostModel> posts = postService.getPosts();

    if (CollectionUtils.isEmpty(posts)) {
      log.info("Get all Posts : No content");
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(posts);
  }

  @Override
  public ResponseEntity<PostModel> getSpecificPost(int id) {
    log.info("Get post information with id : " + id);
    PostModel specificPost = postService.getSpecificPost(id);

    if (specificPost == null) {
      log.info("Get post information with id : " + id + ":  No content");
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(specificPost);
  }

  @Override
  public ResponseEntity<List<CommentModel>> getComment() {
    log.info("Get all Comments");

    List<CommentModel> comments = commentService.getComment();

    if (CollectionUtils.isEmpty(comments)) {
      log.info("Get all Comments : No content");
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(comments);
  }

  @Override
  public ResponseEntity<List<CommentModel>> getSpecificComments(int postId) {

    log.info("Get comment for the user id  : " + postId);

    List<CommentModel> specificComments = commentService.getSpecificComments(postId);

    if (CollectionUtils.isEmpty(specificComments)) {
      log.info("Get specific Comments : No content");
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(specificComments);

  }

  @Override
  public ResponseEntity<PostModel> addPost(PostModel postModel) {

    log.info("Create a new post");

    PostModel postModelCreated = postService.addPost(postModel);

    if (postModelCreated == null) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(postModelCreated);
  }

  @Override
  public ResponseEntity<PostModel> updatePost(PostModel postModel, int id) {
    try {
      log.info("Update post");

      PostModel updatePost = postService.updatePost(postModel, id);

      return ResponseEntity.ok(updatePost);
    } catch (RuntimeException ex) {
      log.error("Post with id : " + id + " NOT FOUND");
      return ResponseEntity.notFound().build();
    }

  }

  @Override
  public ResponseEntity<Integer> deletePost(int id) {

    try {
      postService.getSpecificPost(id);
    } catch (HttpClientErrorException ex) {
      log.error("Post with id : " + id + " NOT FOUND");
      return ResponseEntity.notFound().build();
    }

    Integer postIdDeleted = postService.deletePost(id);

    return ResponseEntity.ok(postIdDeleted);
  }
}
