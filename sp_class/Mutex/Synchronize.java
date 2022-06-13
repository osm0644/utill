package sp.Mutex;

import java.util.concurrent.locks.ReentrantLock;

class ThreadClass extends Thread {

    static ReentrantLock lock = new ReentrantLock();

    String thread_name;
    public ThreadClass(String name) {
        this.thread_name = name;
    }

    public void run() {

        PrintNums(thread_name);

    }

    synchronized static void PrintNums(String str)
    {
        int i;

        System.out.println(str);

        for (i=1; i<=30; i++)
        {
            System.out.print(i+" ");
        }
        System.out.println();
    }
}

public class Synchronize {
    public static void main(String[] args) throws InterruptedException {
        ThreadClass tc1 = new ThreadClass("[Thread1] ");
        ThreadClass tc2 = new ThreadClass("[Thread2] ");
        tc1.start();
        tc2.start();

        ThreadClass.PrintNums("[Main]");

        tc1.join();
        tc2.join();
    }
}