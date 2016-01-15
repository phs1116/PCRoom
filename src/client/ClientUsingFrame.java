package client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DAO.H2DB_init;
import model.ClientUserState;

public class ClientUsingFrame extends JFrame {
	ClientUsingPan usingPan = null;
	ClientMain cMain = null;
	ClientUserState user = null;

	public void setcMain(ClientMain cMain) {
		this.cMain = cMain;
	}

	public void setUser(ClientUserState user) {
		this.user = user;
	}

	public static void main(String[] args) {
		H2DB_init h2db_init = new H2DB_init();
		h2db_init.init();
		new ClientUsingFrame("asdf", "12");
	}

	public ClientUsingFrame(String id, String seatNum) {
		this.user = new ClientUserState(id, seatNum);
		// 로그인 프레임 설정

		usingPan = new ClientUsingPan();
		new Thread(user).start();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension whindowsize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension framesize = new Dimension(200, 200);
		this.setResizable(false);
		setSize(framesize);
		setLocation(whindowsize.width - framesize.width, 0);

		usingPan.setBounds(0, 0, 400, 400);
		usingPan.setOpaque(false);

		add(usingPan);
		setVisible(true);
		
	}

	class ClientUsingPan extends JPanel implements ActionListener {
		JLabel idlb = new JLabel("아이디");
		JLabel id = new JLabel(user.getUserId());

		JLabel seatNumlb = new JLabel("좌석");
		JLabel seatNum = new JLabel(user.getSeatNum());

		JLabel timelb = new JLabel("시간");
		JLabel time = new JLabel("0분 0초");

		JLabel creditlb = new JLabel("요금");
		JLabel credit = new JLabel("0원");

		JButton chat = new JButton("채팅");
		JButton menu = new JButton("메뉴");

		public ClientUsingPan() {
			// setSize(200,100);

			setLayout(null);

			idlb.setBounds(20, 10, 60, 40);
			id.setBounds(100, 10, 60, 40);
			add(idlb);
			add(id);

			seatNumlb.setBounds(25, 40, 60, 40);
			seatNum.setBounds(100, 40, 60, 40);
			add(seatNumlb);
			add(seatNum);

			timelb.setBounds(25, 70, 60, 40);
			time.setBounds(100, 70, 60, 40);
			user.setTimelb(time);
			add(timelb);
			add(time);

			creditlb.setBounds(25, 100, 60, 40);
			credit.setBounds(100, 100, 60, 40);
			user.setCreditlb(credit);
			add(creditlb);
			add(credit);

			chat.setBounds(7, 140, 85, 25);
			chat.addActionListener(this);
			add(chat);

			menu.setBounds(100, 140, 85, 25);
			menu.addActionListener(this);
			add(menu);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// member jMember = null;

			if(e.getActionCommand().equals("채팅")){
				cMain.showChat();
				
			}
				
		}

	}
}
