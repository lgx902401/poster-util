package com.lam.poster.utils;

import com.lam.poster.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

public class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class.getName());

    private static final String CONFIGURATION_FILE = "poster.properties";

    private final Properties properties = new Properties();
//
//    void init(){
//       loadProperties();
//    }
//    private void loadProperties() {
//        // Add props from the resource simplelogger.properties
//        InputStream in = AccessController.doPrivileged(new PrivilegedAction<InputStream>() {
//            public InputStream run() {
//                ClassLoader threadCL = Thread.currentThread().getContextClassLoader();
//                if (threadCL != null) {
//                    return threadCL.getResourceAsStream(CONFIGURATION_FILE);
//                } else {
//                    return ClassLoader.getSystemResourceAsStream(CONFIGURATION_FILE);
//                }
//            }
//        });
//        if (null != in) {
//            try {
//                properties.load(in);
//            } catch (java.io.IOException e) {
//                // ignored
//            } finally {
//                try {
//                    in.close();
//                } catch (java.io.IOException e) {
//                    // ignored
//                }
//            }
//        }
//    }
//    String getStringProperty(String name) {
//        String prop = null;
//        try {
//            prop = System.getProperty(name);
//        } catch (SecurityException e) {
//            ; // Ignore
//        }
//        return (prop == null) ? properties.getProperty(name) : prop;
//    }

//    public static String readValue(String key) {
//        Properties properties = new Properties();
//        InputStream in = null;
//        try {
//            in = App.class.getClassLoader().getResourceAsStream(CONFIGURATION_FILE);
//            if(in != null){
//                properties.load(in);
//            }
//            return properties.getProperty(key);
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//            return null;
//        }finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException e) {
//                logger.error(e.getMessage(), e);
//            }
//        }
//    }
}
