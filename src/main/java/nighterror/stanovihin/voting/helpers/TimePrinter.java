package nighterror.stanovihin.voting.helpers;

import java.sql.Time;

public class TimePrinter extends Thread{

    public TimePrinter(){

    }

    @Override
    public void run() {
        while(true){
            System.out.println("Current time in millis: " + System.currentTimeMillis());
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
