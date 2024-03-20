package model;

import model.Student;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

class Assistant extends Thread {
    private final Semaphore semaphore;
    private final Queue<Student> waitlist;
    private boolean sleeping;
    private boolean flag;
    Random rand = new Random();

    public Assistant(Semaphore semaphore) {
        super("Assistant");
        sleeping = false;
        this.semaphore = semaphore;
        this.waitlist = new ConcurrentLinkedQueue<>();
    }

    public void run() {
        while (true) {
            try {
                semaphore.acquire(); // Wait for a student to signal
                processStudents();
            } catch (InterruptedException e) {
                System.out.println("Assistant's sleep interrupted");
                System.out.println("Number of students waiting to be attended: " + waitlist.size());
                e.printStackTrace();
            }
        }
    }

    private void processStudents() throws InterruptedException {
        synchronized (waitlist) {
            while (waitlist.isEmpty()) {
                if (!sleeping){
                    System.out.println("No students in the waitlist. Going back to sleep.");
                    flag=true;
                }
                sleeping = true;
                semaphore.release(); // Allow another student to signal
                Thread.sleep(2000);

                //waitlist.wait(); // Esperar en la lista de espera
                //System.out.println("Assistant: I'm awake!####################################");
            }
            if (flag){
                System.out.println("Assistant: waking up.");
            }
            flag=false;
            sleeping=false;

            //System.out.println("Assistant: waking up.");
            Student nextStudent = waitlist.poll();
            System.out.println("Assistant: Hello, " + nextStudent.getName() + "!");
            System.out.println("- Attending, " + nextStudent.getName() + "");
            int randomNum = rand.nextInt(5) * 1000;
            Thread.sleep(randomNum); // Simulate some work
            nextStudent.leaveAssistantsOffice();
        }
    }

    public void addToWaitlist(Student student) {
        waitlist.offer(student);
        sleeping = false; // Notify that there are students waiting
    }

    public boolean isSleeping() {
        return sleeping;
    }

    public int getWaitlistSize() {
        return waitlist.size();
    }

    public synchronized Queue<Student> getWaitlist() {
        return waitlist;
    }
}