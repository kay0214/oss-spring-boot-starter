/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.actuator;

import com.personal.oss.base.OssFactory;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;

import java.util.Map;

/**
 * @author sunpeikai
 * @version OssActuateEndpoint, v0.1 2020/9/18 09:47
 * @description
 */
@WebEndpoint(id = "oss")
public class OssActuatorEndpoint {

    @ReadOperation
    public Map<String, Object> ossActuator() {
        return OssFactory.getOss().healthInfo();
    }
}
