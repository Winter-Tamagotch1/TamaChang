package Chatting;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class NetworkManager {
    // 1. 싱글톤 인스턴스 (어디서든 접근 가능하게)
    private static NetworkManager instance;
    
    // 2. 통신 관련 변수
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    private Thread receiveThread;
    
    // 메시지가 오면 누구한테 알려줄지 저장하는 변수 (콜백 함수)
    private Consumer<String> onMessageReceived;

    private NetworkManager() {} // 생성자 막기

    public static NetworkManager getInstance() {
        if (instance == null) instance = new NetworkManager();
        return instance;
    }

    // 서버 연결
    public void connect(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            
            // 수신 스레드 바로 시작
            startReceiving();
            System.out.println("서버 연결 성공!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 메시지 전송 (어디서든 호출 가능)
    public void send(String msg) {
        if (pw != null) pw.println(msg);
    }

    // 수신 리스너 등록 (화면이 바뀔 때마다 "나한테 알려줘"라고 등록)
    public void setOnMessageReceived(Consumer<String> callback) {
        this.onMessageReceived = callback;
    }

    // 내부적으로 계속 듣는 스레드
    private void startReceiving() {
        receiveThread = new Thread(() -> {
            try {
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    
                    // 등록된 화면(리스너)이 있다면 그쪽으로 메시지 토스
                    if (onMessageReceived != null) {
                        onMessageReceived.accept(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("연결 끊김");
            }
        });
        receiveThread.start();
    }
}