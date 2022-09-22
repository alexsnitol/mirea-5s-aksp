import static java.lang.System.err;
import static java.lang.System.out;

public class Message {

    public synchronized void printPong() throws InterruptedException {
        Thread.sleep(1000);

        out.println("pong");

        notifyAll();
        wait();
    }

    public synchronized void printPing() throws InterruptedException {
        Thread.sleep(1000);

        err.println("ping");

        notifyAll();
        wait();
    }

}
