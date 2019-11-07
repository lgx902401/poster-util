package com.lam.poster.drawable;

import com.lam.poster.service.Drawable;
import com.lam.poster.utils.ColorTools;
import lombok.Data;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.util.DigestUtils;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
public class Poster implements Serializable {
    /**
     * 画布宽度
     */
    @NotNull(message = "画布宽度不能为空")
    private Integer width;

    /**
     * 画布高度
     */
    @NotNull(message = "画布高度不能为空")
    private Integer height;

    /**
     * 画布背景颜色
     */
    private String backgroundColor = null;

    /**
     * 图片格式,支持 png 和 jpg
     */
    private String format = "png";

    /**
     * 文本列表
     */
    private ArrayList<Text> texts;

    /**
     * 图片列表
     */
    private ArrayList<Image> images;

    /**
     * 矩形列表
     */
    private ArrayList<Block> blocks;

    /**
     * 线列表
     */
    private ArrayList<Line> lines;
    /**
     *  指定图片大小,单位kb
     */
    private long desFileSize;
    /**
     *  精度,递归压缩的比率,建议小于0.9,等于1会发生死循环
     */
    private double accuracy = 0.9;

    private void push2map(Map<Integer, ArrayList<Drawable>> indexMap, Drawable drawable) {
        ArrayList<Drawable> drawables = indexMap.get(drawable.getZIndex());
        if (drawables == null) drawables = new ArrayList<>();
        else drawables = drawables;
        drawables.add(drawable);
        indexMap.put(drawable.getZIndex(), drawables);
    }


    /**
     * 绘制图片
     *
     * @return File
     * @throws IOException
     */
    public File draw(String imagePath) throws IOException {
        // 初始化图片
        BufferedImage image = new BufferedImage(width, height, format.equals("png") ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_3BYTE_BGR);

        // create graphics
        Graphics2D gd = image.createGraphics();

        // 初始化画布层级 map
        Map<Integer, ArrayList<Drawable>> indexMap = new HashMap<>();
        ArrayList<Drawable> drawables;

        // 如果有背景，画个矩形做背景
        if (backgroundColor != null) {
            gd.setColor(ColorTools.String2Color(backgroundColor));
            gd.fillRect(0, 0, width, height);
        }

        if (this.blocks != null) {
            // 遍历 blocks
            for (Block block : this.blocks) {
                push2map(indexMap, block);
            }
        }

        if (this.lines != null) {
            // 遍历 lines
            for (Line line : this.lines) {
                push2map(indexMap, line);
            }
        }

        if (this.images != null) {
            // 遍历 images
            for (Image img : this.images) {
                push2map(indexMap, img);
            }
        }

        if (this.texts != null) {
            // 遍历 texts
            for (Text text : this.texts) {
                push2map(indexMap, text);
            }
        }

        // 按 index 顺序执行绘画过程
        for (Integer index : indexMap.keySet()) {
            drawables = indexMap.get(index);
            if (drawables != null) {
                for (Drawable drawable : drawables) {
                    drawable.draw(gd, width, height);
                }
            }
        }

        gd.dispose();

        // 创建临时文件
        File file1 = new File(imagePath);
        File file = File.createTempFile(this.key(), "." + format, file1);
        ImageIO.write(image, format, file); // 把文件写入图片
        if (desFileSize != 0){
            commpressPicForScale(file, file, desFileSize, accuracy);
        }
//        file.deleteOnExit(); // 使用完后删除文件

        return file;
    }

    /**
     * 获取key
     *
     * @return String
     */
    private String key() {
        return DigestUtils.md5DigestAsHex(this.toString().getBytes());
    }

    /**
     * @param srcPath     原图片地址
     * @param desPath     目标图片地址
     * @param desFileSize 指定图片大小,单位kb
     * @param accuracy    精度,递归压缩的比率,建议小于0.9,等于1会发生死循环
     * @return
     */
    public static File commpressPicForScale(File srcPath, File desPath,
                                            long desFileSize, double accuracy) {
        try {
            File srcFile = new File(String.valueOf(srcPath));
            long srcFilesize = srcFile.length();
            System.out.println("原图片:" + srcPath + ",大小:" + srcFilesize / 1024 + "kb");
            //递归压缩,直到目标文件大小小于desFileSize
            commpressPicCycle(desPath, desFileSize, accuracy);

            File desFile = new File(String.valueOf(desPath));
            System.out.println("目标图片:" + desPath + ",大小" + desFile.length() / 1024 + "kb");
            System.out.println("图片压缩完成!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desPath;
    }

    private static void commpressPicCycle(File desPath, long desFileSize,
                                          double accuracy) throws IOException {
        File imgFile = new File(String.valueOf(desPath));
        long fileSize = imgFile.length();
        //判断大小,如果小于500k,不压缩,如果大于等于500k,压缩
        if (fileSize <= desFileSize * 1024) {
            return;
        }
        //计算宽高
        BufferedImage bim = ImageIO.read(imgFile);
        int imgWidth = bim.getWidth();
        int imgHeight = bim.getHeight();
        int desWidth = new BigDecimal(imgWidth).multiply(
                new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(imgHeight).multiply(
                new BigDecimal(accuracy)).intValue();
        Thumbnails.of(desPath).size(desWidth, desHeight).outputQuality(accuracy).toFile(desPath);
        //如果不满足要求,递归直至满足小于1M的要求
        commpressPicCycle(desPath, desFileSize, accuracy);
    }

}
