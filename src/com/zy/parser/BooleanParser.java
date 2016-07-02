package com.zy.parser;

/**
 * Created by zy on 16-7-1.
 */
public class BooleanParser implements Parser {

    @Override
    public Object parse(Reader reader) {
        return reader.nextBoolean();
    }
}
