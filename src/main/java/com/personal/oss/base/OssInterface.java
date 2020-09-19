/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.base;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * @author sunpeikai
 * @version OssInterface, v0.1 2020/9/18 17:30
 * @description
 */
public interface OssInterface {
    String fileUpload(String filePath, String folder);
    String fileUpload(String filePath, String folder, boolean isWithDomain);
    String fileUpload(File file, String folder);
    String fileUpload(File file, String folder, boolean isWithDomain);
    String fileUpload(MultipartFile file, String folder);
    String fileUpload(MultipartFile file, String folder, boolean isWithDomain);
    String fileUpload(InputStream inputStream, String folder, String fileType);
    String fileUpload(InputStream inputStream, String folder, String fileType, boolean isWithDomain);
}
