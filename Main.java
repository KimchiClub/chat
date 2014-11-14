import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.Runnable;
import java.lang.Thread;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("-s")) {
            ServerSocket s = new ServerSocket(chat.Server.PORT);
            System.out.println("Connecting with clients...");
            try {
                Socket socket = s.accept();
                try {
                    new chat.Server(socket);
                    System.out.println("connected with client.");
                } catch(Exception e) {
                    socket.close();
                }
            } finally {
                s.close();
            }
        } else if (args.length > 1) {
            new chat.Client(args[0], InetAddress.getByName(args[1]));
        } else if (args.length > 0) {
            new chat.Client(args[0]);
        } else {
            new chat.Client();
        }
    }
}