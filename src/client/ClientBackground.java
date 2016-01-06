package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientBackground {
	private Socket socket = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	ClientLogin cLogin = null;
	
	
	
	String msg;
	String nickname;

	public static void main(String args[]) {
		ClientBackground cb = new ClientBackground();
		cb.connect();
	}

	public void setcLogin(ClientLogin cLogin) {
		this.cLogin = cLogin;
	}

	public void connect() {
		try {

			socket = new Socket("127.0.0.1", 7777);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			System.out.println("서버 연결 성공");
			dos.writeUTF(nickname);
			sendMsg(nickname + "님이 입장하셨습니다.\n");
			while (dis != null) {
				msg = dis.readUTF();
				// cGui.receiveMsg(msg);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMsg(String msg) {
		try {
			dos.writeUTF(msg);

		} catch (IOException e) {
			e.printStackTrace();
			// sendMsg(nickname +"님이 퇴장하셨습니다.");
		}
	}

	public void setNickname(String nick) {
		this.nickname = nick;
	}

}
