package com.tarik.rest.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tarik.rest.api.model.CommentModel;
import com.tarik.rest.api.service.CommentService;
import com.tarik.rest.api.utils.ApiUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * CommentServiceImpl
 */
@Slf4j
@Data
@Service
public class CommentServiceImpl implements CommentService {

  private RestTemplate restTemplate;

  @Autowired
  private Environment environment;

  public CommentServiceImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Override
  public List<CommentModel> getComment() {

    String postsEnvironment = environment.getProperty(ApiUtils.JSON_PLACEHOLDER_URL_COMMENT);

    if (StringUtils.isEmpty(postsEnvironment)) {
      log.error(ApiUtils.JSON_PLACE_HOLDER_URL_IS_COMING_NULL);
      return new ArrayList<>();
    }

    CommentModel[] comments = restTemplate.getForObject(postsEnvironment, CommentModel[].class);

    return new ArrayList<>(Arrays.asList(comments != null ? comments : new CommentModel[0]));

  }

  @Override
  public List<CommentModel> getSpecificComments(int postId) {

    String postsEnvironment = environment.getProperty(ApiUtils.JSON_PLACEHOLDER_URL_POST);
    String commentsPath = environment.getProperty(ApiUtils.JSON_PLACEHOLDER_URL_COMMENTS_PATH);

    if (StringUtils.isEmpty(postsEnvironment) || StringUtils.isEmpty(commentsPath)) {
      log.error(ApiUtils.JSON_PLACE_HOLDER_URL_IS_COMING_NULL);
      return new ArrayList<>();
    }

    CommentModel[] comments = restTemplate.getForObject(StringUtils.join(postsEnvironment, postId, commentsPath),
        CommentModel[].class);

    return new ArrayList<>(Arrays.asList(comments != null ? comments : new CommentModel[0]));
  }

}
