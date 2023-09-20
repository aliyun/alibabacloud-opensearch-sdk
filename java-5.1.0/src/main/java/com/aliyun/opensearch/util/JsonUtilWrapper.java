package com.aliyun.opensearch.util;
/**
 * Created by dengwx on 16/9/1.
 */

import com.aliyun.opensearch.client.ErrorResult;
import com.aliyun.opensearch.client.OpenSearchResponse;
import com.aliyun.opensearch.exceptions.ThriftSerializationException;
import org.apache.thrift.TBase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtilWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtilWrapper.class);

  public static String toJson(TBase obj) throws JSONException {
    try {
      return ThriftIO.toStringAsProtocol(obj, OpenSearchJSONProtocol.class);
    } catch (ThriftSerializationException e) {
      throw new JSONException(e);
    }
  }

  public static <T> T fromJson(String jsonString, Class<T> tClass) throws
          JSONException {
    return JsonUtil.fromJson(jsonString, tClass);
  }

  public static OpenSearchResponse fromJson(String str) {
    JSONObject jsonObject = new JSONObject(str);
    OpenSearchResponse openSearchResponse = new OpenSearchResponse();
    if (jsonObject.has("request_id")) {
      openSearchResponse.setRequest_id(jsonObject.getString("request_id"));
    }
    if (jsonObject.has("status")) {
      openSearchResponse.setStatus(jsonObject.getString("status"));
    }
    if (jsonObject.has("tracer")) {
      openSearchResponse.setTracer(jsonObject.getString("tracer"));
    }
    if (jsonObject.has("errors")) {
      JSONArray jsonArray = jsonObject.getJSONArray("errors");
      for (int i = 0; i < jsonArray.length(); ++i) {
        JSONObject errorJsonObject = jsonArray.getJSONObject(i);
        ErrorResult errorResult = fromJsonObject(errorJsonObject);
        openSearchResponse.addError(errorResult);
      }
    }
    if (jsonObject.has("result")) {
      openSearchResponse.setResult(jsonObject.get("result"));
    }
    return openSearchResponse;
  }

  private static ErrorResult fromJsonObject(JSONObject jsonObject) {
    ErrorResult errorResult = new ErrorResult();
    if (jsonObject.has("code")) {
      errorResult.setCode(jsonObject.getInt("code"));
    }
    if (jsonObject.has("message")) {
      errorResult.setMessage(jsonObject.getString("message"));
    }
    return errorResult;
  }
}
