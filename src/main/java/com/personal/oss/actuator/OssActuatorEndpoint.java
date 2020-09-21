/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.actuator;

import com.aliyun.oss.OSSClient;
import com.amazonaws.services.s3.AmazonS3;
import com.personal.oss.utils.SpringUtils;
import com.qcloud.cos.COSClient;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunpeikai
 * @version OssActuateEndpoint, v0.1 2020/9/18 09:47
 * @description
 */
@WebEndpoint(id = "oss")
public class OssActuatorEndpoint {

    @ReadOperation
    public Map<String, Object> ossActuator() {
        // 方法返回map
        Map<String, Object> result = new HashMap<>();
        // oss节点数量
        int size = 0;
        // oss.info对象存储列表
        List<Object> ossClientList = new ArrayList<>();

        // 阿里云oss节点
        Map<String, OSSClient> aliyunOSS = SpringUtils.getBeanOfType(OSSClient.class);
        size += aliyunOSS.size();
        aliyunOSS.keySet().forEach(beanName -> {
            Map<String, Object> ossProperties = new HashMap<>();
            OSSClient client = aliyunOSS.get(beanName);
            ossProperties.put("beanName", beanName);
            ossProperties.put("endpoint", client.getEndpoint().toString());
            ossProperties.put("clientConfiguration", client.getClientConfiguration());
            ossProperties.put("credentials", client.getCredentialsProvider().getCredentials());
            ossProperties.put("bucketList", client.listBuckets().stream().map(com.aliyun.oss.model.Bucket::getName).toArray());
            ossClientList.add(ossProperties);
        });
        // 腾讯云oss节点
        Map<String, COSClient> tencentOSS = SpringUtils.getBeanOfType(COSClient.class);
        size += tencentOSS.size();
        tencentOSS.keySet().forEach(beanName ->{
            Map<String, Object> ossProperties = new HashMap<>();
            COSClient client = tencentOSS.get(beanName);
            ossProperties.put("beanName", beanName);
            ossProperties.put("region", client.getClientConfig().getRegion().getRegionName());
            ossProperties.put("bucketList", client.listBuckets().stream().map(com.qcloud.cos.model.Bucket::getName).toArray());
            ossClientList.add(ossProperties);
        });
        // 京东云oss节点
        Map<String, AmazonS3> jdcloudOSS = SpringUtils.getBeanOfType(AmazonS3.class);
        size += jdcloudOSS.size();
        jdcloudOSS.keySet().forEach(beanName ->{
            Map<String, Object> ossProperties = new HashMap<>();
            AmazonS3 client = jdcloudOSS.get(beanName);
            ossProperties.put("beanName", beanName);
            ossProperties.put("region", client.getRegionName());
            ossProperties.put("bucketList", client.listBuckets().stream().map(com.amazonaws.services.s3.model.Bucket::getName).toArray());
            ossClientList.add(ossProperties);
        });

        result.put("size", size);
        result.put("info", ossClientList);
        return result;
    }
}
