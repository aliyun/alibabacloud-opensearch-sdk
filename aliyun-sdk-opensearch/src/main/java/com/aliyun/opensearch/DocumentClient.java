package com.aliyun.opensearch;
/**
 * Created by dengwx on 16/8/29.
 */

import java.util.Map;
import java.util.Map.Entry;

import com.aliyun.opensearch.client.ResourceClient;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchClientException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchException;
import com.aliyun.opensearch.sdk.generated.commons.OpenSearchResult;
import com.aliyun.opensearch.sdk.generated.document.Command;
import com.aliyun.opensearch.sdk.generated.document.DocumentConstants;
import com.aliyun.opensearch.sdk.generated.document.DocumentService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentClient implements DocumentService.Iface {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentClient.class);

  private ResourceClient resourceClient;

  /**
   * 进行提交的数据
   */
  private JSONArray docBuffer = new JSONArray();

  /**
   * 构造函数
   *
   * @param client CloudsearchClient实例。
   */
  public DocumentClient(OpenSearchClient client) {
    this.resourceClient = new ResourceClient("/apps", client);
  }

  /**
   * 添加文档
   *
   * 设置需要添加的属性名称和属性值，用于生成符合文档格式的数据，所有更新结束之后需要调用push(String tableName)方法
   *
   * @param fields 字段名和字段值的map
   */
  public void add(Map<String, Object> fields) {
    pushOneDoc(fields, Command.ADD);
  }

  /**
   * 更新文档
   *
   * 设置需要更新的属性名称和属性值，用于生成符合文档格式的数据，所有更新结束之后需要调用push(String tableName)方法
   * 标准版不支持update
   *
   * @param fields 字段名和字段值的map
   */
  public void update(Map<String, Object> fields) {
    pushOneDoc(fields, Command.UPDATE);
  }

  /**
   * 删除文档
   *
   * 设置需要删除的属性名称和属性值，用于生成符合文档格式的数据，所有更新结束之后需要调用push(String tableName)方法
   *
   * @param fields 字段名和字段值的map
   */
  public void remove(Map<String, Object> fields) {
    pushOneDoc(fields, Command.DELETE);
  }

  private void pushOneDoc(Map<String, Object> fields, Command command) {
    JSONObject JSONFields = new JSONObject();
    for (Entry<String, Object> entry : fields.entrySet()) {
      JSONFields.put(entry.getKey(), entry.getValue());
    }
    JSONObject json = new JSONObject();
    json.put(DocumentConstants.DOC_KEY_CMD, command.toString());
    json.put(DocumentConstants.DOC_KEY_FIELDS, JSONFields);
    docBuffer.put(json);
  }

  /**
   * 执行文档变更操作(1)
   *
   * 针对文档的操作add、update和remove会生成符合文档格式的数据，通过调用此接口用户提交的文档变更才会真正生效。
   *
   * @param appName 应用名
   * @param tableName 表名称
   * @throws OpenSearchException       OpenSearchException
   * @throws OpenSearchClientException OpenSearchClientException
   * @return 返回的数据
   */
	public OpenSearchResult commit(String appName, String tableName)
			throws OpenSearchException, OpenSearchClientException {
		OpenSearchResult result;
		try {
			result = doPush(docBuffer, appName, tableName);
		} finally {
			docBuffer = new JSONArray();
		}
		return result;
	}

  /**
   * 执行文档变更操作(2)
   *
   * 通过此接口可以直接将符合文档格式的数据直接推送到指定的表中
   *
   * @param docsJson 此docs为用户push的数据，此字段为json类型的字符串。
   * @param appName 应用名
   * @param tableName 操作的表名。
   * @return 请求API并返回相应的结果。
   */
  @Override
  public OpenSearchResult push(String docsJson, String appName, String tableName) throws OpenSearchException, OpenSearchClientException {
    JSONArray docs = new JSONArray(docsJson);// verify user input.
    return doPush(docs, appName, tableName);
  }

    /**
     * 执行文档变更操作(3)
     *
     * 通过此接口可以直接将符合文档格式的多条数据直接推送到指定的表中
     *
     * @param docsJsonArray 多条文档
     * @param appName 应用名
     * @param tableName 操作的表名
     * @return OpenSearchResult
     * @throws OpenSearchException OpenSearchException
     * @throws OpenSearchClientException OpenSearchClientException
     */
  @Override
  public OpenSearchResult push(JSONArray docsJsonArray, String appName, String tableName) throws OpenSearchException, OpenSearchClientException {
      return doPush(docsJsonArray, appName, tableName);
  }

  private OpenSearchResult doPush(JSONArray jsonArray, String appName, String tableName) throws OpenSearchException, OpenSearchClientException {
    return resourceClient.post(createPushPath(appName, tableName), jsonArray.toString());
  }

  private String createPushPath(String appName, String tableName) {
    return String.format("/%s/%s/actions/bulk", appName, tableName);
  }
}
