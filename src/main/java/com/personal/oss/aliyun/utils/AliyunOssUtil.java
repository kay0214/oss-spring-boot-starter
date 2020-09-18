/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.aliyun.utils;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.personal.oss.base.BaseOssUtil;
import com.personal.oss.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author sunpeikai
 * @version AliyunOssUtil, v0.1 2020/9/18 09:40
 * @description
 */
public class AliyunOssUtil extends BaseOssUtil {
    private static final Logger log = LoggerFactory.getLogger(AliyunOssUtil.class);
    private static final OSS ossClient = SpringUtils.getBean(OSS.class);

    public static void fileDownload(String fileKey, String fileDownloadPath) throws IOException {
        InputStream inputStream = ossClient.getObject(properties.getBucketName(), fileKey).getObjectContent();
        StreamUtils.copy(inputStream, new FileOutputStream(fileDownloadPath));
        log.info(ossClient.generatePresignedUrl(properties.getBucketName(), fileKey, new Date(System.currentTimeMillis() + 30000L), HttpMethod.GET).toString());
        ossClient.shutdown();
    }

    public static String fileUpload(MultipartFile file, String folder) {
        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!StringUtils.isEmpty(originalFilename) && typeFiles.contains(fileType)) {
            String retFileName = getOssFileName(folder, fileType);
            String fileName = getDoPath(folder) + retFileName;

            try {
                InputStream inStream = file.getInputStream();
                ObjectMetadata objectMetadata = new ObjectMetadata();
                if (picFiles.contains(fileType.toLowerCase())) {
                    objectMetadata.setContentType(PIC_CONTENT_TYPE);
                }

                if (pdfFiles.contains(fileType.toLowerCase())) {
                    objectMetadata.setContentType(PDF_CONTENT_TYPE);
                }

                log.info("bucketName=" + properties.getBucketName());
                log.info("fileName=" + fileName);
                log.info("inStream is null =" + inStream.available());
                log.info("objectMetadata=" + objectMetadata.getContentType());
                PutObjectResult putResult = ossClient.putObject(properties.getBucketName(), fileName, inStream, objectMetadata);
                if (StringUtils.isEmpty(putResult.getETag())) {
                    throw new RuntimeException("---------------文件:" + fileName + "上传失败");
                } else {
                    return fileName;
                }
            } catch (Exception var9) {
                log.error("上传图片失败:", var9);
                return null;
            }
        } else {
            return null;
        }
    }
}
