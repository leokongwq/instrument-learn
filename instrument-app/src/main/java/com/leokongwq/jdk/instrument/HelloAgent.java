package com.leokongwq.jdk.instrument;

/**
 * @author kongwenqiang
 *
 * https://www.ibm.com/developerworks/cn/java/j-lo-jse61/index.html
 */
public class HelloAgent {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println("=============================================");
        Cat cat = new Cat();
        cat.eat("milk");
        System.out.println("=============================================");
    }
}
