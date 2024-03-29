/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.aliyun.opensearch.sdk.generated.search;

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
/**
 * 添加统计信息(aggregate)
 * 
 * 一个关键词通常能命中数以万计的文档，用户不太可能浏览所有文档来获取信息。而用户感兴趣的
 * 可 能是一些统计类的信息，比如，查询“手机”这个关键词，想知道每个卖家所有商品中的最高价格。
 * 则可以按照卖家的user_id分组，统计每个小组中最大的price值，例如：
 * groupKey:user_id,aggFun:max(price)
 * 
 * 相关wiki，请查询：
 * @link http://docs.aliyun.com/?spm=5176.2020520121.103.8.VQIcGd&tag=tun#/pub/opensearch/api-reference/query-clause&aggregate-clause
 * 
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2018-08-17")
public class Aggregate implements org.apache.thrift.TBase<Aggregate, Aggregate._Fields>, java.io.Serializable, Cloneable, Comparable<Aggregate> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Aggregate");

  private static final org.apache.thrift.protocol.TField GROUP_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("groupKey", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField AGG_FUN_FIELD_DESC = new org.apache.thrift.protocol.TField("aggFun", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField RANGE_FIELD_DESC = new org.apache.thrift.protocol.TField("range", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField MAX_GROUP_FIELD_DESC = new org.apache.thrift.protocol.TField("maxGroup", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField AGG_FILTER_FIELD_DESC = new org.apache.thrift.protocol.TField("aggFilter", org.apache.thrift.protocol.TType.STRING, (short)9);
  private static final org.apache.thrift.protocol.TField AGG_SAMPLER_THRES_HOLD_FIELD_DESC = new org.apache.thrift.protocol.TField("aggSamplerThresHold", org.apache.thrift.protocol.TType.STRING, (short)11);
  private static final org.apache.thrift.protocol.TField AGG_SAMPLER_STEP_FIELD_DESC = new org.apache.thrift.protocol.TField("aggSamplerStep", org.apache.thrift.protocol.TType.STRING, (short)13);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new AggregateStandardSchemeFactory());
    schemes.put(TupleScheme.class, new AggregateTupleSchemeFactory());
  }

  private String groupKey; // required
  private String aggFun; // optional
  private String range; // optional
  private String maxGroup; // optional
  private String aggFilter; // optional
  private String aggSamplerThresHold; // optional
  private String aggSamplerStep; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 指定需要统计的字段名称。
     * 
     */
    GROUP_KEY((short)1, "groupKey"),
    /**
     * 指定统计的方法。当前支持：count、max、min、sum等。
     * 
     */
    AGG_FUN((short)3, "aggFun"),
    /**
     * 指定统计范围。
     * 
     */
    RANGE((short)5, "range"),
    /**
     * 最大组个数。
     * 
     */
    MAX_GROUP((short)7, "maxGroup"),
    /**
     * 指定过滤某些统计。
     * 
     */
    AGG_FILTER((short)9, "aggFilter"),
    /**
     * 指定抽样的阈值。
     * 
     */
    AGG_SAMPLER_THRES_HOLD((short)11, "aggSamplerThresHold"),
    /**
     * 指定抽样的步长。
     * 
     */
    AGG_SAMPLER_STEP((short)13, "aggSamplerStep");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // GROUP_KEY
          return GROUP_KEY;
        case 3: // AGG_FUN
          return AGG_FUN;
        case 5: // RANGE
          return RANGE;
        case 7: // MAX_GROUP
          return MAX_GROUP;
        case 9: // AGG_FILTER
          return AGG_FILTER;
        case 11: // AGG_SAMPLER_THRES_HOLD
          return AGG_SAMPLER_THRES_HOLD;
        case 13: // AGG_SAMPLER_STEP
          return AGG_SAMPLER_STEP;
        default:
          return null;
      }
    }

    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

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
  private static final _Fields optionals[] = {_Fields.AGG_FUN,_Fields.RANGE,_Fields.MAX_GROUP,_Fields.AGG_FILTER,_Fields.AGG_SAMPLER_THRES_HOLD,_Fields.AGG_SAMPLER_STEP};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.GROUP_KEY, new org.apache.thrift.meta_data.FieldMetaData("groupKey", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AGG_FUN, new org.apache.thrift.meta_data.FieldMetaData("aggFun", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RANGE, new org.apache.thrift.meta_data.FieldMetaData("range", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MAX_GROUP, new org.apache.thrift.meta_data.FieldMetaData("maxGroup", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AGG_FILTER, new org.apache.thrift.meta_data.FieldMetaData("aggFilter", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AGG_SAMPLER_THRES_HOLD, new org.apache.thrift.meta_data.FieldMetaData("aggSamplerThresHold", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AGG_SAMPLER_STEP, new org.apache.thrift.meta_data.FieldMetaData("aggSamplerStep", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Aggregate.class, metaDataMap);
  }

  public Aggregate() {
  }

  public Aggregate(
    String groupKey)
  {
    this();
    this.groupKey = groupKey;
  }

  public Aggregate(Aggregate other) {
    if (other.isSetGroupKey()) {
      this.groupKey = other.groupKey;
    }
    if (other.isSetAggFun()) {
      this.aggFun = other.aggFun;
    }
    if (other.isSetRange()) {
      this.range = other.range;
    }
    if (other.isSetMaxGroup()) {
      this.maxGroup = other.maxGroup;
    }
    if (other.isSetAggFilter()) {
      this.aggFilter = other.aggFilter;
    }
    if (other.isSetAggSamplerThresHold()) {
      this.aggSamplerThresHold = other.aggSamplerThresHold;
    }
    if (other.isSetAggSamplerStep()) {
      this.aggSamplerStep = other.aggSamplerStep;
    }
  }

  public Aggregate deepCopy() {
    return new Aggregate(this);
  }

  @Override
  public void clear() {
    this.groupKey = null;
    this.aggFun = null;
    this.range = null;
    this.maxGroup = null;
    this.aggFilter = null;
    this.aggSamplerThresHold = null;
    this.aggSamplerStep = null;
  }

  public String getGroupKey() {
    return this.groupKey;
  }

  public Aggregate setGroupKey(String groupKey) {
    this.groupKey = groupKey;
    return this;
  }

  public void unsetGroupKey() {
    this.groupKey = null;
  }

  public boolean isSetGroupKey() {
    return this.groupKey != null;
  }

  public void setGroupKeyIsSet(boolean value) {
    if (!value) {
      this.groupKey = null;
    }
  }

  public String getAggFun() {
    return this.aggFun;
  }

  public Aggregate setAggFun(String aggFun) {
    this.aggFun = aggFun;
    return this;
  }

  public void unsetAggFun() {
    this.aggFun = null;
  }

  public boolean isSetAggFun() {
    return this.aggFun != null;
  }

  public void setAggFunIsSet(boolean value) {
    if (!value) {
      this.aggFun = null;
    }
  }

  public String getRange() {
    return this.range;
  }

  public Aggregate setRange(String range) {
    this.range = range;
    return this;
  }

  public void unsetRange() {
    this.range = null;
  }

  public boolean isSetRange() {
    return this.range != null;
  }

  public void setRangeIsSet(boolean value) {
    if (!value) {
      this.range = null;
    }
  }

  public String getMaxGroup() {
    return this.maxGroup;
  }

  public Aggregate setMaxGroup(String maxGroup) {
    this.maxGroup = maxGroup;
    return this;
  }

  public void unsetMaxGroup() {
    this.maxGroup = null;
  }

  public boolean isSetMaxGroup() {
    return this.maxGroup != null;
  }

  public void setMaxGroupIsSet(boolean value) {
    if (!value) {
      this.maxGroup = null;
    }
  }

  public String getAggFilter() {
    return this.aggFilter;
  }

  public Aggregate setAggFilter(String aggFilter) {
    this.aggFilter = aggFilter;
    return this;
  }

  public void unsetAggFilter() {
    this.aggFilter = null;
  }

  public boolean isSetAggFilter() {
    return this.aggFilter != null;
  }

  public void setAggFilterIsSet(boolean value) {
    if (!value) {
      this.aggFilter = null;
    }
  }

  public String getAggSamplerThresHold() {
    return this.aggSamplerThresHold;
  }

  public Aggregate setAggSamplerThresHold(String aggSamplerThresHold) {
    this.aggSamplerThresHold = aggSamplerThresHold;
    return this;
  }

  public void unsetAggSamplerThresHold() {
    this.aggSamplerThresHold = null;
  }

  public boolean isSetAggSamplerThresHold() {
    return this.aggSamplerThresHold != null;
  }

  public void setAggSamplerThresHoldIsSet(boolean value) {
    if (!value) {
      this.aggSamplerThresHold = null;
    }
  }

  public String getAggSamplerStep() {
    return this.aggSamplerStep;
  }

  public Aggregate setAggSamplerStep(String aggSamplerStep) {
    this.aggSamplerStep = aggSamplerStep;
    return this;
  }

  public void unsetAggSamplerStep() {
    this.aggSamplerStep = null;
  }

  public boolean isSetAggSamplerStep() {
    return this.aggSamplerStep != null;
  }

  public void setAggSamplerStepIsSet(boolean value) {
    if (!value) {
      this.aggSamplerStep = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case GROUP_KEY:
      if (value == null) {
        unsetGroupKey();
      } else {
        setGroupKey((String)value);
      }
      break;

    case AGG_FUN:
      if (value == null) {
        unsetAggFun();
      } else {
        setAggFun((String)value);
      }
      break;

    case RANGE:
      if (value == null) {
        unsetRange();
      } else {
        setRange((String)value);
      }
      break;

    case MAX_GROUP:
      if (value == null) {
        unsetMaxGroup();
      } else {
        setMaxGroup((String)value);
      }
      break;

    case AGG_FILTER:
      if (value == null) {
        unsetAggFilter();
      } else {
        setAggFilter((String)value);
      }
      break;

    case AGG_SAMPLER_THRES_HOLD:
      if (value == null) {
        unsetAggSamplerThresHold();
      } else {
        setAggSamplerThresHold((String)value);
      }
      break;

    case AGG_SAMPLER_STEP:
      if (value == null) {
        unsetAggSamplerStep();
      } else {
        setAggSamplerStep((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case GROUP_KEY:
      return getGroupKey();

    case AGG_FUN:
      return getAggFun();

    case RANGE:
      return getRange();

    case MAX_GROUP:
      return getMaxGroup();

    case AGG_FILTER:
      return getAggFilter();

    case AGG_SAMPLER_THRES_HOLD:
      return getAggSamplerThresHold();

    case AGG_SAMPLER_STEP:
      return getAggSamplerStep();

    }
    throw new IllegalStateException();
  }

  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case GROUP_KEY:
      return isSetGroupKey();
    case AGG_FUN:
      return isSetAggFun();
    case RANGE:
      return isSetRange();
    case MAX_GROUP:
      return isSetMaxGroup();
    case AGG_FILTER:
      return isSetAggFilter();
    case AGG_SAMPLER_THRES_HOLD:
      return isSetAggSamplerThresHold();
    case AGG_SAMPLER_STEP:
      return isSetAggSamplerStep();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Aggregate)
      return this.equals((Aggregate)that);
    return false;
  }

  public boolean equals(Aggregate that) {
    if (that == null)
      return false;

    boolean this_present_groupKey = true && this.isSetGroupKey();
    boolean that_present_groupKey = true && that.isSetGroupKey();
    if (this_present_groupKey || that_present_groupKey) {
      if (!(this_present_groupKey && that_present_groupKey))
        return false;
      if (!this.groupKey.equals(that.groupKey))
        return false;
    }

    boolean this_present_aggFun = true && this.isSetAggFun();
    boolean that_present_aggFun = true && that.isSetAggFun();
    if (this_present_aggFun || that_present_aggFun) {
      if (!(this_present_aggFun && that_present_aggFun))
        return false;
      if (!this.aggFun.equals(that.aggFun))
        return false;
    }

    boolean this_present_range = true && this.isSetRange();
    boolean that_present_range = true && that.isSetRange();
    if (this_present_range || that_present_range) {
      if (!(this_present_range && that_present_range))
        return false;
      if (!this.range.equals(that.range))
        return false;
    }

    boolean this_present_maxGroup = true && this.isSetMaxGroup();
    boolean that_present_maxGroup = true && that.isSetMaxGroup();
    if (this_present_maxGroup || that_present_maxGroup) {
      if (!(this_present_maxGroup && that_present_maxGroup))
        return false;
      if (!this.maxGroup.equals(that.maxGroup))
        return false;
    }

    boolean this_present_aggFilter = true && this.isSetAggFilter();
    boolean that_present_aggFilter = true && that.isSetAggFilter();
    if (this_present_aggFilter || that_present_aggFilter) {
      if (!(this_present_aggFilter && that_present_aggFilter))
        return false;
      if (!this.aggFilter.equals(that.aggFilter))
        return false;
    }

    boolean this_present_aggSamplerThresHold = true && this.isSetAggSamplerThresHold();
    boolean that_present_aggSamplerThresHold = true && that.isSetAggSamplerThresHold();
    if (this_present_aggSamplerThresHold || that_present_aggSamplerThresHold) {
      if (!(this_present_aggSamplerThresHold && that_present_aggSamplerThresHold))
        return false;
      if (!this.aggSamplerThresHold.equals(that.aggSamplerThresHold))
        return false;
    }

    boolean this_present_aggSamplerStep = true && this.isSetAggSamplerStep();
    boolean that_present_aggSamplerStep = true && that.isSetAggSamplerStep();
    if (this_present_aggSamplerStep || that_present_aggSamplerStep) {
      if (!(this_present_aggSamplerStep && that_present_aggSamplerStep))
        return false;
      if (!this.aggSamplerStep.equals(that.aggSamplerStep))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_groupKey = true && (isSetGroupKey());
    list.add(present_groupKey);
    if (present_groupKey)
      list.add(groupKey);

    boolean present_aggFun = true && (isSetAggFun());
    list.add(present_aggFun);
    if (present_aggFun)
      list.add(aggFun);

    boolean present_range = true && (isSetRange());
    list.add(present_range);
    if (present_range)
      list.add(range);

    boolean present_maxGroup = true && (isSetMaxGroup());
    list.add(present_maxGroup);
    if (present_maxGroup)
      list.add(maxGroup);

    boolean present_aggFilter = true && (isSetAggFilter());
    list.add(present_aggFilter);
    if (present_aggFilter)
      list.add(aggFilter);

    boolean present_aggSamplerThresHold = true && (isSetAggSamplerThresHold());
    list.add(present_aggSamplerThresHold);
    if (present_aggSamplerThresHold)
      list.add(aggSamplerThresHold);

    boolean present_aggSamplerStep = true && (isSetAggSamplerStep());
    list.add(present_aggSamplerStep);
    if (present_aggSamplerStep)
      list.add(aggSamplerStep);

    return list.hashCode();
  }

  @Override
  public int compareTo(Aggregate other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetGroupKey()).compareTo(other.isSetGroupKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroupKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.groupKey, other.groupKey);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAggFun()).compareTo(other.isSetAggFun());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAggFun()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.aggFun, other.aggFun);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRange()).compareTo(other.isSetRange());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRange()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.range, other.range);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMaxGroup()).compareTo(other.isSetMaxGroup());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMaxGroup()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.maxGroup, other.maxGroup);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAggFilter()).compareTo(other.isSetAggFilter());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAggFilter()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.aggFilter, other.aggFilter);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAggSamplerThresHold()).compareTo(other.isSetAggSamplerThresHold());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAggSamplerThresHold()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.aggSamplerThresHold, other.aggSamplerThresHold);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAggSamplerStep()).compareTo(other.isSetAggSamplerStep());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAggSamplerStep()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.aggSamplerStep, other.aggSamplerStep);
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
    StringBuilder sb = new StringBuilder("Aggregate(");
    boolean first = true;

    sb.append("groupKey:");
    if (this.groupKey == null) {
      sb.append("null");
    } else {
      sb.append(this.groupKey);
    }
    first = false;
    if (isSetAggFun()) {
      if (!first) sb.append(", ");
      sb.append("aggFun:");
      if (this.aggFun == null) {
        sb.append("null");
      } else {
        sb.append(this.aggFun);
      }
      first = false;
    }
    if (isSetRange()) {
      if (!first) sb.append(", ");
      sb.append("range:");
      if (this.range == null) {
        sb.append("null");
      } else {
        sb.append(this.range);
      }
      first = false;
    }
    if (isSetMaxGroup()) {
      if (!first) sb.append(", ");
      sb.append("maxGroup:");
      if (this.maxGroup == null) {
        sb.append("null");
      } else {
        sb.append(this.maxGroup);
      }
      first = false;
    }
    if (isSetAggFilter()) {
      if (!first) sb.append(", ");
      sb.append("aggFilter:");
      if (this.aggFilter == null) {
        sb.append("null");
      } else {
        sb.append(this.aggFilter);
      }
      first = false;
    }
    if (isSetAggSamplerThresHold()) {
      if (!first) sb.append(", ");
      sb.append("aggSamplerThresHold:");
      if (this.aggSamplerThresHold == null) {
        sb.append("null");
      } else {
        sb.append(this.aggSamplerThresHold);
      }
      first = false;
    }
    if (isSetAggSamplerStep()) {
      if (!first) sb.append(", ");
      sb.append("aggSamplerStep:");
      if (this.aggSamplerStep == null) {
        sb.append("null");
      } else {
        sb.append(this.aggSamplerStep);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (groupKey == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'groupKey' was not present! Struct: " + toString());
    }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class AggregateStandardSchemeFactory implements SchemeFactory {
    public AggregateStandardScheme getScheme() {
      return new AggregateStandardScheme();
    }
  }

  private static class AggregateStandardScheme extends StandardScheme<Aggregate> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Aggregate struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // GROUP_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.groupKey = iprot.readString();
              struct.setGroupKeyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // AGG_FUN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.aggFun = iprot.readString();
              struct.setAggFunIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // RANGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.range = iprot.readString();
              struct.setRangeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // MAX_GROUP
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.maxGroup = iprot.readString();
              struct.setMaxGroupIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // AGG_FILTER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.aggFilter = iprot.readString();
              struct.setAggFilterIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 11: // AGG_SAMPLER_THRES_HOLD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.aggSamplerThresHold = iprot.readString();
              struct.setAggSamplerThresHoldIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 13: // AGG_SAMPLER_STEP
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.aggSamplerStep = iprot.readString();
              struct.setAggSamplerStepIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Aggregate struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.groupKey != null) {
        oprot.writeFieldBegin(GROUP_KEY_FIELD_DESC);
        oprot.writeString(struct.groupKey);
        oprot.writeFieldEnd();
      }
      if (struct.aggFun != null) {
        if (struct.isSetAggFun()) {
          oprot.writeFieldBegin(AGG_FUN_FIELD_DESC);
          oprot.writeString(struct.aggFun);
          oprot.writeFieldEnd();
        }
      }
      if (struct.range != null) {
        if (struct.isSetRange()) {
          oprot.writeFieldBegin(RANGE_FIELD_DESC);
          oprot.writeString(struct.range);
          oprot.writeFieldEnd();
        }
      }
      if (struct.maxGroup != null) {
        if (struct.isSetMaxGroup()) {
          oprot.writeFieldBegin(MAX_GROUP_FIELD_DESC);
          oprot.writeString(struct.maxGroup);
          oprot.writeFieldEnd();
        }
      }
      if (struct.aggFilter != null) {
        if (struct.isSetAggFilter()) {
          oprot.writeFieldBegin(AGG_FILTER_FIELD_DESC);
          oprot.writeString(struct.aggFilter);
          oprot.writeFieldEnd();
        }
      }
      if (struct.aggSamplerThresHold != null) {
        if (struct.isSetAggSamplerThresHold()) {
          oprot.writeFieldBegin(AGG_SAMPLER_THRES_HOLD_FIELD_DESC);
          oprot.writeString(struct.aggSamplerThresHold);
          oprot.writeFieldEnd();
        }
      }
      if (struct.aggSamplerStep != null) {
        if (struct.isSetAggSamplerStep()) {
          oprot.writeFieldBegin(AGG_SAMPLER_STEP_FIELD_DESC);
          oprot.writeString(struct.aggSamplerStep);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class AggregateTupleSchemeFactory implements SchemeFactory {
    public AggregateTupleScheme getScheme() {
      return new AggregateTupleScheme();
    }
  }

  private static class AggregateTupleScheme extends TupleScheme<Aggregate> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Aggregate struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.groupKey);
      BitSet optionals = new BitSet();
      if (struct.isSetAggFun()) {
        optionals.set(0);
      }
      if (struct.isSetRange()) {
        optionals.set(1);
      }
      if (struct.isSetMaxGroup()) {
        optionals.set(2);
      }
      if (struct.isSetAggFilter()) {
        optionals.set(3);
      }
      if (struct.isSetAggSamplerThresHold()) {
        optionals.set(4);
      }
      if (struct.isSetAggSamplerStep()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetAggFun()) {
        oprot.writeString(struct.aggFun);
      }
      if (struct.isSetRange()) {
        oprot.writeString(struct.range);
      }
      if (struct.isSetMaxGroup()) {
        oprot.writeString(struct.maxGroup);
      }
      if (struct.isSetAggFilter()) {
        oprot.writeString(struct.aggFilter);
      }
      if (struct.isSetAggSamplerThresHold()) {
        oprot.writeString(struct.aggSamplerThresHold);
      }
      if (struct.isSetAggSamplerStep()) {
        oprot.writeString(struct.aggSamplerStep);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Aggregate struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.groupKey = iprot.readString();
      struct.setGroupKeyIsSet(true);
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.aggFun = iprot.readString();
        struct.setAggFunIsSet(true);
      }
      if (incoming.get(1)) {
        struct.range = iprot.readString();
        struct.setRangeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.maxGroup = iprot.readString();
        struct.setMaxGroupIsSet(true);
      }
      if (incoming.get(3)) {
        struct.aggFilter = iprot.readString();
        struct.setAggFilterIsSet(true);
      }
      if (incoming.get(4)) {
        struct.aggSamplerThresHold = iprot.readString();
        struct.setAggSamplerThresHoldIsSet(true);
      }
      if (incoming.get(5)) {
        struct.aggSamplerStep = iprot.readString();
        struct.setAggSamplerStepIsSet(true);
      }
    }
  }

}

