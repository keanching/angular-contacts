package com.intecore.angularcontacts.util;

import java.text.SimpleDateFormat;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtils {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    
    public static String toJsonString(Object obj) {
        String retStr = null;
        
        try {
            retStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }
        catch (JsonProcessingException e) {
            logger.error("", e);
        }
        
        return retStr;
    }
    
    public static void simulateLongRunningTask(int timeMs) {
        if (timeMs == 0) {
            return;
        }
        
        long sleepTime = (timeMs < 0) ? new Random().nextInt(800) : timeMs;
        
        try {
            Thread.sleep(sleepTime);
        }
        catch (Exception e) {
            logger.error("", e);
        }
    }
}
