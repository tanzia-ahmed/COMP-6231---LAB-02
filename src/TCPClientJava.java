import java.net.*;

public class TCPClientJava {
    private String host;
    private int port;
    private String userName;

    public TCPClientJava(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket(host, port);
            System.out.println("* Connected to Chat Room. Press Ctrl+C OR Q to quit.* :)");
            new ReadMessageThread(socket, this).start();
            new WriteMessageThread(socket, this).start();
        } catch (Exception ex) {
            System.out.println("Exception found: " + ex.getMessage());
        }
    }

    void setUserName(String userName) {
        this.userName = userName;
    }
    String getUserName() {
        return this.userName;
    }

    /** Main Class
     * To run from cmd prompt,
     * takes arguments host=localhost
     * and port = 8989
     * e.g. > java TCPClientJava localhost 8989 **/

    public static void main(String[] args) {
        if (args.length < 2) return;
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        TCPClientJava client = new TCPClientJava(host, port);
        client.execute();
    }
}
