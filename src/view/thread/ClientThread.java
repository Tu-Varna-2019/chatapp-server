package view.thread;

public class ClientThread {
    private static int sharedVariable = 0;
    private static ClientThread instance;

    ClientThread() {
        Thread thread1 = new Thread(new Task("Thread 1"));
        Thread thread2 = new Thread(new Task("Thread 2"));
        Thread thread3 = new Thread(new Task("Thread 3"));

        thread1.start();
        try {
            thread1.join(); // izchakva da zavyrshi
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread3.start();
        try {
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static ClientThread getInstance() {
        if (instance == null) {
            instance = new ClientThread();
        }
        return instance;
    }

    static class Task implements Runnable {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (ClientThread.class) {
                System.out.println(name + " locked the shared variable " + sharedVariable);

                if (Thread.holdsLock(sharedVariable)) {
                    System.out.println("Variable is locked");
                } else {
                    System.out.println("Variable is not locked");
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                sharedVariable++;
                System.out.println(name + " incremented the shared variable to " + sharedVariable);
            }
        }
    }
}
