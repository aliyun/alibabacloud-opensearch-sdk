package com.aliyun.opensearch.auth.credential;

/**
 * 基于AccessKey的认证凭证
 *
 * @author 隆宇
 * @link <a href="https://help.aliyun.com/zh/ram/user-guide/create-an-accesskey-pair">创建AccessKey</a>
 */
public class AccessKeyCredentials implements Credentials {
    private String accessKeyId;
    private String accessKeySecret;

    public AccessKeyCredentials(String accessKeyId, String accessKeySecret) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    /**
     * 获取AccessKey ID
     */
    public String getAccessKeyId() {
        return accessKeyId;
    }

    /**
     * 获取AccessKey密钥
     */
    public String getAccessKeySecret() {
        return accessKeySecret;
    }
}
