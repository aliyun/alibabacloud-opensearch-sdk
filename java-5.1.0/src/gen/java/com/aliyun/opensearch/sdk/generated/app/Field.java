/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.aliyun.opensearch.sdk.generated.app;

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
public class Field implements org.apache.thrift.TBase<Field, Field._Fields>, java.io.Serializable, Cloneable, Comparable<Field> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Field");

  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PRIMARY_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("primary_key", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField JOIN_WITH_FIELD_DESC = new org.apache.thrift.protocol.TField("join_with", org.apache.thrift.protocol.TType.LIST, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FieldStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FieldTupleSchemeFactory());
  }

  private String name; // optional
  private String type; // optional
  private boolean primary_key; // optional
  private List<String> join_with; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAME((short)1, "name"),
    TYPE((short)2, "type"),
    PRIMARY_KEY((short)3, "primary_key"),
    JOIN_WITH((short)4, "join_with");

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
        case 1: // NAME
          return NAME;
        case 2: // TYPE
          return TYPE;
        case 3: // PRIMARY_KEY
          return PRIMARY_KEY;
        case 4: // JOIN_WITH
          return JOIN_WITH;
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
  private static final int __PRIMARY_KEY_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.NAME,_Fields.TYPE,_Fields.PRIMARY_KEY,_Fields.JOIN_WITH};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRIMARY_KEY, new org.apache.thrift.meta_data.FieldMetaData("primary_key", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.JOIN_WITH, new org.apache.thrift.meta_data.FieldMetaData("join_with", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Field.class, metaDataMap);
  }

  public Field() {
  }

  public Field(Field other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
    this.primary_key = other.primary_key;
    if (other.isSetJoin_with()) {
      List<String> __this__join_with = new ArrayList<String>(other.join_with);
      this.join_with = __this__join_with;
    }
  }

  public Field deepCopy() {
    return new Field(this);
  }

  @Override
  public void clear() {
    this.name = null;
    this.type = null;
    setPrimary_keyIsSet(false);
    this.primary_key = false;
    this.join_with = null;
  }

  public String getName() {
    return this.name;
  }

  public Field setName(String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /**
   * @return Returns true if field name is set (has been assigned a value) and false otherwise
   **/
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public String getType() {
    return this.type;
  }

  public Field setType(String type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /**
   * @return Returns true if field type is set (has been assigned a value) and false otherwise
   **/
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public boolean isPrimary_key() {
    return this.primary_key;
  }

  public Field setPrimary_key(boolean primary_key) {
    this.primary_key = primary_key;
    setPrimary_keyIsSet(true);
    return this;
  }

  public void unsetPrimary_key() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRIMARY_KEY_ISSET_ID);
  }

  /**
   * @return Returns true if field primary_key is set (has been assigned a value) and false otherwise
   **/
  public boolean isSetPrimary_key() {
    return EncodingUtils.testBit(__isset_bitfield, __PRIMARY_KEY_ISSET_ID);
  }

  public void setPrimary_keyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRIMARY_KEY_ISSET_ID, value);
  }

  public int getJoin_withSize() {
    return (this.join_with == null) ? 0 : this.join_with.size();
  }

  public java.util.Iterator<String> getJoin_withIterator() {
    return (this.join_with == null) ? null : this.join_with.iterator();
  }

  public void addToJoin_with(String elem) {
    if (this.join_with == null) {
      this.join_with = new ArrayList<String>();
    }
    this.join_with.add(elem);
  }

  public List<String> getJoin_with() {
    return this.join_with;
  }

  public Field setJoin_with(List<String> join_with) {
    this.join_with = join_with;
    return this;
  }

  public void unsetJoin_with() {
    this.join_with = null;
  }

  /**
   * @return Returns true if field join_with is set (has been assigned a value) and false otherwise
   **/
  public boolean isSetJoin_with() {
    return this.join_with != null;
  }

  public void setJoin_withIsSet(boolean value) {
    if (!value) {
      this.join_with = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((String)value);
      }
      break;

    case PRIMARY_KEY:
      if (value == null) {
        unsetPrimary_key();
      } else {
        setPrimary_key((Boolean)value);
      }
      break;

    case JOIN_WITH:
      if (value == null) {
        unsetJoin_with();
      } else {
        setJoin_with((List<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME:
      return getName();

    case TYPE:
      return getType();

    case PRIMARY_KEY:
      return isPrimary_key();

    case JOIN_WITH:
      return getJoin_with();

    }
    throw new IllegalStateException();
  }

  /**
   * @param field field
   * @return Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise
   **/
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NAME:
      return isSetName();
    case TYPE:
      return isSetType();
    case PRIMARY_KEY:
      return isSetPrimary_key();
    case JOIN_WITH:
      return isSetJoin_with();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Field)
      return this.equals((Field)that);
    return false;
  }

  public boolean equals(Field that) {
    if (that == null)
      return false;

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_primary_key = true && this.isSetPrimary_key();
    boolean that_present_primary_key = true && that.isSetPrimary_key();
    if (this_present_primary_key || that_present_primary_key) {
      if (!(this_present_primary_key && that_present_primary_key))
        return false;
      if (this.primary_key != that.primary_key)
        return false;
    }

    boolean this_present_join_with = true && this.isSetJoin_with();
    boolean that_present_join_with = true && that.isSetJoin_with();
    if (this_present_join_with || that_present_join_with) {
      if (!(this_present_join_with && that_present_join_with))
        return false;
      if (!this.join_with.equals(that.join_with))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_type = true && (isSetType());
    list.add(present_type);
    if (present_type)
      list.add(type);

    boolean present_primary_key = true && (isSetPrimary_key());
    list.add(present_primary_key);
    if (present_primary_key)
      list.add(primary_key);

    boolean present_join_with = true && (isSetJoin_with());
    list.add(present_join_with);
    if (present_join_with)
      list.add(join_with);

    return list.hashCode();
  }

  @Override
  public int compareTo(Field other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPrimary_key()).compareTo(other.isSetPrimary_key());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrimary_key()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.primary_key, other.primary_key);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetJoin_with()).compareTo(other.isSetJoin_with());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetJoin_with()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.join_with, other.join_with);
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
    StringBuilder sb = new StringBuilder("Field(");
    boolean first = true;

    if (isSetName()) {
      sb.append("name:");
      if (this.name == null) {
        sb.append("null");
      } else {
        sb.append(this.name);
      }
      first = false;
    }
    if (isSetType()) {
      if (!first) sb.append(", ");
      sb.append("type:");
      if (this.type == null) {
        sb.append("null");
      } else {
        sb.append(this.type);
      }
      first = false;
    }
    if (isSetPrimary_key()) {
      if (!first) sb.append(", ");
      sb.append("primary_key:");
      sb.append(this.primary_key);
      first = false;
    }
    if (isSetJoin_with()) {
      if (!first) sb.append(", ");
      sb.append("join_with:");
      if (this.join_with == null) {
        sb.append("null");
      } else {
        sb.append(this.join_with);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class FieldStandardSchemeFactory implements SchemeFactory {
    public FieldStandardScheme getScheme() {
      return new FieldStandardScheme();
    }
  }

  private static class FieldStandardScheme extends StandardScheme<Field> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Field struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.type = iprot.readString();
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PRIMARY_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.primary_key = iprot.readBool();
              struct.setPrimary_keyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // JOIN_WITH
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.join_with = new ArrayList<String>(_list0.size);
                String _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = iprot.readString();
                  struct.join_with.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setJoin_withIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Field struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.name != null) {
        if (struct.isSetName()) {
          oprot.writeFieldBegin(NAME_FIELD_DESC);
          oprot.writeString(struct.name);
          oprot.writeFieldEnd();
        }
      }
      if (struct.type != null) {
        if (struct.isSetType()) {
          oprot.writeFieldBegin(TYPE_FIELD_DESC);
          oprot.writeString(struct.type);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetPrimary_key()) {
        oprot.writeFieldBegin(PRIMARY_KEY_FIELD_DESC);
        oprot.writeBool(struct.primary_key);
        oprot.writeFieldEnd();
      }
      if (struct.join_with != null) {
        if (struct.isSetJoin_with()) {
          oprot.writeFieldBegin(JOIN_WITH_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.join_with.size()));
            for (String _iter3 : struct.join_with)
            {
              oprot.writeString(_iter3);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FieldTupleSchemeFactory implements SchemeFactory {
    public FieldTupleScheme getScheme() {
      return new FieldTupleScheme();
    }
  }

  private static class FieldTupleScheme extends TupleScheme<Field> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Field struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetName()) {
        optionals.set(0);
      }
      if (struct.isSetType()) {
        optionals.set(1);
      }
      if (struct.isSetPrimary_key()) {
        optionals.set(2);
      }
      if (struct.isSetJoin_with()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetName()) {
        oprot.writeString(struct.name);
      }
      if (struct.isSetType()) {
        oprot.writeString(struct.type);
      }
      if (struct.isSetPrimary_key()) {
        oprot.writeBool(struct.primary_key);
      }
      if (struct.isSetJoin_with()) {
        {
          oprot.writeI32(struct.join_with.size());
          for (String _iter4 : struct.join_with)
          {
            oprot.writeString(_iter4);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Field struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.type = iprot.readString();
        struct.setTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.primary_key = iprot.readBool();
        struct.setPrimary_keyIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.join_with = new ArrayList<String>(_list5.size);
          String _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = iprot.readString();
            struct.join_with.add(_elem6);
          }
        }
        struct.setJoin_withIsSet(true);
      }
    }
  }

}
