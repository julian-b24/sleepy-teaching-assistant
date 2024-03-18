package model;

import java.util.concurrent.Semaphore;


public class Student extends Thread {
    private final Assistant assistant;
    private final Semaphore semaphore;

    public Student(String name, Assistant assistant, Semaphore semaphore) {
        super(name);
        this.assistant = assistant;
        this.semaphore = semaphore;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); // Simulate some waiting time between requests
                semaphore.acquire(); // Signal the assistant
                if (assistant.isSleeping()) {
                    System.out.println(getName() + " is waking up the assistant.");

                    assistant.interrupt(); // Wake up the assistant if it's sleeping
                    System.out.println(getName() + " prueba");
                } else if (assistant.getWaitlist().size()< 3) {
                    assistant.addToWaitlist(this);
                    System.out.println(getName() + " is waiting in line.");
                    semaphore.release(); // Release the assistant for others to use
                    Thread.sleep(Long.MAX_VALUE); // Sleep until attended
                } else {
                    System.out.println(getName() + " is coding.");
                    semaphore.release(); // Release the assistant for others to use
                    Thread.sleep((long) (Math.random() * 5000)); // Simulate coding time
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void waitForAssistance() {
        interrupt();
    }
}
