package com.csczn.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.util.Properties;


/**
 * @author Jesse
 * @version 03/12/2014
 */
public class ConfigUtils {

    private static Log logger = LogFactory.getLog(ConfigUtils.class);

    private static ConfigUtils _instance = null;
    private Properties properties = new Properties();

    private ConfigUtils() {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
            properties.load(inputStream);
            if (inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            logger.error("config.properties not found or wrong format", e);
        }
    }

    private static ConfigUtils getInstance() {
        if (_instance == null) {
            _instance = new ConfigUtils();
        }
        return _instance;
    }

    @Override
    public ConfigUtils clone() {
        return getInstance();
    }

    public static String getConfig(String key) {
        return getInstance().properties.getProperty(key);
    }
    public static Boolean isCacheEnable() {
        return Boolean.parseBoolean(getInstance().properties.getProperty("cache.enable"));
    }

}