/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.tencent.utils;

import com.personal.oss.base.OssBase;
import com.personal.oss.base.OssInterface;
import com.personal.oss.utils.SpringUtils;
import com.qcloud.cos.COSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * @author sunpeikai
 * @version TencentOssUtil, v0.1 2020/9/18 15:48
 * @description
 */
public class TencentOssUtil extends OssBase implements OssInterface {
    private static final Logger log = LoggerFactory.getLogger(TencentOssUtil.class);
    private static final COSClient cosClient = SpringUtils.getBean(COSClient.class);

    /**
     * @description 下载文件到指定
     * 下载路径中可以带文件名,例如C:\Users\dell\Desktop\doc_1600583438497.jpg
     * 也可以不带文件名,例如C:\Users\dell\Desktop\
     * @auth sunpeikai
     * @param fileName 文件名
     * @param filePath 下载路径
     * @return
     */
    @Override
    public void fileDownload(String fileName, String filePath) {

    }

    /**
     * @description 下载文件到输入流
     * @auth sunpeikai
     * @param fileName 文件名
     * @return InputStream 输入流
     */
    @Override
    public InputStream fileDownload(String fileName) {
        return null;
    }

    /**
     * @description 文件路径上传文件,默认不带domain
     * @param filePath 文件路径
     * @param folder oss文件夹
     * @return
     */
    @Override
    public String fileUpload(String filePath, String folder) {
        return "not implements";
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
        return "not implements";
    }

    /**
     * @description base64 文件上传,默认不带domain
     * @auth sunpeikai
     * @param base64 文件的base64码
     * @param folder oss文件夹
     * @param fileType 文件类型
     * @return
     */
    @Override
    public String fileUpload(String base64, String folder, String fileType) {
        return "not implements";
    }

    /**
     * @description base64 文件上传,默认不带domain
     * @auth sunpeikai
     * @param base64 文件的base64码
     * @param folder oss文件夹
     * @param fileType 文件类型
     * @param isWithDomain 是否带domain的完整路径
     * @return
     */
    @Override
    public String fileUpload(String base64, String folder, String fileType, boolean isWithDomain) {
        return "not implements";
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
        return "not implements";
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
    public String fileUpload(File file, String folder, boolean isWithDomain) {
        return "not implements";
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
        return "not implements";
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
        return "not implements";
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
        return "not implements";
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
    public String fileUpload(InputStream inputStream, String folder, String fileType, boolean isWithDomain) {
        return "not implements";
    }
}
