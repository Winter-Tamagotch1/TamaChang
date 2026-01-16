package Chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class sendMessageThread extends Thread{
	PrintWriter pw;
	Socket socket;
	BufferedReader reader ;
	public sendMessageThread(Socket socket){

		this.socket= socket;
		
		//키보드에서 보낼 메시지를 입력받을 InputStreamReader와 BufferedReader의 조합으로 Setting.
		try {
     	// 클라이언트 입력에서 사용하는 인코딩 지정 가능
			reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));  
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//전송을 위한 PrintWriter setting
		try {
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendMessage() throws IOException {
		//System.out.print("보낼 메시지 : ");
		String message = reader.readLine();
		String request = "message:" + message +"\n";
		pw.println(request);		
	}


	public void run() {
		while(true) {
			try {
				sendMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}