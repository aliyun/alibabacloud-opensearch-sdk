package com.aliyun.opensearch.util;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.aliyun.opensearch.exceptions.ThriftSerializationException;

/**
 * Created by dorispan on 16/10/15.
 */

public class ThriftIO {

  public static void fromJson(TBase obj, String data)
      throws ThriftSerializationException {
    fromStringAsProtocol(obj, data, TJSONProtocol.class);
  }

  public static String toJson(TBase obj) throws ThriftSerializationException {
    return toStringAsProtocol(obj, TJSONProtocol.class);
  }

  public static String toSimpleJson(TBase obj)
      throws ThriftSerializationException {
    return toStringAsProtocol(obj, TSimpleJSONProtocol.class);
  }

  public static void fromBinary(TBase obj, byte[] data) throws ThriftSerializationException {
    fromBytesAsProtocol(obj, data, TBinaryProtocol.class);
  }

  public static byte[] toBinary(TBase obj) throws ThriftSerializationException {
    return toBytesAsProtocol(obj, TBinaryProtocol.class);
  }

  public static void fromCompactBinary(TBase obj, byte[] data) throws ThriftSerializationException {
    fromBytesAsProtocol(obj, data, TCompactProtocol.class);
  }

  public static byte[] toCompactBinary(TBase obj) throws ThriftSerializationException {
    return toBytesAsProtocol(obj, TCompactProtocol.class);
  }

  public static void fromBase64(TBase obj, String value) throws ThriftSerializationException {
    byte[] bytes = Base64.decodeBase64(value);
    if (bytes == null) {
      throw new ThriftSerializationException(String.format("failed to deserialize, value [%s]", value));
    }
    fromBinary(obj, bytes);
  }

  public static String toBase64(TBase obj) throws ThriftSerializationException {
    byte[] bytes = ThriftIO.toBinary(obj);
    return Base64.encodeBase64String(bytes);
  }

  public static void fromStringAsProtocol(TBase obj, String data,
                                         Class<? extends TProtocol> protocoClass)
          throws ThriftSerializationException {
    fromBytesAsProtocol(obj, data.getBytes(), protocoClass);
  }

  public static void fromBytesAsProtocol(TBase obj, byte[] data,
                                         Class<? extends TProtocol> protocoClass)
          throws ThriftSerializationException {
    TMemoryBuffer buffer = new TMemoryBuffer(1024);
    TProtocol protocol;
    try {
      buffer.write(data);
      Constructor<? extends TProtocol> constructor =
              protocoClass.getConstructor(TTransport.class);
      protocol = constructor.newInstance(buffer);
      obj.read(protocol);
    } catch (Exception e) {
      throw new ThriftSerializationException(e);
    }
  }

  public static String toStringAsProtocol(TBase obj,
                                          Class<? extends TProtocol> protocoClass)
          throws ThriftSerializationException {
    return new String(toBytesAsProtocol(obj, protocoClass));
  }

  public static byte[] toBytesAsProtocol(TBase obj,
                                          Class<? extends TProtocol> protocoClass)
          throws ThriftSerializationException {
    TMemoryBuffer buffer = new TMemoryBuffer(1024);
    TProtocol protocol;
    try {
      Constructor<? extends TProtocol> constructor =
              protocoClass.getConstructor(TTransport.class);
      protocol = constructor.newInstance(buffer);
    } catch (Exception e) {
      throw new ThriftSerializationException(e);
    }
    try {
      obj.write(protocol);
    } catch (TTransportException e) {
      throw new ThriftSerializationException(e);
    } catch (TException e) {
      throw new ThriftSerializationException(e);
    }
    return Arrays.copyOf(buffer.getArray(), buffer.length());
  }

}
