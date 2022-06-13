package sp.Thread;


class ThreadClass extends Thread {

    String idx = "";
    ThreadClass(String idx){
        this.idx = idx;
    }

    public void run(){
        for(int i = 0; i < 10; i++){
            System.out.println(idx + i);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
public class ThreadExample {

    //Thread 2개를 만든후, main 함수와 thread 두개에서 0부터 9까지 출력
    public static void main(String[] args) throws Exception{

        ThreadClass m1 = new ThreadClass("[Thread1]");
        m1.start();

        ThreadClass m2 = new ThreadClass("[Thread2]");
        m2.start();

        for(int i = 0; i < 10; i++){
            System.out.println("[Main]" + i);
        }

        m1.join();
        m2.join();

    }


}
