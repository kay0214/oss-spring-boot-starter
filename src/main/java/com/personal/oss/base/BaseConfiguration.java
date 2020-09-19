/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.base;

import com.personal.oss.enums.OssCompanyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author sunpeikai
 * @version BaseConfiguration, v0.1 2020/9/19 21:03
 * @description
 */
public class BaseConfiguration {
    private static final Logger log = LoggerFactory.getLogger(BaseConfiguration.class);
    public static volatile OssCompanyEnum thisCompany = null;

    protected synchronized void checkAndSwitch(OssCompanyEnum companyEnum){
        Assert.isTrue(thisCompany == null, "only one company cloud be supported");
        log.info("company [{}] init ok", companyEnum);
        thisCompany = companyEnum;
    }
}
