package org.ssh.app.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//http://ideasforjava.javaeye.com/blog/647801
//入门，定义一个做倒计时的类，Counter，该类实现了Runnable接口
public class Counter implements Runnable{

    private int countNum;
    private static int taskCount = 0;
    private final int taskId = taskCount++;

    public Counter(int countNum){
        this.countNum = countNum;
    }

    public String show(){
        return "Id[" + taskId + "] countNum:" + countNum + "  ";
    }

    public void run(){
        while (countNum-- > 0){
            System.out.println(show());
            Thread.yield();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0;i<10;i++){
            exec.execute(new Counter(10));
        }
        exec.shutdown();
    }
}
