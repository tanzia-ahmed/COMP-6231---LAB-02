import java.io.*;
import java.net.*;

public class TCPUserThread extends Thread {
    private Socket socket;
    private TCPServerJava server;
    private PrintWriter pWriter;
    public TCPUserThread(Socket socket, TCPServerJava server) {
        this.socket = socket;
        this.server = server;
    }
    public void run() {
        try {
            /** input stream **/
            InputStream is = socket.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
            /** output stream**/
            OutputStream os = socket.getOutputStream();
            pWriter = new PrintWriter(os, true);
            String userName = bReader.readLine();
            server.addUserName(userName);
            String sBroadcast = "New user "+ userName+" dropped in! ";
            server.broadcast(sBroadcast, this);
            String message;
            do {
                message = bReader.readLine();
                sBroadcast = "[" + userName + "]: " + message;
                server.broadcast(sBroadcast, this);
            } while (!message.equals("Q"));
            socket.close();
        } catch (Exception ex) {
            System.out.println("User exits");
//            ex.printStackTrace();
        }
    }
    void sendMessage(String message) {
        pWriter.println(message);
    }
}
