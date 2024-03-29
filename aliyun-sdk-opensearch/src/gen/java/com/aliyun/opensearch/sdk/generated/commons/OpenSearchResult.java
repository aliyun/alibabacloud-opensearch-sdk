/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.aliyun.opensearch.sdk.generated.commons;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2018-08-17")
public class OpenSearchResult implements org.apache.thrift.TBase<OpenSearchResult, OpenSearchResult._Fields>, java.io.Serializable, Cloneable, Comparable<OpenSearchResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OpenSearchResult");

  private static final org.apache.thrift.protocol.TField RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("result", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TRACE_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("traceInfo", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new OpenSearchResultStandardSchemeFactory());
    schemes.put(TupleScheme.class, new OpenSearchResultTupleSchemeFactory());
  }

  private String result; // optional
  private TraceInfo traceInfo; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RESULT((short)1, "result"),
    TRACE_INFO((short)3, "traceInfo");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * @param fieldId fieldId
     * @return Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // RESULT
          return RESULT;
        case 3: // TRACE_INFO
          return TRACE_INFO;
        default:
          return null;
      }
    }

    /**
     * @param fieldId fieldId
     * @return Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * @param name name
     * @return Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.RESULT,_Fields.TRACE_INFO};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESULT, new org.apache.thrift.meta_data.FieldMetaData("result", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TRACE_INFO, new org.apache.thrift.meta_data.FieldMetaData("traceInfo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TraceInfo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OpenSearchResult.class, metaDataMap);
  }

  public OpenSearchResult() {
  }

  public OpenSearchResult(OpenSearchResult other) {
    if (other.isSetResult()) {
      this.result = other.result;
    }
    if (other.isSetTraceInfo()) {
      this.traceInfo = new TraceInfo(other.traceInfo);
    }
  }

  public OpenSearchResult deepCopy() {
    return new OpenSearchResult(this);
  }

  @Override
  public void clear() {
    this.result = null;
    this.traceInfo = null;
  }

  public String getResult() {
    return this.result;
  }

  public OpenSearchResult setResult(String result) {
    this.result = result;
    return this;
  }

  public void unsetResult() {
    this.result = null;
  }

  /**
   * @return Returns true if field result is set (has been assigned a value) and false otherwise
   **/
  public boolean isSetResult() {
    return this.result != null;
  }

  public void setResultIsSet(boolean value) {
    if (!value) {
      this.result = null;
    }
  }

  public TraceInfo getTraceInfo() {
    return this.traceInfo;
  }

  public OpenSearchResult setTraceInfo(TraceInfo traceInfo) {
    this.traceInfo = traceInfo;
    return this;
  }

  public void unsetTraceInfo() {
    this.traceInfo = null;
  }

  /**
   * @return Returns true if field traceInfo is set (has been assigned a value) and false otherwise
   **/
  public boolean isSetTraceInfo() {
    return this.traceInfo != null;
  }

  public void setTraceInfoIsSet(boolean value) {
    if (!value) {
      this.traceInfo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RESULT:
      if (value == null) {
        unsetResult();
      } else {
        setResult((String)value);
      }
      break;

    case TRACE_INFO:
      if (value == null) {
        unsetTraceInfo();
      } else {
        setTraceInfo((TraceInfo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RESULT:
      return getResult();

    case TRACE_INFO:
      return getTraceInfo();

    }
    throw new IllegalStateException();
  }

  /**
   * @return Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise
   **/
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RESULT:
      return isSetResult();
    case TRACE_INFO:
      return isSetTraceInfo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof OpenSearchResult)
      return this.equals((OpenSearchResult)that);
    return false;
  }

  public boolean equals(OpenSearchResult that) {
    if (that == null)
      return false;

    boolean this_present_result = true && this.isSetResult();
    boolean that_present_result = true && that.isSetResult();
    if (this_present_result || that_present_result) {
      if (!(this_present_result && that_present_result))
        return false;
      if (!this.result.equals(that.result))
        return false;
    }

    boolean this_present_traceInfo = true && this.isSetTraceInfo();
    boolean that_present_traceInfo = true && that.isSetTraceInfo();
    if (this_present_traceInfo || that_present_traceInfo) {
      if (!(this_present_traceInfo && that_present_traceInfo))
        return false;
      if (!this.traceInfo.equals(that.traceInfo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_result = true && (isSetResult());
    list.add(present_result);
    if (present_result)
      list.add(result);

    boolean present_traceInfo = true && (isSetTraceInfo());
    list.add(present_traceInfo);
    if (present_traceInfo)
      list.add(traceInfo);

    return list.hashCode();
  }

  @Override
  public int compareTo(OpenSearchResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetResult()).compareTo(other.isSetResult());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResult()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.result, other.result);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTraceInfo()).compareTo(other.isSetTraceInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTraceInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.traceInfo, other.traceInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("OpenSearchResult(");
    boolean first = true;

    if (isSetResult()) {
      sb.append("result:");
      if (this.result == null) {
        sb.append("null");
      } else {
        sb.append(this.result);
      }
      first = false;
    }
    if (isSetTraceInfo()) {
      if (!first) sb.append(", ");
      sb.append("traceInfo:");
      if (this.traceInfo == null) {
        sb.append("null");
      } else {
        sb.append(this.traceInfo);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (traceInfo != null) {
      traceInfo.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class OpenSearchResultStandardSchemeFactory implements SchemeFactory {
    public OpenSearchResultStandardScheme getScheme() {
      return new OpenSearchResultStandardScheme();
    }
  }

  private static class OpenSearchResultStandardScheme extends StandardScheme<OpenSearchResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OpenSearchResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESULT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.result = iprot.readString();
              struct.setResultIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TRACE_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.traceInfo = new TraceInfo();
              struct.traceInfo.read(iprot);
              struct.setTraceInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, OpenSearchResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.result != null) {
        if (struct.isSetResult()) {
          oprot.writeFieldBegin(RESULT_FIELD_DESC);
          oprot.writeString(struct.result);
          oprot.writeFieldEnd();
        }
      }
      if (struct.traceInfo != null) {
        if (struct.isSetTraceInfo()) {
          oprot.writeFieldBegin(TRACE_INFO_FIELD_DESC);
          struct.traceInfo.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OpenSearchResultTupleSchemeFactory implements SchemeFactory {
    public OpenSearchResultTupleScheme getScheme() {
      return new OpenSearchResultTupleScheme();
    }
  }

  private static class OpenSearchResultTupleScheme extends TupleScheme<OpenSearchResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OpenSearchResult struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetResult()) {
        optionals.set(0);
      }
      if (struct.isSetTraceInfo()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetResult()) {
        oprot.writeString(struct.result);
      }
      if (struct.isSetTraceInfo()) {
        struct.traceInfo.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OpenSearchResult struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.result = iprot.readString();
        struct.setResultIsSet(true);
      }
      if (incoming.get(1)) {
        struct.traceInfo = new TraceInfo();
        struct.traceInfo.read(iprot);
        struct.setTraceInfoIsSet(true);
      }
    }
  }

}

