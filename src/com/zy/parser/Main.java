package com.zy.parser;


import java.io.*;

/**
 * Created by zy on 16-7-1.
 */
public class Main {

    public static class Student {
        private String name;
        private String id;
        private int age;
        private Computer computer;

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", age=" + age +
                    ", computer=" + computer +
                    '}';
        }
    }

    public static class Computer {
        private String display;
        private String cpu;
        private String brand;

        @Override
        public String toString() {
            return "Computer{" +
                    "display='" + display + '\'' +
                    ", cpu='" + cpu + '\'' +
                    ", brand='" + brand + '\'' +
                    '}';
        }
    }


    public static void main(String[] args) {
        char[] buffer = new char[1024];
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            fileInputStream = new FileInputStream("jsontest");
            inputStreamReader = new InputStreamReader(fileInputStream);
            int len = inputStreamReader.read(buffer);
            String json = new String(buffer, 0, len);
            Logger.d("json : " + json);
            JsonParser jsonParser = new JsonParser();
            Student student = (Student) jsonParser.fromJson(json, Student.class);
            Logger.d(student.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStreamReader != null){
                    inputStreamReader.close();
                }
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



