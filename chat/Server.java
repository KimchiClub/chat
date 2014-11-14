package chat;

import java.io.*;
import java.net.Socket;

/**
 * Created by zhanadil on 14.11.14.
 */
public class Server implements Runnable {
    public static final int PORT = 8080;

    Socket socket;
    BufferedReader in;
    PrintWriter out;
    BufferedReader sin;
    Thread t;

    boolean quit;

    class Listener implements Runnable {
        public void run() {
            try {
                String s;
                while (true)
                while (null != (s = in.readLine())) {
                    if (s.equals("\\quit")) {
                        quit = true;
                        break;
                    }
                    System.out.println("Company: " + s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Writer implements Runnable {
        public void run() {
            try {
                String s;
                while (true)
                while (null != (s = sin.readLine())){
                    out.println(s);
                    out.flush();
                    if (s.equals("\\quit")) {
                        quit = true;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Server(Socket s) throws Exception {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        sin = new BufferedReader(new InputStreamReader(System.in));

        t = new Thread(this, "Server");
        t.start();
    }

    public void run() {
        quit = false;

        try {
            System.out.println("inetAddress = " + socket);

            (new Thread(new Listener(), "listener")).start();
            (new Thread(new Writer(), "writer")).start();

            while (!quit) {

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                System.out.println("closing...");
                socket.close();
            }
            catch (IOException e) {
                System.out.println("Socket not closed");
            }
        }

        System.out.println("Closed successfully");
    }
}
