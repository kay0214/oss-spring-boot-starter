/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.tencent.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sunpeikai
 * @version AliyunOssProperties, v0.1 2020/9/17 14:29
 * @description
 */
@ConfigurationProperties("oss.aliyun")
public class TencentOssProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String securityToken;
    private boolean enable;
    private String bucketName;
    private String baseUrl;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String toString() {
        return "AliyunOssProperties{" +
                "endpoint='" + endpoint + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", securityToken='" + securityToken + '\'' +
                ", enable=" + enable +
                ", bucketName='" + bucketName + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }
}
