package server;

import java.io.BufferedWriter;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.System.out;

public class MessageSender extends Thread {

    private List<ClientSocket> clientList;
    private ConcurrentLinkedQueue<String> messageList;


    public MessageSender(List<ClientSocket> clientList, ConcurrentLinkedQueue<String> messageList) {
        this.clientList = clientList;
        this.messageList = messageList;
    }


    @Override
    public void run() {
        try {
            BufferedWriter clientWriter;

            while (true) {
                Thread.sleep(5000);

                if (messageList.isEmpty()) {
                    continue;
                }

                for (ClientSocket client : clientList) {
                    if (!client.getSocket().isClosed()) {
                        clientWriter = client.getWriter();

                        for (String message : messageList) {
                            clientWriter.write(message + "\n");
                            clientWriter.flush();
                        }
                    }
                }

                LocalTime currentTime = LocalTime.now();

                out.println(
                        currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond() + " -"
                                + " Has been send " + messageList.size()
                                + " messages to " + clientList.size() + " clients");

                messageList.clear();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
