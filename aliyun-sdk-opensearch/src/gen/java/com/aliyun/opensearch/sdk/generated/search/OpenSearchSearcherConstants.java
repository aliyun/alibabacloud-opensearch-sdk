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
public class OpenSearchSearcherConstants {

  public static final String CONFIG_CLAUSE_START = "start";

  public static final String CONFIG_CLAUSE_HIT = "hit";

  public static final String CONFIG_CLAUSE_RERANK_SIZE = "rerank_size";

  public static final String CONFIG_CLAUSE_FORMAT = "format";

  public static final String SORT_CLAUSE_INCREASE = "+";

  public static final String SORT_CLAUSE_DECREASE = "-";

  public static final String SORT_CLAUSE_RANK = "RANK";

  public static final String DISTINCT_CLAUSE_DIST_KEY = "dist_key";

  public static final String DISTINCT_CLAUSE_DIST_COUNT = "dist_count";

  public static final String DISTINCT_CLAUSE_DIST_TIMES = "dist_times";

  public static final String DISTINCT_CLAUSE_RESERVED = "reserved";

  public static final String DISTINCT_CLAUSE_DIST_FILTER = "dist_filter";

  public static final String DISTINCT_CLAUSE_UPDATE_TOTAL_HIT = "update_total_hit";

  public static final String DISTINCT_CLAUSE_GRADE = "grade";

  public static final String DISTINCT_CLAUSE_DIST_TYPE = "dist_type";

  public static final String DISTINCT_CLAUSE_DIST_SORT = "dist_sort";

  public static final String DISTINCT_CLAUSE_DIST_SPECIAL_COUNT = "dist_special_count";

  public static final String AGGREGATE_CLAUSE_GROUP_KEY = "group_key";

  public static final String AGGREGATE_CLAUSE_AGG_FUN = "agg_fun";

  public static final String AGGREGATE_CLAUSE_RANGE = "range";

  public static final String AGGREGATE_CLAUSE_MAX_GROUP = "max_group";

  public static final String AGGREGATE_CLAUSE_AGG_FILTER = "agg_filter";

  public static final String AGGREGATE_CLAUSE_AGG_SAMPLER_THRESHOLD = "agg_sampler_threshold";

  public static final String AGGREGATE_CLAUSE_AGG_SAMPLER_STEP = "agg_sampler_step";

  public static final String SUMMARY_PARAM_SUMMARY_FIELD = "summary_field";

  public static final String SUMMARY_PARAM_SUMMARY_LEN = "summary_len";

  public static final String SUMMARY_PARAM_SUMMARY_ELLIPSIS = "summary_ellipsis";

  public static final String SUMMARY_PARAM_SUMMARY_SNIPPET = "summary_snippet";

  public static final String SUMMARY_PARAM_SUMMARY_ELEMENT = "summary_element";

  public static final String SUMMARY_PARAM_SUMMARY_ELEMENT_PREFIX = "summary_element_prefix";

  public static final String SUMMARY_PARAM_SUMMARY_ELEMENT_POSTFIX = "summary_element_postfix";

  public static final String FORMAT_PARAM = "format";

  public static final String ABTEST_PARAM_SCENE_TAG = "scene_tag";

  public static final String ABTEST_PARAM_FLOW_DIVIDER = "flow_divider";

  public static final String USER_ID = "user_id";

  public static final String RAW_QUERY = "raw_query";

}
