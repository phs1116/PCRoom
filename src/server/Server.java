package server;

import java.awt.geom.FlatteningPathIterator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;

import org.h2.engine.User;

import com.mysql.fabric.xmlrpc.Client;

import DAO.DAO_User;
import DAO.H2DB_init;
import model.ClientUserState;

public class Server {
	ServerSocket serverSocket;
	Socket socket;
	Receiver admin = null;
	HashSet<ClientUserState> users = new HashSet<>();
	Map<String, Receiver> receivers = new HashMap<>();
	H2DB_init h2db_init = new H2DB_init();
	DAO_User dao_User = new DAO_User();

	public static void main(String[] args) {
		new Server().connect();
	}

	public void connect() {
		h2db_init.init();
		try {
			Collections.synchronizedMap(receivers);
			Collections.synchronizedSet(users);
			serverSocket = new ServerSocket(7777);
			while (true) {
				System.out.println("연결 대기중");
				socket = serverSocket.accept();
				System.out.println("연결 성공 : " + getTime() + " " + socket.getInetAddress() + "접속");
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			//	ObjectOutputStream oos = new ObjectOutputStream(dos);
				String type = dis.readUTF();

				// 관리자면 관리자 전용 쓰레드 실행
				if (type.equals("Admin")) {
					System.out.println(type);
					admin = new Receiver(socket, "Admin", "0");
					admin.start();

				}
				// 사용자면 사용자 쓰레드 실행
				else {

					StringTokenizer st = new StringTokenizer(type, ",");
					// 받은 문자열을 토큰으로 나눔
					if (st.nextToken().equals("login")) {
						String id = st.nextToken();
						String seatNum = st.nextToken();
						System.out.println("서버 [" + seatNum + "," + id + "]");
						boolean flag = dao_User.UsingService(id, seatNum);
						if (flag == true) {
							Receiver user = new Receiver(socket, id, seatNum);

							// 해당 좌석의 아웃풋스트림과 좌석번호를 등록
							admin.output.writeUTF(seatNum);
							admin.output.writeUTF(id);
							
							ClientUserState newUser = new ClientUserState(id, seatNum);
							System.out.println("서버2 [" + seatNum + "," + id + "]");
							dos.writeUTF("not using");
							//oos.writeObject(newUser);
							//admin.objectOut.writeObject(newUser);
							//Users.put(seatNum, user);	
							
							receivers.put(seatNum, user);
							users.add(newUser);
							user.start();
						} else {
							dos.writeUTF("using");
						}
					} else if (st.nextToken().equals("chat")) {

					}
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

	void sendMassage(String msg, String seatNum) {
		DataOutputStream[] output = { receivers.get("0").output, receivers.get(seatNum).output };

		for (DataOutputStream dataOutputStream : output) {
			try {
				dataOutputStream.writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Receiver extends Thread {
		Socket socket;
		DataInputStream input;
		DataOutputStream output;
	//	ObjectOutputStream objectOut;
		String seatNum;
		String id;

		String msg;

		public Receiver(Socket sock, String id, String sNum) {
			this.socket = sock;
			this.id = id;
			seatNum = sNum;
			try {
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
			//	objectOut = new ObjectOutputStream(output);
			} catch (IOException e) {
			}

		}

		@Override
		public void run() {
			try {
				if (!seatNum.equals("0")) {
					//output.writeUTF(seatNum);
					//output.writeUTF(id);
				}
				while (input != null) {
					msg = seatNum + "번 : " + input.readUTF();

					sendMassage(msg, seatNum);
				}

			} catch (IOException e) {
			}
		}

	}
}
