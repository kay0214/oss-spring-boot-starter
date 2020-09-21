/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.jdcloud.configure;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.personal.oss.base.BaseConfiguration;
import com.personal.oss.enums.OssCompanyEnum;
import com.personal.oss.jdcloud.properties.JdcloudOssProperties;
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
 * @version JdcloudOSSAutoConfiguration, v0.1 2020/9/17 13:56
 * @description
 */
@Configuration
// 引入SpringUtils,确保能用SpringUtils获取上下文
@Import({SpringUtils.class})
@ConditionalOnClass({AmazonS3.class})
@EnableConfigurationProperties({OssProperties.class, JdcloudOssProperties.class})
@ConditionalOnProperty(name = {"oss.jdcloud.enable"}, havingValue = "true")
public class JdcloudOSSAutoConfiguration extends BaseConfiguration {
    private final JdcloudOssProperties jdcloudOssProperties;

    public JdcloudOSSAutoConfiguration(OssProperties ossProperties) {
        this.jdcloudOssProperties = ossProperties.getJdcloud();
    }

    @Bean
    public AmazonS3 amazonS3(){
        Assert.isTrue(!StringUtils.isEmpty(this.jdcloudOssProperties.getEndpoint()), "endpoint can't be empty.");
        Assert.isTrue(!StringUtils.isEmpty(this.jdcloudOssProperties.getAccessKey()), "Access key can't be empty.");
        Assert.isTrue(!StringUtils.isEmpty(this.jdcloudOssProperties.getSecretKey()), "Secret key can't be empty.");
        super.checkAndSwitch(OssCompanyEnum.JDCLOUD);
        // 处理region
        String region = this.jdcloudOssProperties.getEndpoint()
                .replaceAll(".jdcloud-oss.com","")
                .substring(this.jdcloudOssProperties.getEndpoint().indexOf(".") + 1);
        return AmazonS3Client.builder()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(this.jdcloudOssProperties.getEndpoint(),region))
                .withClientConfiguration(new ClientConfiguration())
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(this.jdcloudOssProperties.getAccessKey(),this.jdcloudOssProperties.getSecretKey())))
                .disableChunkedEncoding()
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
