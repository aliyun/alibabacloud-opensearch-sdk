package com.aliyun.opensearch.client;
/**
 * Created by dengwx on 16/9/7.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorResult {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorResult.class);

  private int code;
  private String message;

  public ErrorResult() {
  }

  public ErrorResult(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ErrorResult)) return false;

    ErrorResult that = (ErrorResult) o;

    if (getCode() != that.getCode()) return false;
    return getMessage() != null ? getMessage().equals(that.getMessage()) : that.getMessage() == null;
  }

  @Override
  public String toString() {
    return "ErrorResult{" +
            "code=" + code +
            ", message='" + message + '\'' +
            '}';
  }
}
