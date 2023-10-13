package com.aliyun.opensearch.util;
/**
 * Created by dengwx on 16/9/9.
 */

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenSearchJSONProtocol extends TSimpleJSONProtocol {
    private static final Logger LOG = LoggerFactory.getLogger(OpenSearchJSONProtocol.class);

  /**
   * Factory
   */
  public static class Factory implements TProtocolFactory {
    public TProtocol getProtocol(TTransport trans) {
      return new OpenSearchJSONProtocol(trans);
    }
  }

  /**
   * Constructor
   * @param trans TTransport
   */
  public OpenSearchJSONProtocol(TTransport trans) {
    super(trans);
  }

  public void writeBool(boolean b) throws TException {
    writeContext_.write();
    if (b) {
      _writeStringData("true");
    } else {
      _writeStringData("false");
    }
  }
}
