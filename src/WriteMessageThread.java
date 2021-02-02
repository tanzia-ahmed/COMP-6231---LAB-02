import java.io.*;
import java.net.*;
import java.util.Scanner;

public class WriteMessageThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private TCPClientJava client;
    public WriteMessageThread(Socket socket, TCPClientJava client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        System.out.println("----------------Inside WriteMessage thread run----------------- ");
        Console console = System.console();
        try {
            System.out.print("Enter name: ");
            String userName = console.readLine();
            client.setUserName(userName);
            writer.println(userName);

            String text;

            do {
                text = console.readLine("[" + userName + "]: ");
                writer.println(text);

            } while (!text.equals("bye"));
        }catch (Exception ex){
            System.out.println("Error writing to server--1: " + ex.getMessage());
        }

        try {
            socket.close();
        } catch (IOException ex) {

            System.out.println("Error writing to server--2: " + ex.getMessage());


        }
    }
}
