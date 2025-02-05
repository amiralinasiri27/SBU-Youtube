package com.example.youtube.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 3000;
    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        ServerSocket listener = null;
        try {
            // Start listening on the specified port
            listener = new ServerSocket(PORT);
            System.out.println("[Server] Server is Running ");
            while (true) {
                Socket client = listener.accept();
                ClientHandler clientThread = new ClientHandler(client);
                System.out.println("[Server] Accept New Client ");
                pool.execute(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (listener != null) {
                try {
                    listener.close();
                    System.out.println("[Server] close ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            pool.shutdown();
        }
    }
}
