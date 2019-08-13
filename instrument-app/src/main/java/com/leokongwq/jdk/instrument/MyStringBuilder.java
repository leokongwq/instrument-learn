package com.leokongwq.jdk.instrument;

public class MyStringBuilder {

    public String buildString(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (char)(i%26 + 'a');
        }
        return result;
    }

    public void buildAndPrintString(int length) {
        System.out.println(this.buildString(length));
    }
}
