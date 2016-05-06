package com.example.mc.videolooper;

import java.io.FilenameFilter;
import java.io.File;

/**
 * Created by MC on 1/14/2016.
 */
public class GenericExtFilter implements FilenameFilter {
    private String ext;
    public GenericExtFilter(String ext){
        this.ext = ext;
    }
    public boolean accept(File dir, String name){
        return (name.endsWith(ext));
    }
}
