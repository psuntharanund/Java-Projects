import java.util.*;

public class ThreadManager{
    
    protected List<Thread> numberOfThreads;

    public ThreadManager(){
        this.numberOfThreads = new ArrayList<>();
    }

    public void createThreads(int number){
        MyRunnable myRunnable = new MyRunnable();
        for (int i = 1; i <= number; i++){
            Thread thread = new Thread(myRunnable);
            thread.setName("Thread" + i);
            numberOfThreads.add(thread);
        }
    }

    public void runThreads(){
        for (Thread thread : numberOfThreads){
            System.out.println(thread + " has started. . .");
            thread.start();
        }
    }
}
