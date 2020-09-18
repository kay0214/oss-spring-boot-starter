/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.base;

import java.util.Arrays;
import java.util.List;

/**
 * @author sunpeikai
 * @version BaseOssUtil, v0.1 2020/9/18 11:23
 * @description
 */
public class BaseOssUtil {
    protected static final List<String> typeFiles = Arrays.asList(".gif", ".jpeg", ".png", ".jpg", ".tif", ".bmp", ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".apk");
    protected static final List<String> picFiles = Arrays.asList(".gif", ".jpeg", ".png", ".jpg", ".tif", ".bmp");
    protected static final List<String> pdfFiles = Arrays.asList(".pdf");
    protected static final String PIC_CONTENT_TYPE = "image/jpg";
    protected static final String PDF_CONTENT_TYPE = "application/pdf";
    public static String getOssFileName(String folder, String fileType) {
        String type = ".".equals(fileType.substring(0, 1)) ? fileType : "." + fileType;
        return folder + "_" + System.currentTimeMillis() + type;
    }

    public static String getDoPath(String path) {
        path = path.replace("\\", "/");
        String lastChar = path.substring(path.length() - 1);
        String firstChar = path.substring(0, 1);
        if (!"/".equals(lastChar)) {
            path = path + "/";
        }

        if ("/".equals(firstChar)) {
            path = path.substring(1, path.length());
        }

        System.out.println(path);
        return path;
    }
}
