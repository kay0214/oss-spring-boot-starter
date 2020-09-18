/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.actuator;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author sunpeikai
 * @version OssActuateEndpointConfiguration, v0.1 2020/9/18 09:46
 * @description
 */
@ConditionalOnClass({OssActuatorEndpoint.class})
public class OssActuatorEndpointConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnAvailableEndpoint
    public OssActuatorEndpoint ossActuateEndpoint(){
        return new OssActuatorEndpoint();
    }
}
