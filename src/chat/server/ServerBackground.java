package chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ServerBackground {
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ServerGUI sgui;
	private SeatGUI seatGUI;
	private Map<String, DataOutputStream> ClientInformation = new HashMap<>();

	// GUI 연동을 위한 세터.
	public void setGUI(ServerGUI sgui) {
		this.sgui = sgui;
	}
	
	public void setSeatGUI(SeatGUI seatGUI) {
		this.seatGUI = seatGUI;
	}

	public static void main(String[] args) {

		ServerBackground sbg = new ServerBackground();
		
		sbg.ServerSet();

	}

	public void ServerSet() { // 서버 연결 설정.

		try {
			Collections.synchronizedMap(ClientInformation); // 멀티 쓰레드의 경쟁상태를 대비해
															// 동기화

			serverSocket = new ServerSocket(7777);
			while (true) {
				System.out.println("클라이언트 연결 대기중...");

				socket = serverSocket.accept(); // 클라이언트 연결 대기. 연결될때까지 블락.
				
				//seatGUI.userSeat.setOn();
				System.out.println("연결 성공 : " + getTime() + " " + socket.getInetAddress() + "접속");
				Receiver receiver = new Receiver(socket); // 리시버 객체 생성
				receiver.start(); // 리시버 쓰레드 실행

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getTime() { // 현재 시간 출력
		SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm:ss]");
		return sdf.format(new Date());
	}

	public void addClient(String nickname, DataOutputStream dos) {
		ClientInformation.put(nickname, dos);
	}

	public void removeClient(String nickname) {
		ClientInformation.remove(nickname);
	}

	public void sendMessage(String msg) {
		Iterator<Entry<String, DataOutputStream>> it = ClientInformation.entrySet().iterator();
		while (it.hasNext()) {
			try {
				it.next().getValue().writeUTF(msg);
			} catch (IOException e) {
			}

		}
	}

	public void showlist() {
		Iterator<String> it = ClientInformation.keySet().iterator();
		String key = "";
		while (it.hasNext()) {
			key = it.next();
			sgui.receiveMsg(key + "\n");

		}
	}

	class Receiver extends Thread {
		private Socket socket;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private String nickname;

		public Receiver(Socket socket) {
			this.socket = socket;
			// 입출력 스트림 생성
			try {
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				// 위의 socket.getOutputStream()은 다중 채팅시에 해시맵등에 저장하여 사용해야된다.

				nickname = dis.readUTF();
				addClient(nickname, dos); // 닉네임과 출력 스트림을 해쉬맵에 저장.

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			boolean flag = false;
			while (true) {
				try {
					String msg = dis.readUTF();
					sgui.receiveMsg(msg);
					sendMessage(msg);
				} catch (IOException e) {
					removeClient(nickname);
					if (!flag) {
						sendMessage(nickname + "님이 퇴장하셨습니다.\n");
						sgui.receiveMsg(nickname + "님이 퇴장하셨습니다.\n");
						//seatGUI.userSeat.setOff();
						flag = true;
					}
				}

			}

		}

	}

}
