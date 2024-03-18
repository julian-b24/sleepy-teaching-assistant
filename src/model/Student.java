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
                wakeUpAssistant();
                Thread.sleep(1000); // Simulate some work after accessing the assistant
                leaveAssistantsOffice();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void wakeUpAssistant() {
        assistant.interrupt(); // Wake up the assistant if it's sleeping
    }

    public void leaveAssistantsOffice() {
        semaphore.release(); // Release the assistant for others to use
    }
}
