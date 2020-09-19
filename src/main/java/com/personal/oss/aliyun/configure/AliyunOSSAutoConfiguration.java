/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.aliyun.configure;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.personal.oss.aliyun.properties.AliyunOssProperties;
import com.personal.oss.base.OssFactory;
import com.personal.oss.enums.OssCompanyEnum;
import com.personal.oss.properties.OssProperties;
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
@EnableConfigurationProperties({OssProperties.class,AliyunOssProperties.class})
@ConditionalOnProperty(name = {"oss.aliyun.enable"}, havingValue = "true")
public class AliyunOSSAutoConfiguration {
    private final AliyunOssProperties aliyunOssProperties;

    public AliyunOSSAutoConfiguration(OssProperties ossProperties) {
        this.aliyunOssProperties = ossProperties.getAliyun();
    }

    @Bean
    public OSS oss(){
        Assert.isTrue(!StringUtils.isEmpty(this.aliyunOssProperties.getEndpoint()), "endpoint can't be empty.");
        Assert.isTrue(!StringUtils.isEmpty(this.aliyunOssProperties.getAccessKey()), "Access key can't be empty.");
        Assert.isTrue(!StringUtils.isEmpty(this.aliyunOssProperties.getSecretKey()), "Secret key can't be empty.");
        OssFactory.checkSingleton();
        OssFactory.thisCompany = OssCompanyEnum.ALIYUN;
        if(StringUtils.isEmpty(this.aliyunOssProperties.getSecurityToken())){
            // AK_SK mode
            return new OSSClientBuilder().build(this.aliyunOssProperties.getEndpoint(), this.aliyunOssProperties.getAccessKey(), this.aliyunOssProperties.getSecretKey());
        }else{
            // STS mode
            return new OSSClientBuilder().build(this.aliyunOssProperties.getEndpoint(), this.aliyunOssProperties.getAccessKey(), this.aliyunOssProperties.getSecretKey(), this.aliyunOssProperties.getSecurityToken());
        }
    }
}
