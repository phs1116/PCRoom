package client;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.mysql.fabric.xmlrpc.Client;

import DAO.DAO_Join;
import DAO.DAO_Login;
import DAO.DAO_User;
import DAO.H2DB_init;
import model.ClientUserState;

public class ClientMain {
	ClientLogin loginFrame;
	ClientJoin joinFrame;
	ClientUsingFrame usingFrame;
	ClientBackground cBackground;
	DAO_Join dao_Join;
	DAO_Login dao_Login;
	DAO_User dao_User;

	public static void main(String[] args) {
		ClientMain main = new ClientMain();

		new H2DB_init().init();

		main.dao_Join = new DAO_Join();
		main.dao_Login = new DAO_Login();

		// 백그라운드 생성
		main.cBackground = ClientBackground.getInstance();
		main.cBackground.setcMain(main);
		// 네트워크 연결

		main.loginFrame = new ClientLogin();

		// 로그인창과 메인 연결, dao_login 장착
		main.loginFrame.setMain(main);
		main.loginFrame.setDao_Login(main.dao_Login);

	}

	public void ShowJoin(JFrame component) {
		component.dispose();
		joinFrame = new ClientJoin();
		joinFrame.setcMain(this);
		joinFrame.setDao_Join(dao_Join);
	}

	public void ShowLogin(JFrame component) {
		component.dispose();
		loginFrame.setVisible(true);
	}

	public void ShowUsing(String id, String seatNum) {
		loginFrame.dispose();
		usingFrame = new ClientUsingFrame(id, seatNum);
		usingFrame.setcMain(this);
	}

	public void tryLogin(String id, int seatNum) {
				
			cBackground.connect(id, seatNum);
			ShowUsing(id,seatNum+"");
	}
	
	public void showChat(){
		new ClientChat();
	}

}
