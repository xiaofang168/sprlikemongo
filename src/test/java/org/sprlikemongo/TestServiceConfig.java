package org.sprlikemongo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author fangjie
 * @date Created in 3:46 下午 2021/7/5.
 */
@Configuration
@ImportResource({
        "classpath:config.xml",
        "classpath:applicationContext-mongodb.xml"
})
@ComponentScan(basePackages = {"org.sprlikemongo"})
public class TestServiceConfig {
}

