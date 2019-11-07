package com.lam.poster;


import com.lam.poster.drawable.Poster;
import com.lam.poster.utils.FileUtil;
import com.lam.poster.utils.PosterUtil;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        Poster poster = FileUtil.JsonToPoster();
        File file = PosterUtil.draw(poster);
        System.out.println(file);
    }
}
