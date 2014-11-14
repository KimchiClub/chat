package chat;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by zhanadil on 14.11.14.
 */

public class Client implements Runnable {
    String name = "unknown";
    InetAddress inetAddress;
    Thread t;
    BufferedReader in, sin;
    PrintWriter out;

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
                while (null != (s = sin.readLine())) {
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

    public Client() throws Exception {
        inetAddress = InetAddress.getLocalHost();
        t = new Thread(this, "client " + name);
        t.start();
    }

    public Client(String name) throws Exception {
        this.name = name;
        inetAddress = InetAddress.getLocalHost();
        t = new Thread(this, "client " + this.name);
        t.start();
    }

    public Client(String name, InetAddress inetAddress) throws Exception {
        this.name = name;
        this.inetAddress = inetAddress;
        t = new Thread(this, "client " + this.name);
        t.start();
    }

    public void run() {
        try {
            System.out.println("address = " + inetAddress);
            Socket socket = new Socket(inetAddress, Server.PORT);

            try {
                System.out.println("socket = " + socket);
                sin = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);
                (new Thread(new Listener(), "listener")).start();
                (new Thread(new Writer(), "writer")).start();

                while (!quit) {

                }
            }finally {
                System.out.println("closing...");
                socket.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Closed successfully");
    }
}

