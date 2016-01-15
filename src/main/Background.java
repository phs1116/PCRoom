package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import DAO.DAO_User;
import DAO.H2DB_init;
import model.ClientUserState;

public class Background {
	H2DB_init h2db_init = new H2DB_init();
	DAO_User dao_User = new DAO_User();
	// Map<Integer, String> Users = new HashMap<>();
	Map<String, Receiver> receivers = new HashMap<>();
	ServerSocket serverSocket;
	Socket socket = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	Main main;

	int seatNum;
	String id;

	private static Background background = new Background();
	
	public static Background getInstance(){
		return background;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

	public Receiver getReceiver(String seatNum) {
		return receivers.get(seatNum);
	}

	synchronized public void connect() {
		// h2db_init.init();

		try {
			Collections.synchronizedMap(receivers);
			// 소켓 생성
			serverSocket = new ServerSocket(7777);
			while (true) {
				System.out.println("연결 대기중");
				socket = serverSocket.accept();
				System.out.println("연결 성공 : " + getTime() + " " + socket.getInetAddress() + "접속");
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

				// seatGUI.userSeat.setOn(); // 자리 on상태로 변경

				String type = dis.readUTF();
				// main.setOnSeat(seatNum, id);
				StringTokenizer st = new StringTokenizer(type, ",");
				// 받은 문자열을 토큰으로 나눔
				if (st.nextToken().equals("login")) {
					String id = st.nextToken();
					String seatNum = st.nextToken();
					System.out.println("서버 [" + seatNum + "," + id + "]");
					boolean flag = dao_User.UsingService(id, seatNum);
					if (flag == true) { // 해당 컴퓨터나 아이디가 사용중이 아니면
						Receiver user = new Receiver(socket, seatNum);
						main.setOnSeat(seatNum, id);// 해당 아이디로 좌석을 실행시킨다.
						ClientUserState newUser = new ClientUserState(id, seatNum);
						// 유저 상태를 저장하는 클래스
						dos.writeUTF("not using"); // 사용 가능한 상태라는 신호를 보내줌.

						receivers.put(seatNum, user); // 통신을 위한 리시버를 해시맵에 저장.
						user.start();
					} else {
						dos.writeUTF("using"); // 컴퓨터나 아이디가 사용중이면 사용중이라는 통신을 보냄.
					}
				} else if (st.nextToken().equals("chat")) {

				}
				System.out.println(type);

			}

		} catch (IOException e) {
			// seatGUI.userSeat.setOff();// 접속 종료되면 off상태로.
		}

	}

	public String getTime() { // 현재 시간 출력
		SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm:ss]");
		return sdf.format(new Date());
	}

	public void sendMessage(String msg, String seatNum) {

		try {
			receivers.get(seatNum).output.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class Receiver extends Thread {
		Socket socket;
		DataInputStream input;
		DataOutputStream output;
		String seatNum;
		boolean login = false;

		// 전달받은 소켓으로 인풋,아웃풋 스트림을 생성
		public Receiver(Socket sock, String sNum) {
			this.socket = sock;
			;
			seatNum = sNum;
			try {
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
				// objectOut = new ObjectOutputStream(output);
			} catch (IOException e) {
			}

		}

		@Override
		public void run() {

			try {
				// 통신은 계속 유지.
				String msg = input.readUTF();
				login = true;

				while (login) {
					msg = input.readUTF();
					sendMessage(msg, seatNum);
				}

			} catch (IOException e) {
			}

		}

		public void offComputer() {
			try {
				output.writeUTF("stopCom");
				login = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
