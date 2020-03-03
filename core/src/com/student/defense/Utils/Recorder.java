package com.student.defense.Utils;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Recorder {

    private static StringBuilder sb = new StringBuilder();

    public static void addPoint(String s) {
        System.out.println("adding " + s);
        sb.append(s);
    }

    public static void toFile() throws IOException {
        System.out.println("Writing file");
        BufferedWriter writer = new BufferedWriter(new FileWriter("path"));
        writer.write(sb.toString());
        writer.close();
    }
}
