package com.tarik.rest.api.service;

import java.util.List;

import com.tarik.rest.api.model.CommentModel;

/**
 * CommentService
 */
public interface CommentService {

  /**
   * Method to get all comments
   *
   * @return All comments
   */
  public List<CommentModel> getComment();

  /**
   * Method to get specific comment
   *
   * @return Comment information
   */
  public List<CommentModel> getSpecificComments(int postId);
}
