/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.aliyun.utils;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.personal.oss.base.OssBase;
import com.personal.oss.base.OssInterface;
import com.personal.oss.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Date;

/**
 * @author sunpeikai
 * @version AliyunOssUtil, v0.1 2020/9/18 09:40
 * @description
 */
public class AliyunOssUtil extends OssBase implements OssInterface {
    private static final Logger log = LoggerFactory.getLogger(AliyunOssUtil.class);
    private static final OSS ossClient = SpringUtils.getBean(OSS.class);

    public static void fileDownload(String fileKey, String fileDownloadPath) throws IOException {
        InputStream inputStream = ossClient.getObject(properties.getBucketName(), fileKey).getObjectContent();
        StreamUtils.copy(inputStream, new FileOutputStream(fileDownloadPath));
        log.info(ossClient.generatePresignedUrl(properties.getBucketName(), fileKey, new Date(System.currentTimeMillis() + 30000L), HttpMethod.GET).toString());
        ossClient.shutdown();
    }

    /**
     * @description 文件路径上传文件,默认不带domain
     * @param filePath 文件路径
     * @param folder oss文件夹
     * @return
     */
    @Override
    public String fileUpload(String filePath, String folder) {
        return fileUpload(filePath, folder, false);
    }

    /**
     * @description 文件路径上传文件
     * @param filePath 文件路径
     * @param folder oss文件夹
     * @param isWithDomain 是否带domain的完整路径
     * @return
     */
    @Override
    public String fileUpload(String filePath, String folder, boolean isWithDomain) {
        if(StringUtils.isEmpty(filePath) || StringUtils.isEmpty(folder)){
            log.error("[filePath] or [folder] must not be empty");
            return null;
        }else{
            String fileType = getFileType(filePath);
            if(!typeFiles.contains(fileType)){
                log.error("upload file type not be supported");
                return null;
            }else{
                String retFileName = getFileName(folder, fileType);
                String fileName = getDoPath(folder) + retFileName;
                try {
                    File file = new File(filePath);
                    if(!file.exists() || file.isDirectory()){
                        log.error("upload file is not exists or is a directory");
                        return null;
                    }else{
                        return baseFileUpload(new FileInputStream(file), fileName, fileType, isWithDomain);
                    }

                } catch (FileNotFoundException e) {
                    log.error("file [{}] upload fail", fileName, e);
                    return null;
                }
            }
        }
    }

    @Override
    public String fileUpload(String base64, String folder, String fileType) {
        return fileUpload(base64, folder, fileType, false);
    }

