package com.lam.poster;

import com.lam.poster.utils.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        String fontPath = PropertiesUtil.readValue("poster.properties","poster-fonts-path");
        System.out.println(fontPath);

    }
}
