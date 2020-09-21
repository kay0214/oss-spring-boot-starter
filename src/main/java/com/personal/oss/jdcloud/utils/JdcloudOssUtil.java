/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.jdcloud.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.personal.oss.base.OssBase;
import com.personal.oss.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author sunpeikai
 * @version JdcloudOssUtil, v0.1 2020/9/18 09:40
 * @description
 */
public class JdcloudOssUtil extends OssBase {
    private static final Logger log = LoggerFactory.getLogger(JdcloudOssUtil.class);
    private static final AmazonS3 ossClient = SpringUtils.getBean(AmazonS3.class);

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
}
