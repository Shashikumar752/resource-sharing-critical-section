package com.shashi.threading.resourcesharingcriticalsection;

import java.util.concurrent.ThreadLocalRandom;

public class MinMaxMetrics {

    // Add all necessary member variables
    private volatile long min;
    private volatile long max;

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics() {
        // Add code here
        min = 30;
        max = 30;
    }

    /**
     * Adds a new sample to our metrics.
     */
    public void addSample(long newSample) {
        // Add code here
        synchronized (this) {
            if (newSample > max) {
                max = newSample;
                return;
            }
            if (newSample < min) {
                min = newSample;
                return;
            }
        }
    }


    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        // Add code here
        return min;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        // Add code here
        return max;
    }

    public static void main(String[] args) {
        MinMaxMetrics metrics = new MinMaxMetrics();

        Thread sT = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // Random random = new Random();
                    metrics.addSample(ThreadLocalRandom.current().nextLong(100));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread maxT = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // Random random = new Random();
                    System.out.println("metrics.getMax(): " + metrics.getMax());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread minT = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // Random random = new Random();
                    System.out.println("metrics.getMin(): " + metrics.getMin());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        sT.start();
        minT.start();
        maxT.start();
    }
}
