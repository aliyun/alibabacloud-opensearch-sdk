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
public class Schema implements org.apache.thrift.TBase<Schema, Schema._Fields>, java.io.Serializable, Cloneable, Comparable<Schema> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Schema");

  private static final org.apache.thrift.protocol.TField TABLES_FIELD_DESC = new org.apache.thrift.protocol.TField("tables", org.apache.thrift.protocol.TType.MAP, (short)1);
  private static final org.apache.thrift.protocol.TField INDEXES_FIELD_DESC = new org.apache.thrift.protocol.TField("indexes", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField ROUTE_FIELD_FIELD_DESC = new org.apache.thrift.protocol.TField("route_field", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SchemaStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SchemaTupleSchemeFactory());
  }

  private Map<String,Table> tables; // optional
  private Indexes indexes; // optional
  private String route_field; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TABLES((short)1, "tables"),
    INDEXES((short)2, "indexes"),
    ROUTE_FIELD((short)3, "route_field");

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
        case 1: // TABLES
          return TABLES;
        case 2: // INDEXES
          return INDEXES;
        case 3: // ROUTE_FIELD
          return ROUTE_FIELD;
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
  private static final _Fields optionals[] = {_Fields.TABLES,_Fields.INDEXES,_Fields.ROUTE_FIELD};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TABLES, new org.apache.thrift.meta_data.FieldMetaData("tables", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Table.class))));
    tmpMap.put(_Fields.INDEXES, new org.apache.thrift.meta_data.FieldMetaData("indexes", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Indexes.class)));
    tmpMap.put(_Fields.ROUTE_FIELD, new org.apache.thrift.meta_data.FieldMetaData("route_field", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Schema.class, metaDataMap);
  }

  public Schema() {
  }

  public Schema(Schema other) {
    if (other.isSetTables()) {
      Map<String,Table> __this__tables = new HashMap<String,Table>(other.tables.size());
      for (Map.Entry<String, Table> other_element : other.tables.entrySet()) {

        String other_element_key = other_element.getKey();
        Table other_element_value = other_element.getValue();

        String __this__tables_copy_key = other_element_key;

        Table __this__tables_copy_value = new Table(other_element_value);

        __this__tables.put(__this__tables_copy_key, __this__tables_copy_value);
      }
      this.tables = __this__tables;
    }
    if (other.isSetIndexes()) {
      this.indexes = new Indexes(other.indexes);
    }
    if (other.isSetRoute_field()) {
      this.route_field = other.route_field;
    }
  }

  public Schema deepCopy() {
    return new Schema(this);
  }

  @Override
  public void clear() {
    this.tables = null;
    this.indexes = null;
    this.route_field = null;
  }

  public int getTablesSize() {
    return (this.tables == null) ? 0 : this.tables.size();
  }

  public void putToTables(String key, Table val) {
    if (this.tables == null) {
      this.tables = new HashMap<String,Table>();
    }
    this.tables.put(key, val);
  }

  public Map<String,Table> getTables() {
    return this.tables;
  }

  public Schema setTables(Map<String,Table> tables) {
    this.tables = tables;
    return this;
  }

  public void unsetTables() {
    this.tables = null;
  }

  /**
   * @return Returns true if field tables is set (has been assigned a value) and false otherwise
   **/
  public boolean isSetTables() {
    return this.tables != null;
  }

  public void setTablesIsSet(boolean value) {
    if (!value) {
      this.tables = null;
    }
  }

  public Indexes getIndexes() {
    return this.indexes;
  }

  public Schema setIndexes(Indexes indexes) {
    this.indexes = indexes;
    return this;
  }

  public void unsetIndexes() {
    this.indexes = null;
  }

  /**
   * @return Returns true if field indexes is set (has been assigned a value) and false otherwise
   **/
  public boolean isSetIndexes() {
    return this.indexes != null;
  }

  public void setIndexesIsSet(boolean value) {
    if (!value) {
      this.indexes = null;
    }
  }

  public String getRoute_field() {
    return this.route_field;
  }

  public Schema setRoute_field(String route_field) {
    this.route_field = route_field;
    return this;
  }

  public void unsetRoute_field() {
    this.route_field = null;
  }

  /**
   * @return Returns true if field route_field is set (has been assigned a value) and false otherwise
   **/
  public boolean isSetRoute_field() {
    return this.route_field != null;
  }

  public void setRoute_fieldIsSet(boolean value) {
    if (!value) {
      this.route_field = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TABLES:
      if (value == null) {
        unsetTables();
      } else {
        setTables((Map<String,Table>)value);
      }
      break;

    case INDEXES:
      if (value == null) {
        unsetIndexes();
      } else {
        setIndexes((Indexes)value);
      }
      break;

    case ROUTE_FIELD:
      if (value == null) {
        unsetRoute_field();
      } else {
        setRoute_field((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TABLES:
      return getTables();

    case INDEXES:
      return getIndexes();

    case ROUTE_FIELD:
      return getRoute_field();

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
    case TABLES:
      return isSetTables();
    case INDEXES:
      return isSetIndexes();
    case ROUTE_FIELD:
      return isSetRoute_field();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Schema)
      return this.equals((Schema)that);
    return false;
  }

  public boolean equals(Schema that) {
    if (that == null)
      return false;

    boolean this_present_tables = true && this.isSetTables();
    boolean that_present_tables = true && that.isSetTables();
    if (this_present_tables || that_present_tables) {
      if (!(this_present_tables && that_present_tables))
        return false;
      if (!this.tables.equals(that.tables))
        return false;
    }

    boolean this_present_indexes = true && this.isSetIndexes();
    boolean that_present_indexes = true && that.isSetIndexes();
    if (this_present_indexes || that_present_indexes) {
      if (!(this_present_indexes && that_present_indexes))
        return false;
      if (!this.indexes.equals(that.indexes))
        return false;
    }

    boolean this_present_route_field = true && this.isSetRoute_field();
    boolean that_present_route_field = true && that.isSetRoute_field();
    if (this_present_route_field || that_present_route_field) {
      if (!(this_present_route_field && that_present_route_field))
        return false;
      if (!this.route_field.equals(that.route_field))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_tables = true && (isSetTables());
    list.add(present_tables);
    if (present_tables)
      list.add(tables);

    boolean present_indexes = true && (isSetIndexes());
    list.add(present_indexes);
    if (present_indexes)
      list.add(indexes);

    boolean present_route_field = true && (isSetRoute_field());
    list.add(present_route_field);
    if (present_route_field)
      list.add(route_field);

    return list.hashCode();
  }

  @Override
  public int compareTo(Schema other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTables()).compareTo(other.isSetTables());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTables()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tables, other.tables);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIndexes()).compareTo(other.isSetIndexes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIndexes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.indexes, other.indexes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRoute_field()).compareTo(other.isSetRoute_field());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRoute_field()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.route_field, other.route_field);
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
    StringBuilder sb = new StringBuilder("Schema(");
    boolean first = true;

    if (isSetTables()) {
      sb.append("tables:");
      if (this.tables == null) {
        sb.append("null");
      } else {
        sb.append(this.tables);
      }
      first = false;
    }
    if (isSetIndexes()) {
      if (!first) sb.append(", ");
      sb.append("indexes:");
      if (this.indexes == null) {
        sb.append("null");
      } else {
        sb.append(this.indexes);
      }
      first = false;
    }
    if (isSetRoute_field()) {
      if (!first) sb.append(", ");
      sb.append("route_field:");
      if (this.route_field == null) {
        sb.append("null");
      } else {
        sb.append(this.route_field);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (indexes != null) {
      indexes.validate();
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

  private static class SchemaStandardSchemeFactory implements SchemeFactory {
    public SchemaStandardScheme getScheme() {
      return new SchemaStandardScheme();
    }
  }

  private static class SchemaStandardScheme extends StandardScheme<Schema> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Schema struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TABLES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map44 = iprot.readMapBegin();
                struct.tables = new HashMap<String,Table>(2*_map44.size);
                String _key45;
                Table _val46;
                for (int _i47 = 0; _i47 < _map44.size; ++_i47)
                {
                  _key45 = iprot.readString();
                  _val46 = new Table();
                  _val46.read(iprot);
                  struct.tables.put(_key45, _val46);
                }
                iprot.readMapEnd();
              }
              struct.setTablesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // INDEXES
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.indexes = new Indexes();
              struct.indexes.read(iprot);
              struct.setIndexesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ROUTE_FIELD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.route_field = iprot.readString();
              struct.setRoute_fieldIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Schema struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.tables != null) {
        if (struct.isSetTables()) {
          oprot.writeFieldBegin(TABLES_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.tables.size()));
            for (Map.Entry<String, Table> _iter48 : struct.tables.entrySet())
            {
              oprot.writeString(_iter48.getKey());
              _iter48.getValue().write(oprot);
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.indexes != null) {
        if (struct.isSetIndexes()) {
          oprot.writeFieldBegin(INDEXES_FIELD_DESC);
          struct.indexes.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.route_field != null) {
        if (struct.isSetRoute_field()) {
          oprot.writeFieldBegin(ROUTE_FIELD_FIELD_DESC);
          oprot.writeString(struct.route_field);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SchemaTupleSchemeFactory implements SchemeFactory {
    public SchemaTupleScheme getScheme() {
      return new SchemaTupleScheme();
    }
  }

  private static class SchemaTupleScheme extends TupleScheme<Schema> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Schema struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTables()) {
        optionals.set(0);
      }
      if (struct.isSetIndexes()) {
        optionals.set(1);
      }
      if (struct.isSetRoute_field()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetTables()) {
        {
          oprot.writeI32(struct.tables.size());
          for (Map.Entry<String, Table> _iter49 : struct.tables.entrySet())
          {
            oprot.writeString(_iter49.getKey());
            _iter49.getValue().write(oprot);
          }
        }
      }
      if (struct.isSetIndexes()) {
        struct.indexes.write(oprot);
      }
      if (struct.isSetRoute_field()) {
        oprot.writeString(struct.route_field);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Schema struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TMap _map50 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.tables = new HashMap<String,Table>(2*_map50.size);
          String _key51;
          Table _val52;
          for (int _i53 = 0; _i53 < _map50.size; ++_i53)
          {
            _key51 = iprot.readString();
            _val52 = new Table();
            _val52.read(iprot);
            struct.tables.put(_key51, _val52);
          }
        }
        struct.setTablesIsSet(true);
      }
      if (incoming.get(1)) {
        struct.indexes = new Indexes();
        struct.indexes.read(iprot);
        struct.setIndexesIsSet(true);
      }
      if (incoming.get(2)) {
        struct.route_field = iprot.readString();
        struct.setRoute_fieldIsSet(true);
      }
    }
  }

}

