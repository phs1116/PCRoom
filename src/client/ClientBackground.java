package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import org.h2.engine.User;

import DAO.DAO_User;

public class ClientBackground implements Runnable {
	private Socket socket = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private ObjectInputStream ois = null;
	ClientMain cMain = null;
	ClientChat chat = null;
	int seatNum;
	DAO_User dao_User = new DAO_User();

	String msg;
	String nickname;
	
	private static ClientBackground clientBackground = new ClientBackground();
	public static ClientBackground getInstance(){
		return clientBackground;
	}
	public static void main(String args[]) {
		ClientBackground cb = new ClientBackground();
		// cb.connect();
	}

	public void setcMain(ClientMain cMain) {
		this.cMain = cMain;
	}

	public void setChat(ClientChat chat) {
		this.chat = chat;
	}
	public void connect(String id, int seatNum) {
		this.seatNum = seatNum;
		try {

			socket = new Socket("127.0.0.1", 7777);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			// ois = new ObjectInputStream(dis);
			System.out.println("서버 연결 성공");
			dos.writeUTF("login," + id + "," + seatNum);
			System.out.println("쓰레드 실행함");
			new Thread(this).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMsg(String msg) {
		try {
			dos.writeUTF(seatNum+"번 고객님 : "+msg);

		} catch (IOException e) {
			e.printStackTrace();
			// sendMsg(nickname +"님이 퇴장하셨습니다.");
		}
	}

	public void sendLogin(String id, String seatNum) {
		try {
			dos.writeUTF("login," + id + "," + seatNum);
		} catch (IOException e) {
			e.printStackTrace();
			// sendMsg(nickname +"님이 퇴장하셨습니다.");
		}
	}

	public void setNickname(String nick) {
		this.nickname = nick;
	}

	@Override
	public void run() {
		try {
			msg = dis.readUTF();
			System.out.println("메세지 받음");
			System.out.println(msg);
			if (msg.equals("using")) {
				cMain.loginFrame.UsingMessage();
				return;
			} else {

			}
			while (dis != null) {

				msg = dis.readUTF();
				System.out.println(msg);
				if (msg.equals("stopCom")) {
					dao_User.UserStop(seatNum);
					cMain.usingFrame.user.setRun(false);
					cMain.ShowLogin(cMain.usingFrame);
					return;
				}else{
					chat.receiveMsg(msg);
				}
				System.out.println(msg);
				// cGui.receiveMsg(msg);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} // catch(ClassNotFoundException ce){ce.printStackTrace();}
	}

}
