package com.aliyun.opensearch.exceptions;

/**
 * Created by dorispan on 16/10/15.
 */
public class ThriftSerializationException extends Throwable {

  public ThriftSerializationException() {
    super();
  }

  public ThriftSerializationException(String message) {
    super(message);
  }

  public ThriftSerializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ThriftSerializationException(Throwable cause) {
    super(cause);
  }

}
