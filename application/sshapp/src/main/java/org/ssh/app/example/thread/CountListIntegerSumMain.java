package org.ssh.app.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//如何充分利用多核CPU，计算很大的List中所有整数的和
//http://www.javaeye.com/topic/711162
public class CountListIntegerSumMain {

    /**
     * 计算List中所有整数的和<br>
     * 采用多线程，分割List计算
     *
     * @author 飞雪无情
     * @since 2010-7-12 topic/711162
     */
    public class CountListIntegerSum {
        private long sum;// 存放整数的和
        private CyclicBarrier barrier;// 障栅集合点(同步辅助器)
        private List<Integer> list;// 整数集合List
        private int threadCounts;// 使用的线程数

        public CountListIntegerSum(List<Integer> list, int threadCounts) {
            this.list = list;
            this.threadCounts = threadCounts;
            barrier = new CyclicBarrier(threadCounts + 1);// 创建的线程数和主线程main
        }

        /**
         * 获取List中所有整数的和
         *
         * @return List中所有整数的和
         */
        public long getIntegerSum() {
            ExecutorService exec = Executors.newFixedThreadPool(threadCounts);
            int len = list.size() / threadCounts;// 平均分割List
            for (int i = 0; i < threadCounts; i++) {
                // 创建线程任务
                exec.execute(new SubIntegerSumTask(list.subList(i * len, len * (i + 1))));
            }
            try {
                barrier.await();// 关键，使该线程(main主线程)在障栅处等待，直到所有的线程都到达障栅处
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + ":Interrupted");
            } catch (BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + ":BrokenBarrier");
            }
            exec.shutdown();
            return sum;
        }

        /**
         * 分割计算List整数和的线程(任务)<br>
         * 内部类实现，可以隐藏实现细节，更便于访问外部类的私有变量
         *
         * @author 飞雪无情
         * @since 2010-7-12
         */
        private class SubIntegerSumTask implements Runnable {
            private List<Integer> subList;

            public SubIntegerSumTask(List<Integer> subList) {
                this.subList = subList;
            }

            public void run() {
                long subSum = 0L;
                for (Integer i : subList) {
                    subSum += i;
                }
                synchronized (CountListIntegerSum.this) {// 在CountListIntegerSum对象上同步
                    sum += subSum;
                }
                try {
                    barrier.await();// 关键，使该线程在障栅处等待，直到所有的线程都到达障栅处
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + ":Interrupted");
                } catch (BrokenBarrierException e) {
                    System.out.println(Thread.currentThread().getName() + ":BrokenBarrier");
                }
                System.out.println("分配给线程：" + Thread.currentThread().getName()
                        + "那一部分List的整数和为：\tSubSum:" + subSum);
            }

        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        int threadCounts = 10;// 采用的线程数
        // 生成的List数据
        for (int i = 1; i <= 10000000; i++) {
            list.add(i);
        }

        CountListIntegerSumMain m = new CountListIntegerSumMain();
        CountListIntegerSum s = m.new CountListIntegerSum(list, threadCounts);
        long sum = s.getIntegerSum();
        System.out.println("List中所有整数的和为:" + sum);
    }

}
