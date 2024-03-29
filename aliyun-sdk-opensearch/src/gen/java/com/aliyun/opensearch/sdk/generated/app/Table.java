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
public class Table implements org.apache.thrift.TBase<Table, Table._Fields>, java.io.Serializable, Cloneable, Comparable<Table> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Table");

  private static final org.apache.thrift.protocol.TField FIELDS_FIELD_DESC = new org.apache.thrift.protocol.TField("fields", org.apache.thrift.protocol.TType.MAP, (short)1);
  private static final org.apache.thrift.protocol.TField PRIMARY_TABLE_FIELD_DESC = new org.apache.thrift.protocol.TField("primary_table", org.apache.thrift.protocol.TType.BOOL, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TableStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TableTupleSchemeFactory());
  }

  private Map<String,Field> fields; // optional
  private boolean primary_table; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FIELDS((short)1, "fields"),
    PRIMARY_TABLE((short)2, "primary_table");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * @param fieldId   fieldId
     * @return Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // FIELDS
          return FIELDS;
        case 2: // PRIMARY_TABLE
          return PRIMARY_TABLE;
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
  private static final int __PRIMARY_TABLE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.FIELDS,_Fields.PRIMARY_TABLE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FIELDS, new org.apache.thrift.meta_data.FieldMetaData("fields", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Field.class))));
    tmpMap.put(_Fields.PRIMARY_TABLE, new org.apache.thrift.meta_data.FieldMetaData("primary_table", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Table.class, metaDataMap);
  }

  public Table() {
  }

  public Table(Table other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetFields()) {
      Map<String,Field> __this__fields = new HashMap<String,Field>(other.fields.size());
      for (Map.Entry<String, Field> other_element : other.fields.entrySet()) {

        String other_element_key = other_element.getKey();
        Field other_element_value = other_element.getValue();

        String __this__fields_copy_key = other_element_key;

        Field __this__fields_copy_value = new Field(other_element_value);

        __this__fields.put(__this__fields_copy_key, __this__fields_copy_value);
      }
      this.fields = __this__fields;
    }
    this.primary_table = other.primary_table;
  }

  public Table deepCopy() {
    return new Table(this);
  }

  @Override
  public void clear() {
    this.fields = null;
    setPrimary_tableIsSet(false);
    this.primary_table = false;
  }

  public int getFieldsSize() {
    return (this.fields == null) ? 0 : this.fields.size();
  }

  public void putToFields(String key, Field val) {
    if (this.fields == null) {
      this.fields = new HashMap<String,Field>();
    }
    this.fields.put(key, val);
  }

  public Map<String,Field> getFields() {
    return this.fields;
  }

  public Table setFields(Map<String,Field> fields) {
    this.fields = fields;
    return this;
  }

  public void unsetFields() {
    this.fields = null;
  }

  /**
   * @return Returns true if field fields is set (has been assigned a value) and false otherwise
   **/
  public boolean isSetFields() {
    return this.fields != null;
  }

  public void setFieldsIsSet(boolean value) {
    if (!value) {
      this.fields = null;
    }
  }

  public boolean isPrimary_table() {
    return this.primary_table;
  }

  public Table setPrimary_table(boolean primary_table) {
    this.primary_table = primary_table;
    setPrimary_tableIsSet(true);
    return this;
  }

  public void unsetPrimary_table() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRIMARY_TABLE_ISSET_ID);
  }

  /**
   * @return Returns true if field primary_table is set (has been assigned a value) and false otherwise
   **/
  public boolean isSetPrimary_table() {
    return EncodingUtils.testBit(__isset_bitfield, __PRIMARY_TABLE_ISSET_ID);
  }

  public void setPrimary_tableIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRIMARY_TABLE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case FIELDS:
      if (value == null) {
        unsetFields();
      } else {
        setFields((Map<String,Field>)value);
      }
      break;

    case PRIMARY_TABLE:
      if (value == null) {
        unsetPrimary_table();
      } else {
        setPrimary_table((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case FIELDS:
      return getFields();

    case PRIMARY_TABLE:
      return isPrimary_table();

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
    case FIELDS:
      return isSetFields();
    case PRIMARY_TABLE:
      return isSetPrimary_table();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Table)
      return this.equals((Table)that);
    return false;
  }

  public boolean equals(Table that) {
    if (that == null)
      return false;

    boolean this_present_fields = true && this.isSetFields();
    boolean that_present_fields = true && that.isSetFields();
    if (this_present_fields || that_present_fields) {
      if (!(this_present_fields && that_present_fields))
        return false;
      if (!this.fields.equals(that.fields))
        return false;
    }

    boolean this_present_primary_table = true && this.isSetPrimary_table();
    boolean that_present_primary_table = true && that.isSetPrimary_table();
    if (this_present_primary_table || that_present_primary_table) {
      if (!(this_present_primary_table && that_present_primary_table))
        return false;
      if (this.primary_table != that.primary_table)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_fields = true && (isSetFields());
    list.add(present_fields);
    if (present_fields)
      list.add(fields);

    boolean present_primary_table = true && (isSetPrimary_table());
    list.add(present_primary_table);
    if (present_primary_table)
      list.add(primary_table);

    return list.hashCode();
  }

  @Override
  public int compareTo(Table other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetFields()).compareTo(other.isSetFields());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFields()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fields, other.fields);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPrimary_table()).compareTo(other.isSetPrimary_table());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrimary_table()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.primary_table, other.primary_table);
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
    StringBuilder sb = new StringBuilder("Table(");
    boolean first = true;

    if (isSetFields()) {
      sb.append("fields:");
      if (this.fields == null) {
        sb.append("null");
      } else {
        sb.append(this.fields);
      }
      first = false;
    }
    if (isSetPrimary_table()) {
      if (!first) sb.append(", ");
      sb.append("primary_table:");
      sb.append(this.primary_table);
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

  private static class TableStandardSchemeFactory implements SchemeFactory {
    public TableStandardScheme getScheme() {
      return new TableStandardScheme();
    }
  }

  private static class TableStandardScheme extends StandardScheme<Table> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Table struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FIELDS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map8 = iprot.readMapBegin();
                struct.fields = new HashMap<String,Field>(2*_map8.size);
                String _key9;
                Field _val10;
                for (int _i11 = 0; _i11 < _map8.size; ++_i11)
                {
                  _key9 = iprot.readString();
                  _val10 = new Field();
                  _val10.read(iprot);
                  struct.fields.put(_key9, _val10);
                }
                iprot.readMapEnd();
              }
              struct.setFieldsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PRIMARY_TABLE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.primary_table = iprot.readBool();
              struct.setPrimary_tableIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Table struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.fields != null) {
        if (struct.isSetFields()) {
          oprot.writeFieldBegin(FIELDS_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.fields.size()));
            for (Map.Entry<String, Field> _iter12 : struct.fields.entrySet())
            {
              oprot.writeString(_iter12.getKey());
              _iter12.getValue().write(oprot);
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetPrimary_table()) {
        oprot.writeFieldBegin(PRIMARY_TABLE_FIELD_DESC);
        oprot.writeBool(struct.primary_table);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TableTupleSchemeFactory implements SchemeFactory {
    public TableTupleScheme getScheme() {
      return new TableTupleScheme();
    }
  }

  private static class TableTupleScheme extends TupleScheme<Table> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Table struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetFields()) {
        optionals.set(0);
      }
      if (struct.isSetPrimary_table()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetFields()) {
        {
          oprot.writeI32(struct.fields.size());
          for (Map.Entry<String, Field> _iter13 : struct.fields.entrySet())
          {
            oprot.writeString(_iter13.getKey());
            _iter13.getValue().write(oprot);
          }
        }
      }
      if (struct.isSetPrimary_table()) {
        oprot.writeBool(struct.primary_table);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Table struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TMap _map14 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.fields = new HashMap<String,Field>(2*_map14.size);
          String _key15;
          Field _val16;
          for (int _i17 = 0; _i17 < _map14.size; ++_i17)
          {
            _key15 = iprot.readString();
            _val16 = new Field();
            _val16.read(iprot);
            struct.fields.put(_key15, _val16);
          }
        }
        struct.setFieldsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.primary_table = iprot.readBool();
        struct.setPrimary_tableIsSet(true);
      }
    }
  }

}

