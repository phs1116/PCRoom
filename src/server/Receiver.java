package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

import org.omg.PortableInterceptor.DISCARDING;

public class Receiver extends Thread {
	Socket socket;
	DataInputStream input;
	DataOutputStream output;

	public Receiver(Socket sock) {
		this.socket = sock;
		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {

		}
	}
}

// 관리자 리시버
class AdminReceiver extends Receiver {

	public AdminReceiver(Socket sock) {
		super(sock);
	}

	public void addUser(String seatNum, String id) {
		// Users.put(seatNum, id);
		try {
			output.writeUTF(seatNum);
			output.writeUTF(id);
		} catch (IOException e) {
		}
	}

	public void removeUser(String seatNum) {
		// Users.remove(seatNum);
	}

}

// 유저 리시버
class UserReceiver extends Receiver {
	int seatNum;

	public UserReceiver(Socket sock, String sNum) {
		super(sock);
		seatNum = Integer.parseInt(sNum);
	}

	@Override
	public void run() {
		while (input != null) {
			try {
				
				sendMassage(input.readUTF());
			} catch (IOException e) {
			}

		}
		
	}
	
	void sendMassage(String msg){}
	

}
