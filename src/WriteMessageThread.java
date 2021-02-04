import java.io.*;
import java.net.*;

public class WriteMessageThread extends Thread {
    private PrintWriter pWriter;
    private Socket socket;
    private TCPClientJava client;

    public WriteMessageThread(Socket socket, TCPClientJava client) {
        this.socket = socket;
        this.client = client;
        try {
            OutputStream os = socket.getOutputStream();
            pWriter = new PrintWriter(os, true);
        } catch (IOException ex) {
            System.out.println("Exception occured- outputStream in WriteMsgThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        Console console = System.console();
        try {
            System.out.print("Nick-name: ");
            String userName = console.readLine();
            client.setUserName(userName);
            pWriter.println(userName);
            String message;
            do {
                message = console.readLine("[" + userName + "]: ");
                pWriter.println(message);
            } while (!message.equals("Q"));
        } catch (Exception ex) {
            System.out.println("User exited ");
        }
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("IOException writing: " + ex.getMessage());
        }
    }
}
