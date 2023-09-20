package com.aliyun.opensearch.exceptions;

import org.apache.thrift.TException;

/**
 * Created by dengwx on 16/8/29.
 */

public class InvalidParameterException extends TException {
  public InvalidParameterException() {
    super();
  }

  public InvalidParameterException(String message) {
    super(message);
  }

  public InvalidParameterException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidParameterException(Throwable cause) {
    super(cause);
  }

}

