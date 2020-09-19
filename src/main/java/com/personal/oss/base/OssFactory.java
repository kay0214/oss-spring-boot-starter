/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.base;

import com.personal.oss.aliyun.utils.AliyunOssUtil;
import com.personal.oss.enums.OssCompanyEnum;
import com.personal.oss.tencent.utils.TencentOssUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author sunpeikai
 * @version OssFactory, v0.1 2020/9/18 17:29
 * @description
 */
public class OssFactory {

    private static final Logger log = LoggerFactory.getLogger(OssFactory.class);

    public static OssCompanyEnum thisCompany = null;

    private static volatile int limit = 0;

    public synchronized static void checkSingleton(){
        Assert.isTrue(++limit==1,"only support one company init");
    }

    public static OssInterface getOss(){
        switch (thisCompany){
            case ALIYUN:return new AliyunOssUtil();
            case TENCENT:return new TencentOssUtil();
            default:return null;
        }
    }
}
