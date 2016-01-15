package main;

import DAO.DAO_Login;
import DAO.H2DB_init;
import assets.Setting;
import veiw.LoginFrame;
import veiw.ManageFrame;

public class Main {
	private LoginFrame loginFrame;
	private ManageFrame manageFrame;
	private H2DB_init h2db_init;
	private DAO_Login daoLogin;

	private Background background;

	public Main() {
		// DB 초기화 및 테이블 생성

		h2db_init = new H2DB_init();
		h2db_init.init();

		background = Background.getInstance();
		background.setMain(this);

		loginFrame = new LoginFrame();

		// main과 LoginFrame을 연결
		try {
			loginFrame.setMain(this).setJComponents(LoginFrame.class, loginFrame, Setting.class, Setting.getInstance());
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// login에 DAO_Login을 넘김
		daoLogin = new DAO_Login();
		loginFrame.setDAO_Login(daoLogin);
		background.connect();

	}

	public static void main(String[] args) {
		Main main = new Main();
		/*
		 * //DB 초기화 및 테이블 생성
		 * 
		 * main.h2db_init = new H2DB_init(); main.h2db_init.init();
		 * 
		 * 
		 * main.background = new Background(); main.background.setMain(main);
		 * 
		 * main.loginFrame = new LoginFrame();
		 * 
		 * //main과 LoginFrame을 연결 try{
		 * main.loginFrame.setMain(main).setJComponents(LoginFrame.class,
		 * main.loginFrame, Setting.class, Setting.getInstance());; } catch
		 * (Exception e) { e.printStackTrace(); } //login에 DAO_Login을 넘김
		 * main.daoLogin = new DAO_Login();
		 * main.loginFrame.setDAO_Login(main.daoLogin);
		 * main.background.connect();
		 */
	}

	public void ShowMange() {
		loginFrame.dispose();// 로그인창을 닫고
		manageFrame = new ManageFrame(); // 메인프레임 객체 생성
		manageFrame.setMain(this);
		manageFrame.getSeatBoard().setMain(this);
		// mageFrame를 리플렉션으로 설정
		try {
			manageFrame.setJComponent(ManageFrame.class, manageFrame, Setting.class, Setting.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setOnSeat(String seatNum, String id) {
		int seat = Integer.parseInt(seatNum);
		manageFrame.getSeatBoard().setOn(seat, id);
	}
	
	public void setOffSeat(String seatNum) {
		background.getReceiver(seatNum).offComputer();
	}
}
