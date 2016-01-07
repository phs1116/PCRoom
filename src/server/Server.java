package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
	ServerSocket serverSocket;
	Socket socket;
	AdminReceiver admin = null;
	
	public static void main(String[] args){
		new Server();
	}

	public Server() {
		try {
			serverSocket = new ServerSocket(7777);
			while (true) {
				System.out.println("연결 대기중");
				socket = serverSocket.accept();
				System.out.println("연결 성공 : " + getTime() + " " + socket.getInetAddress() + "접속");
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				String type = dis.readUTF();

				// 관리자면 관리자 전용 쓰레드 실행
				if (type.equals("Admin")) {
					System.out.println(type);
					admin = new AdminReceiver(socket);
					admin.start();

				}
				// 사용자면 사용자 쓰레드 실행
				else {
					String seatNum = dis.readUTF();
					Receiver user = new UserReceiver(socket, seatNum);
					System.out.println("서버 ["+seatNum+","+type+"]");
					admin.addUser(seatNum, type);
					user.start();
				}

			}

		} catch (IOException e) {
			System.exit(0);
		}

	}

	public String getTime() { // 현재 시간 출력
		SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm:ss]");
		return sdf.format(new Date());
	}

}
