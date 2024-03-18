package model;

import model.Student;

import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

class Assistant extends Thread {
    private final Semaphore semaphore;
    private final Queue<Student> waitlist;
    private boolean sleeping;


    public Assistant(Semaphore semaphore) {
        sleeping = true;
        this.semaphore = semaphore;
        this.waitlist = new LinkedList<>();
    }

    public void run() {
        while (true) {
            try {
                semaphore.acquire(); // Wait for a student to signal
                if (!waitlist.isEmpty()) {
                    Student nextStudent = waitlist.poll();
                    System.out.println("Assistant: Hello, " + nextStudent.getName() + "!");
                    Thread.sleep(2000); // Simulate some work
                    semaphore.release(); // Allow another student to signal
                } else {
                    System.out.println("No students in the waitlist. Going back to sleep.");
                    semaphore.release(); // Allow another student to signal
                    Thread.sleep(Long.MAX_VALUE); // Sleep until interrupted
                    System.out.printf("pasa por aqui");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(waitlist.size());
            }
        }
    }

    public void addToWaitlist(Student student) {
        waitlist.offer(student);
    }

    public boolean isSleeping() {
        return sleeping;
    }

    public synchronized Queue<Student> getWaitlist() {
        return waitlist;
    }

    public int getWaitlistSize() {
        return waitlist.size();
    }
}