    @Override
    public String fileUpload(String base64, String folder, String fileType, boolean isWithDomain) {
        if(StringUtils.isEmpty(base64)){
            log.error("file base64 code must not be empty");
            return null;
        }else{
            if(StringUtils.isEmpty(folder) || StringUtils.isEmpty(fileType)){
                log.error("folder or fileType must not be empty");
                return null;
            }else{
                if(!typeFiles.contains(fileType)){
                    log.error("upload file type not be supported");
                    return null;
                }else{
                    // 参数处理 - 去掉base64的头data:image/png;base64,
                    base64 = base64.contains(",") ? base64.substring(base64.indexOf(",") + 1) : base64;
                    String retFileName = getFileName(folder, fileType);
                    String fileName = getDoPath(folder) + retFileName;
                    try{
                        return baseFileUpload(new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(base64)),fileName,fileType,isWithDomain);
                    }catch (Exception e){
                        log.error("file [{}] upload fail", fileName, e);
                        return null;
                    }
                }
            }
        }
    }

    /**
     * @description 上传文件,默认不带domain
     * @auth sunpeikai
     * @param file 文件
     * @param folder oss文件夹
     * @return
     */
    @Override
    public String fileUpload(File file, String folder) {
        return fileUpload(file, folder, false);
    }

    /**
     * @description 上传文件
     * @auth sunpeikai
     * @param file 文件
     * @param folder oss文件夹
     * @param isWithDomain 是否带domain的完整路径
     * @return
     */
    @Override
    public String fileUpload(File file, String folder, boolean isWithDomain){
        if(file == null || StringUtils.isEmpty(folder)){
            log.error("upload file or folder must not be null");
            return null;
        }else{
            if(!file.exists() || file.isDirectory()){
                log.error("upload file is not exists or is a directory");
                return null;
            }else{
                String name = file.getName();
                if(StringUtils.isEmpty(name)){
                    log.error("upload file name must not be empty");
                    return null;
                }else{
                    String fileType = getFileType(name);
                    if(!typeFiles.contains(fileType)){
                        log.error("upload file type not be supported");
                        return null;
                    }else{
                        // 生成文件名
                        String retFileName = getFileName(folder, fileType);
                        // 生成doc/doc_1234567890.docx
                        String fileName = getDoPath(folder) + retFileName;
                        try {
                            return baseFileUpload(new FileInputStream(file), fileName, fileType, isWithDomain);
                        } catch (Exception e) {
                            log.error("file [{}] upload fail {}", fileName, e);
                            return null;
                        }
                    }
                }
            }
        }
    }

    /**
     * @description 上传文件,默认不带domain
     * @auth sunpeikai
     * @param file 文件
     * @param folder oss文件夹
     * @return
     */
    @Override
    public String fileUpload(MultipartFile file, String folder) {
        return fileUpload(file, folder, false);
    }

    /**
     * @description 上传文件
     * @auth sunpeikai
     * @param file 文件
     * @param folder oss文件夹
     * @param isWithDomain 是否带domain的完整路径
     * @return
     */
    @Override
    public String fileUpload(MultipartFile file, String folder, boolean isWithDomain) {
        if(file == null || StringUtils.isEmpty(folder)){
            log.error("upload file or folder must not be null");
            return null;
        }else{
            String originalFilename = file.getOriginalFilename();
            if(StringUtils.isEmpty(originalFilename)){
                log.error("upload file name must not be empty");
                return null;
            }else{
                String fileType = getFileType(originalFilename);
                if(!typeFiles.contains(fileType)){
                    log.error("upload file type not be supported");
                    return null;
                }else{
                    // 生成文件名
                    String retFileName = getFileName(folder, fileType);
                    // 生成doc/doc_1234567890.docx
                    String fileName = getDoPath(folder) + retFileName;
                    try {
                        return baseFileUpload(file.getInputStream(), fileName, fileType, isWithDomain);
                    } catch (Exception e) {
                        log.error("file [{}] upload fail {}", fileName, e);
                        return null;
                    }
                }
            }
        }
    }

    /**
     * @description 文件流上传OSS,默认不带domain
     * @auth sunpeikai
     * @param inputStream 文件流
     * @param folder oss文件夹
     * @param fileType 文件类型
     * @return
     */
    @Override
    public String fileUpload(InputStream inputStream, String folder, String fileType) {
        return fileUpload(inputStream, folder, fileType, false);
    }

    /**
     * @description 文件流上传OSS
     * @auth sunpeikai
     * @param inputStream 文件流
     * @param folder oss文件夹
     * @param fileType 文件类型
     * @param isWithDomain 是否带domain的完整路径
     * @return
     */
    @Override
    public String fileUpload(InputStream inputStream, String folder, String fileType, boolean isWithDomain){
        if(StringUtils.isEmpty(folder) || StringUtils.isEmpty(fileType)){
            log.error("[folder] or [fileType] must not be empty");
            return null;
        }else{
            if(!typeFiles.contains(fileType)){
                log.error("upload file type not be supported");
                return null;
            }else{
                // 生成文件名
                String retFileName = getFileName(folder, fileType);
                String fileName = getDoPath(folder) + retFileName;
                return baseFileUpload(inputStream, fileName, fileType, isWithDomain);
            }
        }
    }

    /**
     * @description 基础文件上传方法
     * @auth sunpeikai
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @param fileType 文件类型
     * @param isWithDomain 是否包含完整domain路径
     * @return
     */
    private String baseFileUpload(InputStream inputStream, String fileName, String fileType, boolean isWithDomain){
        try{
            ObjectMetadata objectMetadata = new ObjectMetadata();
            if (picFiles.contains(fileType)) {
                objectMetadata.setContentType(PIC_CONTENT_TYPE);
            }
            if (pdfFiles.contains(fileType)) {
                objectMetadata.setContentType(PDF_CONTENT_TYPE);
            }
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
