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
        super("Assistant");
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
                    System.out.println("- Attending, " + nextStudent.getName() + "");
                    Thread.sleep(2000); // Simulate some work
                    nextStudent.leaveAssistantsOffice();
                } else {
                    System.out.println("No students in the waitlist. Going back to sleep.");
                    semaphore.release(); // Allow another student to signal
                    synchronized (this) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Assistant's sleep interrupted");
                //e.printStackTrace();
                System.out.println("Number of students waiting to be attended: " + getWaitlist().size());
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