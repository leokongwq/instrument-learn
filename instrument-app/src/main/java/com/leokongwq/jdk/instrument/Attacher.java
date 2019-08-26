package com.leokongwq.jdk.instrument;

import com.sun.tools.attach.VirtualMachine;

public class Attacher extends Thread {

    private final String jar;

    private final String pid;

    Attacher(String attachJar, String pid) {
        // 记录程序启动时的 VM 集合
        jar = attachJar;
        this.pid = pid;
    }

    @Override
    public void run() {
        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(jar);
            vm.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Attacher("D:\\instrument-agent-1.0-SNAPSHOT.jar", "104764").start();
    }
}
