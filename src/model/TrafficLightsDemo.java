package model;

import java.util.concurrent.Semaphore;

public class TrafficLightsDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1); // Only one student can access the assistant at a time
        Assistant assistant = new Assistant(semaphore);
        assistant.start();

        // Create and start some students
        for (int i = 0; i < 5; i++) {
            Student student = new Student(assistant, semaphore);
            student.start();
        }
    }
}
