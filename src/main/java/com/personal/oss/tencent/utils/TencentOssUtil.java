/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.tencent.utils;

import com.personal.oss.base.OssBase;
import com.personal.oss.utils.SpringUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author sunpeikai
 * @version TencentOssUtil, v0.1 2020/9/18 15:48
 * @description
 */
public class TencentOssUtil extends OssBase {
    private static final Logger log = LoggerFactory.getLogger(TencentOssUtil.class);
    private static final COSClient cosClient = SpringUtils.getBean(COSClient.class);

    /**
     * @description 腾讯云实现 - 基础文件下载方法
     * @auth sunpeikai
     * @param fileName 文件名称
     * @return
     */
    @Override
    protected InputStream baseFileDownload(String fileName) {
        return cosClient.getObject(properties.getBucketName(), getFileKey(fileName)).getObjectContent();
    }

    /**
     * @description 腾讯云实现 - 基础文件上传方法
     * @auth sunpeikai
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @param fileType 文件类型
     * @param isWithDomain 是否包含完整domain路径
     * @return
     */
    @Override
    protected String baseFileUpload(InputStream inputStream, String fileName, String fileType, boolean isWithDomain) {
        try{
            ObjectMetadata objectMetadata = new ObjectMetadata();
            if (picFiles.contains(fileType)) {
                objectMetadata.setContentType(PIC_CONTENT_TYPE);
            }
            if (pdfFiles.contains(fileType)) {
                objectMetadata.setContentType(PDF_CONTENT_TYPE);
            }
            PutObjectResult putResult = cosClient.putObject(properties.getBucketName(), fileName, inputStream, objectMetadata);
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
