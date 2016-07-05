package com.zy.parser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by zy on 16-7-1.
 */
public class ObjectParser implements Parser {

    private static final int START_TAG = -1;
    private static final int OBJECT = 0;
    private static final int NAME = 1;
    private static final int VALUE = 2;

    private Class<?> raw;
    private Map<String, Field> map; // <fieldName, typeName>
    private Stack<Integer> stack;
    private Stack<String> nameStack;

    public ObjectParser(Class<?> raw) {
        this.raw = raw;
        map = new HashMap<>();
        stack = new Stack<>();
        nameStack = new Stack<>();
    }

    @Override
    public Object parse(Reader reader) {
        Field[] fields = raw.getFields();
        for (Field f : fields){
            String name = f.getName();
            map.put(name, f);
        }

        Object instance;
        try {
            instance = raw.newInstance();
        } catch (Exception e) {
            return null;
        }

        stack.push(START_TAG);
        while (reader.hasNext() && !stack.empty()){
            if (stack.peek() == START_TAG){
                stack.pop();
            }
            char c;
            try {
                c = reader.next();
            } catch (Reader.BufferException e) {
                e.printStackTrace();
                break ;
            }
            reader.skipNext();

            Logger.d("c is " + c);
            switch (c){
                case '{':
                    if (stack != null && !stack.isEmpty() && stack.peek() == VALUE){
                        reader.moveToLast();
                        setValue(reader, instance);
                    }else {
                        stack.push(OBJECT);
                        stack.push(NAME);
                    }
                    continue;
                case '"':
                    setNameAndValue(reader, instance);
                    break;
                case ',':
                    stack.push(NAME);
                    break;
                case ':':
                    stack.push(VALUE);
                    break;
                case '}':
                    if (stack.peek() == OBJECT){
                        stack.pop();
                    }
                    break;
                case ' ':
                    break;
                default:
                    reader.moveToLast();
                    setNameAndValue(reader, instance);
                    break;
            }
        }

        return instance;
    }

    private void setValue(Reader reader, Object instance) {
        if (nameStack.isEmpty()){
            return;
        }
        String name = nameStack.pop();
        Field field = map.get(name);
        if (field != null){
            Parser p;
            String type = field.getType().getName();
            Logger.d("type is : " + type);
            Class<?> clazz;
            try {
                clazz = Class.forName(type);
            } catch (Exception e) {
                e.printStackTrace();
                clazz = null;
            }
            p = ParserFactory.getParser(type, clazz);
            Object obj = p.parse(reader);
            Logger.d("value is " + obj);
            try {
                field.set(instance, obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        stack.pop();
    }

    private void setNameAndValue(Reader reader, Object instance) {
         if (stack.peek() == NAME){
            String name = reader.nextName();
            Logger.d("name is " + name);
            Field field = map.get(name);
            if (field != null){
                nameStack.push(name);
            }
            stack.pop();
        }else if (stack.peek() == VALUE){
             setValue(reader, instance);
        }
    }

    public static class Test {
        public String a;
        public int b;
        public double d;
        public boolean bo;
        public Test2 t;

        @Override
        public String toString() {
            return "test1 : " + a + " " + b + " " + d + " " + bo + " " + (t == null ? "null" : t.toString());
        }
    }

    public static class Test2 {
        public int a;

        @Override
        public String toString() {
            return " test2 : " + a;
        }
    }

    public static void main(String[] args) {
        Logger.d(ObjectParser.fieldString(Test.class));
        String json = "{ \"a\":\"test\", \"b\":1 , \"d\":1.1, \"bo\":\"True\", \"t\":{\"a\":1}}";
        ObjectParser objectParser = new ObjectParser(Test.class);
        Test t = (Test) objectParser.parse(new Reader(json));
        Logger.d(t.toString());
    }

    public static String fieldString(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        for (Field f : fields){
            stringBuilder.append(f.getType().getTypeName()).append(" ");
        }
        return stringBuilder.toString();
    }
}
