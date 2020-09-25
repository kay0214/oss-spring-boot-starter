/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.base;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * @author sunpeikai
 * @version OssInterface, v0.1 2020/9/18 17:30
 * @description
 */
public interface OssInterface {

    /**
     * @description 下载文件到指定
     * 下载路径中可以带文件名,例如C:\Users\dell\Desktop\doc_1600583438497.jpg
     * 也可以不带文件名,例如C:\Users\dell\Desktop\
     * @auth sunpeikai
     * @param fileName 文件名
     * @param filePath 下载路径
     * @return
     */
    void fileDownload(String fileName, String filePath);

    /**
     * @description 下载文件到输入流
     * @auth sunpeikai
     * @param fileName 文件名
     * @return InputStream 输入流
     */
    InputStream fileDownload(String fileName);

    /**
     * @description 文件路径上传文件,默认不带domain
     * @param filePath 文件路径
     * @param folder oss文件夹
     * @return
     */
    String fileUpload(String filePath, String folder);

    /**
     * @description 文件路径上传文件
     * @param filePath 文件路径
     * @param folder oss文件夹
     * @param isWithDomain 是否带domain的完整路径
     * @return
     */
    String fileUpload(String filePath, String folder, boolean isWithDomain);

    /**
     * @description base64 文件上传,默认不带domain
     * @auth sunpeikai
     * @param base64 文件的base64码
     * @param folder oss文件夹
     * @param fileType 文件类型
     * @return
     */
    String fileUpload(String base64,String folder, String fileType);

    /**
     * @description base64 文件上传,默认不带domain
     * @auth sunpeikai
     * @param base64 文件的base64码
     * @param folder oss文件夹
     * @param fileType 文件类型
     * @param isWithDomain 是否带domain的完整路径
     * @return
     */
    String fileUpload(String base64,String folder, String fileType, boolean isWithDomain);

    /**
     * @description 上传文件,默认不带domain
     * @auth sunpeikai
     * @param file 文件
     * @param folder oss文件夹
     * @return
     */
    String fileUpload(File file, String folder);

    /**
     * @description 上传文件
     * @auth sunpeikai
     * @param file 文件
     * @param folder oss文件夹
     * @param isWithDomain 是否带domain的完整路径
     * @return
     */
    String fileUpload(File file, String folder, boolean isWithDomain);

    /**
     * @description 上传文件,默认不带domain
     * @auth sunpeikai
     * @param file 文件
     * @param folder oss文件夹
     * @return
     */
    String fileUpload(MultipartFile file, String folder);

    /**
     * @description 上传文件
     * @auth sunpeikai
     * @param file 文件
     * @param folder oss文件夹
     * @param isWithDomain 是否带domain的完整路径
     * @return
     */
    String fileUpload(MultipartFile file, String folder, boolean isWithDomain);

    /**
     * @description 文件流上传OSS,默认不带domain
     * @auth sunpeikai
     * @param inputStream 文件流
     * @param folder oss文件夹
     * @param fileType 文件类型
     * @return
     */
    String fileUpload(InputStream inputStream, String folder, String fileType);

    /**
     * @description 文件流上传OSS
     * @auth sunpeikai
     * @param inputStream 文件流
     * @param folder oss文件夹
     * @param fileType 文件类型
     * @param isWithDomain 是否带domain的完整路径
     * @return
     */
    String fileUpload(InputStream inputStream, String folder, String fileType, boolean isWithDomain);

    /**
     * @description actuator监控需要的健康信息
     * @auth sunpeikai
     */
    Map<String, Object> healthInfo();
}
