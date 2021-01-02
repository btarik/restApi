package com.tarik.rest.api.utls;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.tarik.rest.api.model.CommentModel;
import com.tarik.rest.api.model.PostModel;
import com.tarik.rest.api.utils.ApiUtils;

/**
 * TestUtils
 */
public class TestUtils {

  public static final String JSON_PLACEHOLDER_URL = "json.placeholder.url";

  public static final PostModel post = PostModel.builder()
      .userId(1)
      .id(1)
      .title("Title post")
      .body("body post")
      .build();

  public static final CommentModel comment = CommentModel.builder()
      .body("body comment")
      .email("email comment")
      .id(1)
      .name("name comment")
      .postId(1)
      .build();

  /**
   * Method to create entity
   * 
   * @return Entity
   */
  public static HttpEntity<Map<String, Object>> createEntity() {
    PostModel postModel = TestUtils.post;
    Map<String, Object> map = new HashMap<>();
    map.put(ApiUtils.JSON_USER_ID, postModel.getUserId());
    map.put(ApiUtils.JSON_POST_TITLE, postModel.getTitle());
    map.put(ApiUtils.JSON_POST_BODY, postModel.getBody());

    HttpHeaders headers = getHttpHeaders();

    return new HttpEntity<>(map, headers);
  }

  private static HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    // set 'accept' header
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    return headers;
  }

  public static HttpEntity<PostModel> createEntityFoUpdate() {
    return new HttpEntity<>(TestUtils.post, getHttpHeaders());
  }

}
