/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.base;

import com.personal.oss.properties.OssProperties;
import com.personal.oss.utils.SpringUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author sunpeikai
 * @version OssBase, v0.1 2020/9/18 11:23
 * @description
 */
public class OssBase{
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
            String shortName = getShortName(fileName);
            String folder = shortName.substring(0, shortName.lastIndexOf("_"));
            return properties.getDomain() + "/" + folder + "/" + shortName;
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
    protected String getFileName(String folder, String fileType) {
        String type = ".".equals(fileType.substring(0, 1)) ? fileType : "." + fileType;
        return folder + "_" + System.currentTimeMillis() + type;
    }

    /**
     * @description 获取oss文件夹作为路径 doc/
     * @auth sunpeikai
     * @param folder oss文件夹
     * @return
     */
    protected String getDoPath(String folder) {
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
}
