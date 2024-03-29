package model;

import java.util.concurrent.Semaphore;

public class TrafficLightsDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1, true); // Only one student can access the assistant at a time
        Assistant assistant = new Assistant(semaphore);
        assistant.start();

        // Create and start some students
        for (int i = 1; i <= 5; i++) {
            Student student = new Student("Student" + i, assistant, semaphore);
            student.start();
        }
    }
}
