public class Main {

    public static void main(String[] args) {

        Message message = new Message();

        Ping ping = new Ping(message);
        Pong pong = new Pong(message);

        Thread t1 = new Thread(ping);
        Thread t2 = new Thread(pong);

        t1.start();
        t2.start();

    }

}
