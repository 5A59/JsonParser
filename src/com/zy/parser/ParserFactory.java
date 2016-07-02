package com.zy.parser;

/**
 * Created by zy on 16-7-2.
 */
public class ParserFactory {

    public static final String INTEGER = "int";
    public static final String DOUBLE = "double";
    public static final String FLOAT = "float";
    public static final String BOOLEAN = "boolean";
    public static final String STRING = "java.lang.String";
    public static final String OBJECT = "object";

    public static Parser getParser(String type, Class<?> clazz) {
        if (type.equals(INTEGER)){
            return new IntegerParser();
        }else if (type.equals(DOUBLE)){
            return new DoubleParser();
        }else if (type.equals(FLOAT)){
            return new FloatParser();
        }else if (type.equals(BOOLEAN)){
            return new BooleanParser();
        }else if (type.equals(STRING)){
            return null;
        }else {
            return new ObjectParser(clazz);
        }
    }

    public static Parser getDefaultIntegerParser() {
        return new IntegerParser();
    }

    public static Parser getDefaultBooleanParser() {
        return new BooleanParser();
    }

    public static Parser getDefaultDoubleParser() {
        return new DoubleParser();
    }

    public static Parser getDefaultFloatParser() {
        return new FloatParser();
    }

    public static Parser getObjectParser(Class<?> clazz) {
        return new ObjectParser(clazz);
    }
}
