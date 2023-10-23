package com.aliyun.opensearch.client;
/**
 * Created by dengwx on 16/9/7.
 */

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenSearchResponse {
    private static final Logger LOG = LoggerFactory.getLogger(OpenSearchResponse.class);

  public static final String OK = "OK";
  public static final String FAIL = "FAIL";

  private String request_id;
  private String status = OK;
  private List<ErrorResult> errors;
  private Object result;
  private String tracer;

  public OpenSearchResponse() {
  }

  public String getRequest_id() {
    return request_id;
  }

  public void setRequest_id(String request_id) {
    this.request_id = request_id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void addError(ErrorResult errorResult) {
    if (errors == null) {
      errors = new ArrayList<ErrorResult>();
    }
    errors.add(errorResult);
  }

  public List<ErrorResult> getErrors() {
    return errors;
  }

  public void setErrors(List<ErrorResult> errors) {
    this.errors = errors;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }

  public String getResultString() {
    if (result != null) {
      return this.result.toString();
    }
    return "";
  }

  public String getTracer() {
    return tracer;
  }

  public void setTracer(String tracer) {
    this.tracer = tracer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OpenSearchResponse)) return false;

    OpenSearchResponse that = (OpenSearchResponse) o;

    if (getRequest_id() != null ? !getRequest_id().equals(that.getRequest_id()) : that.getRequest_id() != null)
      return false;
    if (getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null)
      return false;
    if (getErrors() != null ? !getErrors().equals(that.getErrors()) : that.getErrors() != null)
      return false;
    if (getResult() != null ? !getResult().equals(that.getResult()) : that.getResult() != null)
      return false;
    return getTracer() != null ? getTracer().equals(that.getTracer()) : that.getTracer() == null;

  }

  @Override
  public String toString() {
    return "OpenSearchResponse{" +
            "request_id='" + request_id + '\'' +
            ", status='" + status + '\'' +
            ", errors=" + errors +
            ", result=" + result +
            ", tracer='" + tracer + '\'' +
            '}';
  }
}
