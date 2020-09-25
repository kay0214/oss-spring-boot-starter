/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.aliyun.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.personal.oss.base.OssBase;
import com.personal.oss.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * @author sunpeikai
 * @version AliyunOssUtil, v0.1 2020/9/18 09:40
 * @description
 */
public class AliyunOssUtil extends OssBase {
    private static final Logger log = LoggerFactory.getLogger(AliyunOssUtil.class);
    private static final OSS ossClient = SpringUtils.getBean(OSS.class);

    /**
     * @description 阿里云实现 - 基础文件下载方法
     * @auth sunpeikai
     * @param fileName 文件名称
     * @return
     */
    @Override
    protected InputStream baseFileDownload(String fileName) {
        return ossClient.getObject(properties.getBucketName(), getFileKey(fileName)).getObjectContent();
    }

    /**
     * @description 阿里云实现 - 基础文件上传方法
     * @auth sunpeikai
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @param fileType 文件类型
     * @param isWithDomain 是否包含完整domain路径
     * @return
     */
    @Override
    protected String baseFileUpload(InputStream inputStream, String fileName, String fileType, boolean isWithDomain){
        try{
            ObjectMetadata objectMetadata = new ObjectMetadata();
            if (picFiles.contains(fileType)) {
                objectMetadata.setContentType(PIC_CONTENT_TYPE);
            }
            if (pdfFiles.contains(fileType)) {
                objectMetadata.setContentType(PDF_CONTENT_TYPE);
            }
            objectMetadata.setContentLength(inputStream.available());
            PutObjectResult putResult = ossClient.putObject(properties.getBucketName(), fileName, inputStream, objectMetadata);
            if (StringUtils.isEmpty(putResult.getETag())) {
                throw new RuntimeException("eTag is empty");
            } else {
                return isWithDomain ? getPathWithDomain(fileName) : getShortName(fileName);
            }
        }catch (Exception e){
            log.error("file [{}] upload fail {}", fileName, e);
            return null;
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream close fail");
                }
            }
        }
    }

    @Override
    protected Map<String, Object> baseHealthInfo() {
        OSSClient ossClient = SpringUtils.getBean(OSSClient.class);;
        Map<String, Object> result = new HashMap<>();
        result.put("beanName", "ossClient");
        result.put("endpoint", ossClient.getEndpoint().toString());
        result.put("clientConfiguration", ossClient.getClientConfiguration());
        result.put("credentials", ossClient.getCredentialsProvider().getCredentials());
        result.put("bucketList", ossClient.listBuckets().stream().map(Bucket::getName).toArray());
        return result;
    }
}
