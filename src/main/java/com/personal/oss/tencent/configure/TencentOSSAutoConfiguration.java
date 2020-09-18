/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.tencent.configure;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.personal.oss.tencent.properties.TencentOssProperties;
import com.personal.oss.utils.SpringUtils;
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
@ConditionalOnClass({OSS.class})
@EnableConfigurationProperties({TencentOssProperties.class})
@ConditionalOnProperty(name = {"oss.aliyun.enable"}, havingValue = "true", matchIfMissing = true)
public class TencentOSSAutoConfiguration {
    private final TencentOssProperties tencentOssProperties;

    public TencentOSSAutoConfiguration(TencentOssProperties tencentOssProperties) {
        this.tencentOssProperties = tencentOssProperties;
    }

    @Bean
    public OSS oss(){
        Assert.isTrue(!StringUtils.isEmpty(this.tencentOssProperties.getEndpoint()), "endpoint can't be empty.");
        Assert.isTrue(!StringUtils.isEmpty(this.tencentOssProperties.getAccessKey()), "Access key can't be empty.");
        Assert.isTrue(!StringUtils.isEmpty(this.tencentOssProperties.getSecretKey()), "Secret key can't be empty.");
        if(StringUtils.isEmpty(this.tencentOssProperties.getSecurityToken())){
            // AK_SK mode
            return new OSSClientBuilder().build(this.tencentOssProperties.getEndpoint(), this.tencentOssProperties.getAccessKey(), this.tencentOssProperties.getSecretKey());
        }else{
            // STS mode
            return new OSSClientBuilder().build(this.tencentOssProperties.getEndpoint(), this.tencentOssProperties.getAccessKey(), this.tencentOssProperties.getSecretKey(), this.tencentOssProperties.getSecurityToken());
        }
    }
}
