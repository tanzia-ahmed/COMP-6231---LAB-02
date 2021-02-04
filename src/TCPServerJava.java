import java.io.*;import java.net.*;import java.util.*;

public class TCPServerJava {

    private int port=8989;
    private ArrayList<String> userNames = new ArrayList<>();
    private ArrayList<TCPUserThread> users = new ArrayList<>();

    public TCPServerJava() {    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is ready to accept on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected to this server");
                TCPUserThread newUser = new TCPUserThread(socket, this);
                users.add(newUser);
                newUser.start();
            }
        } catch (IOException ex) {
            System.out.println("Exception found: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    void broadcast(String message, TCPUserThread excludeUser) {
        for (TCPUserThread user : users) {
            if (user != excludeUser) { user.sendMessage(message); }
        }
    }
    void addUserName(String userName) { userNames.add(userName); }
    /** Main Class **/
    public static void main(String[] args) {
        TCPServerJava server = new TCPServerJava();
        server.execute();
    }

}
