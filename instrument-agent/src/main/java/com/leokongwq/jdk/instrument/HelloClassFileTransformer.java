package com.leokongwq.jdk.instrument;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.ProtectionDomain;

/**
 * @author kongwenqiang
 *
 * 1. ClassFileTransformer 是一个接口， 通常有一个Agent来提供实现。
 * 2. ClassFileTransformer 的作用是对class进行转换(个人感觉更多的是增强)
 * 3. transform 的时机发生的jvm对class 进行define
 */
public class HelloClassFileTransformer implements ClassFileTransformer {

    private static final String CLASS_NAME_FOR_TRANSFORM = "D:\\Cat.class.2";

    /**
     * 什么时候被调用呢？
     * 1. the transformer will be called for every new class definition and every class redefinition.
     *  也就是每个类第一次被 define 或 redefine
     *  第一次define 发生在类被加载的时候 -> ClassLoader.defineClass  或 等价的本地方法
     *  redefine 可以通过 Instrumentation.redefineClasses 或 等价的本地方法
     * 2. Retransformation capable transformers will also be called on every class retransformation.
     *   如果这个ClassTransformer 支持 retransform 那么，当每个类被 retransformation 的时候 也会被调用。
     *   retransformation 可以通过 Instrumentation.retransformClasses 或 等价的本地方法 来调用触发
     * 3. ClassFileTransformer.transform 的执行时在 class 类的字节数组 verified or applied 之前。
     * @param loader 被转换类的类加载器, 如果是BootstrapClassLoader 那么该参数为null
     * @param className className 的格式类似 java/lang/Shutdown
     * @param classBeingRedefined 如果是被 redefine (Instrumentation.redefineClasses) 或 retransform (Instrumentation.retransformClasses)触发的类转换，
     *                            那么该参数就是 redefine 或 retransform 的类；如果是正常被加载的类则为null
     * @param protectionDomain {@link ProtectionDomain}
     * @param classfileBuffer 被转换类的class文件字节数组， 注意不要修改改字节数组
     * @return 转换后的类的字节码字节数组， 返回null表示元类没有被transform
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.equals("com/leokongwq/jdk/instrument/Cat")) {
            return null;
        }
        System.out.println("transform classloader: " + loader);
        System.out.println(className);
        System.out.println("classBeingRedefined " + classBeingRedefined);
        return getBytesFromFile(CLASS_NAME_FOR_TRANSFORM);
    }

    private static byte[] getBytesFromFile(String classFilePath) {
        try {
            return Files.readAllBytes(Paths.get(classFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
