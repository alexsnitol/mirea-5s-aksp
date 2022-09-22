public class Pong implements Runnable {

    private Message message;

    public Pong(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        while (true) {
            try {
                message.printPong();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
