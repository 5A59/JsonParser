package com.zy.parser;

/**
 * Created by zy on 16-7-1.
 */
public class IntegerParser implements Parser {

    @Override
    public Object parse(Reader reader) {
        int res;
        res = reader.nextInt();
        return res;
    }

    public static void main(String[] args) {
        String s = "1234";
        Reader reader = new Reader(s);
        IntegerParser integerParser = new IntegerParser();
        int i = (int) integerParser.parse(reader);
        Logger.d(i);
    }
}
