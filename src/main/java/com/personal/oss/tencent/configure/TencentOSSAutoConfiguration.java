/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.tencent.configure;

import com.personal.oss.tencent.properties.TencentOssProperties;
import com.personal.oss.utils.SpringUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author sunpeikai
 * @version AliyunOSSAutoConfiguration, v0.1 2020/9/17 13:56
 * @description
 */
@Configuration
// 引入SpringUtils,确保能用SpringUtils获取上下文
@Import({SpringUtils.class})
@ConditionalOnClass({COSClient.class})
@EnableConfigurationProperties({TencentOssProperties.class})
@ConditionalOnProperty(name = {"oss.tencent.enable"}, havingValue = "true")
public class TencentOSSAutoConfiguration {
    private final TencentOssProperties tencentOssProperties;

    public TencentOSSAutoConfiguration(TencentOssProperties tencentOssProperties) {
        this.tencentOssProperties = tencentOssProperties;
    }

    @Bean
    public COSClient cosClient(){
        Assert.isTrue(!StringUtils.isEmpty(this.tencentOssProperties.getRegion()), "region can't be empty.");
        Assert.isTrue(!StringUtils.isEmpty(this.tencentOssProperties.getAccessKey()), "Access key can't be empty.");
        Assert.isTrue(!StringUtils.isEmpty(this.tencentOssProperties.getSecretKey()), "Secret key can't be empty.");
        COSCredentials credentials = new BasicCOSCredentials(this.tencentOssProperties.getAccessKey(), this.tencentOssProperties.getSecretKey());
        Region region = new Region(this.tencentOssProperties.getRegion());
        ClientConfig config = new ClientConfig(region);
        return new COSClient(credentials,config);
    }
}
