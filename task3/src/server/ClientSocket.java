package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalTime;

import static java.lang.System.err;

public class ClientSocket extends Thread {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ClientSocket(Socket socket) {
        this.socket = socket;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e) {
            err.println(e.toString());
        }

        start();
    }

    @Override
    public void run() {
        try {

            while (true) {
                String message = reader.readLine();

                if (message != null) {
                    if (message.equals("exit")) {
                        break;
                    }

                    LocalTime currentTime = LocalTime.now();

                    String structuredMessage
                            = message
                            + " ["
                            + currentTime.getHour()
                            + ":" + currentTime.getMinute()
                            + ":" + currentTime.getSecond()
                            + "]";

                    Server.messageList.add(structuredMessage);
                }
            }

        } catch (Exception e) {
            err.println(e.toString());
        } finally {
            try {
                socket.close();

                reader.close();
                writer.close();
            } catch (Exception e) {
                err.println(e.toString());
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

}
