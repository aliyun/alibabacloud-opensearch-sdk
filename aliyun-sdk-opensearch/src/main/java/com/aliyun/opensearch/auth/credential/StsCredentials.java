package com.aliyun.opensearch.auth.credential;

/**
 * 基于STS的认证凭证
 *
 * @author 隆宇
 * @link <a href="https://help.aliyun.com/zh/ram/product-overview/what-is-sts">什么是STS</a>
 */
public class StsCredentials extends AccessKeyCredentials {
    private String securityToken;

    public StsCredentials(String accessKeyId, String accessKeySecret, String securityToken) {
        super(accessKeyId, accessKeySecret);
        this.securityToken = securityToken;
    }

    /**
     * 获取安全令牌（STS Token）
     */
    public String getSecurityToken() {
        return securityToken;
    }
}
