package com.web.java.thread;

/**
 * Created by xieyuhui on 2018/12/19.
 */
public class Daemon {

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Daemon线程也称为守护线程，当jvm中存在非Daemon线程时会退出，
     * 所有的Daemon线程也会立即退出。
     */
    static class DaemonRunner implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
