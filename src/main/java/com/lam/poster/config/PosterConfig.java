package com.lam.poster.config;


import com.lam.poster.utils.PropertiesUtil;
import com.lam.poster.utils.ResourceUtils;
import org.springframework.util.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

public class PosterConfig{
    private static final String CONFIGURATION_FILE = "poster.properties";

    private final Properties properties = new Properties();
    /**
     * 图片生成路径
     */
    private String imagePath;

    /**
     * 模板路径
     */
    private String templatesPath;

    /**
     * 模板路径
     */
    private String fontsPath;

    /**
     * 下载路径
     */
    private String downloadPath;

    /**
     * 获取文件下载目录
     *
     * @return String
     */
    public String getDownloadPath() {
        return downloadPath;
    }

    /**
     * 获取文件下载后的地址
     *
     * @param url
     *
     * @return String
     */
    public String getDownloadPath(String url) {
        return getDownloadPath() + url2fileName(url);
    }

    public String getTemplatesPath() {
        return templatesPath;
    }

    public String getTemplatesPath(String imageName) {
        return getTemplatesPath() + imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImagePath(String imagePath){
        return getImagePath()+ imagePath;
    }

    public String getFontsPath() {
        return fontsPath;
    }

    public String getFontsPath(String font) {
        return getFontsPath() + font;
    }

//
//    public static String (String path) {
//        return path + (path.endsWith("/") ? "" : "/");
//    }

    public void init (){
        loadProperties();
        fontsPath = getStringProperty("poster-fonts-path");
        templatesPath = getStringProperty("poster-templates-path");
        imagePath = getStringProperty("poster-image-path");

    }
    private void loadProperties() {
        // Add props from the resource simplelogger.properties
        InputStream in = AccessController.doPrivileged(new PrivilegedAction<InputStream>() {
            public InputStream run() {
                ClassLoader threadCL = Thread.currentThread().getContextClassLoader();
                if (threadCL != null) {
                    return threadCL.getResourceAsStream(CONFIGURATION_FILE);
                } else {
                    return ClassLoader.getSystemResourceAsStream(CONFIGURATION_FILE);
                }
            }
        });
        if (null != in) {
            try {
                properties.load(in);
            } catch (java.io.IOException e) {
                // ignored
            } finally {
                try {
                    in.close();
                } catch (java.io.IOException e) {
                    // ignored
                }
            }
        }
    }

    String getStringProperty(String name) {
        String prop = null;
        try {
            prop = System.getProperty(name);
        } catch (SecurityException e) {
            ; // Ignore
        }
        return (prop == null) ? properties.getProperty(name) : prop;
    }
    /**
     * 从模板中获取图片
     *
     * @param imageName
     *
     * @return imageFile
     *
     * @throws IOException
     */
    public BufferedImage getTemplateImage(String imageName) throws IOException {

        // 从用户自定义的目录中找
        File imageFile = new File(getTemplatesPath(imageName));
        if (imageFile.exists()) {
            return ImageIO.read(imageFile);
        }

        // 找不到的话从默认模板中找
        imageFile = new File(ResourceUtils.getResourcePath("templates/" + imageName));
        if (imageFile.exists()) {
            return ImageIO.read(imageFile);
        }

        // 实在找不到就抛异常
        throw new IOException("file not found!");
    }

    /**
     * 从字体库中获取字体
     *
     * @param font
     *
     * @return File
     *
     * @throws IOException
     */
    public File getFontFile(String font) throws IOException {

        // 从用户自定义的目录中找
        File fontFile = new File(getFontsPath(font));
        if (fontFile.exists()) {
            return fontFile;
        }

        // 找不到的话从默认字体库中找
        fontFile = new File(ResourceUtils.getResourcePath("fonts/" + font));
        if (fontFile.exists()) {
            return fontFile;
        }

        // 实在找不到就抛异常
        throw new IOException(font + " font not found!");
    }
    /**
     * 获取下载过的文件
     *
     * @param url
     *
     * @return File
     */
    public File getDownloadedFile(String url) {
        File imageFile = new File(getDownloadPath(url));

        if (imageFile.exists()) {
            return imageFile;
        }

        return null;
    }

    public String url2fileName(String url) {
        return DigestUtils.md5DigestAsHex(url.getBytes()) + ".png";
    }


}
