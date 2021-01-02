package com.tarik.rest.api.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.tarik.rest.api.model.CommentModel;
import com.tarik.rest.api.model.PostModel;
import com.tarik.rest.api.utls.TestUtils;

/**
 * CommentServiceImplTest
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {

  @InjectMocks
  private CommentServiceImpl commentServiceImpl;

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
    commentServiceImpl.setRestTemplate(restTemplate);
    commentServiceImpl.setEnvironment(environment);
  }

  @Test
  public void shouldReturnEmptyListWhenStringEnvironmentIsNull() {

    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(null);

    List<CommentModel> comments = commentServiceImpl.getComment();

    assertTrue(CollectionUtils.isEmpty(comments));

  }

  @Test
  public void shouldReturnCommentsList() {

    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(TestUtils.JSON_PLACEHOLDER_URL);

    Mockito.when(restTemplate.getForObject(TestUtils.JSON_PLACEHOLDER_URL, CommentModel[].class))
        .thenReturn(new CommentModel[] { TestUtils.comment });

    List<CommentModel> comments = commentServiceImpl.getComment();

    assertTrue(CollectionUtils.isNotEmpty(comments));
  }

  @Test
  public void shouldReturnEmptyListWhenStringEnvironmentIsNullIGetSpecificComment() {

    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(null);

    List<CommentModel> comments = commentServiceImpl.getSpecificComments(1);

    assertTrue(CollectionUtils.isEmpty(comments));
  }

  @Test
  public void shouldReturnSpecificComment() {
    Mockito.when(environment.getProperty(Mockito.anyString())).thenReturn(TestUtils.JSON_PLACEHOLDER_URL);

    Mockito
        .when(restTemplate.getForObject(
            StringUtils.join(TestUtils.JSON_PLACEHOLDER_URL, 1, TestUtils.JSON_PLACEHOLDER_URL), CommentModel[].class))
        .thenReturn(new CommentModel[] { TestUtils.comment });

    List<CommentModel> comments = commentServiceImpl.getSpecificComments(1);

    assertTrue(CollectionUtils.isNotEmpty(comments));
  }

}
