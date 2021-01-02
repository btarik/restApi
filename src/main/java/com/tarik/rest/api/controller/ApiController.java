package com.tarik.rest.api.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tarik.rest.api.model.CommentModel;
import com.tarik.rest.api.model.PostModel;

import io.swagger.annotations.ApiOperation;

public interface ApiController {

  @ApiOperation(value = "Get all posts information from jsonplaceholder.typicode.com")
  @GetMapping("/posts")
  ResponseEntity<List<PostModel>> getAllPosts() throws IOException;

  @ApiOperation(value = "Get specific post information from jsonplaceholder.typicode.com")
  @GetMapping("/posts/{id}")
  ResponseEntity<PostModel> getSpecificPost(@PathVariable("id") int id);

  @ApiOperation(value = "Get all comments information from jsonplaceholder.typicode.com")
  @GetMapping("/comments")
  ResponseEntity<List<CommentModel>> getComment();

  @ApiOperation(value = "Get comment information from jsonplaceholder.typicode.com for specific Post")
  @GetMapping("/posts/{postId}/comments")
  ResponseEntity<List<CommentModel>> getSpecificComments(@PathVariable("postId") int postId);

  @ApiOperation(value = "Create a new Post")
  @PostMapping("/posts")
  ResponseEntity<PostModel> addPost(@RequestBody PostModel postModel);

  @ApiOperation(value = "Create a new Post")
  @PutMapping("/posts/{id}")
  ResponseEntity<PostModel> updatePost(@RequestBody PostModel postModel, @PathVariable("id") int id)
      throws InvocationTargetException;

  @ApiOperation(value = "Delete Post")
  @DeleteMapping("/posts/{id}")
  ResponseEntity<Integer> deletePost(@PathVariable("id") int id);

}
