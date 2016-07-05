package com.zy.parser;

/**
 * Created by zy on 16-7-1.
 */
public class JsonParser {

    /**
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Object fromJson(String json, Class<T> clazz) {
        Reader reader = new Reader(json);
        char c;
        try {
            c = reader.next();
        } catch (Reader.BufferException e) {
            e.printStackTrace();
            return null;
        }
        if (reader.hasNext() && c != '['){
            ObjectParser objectParser = new ObjectParser(clazz);
            Object obj = objectParser.parse(reader);
            return obj;
        }
        return null;
    }
}
