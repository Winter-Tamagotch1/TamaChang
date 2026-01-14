package Chatting;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int PORT = 50023;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        List<PrintWriter> listWriters = new ArrayList<PrintWriter>();

        try {
            serverSocket = new ServerSocket();
            
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            serverSocket.bind( new InetSocketAddress(hostAddress, PORT) );
            consoleLog(hostAddress + ":" + PORT);

            //여러 클라이언트의 요청을 수락하고 writer를 연결한다. 이 작업은 쓰레드로 처리해야 한다.
            while(true) {
                Socket socket = serverSocket.accept();
                new ServerThread(socket, listWriters).start();	
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void consoleLog(String log) {
        System.out.println("[server " + Thread.currentThread().getId() + "] " + log);
    }
}