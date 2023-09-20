package com.aliyun.opensearch.util;
/**
 * Created by dengwx on 16/8/31.
 */

import java.util.HashMap;
import java.util.Map;

import com.aliyun.opensearch.sdk.generated.commons.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ThriftUtil.class);

  public static Map<String, String> pagableToMap(Pageable pageable) {
    Map<String, String> m = new HashMap<String, String>();
    m.put("page", String.valueOf(pageable.getPage()));
    m.put("size", String.valueOf(pageable.getSize()));
    m.put("start", String.valueOf(pageable.getStart()));
    return m;
  }
}
