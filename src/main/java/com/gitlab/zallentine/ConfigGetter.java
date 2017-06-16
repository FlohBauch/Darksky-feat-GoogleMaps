package com.gitlab.zallentine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigGetter {

    static String configFile;

    public ConfigGetter(String configFile) {
        this.configFile = configFile;
    }

    public static String settings(String key) throws IOException {
        Properties mySettings = new Properties();
        mySettings.load(new FileInputStream(configFile));

        // getProperty() returns a String
        return mySettings.getProperty(key);
    }

}
