package client;


import DAO.DAO_Join;
import DAO.DAO_Login;
import DAO.H2DB_init;

public class ClientMain {
	ClientLogin loginFrame;
	ClientJoin joinFrame;
	ClientBackground cBackground;
	DAO_Join dao_Join;
	DAO_Login dao_Login;

	public static void main(String[] args) {
		ClientMain main = new ClientMain();

		new H2DB_init().init();

		main.dao_Join = new DAO_Join();
		main.dao_Login = new DAO_Login();

		// 백그라운드 생성
		main.cBackground = new ClientBackground();
		main.cBackground.setcMain(main);
		 // 네트워크 연결

		main.loginFrame = new ClientLogin();

		// 로그인창과 메인 연결, dao_login 장착
		main.loginFrame.setMain(main);
		main.loginFrame.setDao_Login(main.dao_Login);
		main.cBackground.connect();

	}

	public void ShowJoin() {
		loginFrame.dispose();
		joinFrame = new ClientJoin();
		joinFrame.setcMain(this);
		joinFrame.setDao_Join(dao_Join);
	}

	public void ShowLogin() {
		joinFrame.dispose();
		loginFrame.setVisible(true);
	}

	public void tryLogin(String id, int seatNum) {
		cBackground.sendLogin(id, seatNum);

	}
}
