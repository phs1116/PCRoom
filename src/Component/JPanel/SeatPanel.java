package Component.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import main.Background;
import main.Main;
import model.ClientUserState;
import veiw.ServerChat;

public class SeatPanel extends JPanel {
	int seatNum;
	ClientUserState user;
	BufferedImage image;
	Image checkimg;
	Image drawCh = null;
	JPanel panText = new JPanel();
	JLabel[] labels = new JLabel[4];

	SeatBoard seatBoard;
	ServerChat serverChat;
	boolean check = false;

	public void setSeatBoard(SeatBoard seatBoard) {
		this.seatBoard = seatBoard;
	}

	public SeatPanel(int seatNum) {
		// 자리 번호 초기화
		this.seatNum = seatNum;
		// setFocusable(true);
		// 패널에 들어갈 텍스트
		panText.setLayout(null);
		panText.setBounds(0, 0, 99, 99);
		panText.setOpaque(false);

		// 라벨 초기화
		initLabel();

		// 패널에 들어갈 배경 이미지
		InnerPanel panImg = new InnerPanel();
		panImg.setLocation(0, 0);
		panImg.setSize(99, 99);
		panImg.setOpaque(false);
		setOnOff(false, ""); // 배경 이미지 설정

		// 체크 패널 등록
		CheckPanel panCheck = new CheckPanel();
		panCheck.setBounds(0, 0, 99, 99);
		panCheck.setOpaque(false);

		// JLayeredPane 생성 및 설정
		JLayeredPane layered = new JLayeredPane();
		layered.setBounds(0, 0, 1600, 900);
		layered.setLayout(null);
		layered.setOpaque(false);
		layered.setVisible(true);
		layered.add(panImg, 2);
		layered.add(panCheck, 0);
		layered.add(panText, 1);

		add(layered);

		// 마우스 이벤트 등록
		addMouseListener(new PanEvent());
		setLayout(null);
		setOpaque(false);

	}

	public void initLabel() {
		int labelPos = 16;
		for (int i = 0; i < labels.length; i++) {
			if (i == 0)
				labels[i] = new JLabel(seatNum + 1 + ". 빈자리");
			else
				labels[i] = new JLabel("");

			labels[i].setBounds(20, labelPos, 80, 15);
			labelPos += 16;
			labels[i].setForeground(new Color(36, 205, 198));
			panText.add(labels[i]);
		}
	}

	public void setOnOff(boolean set, String id) {

		try {
			if (set == true) {
				if (!id.equals("관리자")) {
					user = new ClientUserState(id, seatNum + "");
					labels[1].setForeground(new Color(36, 205, 198));
					user.setTimelb(labels[2]);
					labels[2].setForeground(new Color(36, 205, 198));
					user.setCreditlb(labels[3]);
					labels[2].setForeground(new Color(36, 205, 198));
				}
				image = ImageIO.read(new File("img/gameOn.png"));
				new Thread(user).start();
				labels[0].setText(seatNum + 1 + ". 사용중");
				labels[0].setForeground(Color.red);
				labels[1].setText(id);

			} else if (set == false) {
				if (user != null) {
					seatBoard.getMain().setOffSeat((seatNum + 1) + "");
					user.setRun(false);
					user = null;

				}
				image = ImageIO.read(new File("img/gameOff.png"));

				labels[0].setText(seatNum + 1 + ". 빈자리");
				labels[0].setForeground(new Color(36, 205, 198));
				labels[1].setText("");
				labels[2].setText("");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		repaint();
	}

	public void check(boolean checkon) {
		if (checkon == true) {
			try {
				checkimg = ImageIO.read(new File("img/check.png"));
			} catch (IOException e) {
			}
			check = true;
		} else {
			checkimg = null;
			check = false;
		}
		repaint();
	}

	class InnerPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(image, 0, 0, this);
		}
	}

	class CheckPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(checkimg, 0, 0, this);
		}
	}

	class PanEvent extends MouseAdapter implements ActionListener {
		JPopupMenu pMenu = new JPopupMenu();

		public PanEvent() {
			super();
			initPopupMenu();
		}

		public void mouseClicked(java.awt.event.MouseEvent e) {
			int LEFT_CTRL_MASK = InputEvent.BUTTON1_MASK + InputEvent.CTRL_MASK;

			super.mouseClicked(e);
			if (e.getButton() == 3)
				pMenu.show((SeatPanel) e.getSource(), e.getX(), e.getY());
			else if ((e.getModifiers() & LEFT_CTRL_MASK) == LEFT_CTRL_MASK) {
				System.out.println("컨트롤+마우스");
				if (check == false) {
					seatBoard.addSelect((SeatPanel) e.getSource());
					check(true);
				} else {
					seatBoard.removeSelect((SeatPanel) e.getSource());
					check(false);
				}

			}
		}

		public void initPopupMenu() {
			JMenuItem run = new JMenuItem("실행");
			run.addActionListener(this);
			JMenuItem off = new JMenuItem("종료");
			off.addActionListener(this);
			JMenuItem chat = new JMenuItem("채팅");
			chat.addActionListener(this);
			pMenu.add(run);
			pMenu.add(off);
			pMenu.add(new JMenuItem("정산"));
			pMenu.add(chat);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("실행"))
				setOnOff(true, "관리자");
			else if (e.getActionCommand().equals("종료"))
				setOnOff(false, "관리자");
			else if(e.getActionCommand().equals("채팅")){
				serverChat = new ServerChat(seatNum);
			}
		}

	}
}
