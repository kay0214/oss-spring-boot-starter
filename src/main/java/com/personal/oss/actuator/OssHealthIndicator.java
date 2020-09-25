/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.oss.actuator;

import com.personal.oss.base.OssFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sunpeikai
 * @version OssHealthIndicator, v0.1 2020/9/24 17:45
 * @description oss健康检查
 */
@Component("oss")
public class OssHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        Map<String, Object> healthInfo = OssFactory.getOss().healthInfo();
        Health.Builder builder = new Health.Builder();
        if(healthInfo != null && healthInfo.size() > 0){
            // 参数装配到details
            healthInfo.forEach(builder::withDetail);
            return builder.up().build();
        }else{
            return builder.down().build();
        }
    }
}
