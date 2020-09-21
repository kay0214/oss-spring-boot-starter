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

    @Override
    protected InputStream baseFileDownload(String fileName) {
        return cosClient.getObject(properties.getBucketName(), getFileKey(fileName)).getObjectContent();
    }

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
