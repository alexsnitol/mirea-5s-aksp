package client;

import java.io.BufferedReader;

import static java.lang.System.err;
import static java.lang.System.out;

public class MessageReader extends Thread {

    private BufferedReader serverReader;


    public MessageReader(BufferedReader serverReader) {
        this.serverReader = serverReader;
    }


    @Override
    public void run() {
        String serverMessage;

        while (true) {
            try {
                Thread.sleep(1000);

                while (serverReader.ready()) {
                    try {
                        serverMessage = serverReader.readLine();
                        out.println("SERVER: " + serverMessage);
                    } catch (Exception e) {
                        err.println(e.toString());
                    }
                }
            } catch (Exception e) {
                err.println(e.toString());
            }
        }
    }
}
