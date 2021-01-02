package com.tarik.rest.api.service;

import java.io.IOException;
import java.util.List;

import com.tarik.rest.api.model.PostModel;

/**
 * PostService
 */
public interface PostService {

  /**
   * Method to get all posts
   * 
   * @return Posts list informations
   */
  public List<PostModel> getPosts() throws IOException;

  /**
   * Method to get specific post information
   * 
   * @param id
   *          Post id
   * @return Specific post information
   */
  public PostModel getSpecificPost(int id);

  /**
   * Method to create new Post
   * 
   * @param postModel
   *          Post model to create
   * @return Post model created
   */
  public PostModel addPost(PostModel postModel);

  /**
   * Method to update Post
   * 
   * @param id
   *          Post id
   * @return Post updated
   */
  public PostModel updatePost(PostModel postModel, int id);

  /**
   * Method to delete post
   * 
   * @param id
   *          Post id to delete
   * @return Id post deleted
   */
  public Integer deletePost(int id);

}
