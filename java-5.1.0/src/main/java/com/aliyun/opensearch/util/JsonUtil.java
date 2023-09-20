package com.aliyun.opensearch.util;


/**
 * Created by dengwx on 15/12/10.
 */

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;

public class JsonUtil {
  private static final Log log = LogFactory.getLog(JsonUtil.class);

  public static <T> T fromJson(String jsonString, Class<T> tClass) throws
      JSONException {
    try {
      Gson gson = new Gson();
      return gson.fromJson(jsonString, tClass);
    } catch (Exception e) {
      throw new JSONException(e);
    }
  }

  public static <T> List<T> fromJsonList(String jsonString, Type type) throws JSONException {
    try {
      Gson gson = new GsonBuilder().create();
      return gson.fromJson(jsonString, type);
    } catch (Exception e) {
      throw new JSONException(e);
    }
  }

  public static <T> String toJson(T object) throws JSONException {
    try {
      Gson gson = new Gson();
      return gson.toJson(object);
    } catch (Exception e) {
      throw new JSONException(e);
    }
  }

  public static <T> String toPrettyJson(T object) throws JSONException {
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    try {
      return gson.toJson(object);
    } catch (Exception e) {
      throw new JSONException(e);
    }
  }

  public static <T> String objectToString(T object) {
    try {
      return toPrettyJson(object);
    } catch (JSONException e) {
      log.warn("failed to serialize object", e);
    }
    return "";
  }
}
