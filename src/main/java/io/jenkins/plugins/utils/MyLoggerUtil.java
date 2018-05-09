package io.jenkins.plugins.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLoggerUtil {
    private static Logger logger = LoggerFactory.getLogger(MyLoggerUtil.class);

    public static void i(String msg){
        logger.info(msg);
    }
}
