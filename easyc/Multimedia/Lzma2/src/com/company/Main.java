package com.company;

import org.tukaani.xz.BasicArrayCache;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZInputStream;
import org.tukaani.xz.XZOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {


    // CompressXz
    public static void main(String[] args) throws Exception {


        for (int i = 1; i <= 20; i++) {
            String from = "E:\\DataSet_"+i+".tsv";
            String to = "E:\\DataSet_"+i+".txt";
            try (FileOutputStream fileStream = new FileOutputStream(to);
                 XZOutputStream xzStream = new XZOutputStream(
                         fileStream, new LZMA2Options(LZMA2Options.PRESET_MAX), BasicArrayCache.getInstance())) {

                Files.copy(Paths.get(from), xzStream);
            }
        }
    }

/*
    // DecompressXz
    public static void main(String[] args) throws Exception {
        String from = "C:\\\\Users\\\\KhALeD SaBrY\\\\Desktop\\\\DataSet_1.txt";
        String to = "C:\\\\Users\\\\KhALeD SaBrY\\\\Desktop\\\\DataSet_2.txt";
        try (FileInputStream fileStream = new FileInputStream(from);
             XZInputStream xzStream = new XZInputStream(fileStream, BasicArrayCache.getInstance())) {

            Files.copy(xzStream, Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
        }
    }*/
}
