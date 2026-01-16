package Chatting;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientView {
    private String name;
    private Frame frame;
    private Panel pannel;
    private Button buttonSend;
    private TextField textField;
    private TextArea textArea;

    private Socket socket;
    int keyCount=0;
    int backKeyCount=0;
    public ClientView(String name, Socket socket) {
        this.name = name;
        frame = new Frame("마을광장");
        frame.setSize(1280,720);
        pannel = new Panel();
        buttonSend = new Button("전송");
        textField = new TextField();
        textArea = new TextArea(30, 80);
        this.socket = socket;

        new ChatClientReceiveThread(socket).start();
    }

    public void show() {
        buttonSend.setBackground(Color.BLACK);
        buttonSend.setForeground(Color.WHITE);
        buttonSend.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                sendMessage();
            }
        });

        textField.setColumns(80);
        textField.setForeground(Color.BLACK);
        textField.addKeyListener( new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
            	
                char keyCode = e.getKeyChar();
                if (keyCode == KeyEvent.VK_ENTER) {  //전송
                    sendMessage();
                    keyCount=0;
                    backKeyCount=0;
                }
                
                else if (keyCode ==KeyEvent.VK_BACK_SPACE )  //지우기
                	backKeyCount++; 
                else keyCount++;               	
                
                //이제부터는 사용자가 누른 키값에 따라 감성분서 처리르 추가해봅니다. 
                if(backKeyCount >=20) {   //백스페이스키를 몇번 눌렀는지 체크.
                	sendStatus(name+"이 문자를 썼다 지우기를 반복합니다..망설이나 봅니다");
                	backKeyCount=0;
                }
                if(keyCount >=20) { 
                	sendStatus(name+"이 입력중입니다.");
                	keyCount=0;
                }
                //기타 다양한 상태메시지를 추가해도 좋습니다.
                
            }
        });

        pannel.setBackground(Color.BLACK);
        pannel.setForeground(Color.WHITE);
        pannel.add(textField);
        pannel.add(buttonSend);
        frame.add(BorderLayout.SOUTH, pannel);

        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        frame.add(BorderLayout.CENTER, textArea);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                PrintWriter pw;
                try {
                    pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                    String request = "quit\r\n";
                    pw.println(request);
                    System.exit(0);
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.setVisible(true);
        frame.pack();
    }

    private void sendMessage() {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            String message = textField.getText();
            String request = "message:" + message + "\r\n";
            pw.println(request);

            textField.setText("");
            textField.requestFocus();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void sendStatus(String msg) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            
            String request = "status:" + msg + "\r\n";
            pw.println(request);
            textField.requestFocus();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    //GUI_쓰레드 처리를 위해 내부 클래스로 만듬.
    private class ChatClientReceiveThread extends Thread{
    Socket socket = null;

    ChatClientReceiveThread(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            while(true) {
                String msg = br.readLine();
                textArea.append(msg);
                textArea.append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
}