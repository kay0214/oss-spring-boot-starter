/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.base;

import com.personal.oss.aliyun.utils.AliyunOssUtil;
import com.personal.oss.jdcloud.utils.JdcloudOssUtil;
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
            case JDCLOUD:return new JdcloudOssUtil();
            default:throw new IllegalArgumentException("factory assemble fail, oss init fail already");
        }
    }
}
