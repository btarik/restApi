package com.tarik.rest.api.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.json.simple.JSONArray;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

/**
 * ApiUtils
 */
@Slf4j
public class ApiUtils {

  // Posts
  public static final String JSON_PLACEHOLDER_URL_POST = "json.placeholder.url.post";
  public static final String JSON_PLACEHOLDER_URL_COMMENT = "json.placeholder.url.comment";
  public static final String JSON_USER_ID = "userId";
  public static final String JSON_POST_ID = "id";
  public static final String JSON_POST_TITLE = "title";
  public static final String JSON_POST_BODY = "body";
  public static final String JSON_PLACEHOLDER_URL_COMMENTS_PATH = "json.placeholder.comment.path";
  public static final String JSON_POST_FILE_NAME = "Getposts.json";
  public static final String JSON_POST_XML_FILE_NAME = "Getposts.xml";
  public static final String DELETE_INFO_LOG = "Id -1 will be returned";

  public static final String JSON_PLACE_HOLDER_URL_IS_COMING_NULL = "Environment property or PostModel object is NULL";

  private static FileWriter file;

  private ApiUtils() {
  }

  /**
   * method to save json file
   * 
   * @param company
   *          Json array to save
   * @param fileName
   *          file name
   * @throws JsonProcessingException
   *           Exception
   */
  public static void saveJsonFile(JSONArray company, String fileName) throws JsonProcessingException {
    if (company == null) {
      return;
    }
    try {

      file = new FileWriter(fileName);
      file.write(company.toJSONString());

    } catch (IOException e) {
      e.printStackTrace();

    } finally {

      try {
        file.flush();
        file.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Method to save xml file
   * 
   * @param jsonFileName
   *          Json file name
   * @param xmlFileName
   *          Xml file name
   * @throws IOException
   *           Exception
   */
  public static void saveXmlFile(String jsonFileName, String xmlFileName) throws IOException {

    if (StringUtils.isEmpty(jsonFileName) || StringUtils.isEmpty(xmlFileName)) {
      log.error("Json file name or xml file name is empty");
      return;
    }

    try (FileWriter fileWriter = new FileWriter(xmlFileName)) {

      String jsonStr = new String(Files.readAllBytes(Paths.get(jsonFileName)));
      // Delete de "[" to avoid error JSONObject when want to read the first line
      String replace = jsonStr.replace("[", "");
      // Split a block data json
      List<String> list = Stream.of(replace.split("},")).collect(Collectors.toList());
      // Iterate each json block to store it in the xml file
      list.forEach(data -> {

        JSONObject json = new JSONObject(StringUtils.join(data, "}"));
        try {
          fileWriter.write(XML.toString(json));
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

    }
  }
}
