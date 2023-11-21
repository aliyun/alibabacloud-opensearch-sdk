package com.aliyun.opensearch.auth.credential.provider;

import com.aliyun.opensearch.auth.credential.AccessKeyCredentials;
import com.aliyun.opensearch.auth.credential.Credentials;
import com.aliyun.opensearch.auth.credential.CredentialsException;
import com.aliyun.opensearch.auth.credential.StsCredentials;

/**
 * 通过未经处理的AccessKey构建静态认证凭证服务商
 *
 * @author 隆宇
 */
public class StaticCredentialsProvider implements CredentialsProvider {
    private String accessKeyId;
    private String accessKeySecret;
    private String securityToken = null;

    /**
     * 构造函数
     *
     * @param accessKeyId AccessKey ID
     * @param accessKeySecret AccessKey密钥
     */
    public StaticCredentialsProvider(String accessKeyId, String accessKeySecret) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    /**
     * 构造函数
     *
     * @param accessKeyId AccessKey ID
     * @param accessKeySecret AccessKey密钥
     * @param securityToken 安全令牌（STS Token）
     */
    public StaticCredentialsProvider(String accessKeyId, String accessKeySecret, String securityToken) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.securityToken = securityToken;
    }

    @Override
    public Credentials getCredentials() throws CredentialsException {
        if (securityToken != null) {
            return new StsCredentials(accessKeyId, accessKeySecret, securityToken);
        } else {
            return new AccessKeyCredentials(accessKeyId, accessKeySecret);
        }
    }
}
