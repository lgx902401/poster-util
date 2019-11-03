package com.lam.poster.config;


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

    private  Properties properties;
    /**
     * 图片生成路径
     */
    private  String imagePath;

    /**
     * 模板路径
     */
    private  String templatesPath;

    /**
     * 模板路径
     */
    private  String fontsPath;

    public PosterConfig(Properties properties, String imagePath, String templatesPath, String fontsPath) {
        this.properties = properties;
        this.imagePath = imagePath;
        this.templatesPath = templatesPath;
        this.fontsPath = fontsPath;
    }

    void init(){
      this.loadProperties();
        }
    }

    private void loadProperties(){
        InputStream in = (InputStream) AccessController.doPrivileged(new PrivilegedAction<InputStream>() {
            public InputStream run() {
                ClassLoader threadCL = Thread.currentThread().getContextClassLoader();
                return threadCL != null ? threadCL.getResourceAsStream("simplelogger.properties") : ClassLoader.getSystemResourceAsStream("simplelogger.properties");
            }
        });
        if (null != in) {
            try {
                this.properties.load(in);
            } catch (IOException var11) {
            } finally {
                try {
                    in.close();
                } catch (IOException var10) {
                }

            }
        }
    }

    String getStringProperty(String name, String defaultValue) {
        String prop = this.getStringProperty(name);
        return prop == null ? defaultValue : prop;
    }

    boolean getBooleanProperty(String name, boolean defaultValue) {
        String prop = this.getStringProperty(name);
        return prop == null ? defaultValue : "true".equalsIgnoreCase(prop);
    }

    String getStringProperty(String name) {
        String prop = null;

        try {
            prop = System.getProperty(name);
        } catch (SecurityException var4) {
        }

        return prop == null ? this.properties.getProperty(name) : prop;
    }
//
//    public String getTemplatesPath() {
//        return withTail(templatesPath);
//    }
//
//    public String getTemplatesPath(String imageName) {
//        return getTemplatesPath() + imageName;
//    }
//
//    public String getImagePath() {
//        return withTail(imagePath);
//    }
//
//    public String getImagePath(String imagePath){
//        return getImagePath()+ imagePath;
//    }
//
//    public String getFontsPath() {
//        return withTail(fontsPath);
//    }
//
//    public String getFontsPath(String font) {
//        return getFontsPath() + font;
//    }
//
//    public void setTemplatesPath(String templatesPath) {
//        this.templatesPath = templatesPath;
//    }
//
//    public void setFontsPath(String fontsPath) {
//        this.fontsPath = fontsPath;
//    }
//
//    public static String withTail(String path) {
//        return path + (path.endsWith("/") ? "" : "/");
//    }
//
//    public void setImagePath(String imagePath) {
//        this.imagePath = imagePath;
//    }

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


    public String url2fileName(String url) {
        return DigestUtils.md5DigestAsHex(url.getBytes()) + ".png";
    }


}
