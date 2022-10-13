package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.System.err;
import static java.lang.System.out;

public class Server {

    protected static ConcurrentLinkedQueue<String> messageList = new ConcurrentLinkedQueue<>();
    protected static List<ClientSocket> clientList = new LinkedList<>();

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            out.println("Server is started!");
            out.println("Details: " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());

            MessageSender messageSender = new MessageSender(clientList, messageList);
            messageSender.start();

            while (true) {
                Socket client = serverSocket.accept();
                clientList.add(new ClientSocket(client));
            }
        } catch (Exception e) {
            err.println(e.toString());
        }
    }

}
