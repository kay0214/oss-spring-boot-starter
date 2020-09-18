/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.properties;

import com.personal.oss.aliyun.properties.AliyunOssProperties;
import com.personal.oss.tencent.properties.TencentOssProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sunpeikai
 * @version OssProperties, v0.1 2020/9/18 17:05
 * @description
 */
@ConfigurationProperties("oss")
public class OssProperties {
    private String bucketName;
    private String baseUrl;
    private AliyunOssProperties aliyun;
    private TencentOssProperties tencent;

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

    public AliyunOssProperties getAliyun() {
        return aliyun;
    }

    public void setAliyun(AliyunOssProperties aliyun) {
        this.aliyun = aliyun;
    }

    public TencentOssProperties getTencent() {
        return tencent;
    }

    public void setTencent(TencentOssProperties tencent) {
        this.tencent = tencent;
    }
}
