public class Ping implements Runnable {

    private Message message;

    public Ping(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        while (true) {
            try {
                message.printPing();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
