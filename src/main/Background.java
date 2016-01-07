package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import chat.server.SeatGUI;

public class Background {
	Map<Integer, String> Users = new HashMap<>();
	Socket socket = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	Main main;

	int seatNum;
	String id;
	public void setMain(Main main) {
		this.main = main;
	}



	synchronized public void connect() {
		try {
			Collections.synchronizedMap(Users);
			// 소켓 연결
			socket = new Socket("127.0.0.1", 7777);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			//seatGUI.userSeat.setOn(); // 자리 on상태로 변경
			System.out.println("서버 연결 성공");
			dos.writeUTF("Admin");
			while (dis != null) {
				seatNum = Integer.parseInt(dis.readUTF())-1;
				id = dis.readUTF();
				System.out.println("메인 백그라운드 ["+seatNum+","+id+"]");
				Users.put(seatNum, id);
				main.setOnSeat(seatNum, id);
				
			}

		} catch (IOException e) {
		//	seatGUI.userSeat.setOff();// 접속 종료되면 off상태로.
		}
	}

}

