package com.myself.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 21:33 2018\9\10 0010
 */
@Data
@Component
@ConfigurationProperties(prefix = "email")
public class EmailConfig {

    private String from;

}
