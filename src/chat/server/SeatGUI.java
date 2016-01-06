package chat.server;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.h2.util.IntIntHashMap;

public class SeatGUI extends JFrame {
	Socket socket = null;
	// UserSeat userSeat = new UserSeat(1);

	public static void main(String[] args) {
		new SeatGUI();
	}

	public SeatGUI() {

		// sbg = new SeatBackground();
		// sbg.setSeatGUI(this); // gui와 백그라운드 연결

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				e.getWindow().setVisible(false);
				e.getWindow().dispose();
				System.exit(0);
			}
		});
		this.setBounds(0, 0, 99, 144);
		this.setTitle("사용자좌석 테스트");
		UserSeat userSeat = new UserSeat(0);
		userSeat.setBounds(0, 0, 99, 400);
		add(userSeat);
		setVisible(true);
		// sbg.connect();
	}

	class UserSeat extends JPanel {
		int seatNum;
		Image[] bgImage = new Image[2];
		Image drawbg = null;

		public UserSeat(int seatNum) {
			bgImage[0] = Toolkit.getDefaultToolkit().createImage("img/gameOff.png"); // 배경
			bgImage[1] = Toolkit.getDefaultToolkit().createImage("img/gameOn.png");

			this.seatNum = seatNum;
			setLayout(null);
			// 현재배경
			setOff();
			InnerPanel panImg = new InnerPanel();
			panImg.setLocation(0, 0);
			panImg.setSize(99, 99);
			panImg.setOpaque(false);

			int labelPos = 16;
			JLabel[] labels = new JLabel[4];
			for (int i = 0; i < labels.length; i++) {
				if (i == 0)
					labels[i] = new JLabel(seatNum + 1 + ". 빈자리");
				else
					labels[i] = new JLabel("");

				labels[i].setBounds(20, labelPos, 80, 15);
				labelPos += 16;
				labels[i].setForeground(new Color(36, 205, 198));
				add(labels[i]);
			}

			add(panImg);
			setOpaque(false);
		}

		class InnerPanel extends JPanel {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.drawImage(drawbg, 0, 0, this);
			}
		}

		public void setOn() {
			drawbg = bgImage[1];
			repaint();
		}

		public void setOff() {
			drawbg = bgImage[0];
			repaint();
		}

	}

}
