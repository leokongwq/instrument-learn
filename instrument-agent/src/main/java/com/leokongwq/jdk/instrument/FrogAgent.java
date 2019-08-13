package com.leokongwq.jdk.instrument;

import java.lang.instrument.Instrumentation;

/**
 * @author kongwenqiang
 *
 * 名称为premain的方法 只能有如下两种格式
 */
public class FrogAgent {

    /**
     * high priority
     */
    public static void premain(String options, Instrumentation ins) {
        if (options != null) {
            System.out.println(String.format("FrogAgent's params is %s", options));
        }
        //可以顺序添加多个transformer; 强烈建议同一个transformer不要添加多次
        ins.addTransformer(new HelloClassFileTransformer());
    }

    /**
     *  low priority
     */
    public static void premain(String agentArgs) {

    }

    public static void agentmain() {

    }
}