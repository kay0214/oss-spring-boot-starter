/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.tencent.utils;

import com.personal.oss.base.BaseOssUtil;
import com.personal.oss.utils.SpringUtils;
import com.qcloud.cos.COSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sunpeikai
 * @version TencentOssUtil, v0.1 2020/9/18 15:48
 * @description
 */
public class TencentOssUtil extends BaseOssUtil {
    private static final Logger log = LoggerFactory.getLogger(TencentOssUtil.class);
    private static final COSClient cosClient = SpringUtils.getBean(COSClient.class);

}
