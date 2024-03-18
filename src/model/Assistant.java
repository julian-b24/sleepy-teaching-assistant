package model;

import java.util.concurrent.Semaphore;

class Assistant extends Thread {
    private final Semaphore semaphore;

    public Assistant(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void run() {
        while (true) {
            try {
                semaphore.acquire(); // Wait for a student to signal
                System.out.println("Assistant: Hello!");
                Thread.sleep(2000); // Simulate some work
                semaphore.release(); // Allow another student to signal
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}