package com.example.face;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleQuestion {
    public static void main(String[] args) {
        final List<String> strList = new ArrayList<>();
        strList.add("Hello");
        strList.add("world");
        List<String> unmodifiableStrList = Collections.unmodifiableList(strList);
        unmodifiableStrList.add("again");
    }
}
