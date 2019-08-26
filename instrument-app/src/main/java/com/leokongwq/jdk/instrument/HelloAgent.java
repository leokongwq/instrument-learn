package com.leokongwq.jdk.instrument;

import java.util.Random;

/**
 * @author kongwenqiang
 *
 * https://www.ibm.com/developerworks/cn/java/j-lo-jse61/index.html
 */
public class HelloAgent {

    public static void main(String[] args) throws Exception {
        System.out.println("=============================================");
        Cat cat = new Cat();
        cat.eat("milk");
        System.out.println("=============================================");

        MyStringBuilder myStringBuilder = new MyStringBuilder();
        System.out.println(myStringBuilder.buildString(10));
        myStringBuilder.buildAndPrintString(10);

        Thread.sleep(1000 * 50);
        Random random = new Random();
        System.out.println(random.nextDouble());
        Thread.sleep(1000 * 50);
        random = new Random();
        System.out.println(random.nextDouble());
    }
}
