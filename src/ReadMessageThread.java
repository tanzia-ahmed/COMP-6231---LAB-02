import java.io.*;
import java.net.*;

public class ReadMessageThread extends Thread{
    private BufferedReader bReader;
    private Socket socket;
    private TCPClientJava client;
    public ReadMessageThread(Socket socket, TCPClientJava client) {
        this.socket = socket;this.client = client;
        try {
            InputStream is = socket.getInputStream();
            bReader = new BufferedReader(new InputStreamReader(is));
        } catch (IOException ex) {
            System.out.println("inputStream in readingMsgThread: " + ex.getMessage());
//            ex.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                String string = bReader.readLine();
                System.out.println("\n" + string);
                if (client.getUserName() != null) {
                    System.out.print("[" + client.getUserName() + "]: ");
                }
            } catch (IOException ex) {
                System.out.println("readmsgthread: " + ex.getMessage());
//                ex.printStackTrace();
                break;
            }
        }
    }
}
