package com.github.jitnegii.ytspotbot;

public class Log {
    public static void e(String tag,String message){
        System.err.println(tag+" : "+message);
    }

    public static void d(String tag,String message){
        print(tag+" : "+message);
    }


    public static void print(String message){
        System.out.println(message);
    }
}
