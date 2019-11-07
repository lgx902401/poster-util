package com.lam.poster.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lam.poster.config.PosterConfig;
import com.lam.poster.drawable.Poster;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 查找.json文件
 */
public class FileUtil {
    private static PosterConfig config = new PosterConfig();

    private static File file;

   public static String ReadFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    public static Poster JsonToPoster(){
        String jsonFilePath = config.getJsonFilePath();
        String jsonContent = FileUtil.ReadFile(jsonFilePath);
        //获取jsonobject对象
        JSONObject posterJson = JSONObject.parseObject(jsonContent);
        Poster poster = JSON.toJavaObject(posterJson,Poster.class);
        return poster;
    }
}
