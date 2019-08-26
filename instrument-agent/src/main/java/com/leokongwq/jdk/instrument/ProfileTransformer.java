package com.leokongwq.jdk.instrument;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Set;

/**
 * @author kongwenqiang
 */
public class ProfileTransformer implements ClassFileTransformer {

    private Set<String> enhanceMethodNames;

    public ProfileTransformer(Set<String> enhanceMethodNames) {
        this.enhanceMethodNames = enhanceMethodNames;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//        if (!className.equals("com/leokongwq/jdk/instrument/MyStringBuilder")) {
//            return null;
//        }
        if (!className.equals("java/util/Random")) {
            return null;
        }
        try {
            //用于取得字节码类，必须在当前的classpath中，使用全称
            //CtClass ctClass = ClassPool.getDefault().get("com.leokongwq.jdk.instrument.MyStringBuilder");
            CtClass ctClass = ClassPool.getDefault().get("java.util.Random");
            if (ctClass.isFrozen()) {
                return null;
            }
            for (CtMethod ctMethod : ctClass.getMethods()) {
                //需要修改的方法名称
                if (enhanceMethodNames.contains(ctMethod.getName())) {
                    enhanceMethod(ctClass, ctMethod);
                }
            }
            System.out.println("增强完成");
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void enhanceMethod(CtClass ctClass, CtMethod mold) throws Exception {
        String methodName = mold.getName();
        //修改原有的方法名称
        String innerMethod = methodName + "$impl";
        mold.setName(innerMethod);
        //创建新的方法，复制原来的方法
        CtMethod methodNew = CtNewMethod.copy(mold, methodName, ctClass, null);
        //主要的注入代码
        StringBuilder body = new StringBuilder();
        body.append("{\nlong start = System.currentTimeMillis();\n");
        //调用原有代码，类似于method();($$)表示所有的参数
        if (mold.getReturnType() != CtClass.voidType) {
            body.append("Object ret = " + innerMethod + "($$);\n");
        } else {
            body.append(innerMethod + "($$);\n");
        }
        body.append("System.out.println(\"Call to method "
            + methodName
            + " took \" +\n (System.currentTimeMillis()-start) + "
            + "\" ms.\");\n");
        body.append("return ret; \n}");
        //替换新方法
        methodNew.setBody(body.toString());
        //增加新方法
        ctClass.addMethod(methodNew);
    }
}
