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


    @Override
    public String fileUpload(String filePath, String folder) {
        return "not implements";
    }

    @Override
    public String fileUpload(String filePath, String folder, boolean isWithDomain) {
        return "not implements";
    }

    @Override
    public String fileUpload(File file, String folder) {
        return "not implements";
    }

    @Override
    public String fileUpload(File file, String folder, boolean isWithDomain) {
        return "not implements";
    }

    @Override
    public String fileUpload(MultipartFile file, String folder) {
        return "not implements";
    }

    @Override
    public String fileUpload(MultipartFile file, String folder, boolean isWithDomain) {
        return "not implements";
    }

    @Override
    public String fileUpload(InputStream inputStream, String folder, String fileType) {
        return "not implements";
    }

    @Override
    public String fileUpload(InputStream inputStream, String folder, String fileType, boolean isWithDomain) {
        return "not implements";
    }
}
