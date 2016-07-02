package com.zy.parser;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zy on 16-7-1.
 */
public class ObjectParser implements Parser {

    private Class<?> raw;
    private Map<String, String> map; // <fieldName, typeName>

    public ObjectParser(Class<?> raw) {
        this.raw = raw;
        map = new HashMap<>();
    }

    @Override
    public Object parse(Reader reader) {
        Field[] fields = raw.getFields();
        for (Field f : fields){
            String name = f.getName();
            Type type = f.getType();
            map.put(name, type.getTypeName());
        }



        return null;
    }

    public static class Test {
        int a;
        double b;
        float c;
        boolean d;
        Integer ai;
        Double bd;
        Float cf;
        Boolean db;
        Testa t;
    }

    public static class Testa {
        int a;
    }

    public static void main(String[] args) {
        Field[] fields = Test.class.getDeclaredFields();
        Logger.d("length : " + fields.length);
        for (Field f : fields){
            Logger.d(f.getName() + " " + f.getType().getName() + " : " + f.getType().getTypeName());
        }
    }
}
