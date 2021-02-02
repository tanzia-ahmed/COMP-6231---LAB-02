import java.io.*;
import java.net.*;
import java.util.*;

public class UserThread extends Thread {
    private Socket socket;
    private TCPServerJava server;
    private PrintWriter writer;

    public UserThread(Socket socket, TCPServerJava server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        System.out.println("-------------------Running user thread--------------------------");
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            System.out.println("-------------------Calling printUser method--------------------------");
            printUsers();
            System.out.println("-------------------printUser method ends--------------------------");

            System.out.println("-------------------Reading username--------------------------");
            String userName = reader.readLine()+" user";
            server.addUserName(userName);

            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);

            } while (!clientMessage.equals("bye"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " has quitted.";
            server.broadcast(serverMessage, this);

        } catch (Exception ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Sends a list of online users to the newly connected user.
     */
    void printUsers() {
        System.out.println("-------------------Inside printUser method--------------------------");
        if (server.hasUsers()) {
            System.out.println("-------------------Found users--------------------------");
            System.out.println("-------------------Writing users--------------------------");
            writer.println("Connected users: " + server.getUserNames());
        } else {
            System.out.println("-------------------No user found--------------------------");
            writer.println("No other users connected");
        }


    }

    /**
     * Sends a message to the client.
     */
    void sendMessage(String message) {
        System.out.println("-------------------Inside sendMessage method--------------------------");
        writer.println(message);
    }
}
