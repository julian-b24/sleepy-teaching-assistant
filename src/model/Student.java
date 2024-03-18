package model;

import java.util.concurrent.Semaphore;

class Student extends Thread {
    private final Assistant assistant;
    private final Semaphore semaphore;

    public Student(Assistant assistant, Semaphore semaphore) {
        this.assistant = assistant;
        this.semaphore = semaphore;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); // Simulate some waiting time between requests
                semaphore.acquire(); // Signal the assistant
                assistant.interrupt(); // Wake up the assistant if it's sleeping
                Thread.sleep(1000); // Simulate some work after accessing the assistant
                semaphore.release(); // Release the assistant for others to use
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
