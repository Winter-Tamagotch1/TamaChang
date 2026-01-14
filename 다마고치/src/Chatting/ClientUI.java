package Chatting;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import screens.ScreenPlaza;

public class ClientUI {
//    private static final String SERVER_IP = "192.168.23.51";
//    private static final int SERVER_PORT = 5000;

    private static String SERVER_IP = "192.168.45.61";  //ipconfig로 확인할 것.
    private static final int SERVER_PORT = 50023;

    public static void main(String[] args) {
        String name = null;
        Scanner sc = new Scanner(System.in);

        System.out.print("Server IP를 입력하세요 : ");
        SERVER_IP = sc.nextLine();

        while( true ) {
            System.out.println("사용할 아이디 를 적어주세요.");
            name = sc.nextLine();
            if (name.isEmpty() == false ) {
                break;
            }
            System.out.println("한 글자 이상 입력해 주세요.\n");
        }
        sc.close();

        Socket socket = new Socket();
        try {
            socket.connect( new InetSocketAddress(SERVER_IP, SERVER_PORT) );
            consoleLog("입장 완료!");
            new ClientView(name, socket).show();

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            String request = "join:" + name + "\r\n";
            pw.println(request);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void consoleLog(String log) {
        System.out.println(log);
    }
}