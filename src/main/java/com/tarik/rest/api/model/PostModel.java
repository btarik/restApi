package com.tarik.rest.api.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Posts class
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostModel implements Serializable {

  private static final long serialVersionUID = -1755666329840504821L;

  private int userId;

  private int id;

  private String title;

  private String body;
}
