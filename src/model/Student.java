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
                Thread.sleep((long) ((Math.random()+1 )* 5000)); // Simulate some waiting time between requests
                if (!assistant.getWaitlist().contains(this)){
                    System.out.println(getName() + " needs help from the assistant.");

                    synchronized (assistant) {
                        if (assistant.getWaitlistSize() < 3) {
                            assistant.addToWaitlist(this);
                            //System.out.println(assistant.getWaitlistSize());
                            assistant.notify(); // Notificar al asistente que hay un estudiante esperando
                            System.out.println(getName() + " is waiting in line.");
                        } else {
                            System.out.println(getName() + " is coding.");
                            Thread.sleep((long) (Math.random() * 5000)); // Simulate coding time
                        }
                    }
                }
                //waitForAssistance(); // Esperar la asistencia después de notificar al asistente
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void wakeUpAssistant() {
        //assistant.interrupt(); // Wake up the assistant if it's sleeping
    }

    public void leaveAssistantsOffice() {
        semaphore.release(); // Release the assistant for others to use
    }

    public synchronized void waitForAssistance() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
