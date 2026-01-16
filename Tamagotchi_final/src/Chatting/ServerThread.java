package Chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class ServerThread extends Thread{
	private String ID = null;
	private Socket socket = null;
	List<PrintWriter> lw = null;

	public ServerThread(Socket socket, List<PrintWriter> lw) {
		this.socket = socket;
		this.lw = lw;
	}

	@Override
	public void run() {
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			//Scanner bf = new Scanner(socket.getInputStream());
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			String request="";
			while(true) {
				
				try {
					//request = bf.nextLine(); //Scanner는 nextLine()
					request = bf.readLine();	//BuffredReader는 readLine()				
				}
				catch(java.util.NoSuchElementException e) {
					log("클라이언트로부터 연결 끊김");
					logOut(pw);
					break;
				}
				String[] tokens = request.split(":");

				// if (tokens !=null) System.out.println(tokens[0]);

				if("join".equals(tokens[0])) {
					enter(tokens[1], pw);
					log(this.ID + "님이 입장했습니다.!!");
				}
				else if("message".equals(tokens[0])) {
					send(tokens[1]);
				}
				else if("status".equals(tokens[0])) {
					System.out.println(tokens[1]);
				}
				else if("quit".equals(tokens[0])) {
					logOut(pw);
				}
			}
		}
		catch(IOException e) {
			log(this.ID + "님이 퇴장했습니다.");
		}
	}

	private void logOut(PrintWriter writer) {
		removeWriter(writer);
		String data = this.ID + "님이 퇴장했습니다===";
		write(data);
	}

	private void addWriter(PrintWriter writer) {
		synchronized (lw) {
			lw.add(writer);
		}
	}

	private void removeWriter(PrintWriter writer) {
		synchronized (lw) {
			lw.remove(writer);
		}
	}

	private void send(String data) {
		write("["+ this.ID + "] : " + data);
	}

	private void enter(String nickname, PrintWriter writer) {
		this.ID = nickname;
		String data = nickname + "님이 입장하였습니다.====";
		write(data);
		addWriter(writer);
	}

	private void write(String data) {
		synchronized (lw) {
			for(PrintWriter writer : lw) {
				writer.println(data);				
				writer.flush();
			}
		}
	}

	private void log(String log) {
		System.out.println(log);
	}
}