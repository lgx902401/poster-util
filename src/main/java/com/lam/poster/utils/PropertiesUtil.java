package com.lam.poster.utils;

import com.lam.poster.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class.getName());

    /**
     * 根据Key读取value
     *
     * @param key
     * @param filePath
     * @return
     */
    public static String readValue(String filePath, String key) {

        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = App.class.getClassLoader().getResourceAsStream(filePath);
            if(in != null){
                properties.load(in);
            }
            return properties.getProperty(key);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }


}
