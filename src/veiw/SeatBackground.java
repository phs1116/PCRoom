package veiw;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import chat.server.SeatGUI;

public class SeatBackground {
	Socket socket = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	SeatGUI seatGUI;

	public void setSeatGUI(SeatGUI seatGUI) {
		this.seatGUI = seatGUI;
	}

	public void connect() {
		try {
			// 소켓 연결
			socket = new Socket("127.0.0.1", 7777);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			seatGUI.userSeat.setOn(); // 자리 on상태로 변경
			System.out.println("서버 연결 성공");
			dos.writeUTF("좌석1");
			while (dis != null) {
			}

		} catch (IOException e) {
			seatGUI.userSeat.setOff();// 접속 종료되면 off상태로.
		}
	}

}
