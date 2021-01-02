package com.tarik.rest.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CommentModel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentModel {

  private int postId;

  private int id;

  private String name;

  private String email;

  private String body;
}
