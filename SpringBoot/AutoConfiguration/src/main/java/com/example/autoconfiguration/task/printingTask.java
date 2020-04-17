package com.example.autoconfiguration.task;

public class printingTask extends Thread{

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                println(String.valueOf(i));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void println(String s) {
        System.out.println("println : " + s);
    }
}
