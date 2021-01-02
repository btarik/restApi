package com.tarik.rest.api.service.impl;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.tarik.rest.api.model.PostModel;
import com.tarik.rest.api.utls.TestUtils;

/**
 * PostServiceImplTest
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {
  @InjectMocks
  private PostServiceImpl postServiceImpl;
  @Mock
  private Environment environment;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private RestTemplateBuilder restTemplateBuilder;

  @Mock
  private ResponseEntity<PostModel> responseEntity;

  @Before
  public void init() {
    postServiceImpl.setRestTemplate(restTemplate);
    postServiceImpl.setEnvironment(environment);
  }

  @Test
  public void shouldReturnEmptyArrayWhenStringEnvironmentIsNull() throws IOException {

    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(null);

    List<PostModel> posts = postServiceImpl.getPosts();

    assertTrue(CollectionUtils.isEmpty(posts));
  }

  @Test
  public void shouldReturnEmptyObjectPostModelWhenGetSpecificPostEnvironmentIsNull() {

    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(null);

    PostModel specificPost = postServiceImpl.getSpecificPost(1);

    checkEmptyPostModelObject(specificPost);

  }

  @Test
  public void shouldReturnPostModel() {
    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(TestUtils.JSON_PLACEHOLDER_URL);
    Mockito.when(restTemplate.getForObject(StringUtils.join(TestUtils.JSON_PLACEHOLDER_URL, 1), PostModel.class))
        .thenReturn(TestUtils.post);
    PostModel specificPost = postServiceImpl.getSpecificPost(1);
    assertNotNull(specificPost);

  }

  @Test
  public void shouldReturnEmptyObjectPostModelWhenAddPostEnvironmentIsNull() {

    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(null);

    PostModel postModel = postServiceImpl.addPost(TestUtils.post);

    checkEmptyPostModelObject(postModel);

  }

  @Test
  public void shouldReturnEmptyObjectPostModelWhenAddPostObjectIsNull() {

    PostModel postModel = postServiceImpl.addPost(TestUtils.post);

    checkEmptyPostModelObject(postModel);

  }

  @Test
  public void shouldReturnNullPostObjectWhenStatusCodeIsNotCreated() {
    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(TestUtils.JSON_PLACEHOLDER_URL);
    Mockito.when(restTemplate.postForEntity(TestUtils.JSON_PLACEHOLDER_URL, TestUtils.createEntity(), PostModel.class))
        .thenReturn(responseEntity);
    Mockito.when(responseEntity.getStatusCode()).thenReturn(HttpStatus.NO_CONTENT);
    PostModel postModel = postServiceImpl.addPost(TestUtils.post);
    assertNull(postModel);
  }

  @Test
  public void shouldReturnPostModelObjectCreated() {
    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(TestUtils.JSON_PLACEHOLDER_URL);
    Mockito.when(restTemplate.postForEntity(TestUtils.JSON_PLACEHOLDER_URL, TestUtils.createEntity(), PostModel.class))
        .thenReturn(responseEntity);
    Mockito.when(responseEntity.getStatusCode()).thenReturn(HttpStatus.CREATED);
    Mockito.when(responseEntity.getBody()).thenReturn(TestUtils.post);
    PostModel postModel = postServiceImpl.addPost(TestUtils.post);
    assertNotNull(postModel);
  }

  @Test
  public void shouldReturnEmptyObjectPostModelWhenUpdatePostEnvironmentIsNull() {

    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(null);

    PostModel postModel = postServiceImpl.updatePost(TestUtils.post, 1);

    checkEmptyPostModelObject(postModel);

  }

  @Test
  public void shouldReturnEmptyObjectPostModelWhenUpdatePostObjectIsNull() {

    PostModel postModel = postServiceImpl.updatePost(null, 1);

    checkEmptyPostModelObject(postModel);
  }

  @Test
  public void shouldReturnPostModelObjectUpdated() {
    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(TestUtils.JSON_PLACEHOLDER_URL);
    Mockito.when(restTemplate.exchange(StringUtils.join(TestUtils.JSON_PLACEHOLDER_URL, 1), HttpMethod.PUT,
        TestUtils.createEntityFoUpdate(), PostModel.class, 1)).thenReturn(responseEntity);

    Mockito.when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
    Mockito.when(responseEntity.getBody()).thenReturn(TestUtils.post);
    PostModel postModel = postServiceImpl.updatePost(TestUtils.post, 1);
    assertNotNull(postModel);

  }

  @Test
  public void shouldReturnNegativeIdWhenDeletePostEnvironmentIsNull() {
    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(null);
    Integer id = postServiceImpl.deletePost(1);
    assertNull(id);
  }

  @Test
  public void shouldReturnPostIdDeleted() {
    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(TestUtils.JSON_PLACEHOLDER_URL);

    Integer id = postServiceImpl.deletePost(1);

    assertNotNull(id);
  }

  /**
   * Method to check if postModel object is empty
   *
   * @param postModel
   *          PostModel
   */
  private void checkEmptyPostModelObject(PostModel postModel) {
    assertEquals(0, postModel.getUserId());
    assertEquals(0, postModel.getId());
    assertNull(postModel.getBody());
    assertNull(postModel.getTitle());
  }

}
