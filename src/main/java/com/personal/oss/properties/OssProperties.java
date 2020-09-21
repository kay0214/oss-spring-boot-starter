/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.properties;

import com.personal.oss.aliyun.properties.AliyunOssProperties;
import com.personal.oss.jdcloud.properties.JdcloudOssProperties;
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
    private String domain;
    private AliyunOssProperties aliyun;
    private TencentOssProperties tencent;
    private JdcloudOssProperties jdcloud;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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

    public JdcloudOssProperties getJdcloud() {
        return jdcloud;
    }

    public void setJdcloud(JdcloudOssProperties jdcloud) {
        this.jdcloud = jdcloud;
    }
}
