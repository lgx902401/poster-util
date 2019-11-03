package com.lam.poster.utils;

import com.lam.poster.config.PosterConfig;
import com.lam.poster.drawable.Poster;

import java.io.File;
import java.io.IOException;

public class PosterUtil {

    private static PosterConfig config;

    private static File file;


    public static void setConfig(PosterConfig config) {
        PosterUtil.config = config;
    }

    public static File draw(Poster poster){
        try {
             file = poster.draw(config.getImagePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
