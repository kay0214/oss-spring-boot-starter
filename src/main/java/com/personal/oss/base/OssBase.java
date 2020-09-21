/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.base;

import com.personal.oss.properties.OssProperties;
import com.personal.oss.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author sunpeikai
 * @version OssBase, v0.1 2020/9/18 11:23
 * @description
 */
public abstract class OssBase implements OssInterface{
    private static final Logger log = LoggerFactory.getLogger(OssBase.class);
    protected static final List<String> typeFiles = Arrays.asList("gif", "jpeg", "png", "jpg", "tif", "bmp", "pdf", "doc", "docx", "xls", "xlsx", "apk");
    protected static final List<String> picFiles = Arrays.asList("gif", "jpeg", "png", "jpg", "tif", "bmp");
    protected static final List<String> pdfFiles = Arrays.asList("pdf");
    protected static final String PIC_CONTENT_TYPE = "image/jpg";
    protected static final String PDF_CONTENT_TYPE = "application/pdf";
    protected static final OssProperties properties = SpringUtils.getBean(OssProperties.class);

    //**********          外界可以继续用的方法          **********//
    /**
     * @description 获取完整domain访问路径
     * @auth sunpeikai
     * @param fileName 文件名
     * @return
     */
    public static String getPathWithDomain(String fileName) {
        if (StringUtils.isEmpty(fileName)){
            return "";
        }else{
            return properties.getDomain() + "/" + getFileKey(fileName);
        }
    }

    /**
     * @description 获取文件类型
     * @auth sunpeikai
     * @param fileName 文件名
     * @return
     */
    public static String getFileType(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    //**********          对外界来说没用的方法          **********//
    /**
     * @description 获取fileKey  doc/doc_1234567890.docx
     * @auth sunpeikai
     * @param fileName 文件名doc_1234567890.docx
     * @return
     */
    protected static String getFileKey(String fileName){
        if(StringUtils.isEmpty(fileName)){
            return "";
        }else{
            String shortName = getShortName(fileName);
            String folder = shortName.substring(0, shortName.lastIndexOf("_"));
            return folder + "/" + shortName;
        }
    }
    /**
     * @description 获取短文件名称 doc_1234567890.docx
     * @auth sunpeikai
     * @param fileName 文件名称 doc/doc_1234567890.docx
     * @return doc_1234567890.docx
     */
    protected static String getShortName(String fileName){
        return fileName.substring(fileName.lastIndexOf("/") + 1);
    }

    /**
     * @description 生成短文件名称 doc_1234567890.docx
     * @auth sunpeikai
     * @param folder oss文件夹
     * @param fileType 文件类型
     * @return
     */
    private String getFileName(String folder, String fileType) {
        String type = ".".equals(fileType.substring(0, 1)) ? fileType : "." + fileType;
        return folder + "_" + System.currentTimeMillis() + type;
    }

    /**
     * @description 获取oss文件夹作为路径 doc/
     * @auth sunpeikai
     * @param folder oss文件夹
     * @return
     */
    private String getDoPath(String folder) {
        folder = folder.replace("\\", "/");
        String lastChar = folder.substring(folder.length() - 1);
        String firstChar = folder.substring(0, 1);
        if (!"/".equals(lastChar)) {
            folder = folder + "/";
        }

        if ("/".equals(firstChar)) {
            folder = folder.substring(1);
        }
        return folder;
    }

    //**********          实现文件上传下载的基础方法 - 子类只需要实现基础的上传下载方法即可          **********//
    /**
     * @description 下载文件到指定路径
     * 下载路径中可以带文件名,例如C:\Users\dell\Desktop\doc_1600583438497.jpg
     * 也可以不带文件名,例如C:\Users\dell\Desktop\
     * @auth sunpeikai
     * @param fileName 文件名
     * @param filePath 下载路径
     * @return
     */
    @Override
    public void fileDownload(String fileName, String filePath) {
        if(StringUtils.isEmpty(filePath)){
            log.error("file download path must not be empty");
        }else{
            InputStream inputStream = fileDownload(fileName);
            FileOutputStream outputStream = null;
            if(inputStream != null){
                try{
                    File file = new File(filePath);
                    if(file.isDirectory()){
                        // 如果是个目录 - 就在filePath加上fileName
                        outputStream = new FileOutputStream(filePath + File.separator + fileName);
                    }else if(!file.isDirectory() && !file.exists()){
                        outputStream = new FileOutputStream(file);
                    }else{
                        log.error("file path is not a directory or exists already");
                        return;
                    }
                    StreamUtils.copy(inputStream, outputStream);
                    outputStream.flush();
                }catch (Exception e){
                    log.error("file [{}] download fail {}", fileName, e);
                }finally {
                    // 先关闭输出流,再关闭输入流
                    if(outputStream != null){
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            log.error("outputStream close fail");
                        }
                    }
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        log.error("inputStream close fail");
                    }
                }
            }
        }
    }

    /**
     * @description 下载文件到输入流
     * @auth sunpeikai
     * @param fileName 文件名
     * @return InputStream 输入流
     */
    @Override
    public InputStream fileDownload(String fileName) {
        if(StringUtils.isEmpty(fileName)){
            log.error("file name must not be empty");
            return null;
        }else{
            return baseFileDownload(fileName);
        }
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
        return fileUpload(base64, folder, fileType, false);
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
     * @description 基础文件下载方法
     * @auth sunpeikai
     * @param fileName 文件名称
     * @return
     */
    protected abstract InputStream baseFileDownload(String fileName);

    /**
     * @description 基础文件上传方法
     * @auth sunpeikai
     * @param inputStream 文件输入流
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @param isWithDomain 是否带domain的完整路径
     * @return
     */
    protected abstract String baseFileUpload(InputStream inputStream, String fileName, String fileType, boolean isWithDomain);
}
