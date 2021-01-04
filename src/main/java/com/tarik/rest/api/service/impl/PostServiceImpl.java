package com.tarik.rest.api.service.impl;

import java.io.IOException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tarik.rest.api.model.PostModel;
import com.tarik.rest.api.service.PostService;
import com.tarik.rest.api.utils.ApiUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Service
public class PostServiceImpl implements PostService {

  private RestTemplate restTemplate;

  @Autowired
  private Environment environment;

  public PostServiceImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Override
  public List<PostModel> getPosts() throws IOException {

    String postsEnvironment = environment.getProperty(ApiUtils.JSON_PLACEHOLDER_URL_POST);

    if (StringUtils.isEmpty(postsEnvironment)) {
      log.error(ApiUtils.JSON_PLACE_HOLDER_URL_IS_COMING_NULL);
      return new ArrayList<>();
    }

    PostModel[] posts = restTemplate.getForObject(postsEnvironment, PostModel[].class);

    JSONArray company = new JSONArray();

    // Save in json file
    for (PostModel postModel : Objects.requireNonNull(posts)) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(ApiUtils.JSON_USER_ID, postModel.getUserId());
      jsonObject.put(ApiUtils.JSON_POST_ID, postModel.getId());
      jsonObject.put(ApiUtils.JSON_POST_TITLE, postModel.getTitle());
      jsonObject.put(ApiUtils.JSON_POST_BODY, postModel.getBody());
      company.add(jsonObject);
    }
    // Save response in json file
    ApiUtils.saveJsonFile(company, ApiUtils.JSON_POST_FILE_NAME);

    // Save response in xml file
    ApiUtils.saveXmlFile(ApiUtils.JSON_POST_FILE_NAME, ApiUtils.JSON_POST_XML_FILE_NAME);

    return new ArrayList<>(Arrays.asList(posts));
  }

  @Override
  public PostModel getSpecificPost(int id) {

    String postsEnvironment = environment.getProperty(ApiUtils.JSON_PLACEHOLDER_URL_POST);

    if (StringUtils.isEmpty(postsEnvironment)) {
      log.error(ApiUtils.JSON_PLACE_HOLDER_URL_IS_COMING_NULL);
      return new PostModel();
    }

    return restTemplate.getForObject(StringUtils.join(postsEnvironment, id), PostModel.class);
  }

  @Override
  public PostModel addPost(PostModel postModel) {

    String postsEnvironment = environment.getProperty(ApiUtils.JSON_PLACEHOLDER_URL_POST);

    if (StringUtils.isEmpty(postsEnvironment) || postModel == null) {
      log.error(ApiUtils.JSON_PLACE_HOLDER_URL_IS_COMING_NULL);
      return new PostModel();
    }

    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    // set 'accept' header
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    // create a map for post parameters
    Map<String, Object> map = new HashMap<>();
    map.put(ApiUtils.JSON_USER_ID, postModel.getUserId());
    map.put(ApiUtils.JSON_POST_TITLE, postModel.getTitle());
    map.put(ApiUtils.JSON_POST_BODY, postModel.getBody());

    // build the request
    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

    ResponseEntity<PostModel> response = restTemplate.postForEntity(StringUtils.join(postsEnvironment), entity,
        PostModel.class);

    // check response status code
    if (response.getStatusCode() == HttpStatus.CREATED) {
      return response.getBody();
    } else {
      return null;
    }
  }

  @Override
  public PostModel updatePost(PostModel postModel, int userId) {

    String postsEnvironment = environment.getProperty(ApiUtils.JSON_PLACEHOLDER_URL_POST);

    if (StringUtils.isEmpty(postsEnvironment) || postModel == null) {
      log.error(ApiUtils.JSON_PLACE_HOLDER_URL_IS_COMING_NULL);
      return new PostModel();
    }

    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    // Set 'accept' header
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    PostModel postModelToUpdate = new PostModel(userId, postModel.getId(), postModel.getTitle(), postModel.getBody());

    HttpEntity<PostModel> entity = new HttpEntity<>(postModelToUpdate, headers);

    ResponseEntity<PostModel> response = restTemplate.exchange(StringUtils.join(postsEnvironment, userId),
        HttpMethod.PUT, entity, PostModel.class, userId);

    // check response status code
    if (response.getStatusCode() == HttpStatus.OK) {
      return response.getBody();
    } else {
      return null;
    }

  }

  @Override
  public Integer deletePost(int id) {
    String postsEnvironment = environment.getProperty(ApiUtils.JSON_PLACEHOLDER_URL_POST);

    if (StringUtils.isEmpty(postsEnvironment)) {
      log.error(StringUtils.join(ApiUtils.JSON_PLACE_HOLDER_URL_IS_COMING_NULL, ApiUtils.DELETE_INFO_LOG));
      return null;
    }

    restTemplate.delete(StringUtils.join(postsEnvironment, id));

    return id;
  }
}
