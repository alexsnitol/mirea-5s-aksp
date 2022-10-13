package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static java.lang.System.err;
import static java.lang.System.in;
import static java.lang.System.out;

public class Client {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 8080)) {
            out.println("Connected to server");
            out.println("Details: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String username = "anonymous";

            out.print("your username: ");
            username = reader.readLine();

            BufferedWriter serverWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            MessageReader messageReader = new MessageReader(serverReader);
            messageReader.start();

            while (true) {
                try {

                    String str = reader.readLine();

                    serverWriter.write("(" + username + ")" + str + "\n");
                    serverWriter.flush();

                    if (str.equals("exit")) {
                        break;
                    }

                } catch (Exception e) {
                    err.println(e.toString());
                }
            }

            serverReader.close();
            serverWriter.close();
        } catch (Exception e) {
            err.println(e.toString());
        }

        out.println("You are disconnected from server");
    }

}
