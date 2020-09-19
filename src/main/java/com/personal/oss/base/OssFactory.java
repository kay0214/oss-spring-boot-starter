/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.base;

import com.personal.oss.aliyun.utils.AliyunOssUtil;
import com.personal.oss.tencent.utils.TencentOssUtil;

/**
 * @author sunpeikai
 * @version OssFactory, v0.1 2020/9/18 17:29
 * @description
 */
public class OssFactory {

    public static OssInterface getOss(){
        switch (BaseConfiguration.thisCompany){
            case ALIYUN:return new AliyunOssUtil();
            case TENCENT:return new TencentOssUtil();
            default:return null;
        }
    }
}