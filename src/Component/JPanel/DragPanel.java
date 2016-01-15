package Component.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import veiw.CalculateFrame;

public class DragPanel extends JPanel {
	SeatBoard seatBoard;
	CalculateFrame calculateFrame;

	// 그림 그리기를 위한 변수
	Point fPoint = new Point();
	Point curPoint = new Point();
	boolean isDrag = false;
	HashSet<SeatPanel> selectPanels = new HashSet<>();

	public void setSeatBoard(SeatBoard seatBoard) {
		this.seatBoard = seatBoard;
	}

	public DragPanel() {
		this.setBounds(0, 0, 1600, 900);
		this.setOpaque(false);
		setLayout(null);
		addMouseListener(new DragEvent());
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				if (isDrag) {
					System.out.println("드래그");
					curPoint.setLocation(e.getX(), e.getY());
					repaint();
					// repaint();
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.setColor(new Color(36, 205, 198));
		if (isDrag) {
			Point recPoint = new Point();
			recPoint.x = curPoint.x < fPoint.x ? curPoint.x : fPoint.x;
			recPoint.y = curPoint.y < fPoint.y ? curPoint.y : fPoint.y;
			int width = Math.abs(curPoint.x - fPoint.x);
			int height = Math.abs(curPoint.y - fPoint.y);
			g.drawRect(recPoint.x, recPoint.y, width, height);

			System.out.println(recPoint);
		}

	}

	public void searchCheck(Point fPoint, Point cPoint) {
		allCheckOff();
		Point recPoint = new Point();
		recPoint.x = cPoint.x < fPoint.x ? cPoint.x : fPoint.x;
		recPoint.y = cPoint.y < fPoint.y ? cPoint.y : fPoint.y;

		Point lastPoint = new Point();
		lastPoint.x = recPoint.x == cPoint.x ? fPoint.x : cPoint.x;
		lastPoint.y = recPoint.y == cPoint.y ? fPoint.y : cPoint.y;

		int width = Math.abs(cPoint.x - fPoint.x);
		int height = Math.abs(cPoint.y - fPoint.y);

		// 화면에 있는 모든 패널 객체이 드래그 화면 안에 있는지 검사.
		for (int i = 0; i < seatBoard.getsPanel().length; i++) {

			// 패널 오브젝트의 꼭지점들의 좌표 (cx,cy) , (cx,py), (px,cy), (px,py)
			int px = seatBoard.getsPanel()[i].getLocation().x + seatBoard.getsPanel()[i].getSize().width - 10;
			int py = seatBoard.getsPanel()[i].getLocation().y + seatBoard.getsPanel()[i].getSize().height - 10;
			int cx = seatBoard.getsPanel()[i].getLocation().x + 10;
			int cy = seatBoard.getsPanel()[i].getLocation().y + 10;

			// 드래그해서 생성된 사각형 내에 객체의 꼭지점 4개중 하나라도 들어가있는지를 검사
			if (((px > recPoint.x && py > recPoint.y) && (px < lastPoint.x && py < lastPoint.y))
					|| ((cx > recPoint.x && cy > recPoint.y) && (cx < lastPoint.x && cy < lastPoint.y))
					|| ((px > recPoint.x && cy < recPoint.y + height) && (px < recPoint.x + width && cy > recPoint.y))
					|| ((cx < recPoint.x + width && py > recPoint.y)
							&& (cx > recPoint.x && py < recPoint.y + height))) {
				seatBoard.getsPanel()[i].check(true);
				selectPanels.add(seatBoard.getsPanel()[i]); // 체크된 패널들을 hashset에
															// 넣어줌
			}
		}

	}

	public void allCheckOff() {
		// HashSet에 들어간 모든 변수들의 체크를 헤제
		Iterator<SeatPanel> it = selectPanels.iterator();
		while (it.hasNext()) {
			it.next().check(false);
			it.remove();
		}
	}

	public void addSelect(SeatPanel select) {
		selectPanels.add(select);
	}

	public void removeSelect(SeatPanel select) {
		selectPanels.remove(select);
	}

	// 이밴트를 위한 클래스
	class DragEvent extends MouseAdapter implements ActionListener {
		JPopupMenu pMenu = new JPopupMenu();

		public DragEvent() {
			super();
			initPopupMenu();
		}

		public void mouseClicked(java.awt.event.MouseEvent e) {
			super.mouseClicked(e);
			if (e.getButton() == 3) {
				pMenu.show((DragPanel) e.getSource(), e.getX(), e.getY());
			} else if (e.getButton() == 1) {
				allCheckOff();
			}
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getButton() == 1) {
				isDrag = true;
				System.out.println("클릭");

				// 좌표들 초기화.
				fPoint.setLocation(e.getX(), e.getY());
				curPoint.setLocation(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getButton() == 1) {
				isDrag = false;
				curPoint.setLocation(e.getX(), e.getY());
				searchCheck(fPoint, curPoint);
			}

		}

		public void initPopupMenu() {
			JMenuItem selrun = new JMenuItem("선택 실행");
			selrun.addActionListener(this);
			JMenuItem seloff = new JMenuItem("선택 종료");
			seloff.addActionListener(this);
			JMenuItem selcal = new JMenuItem("선택 정산");
			selcal.addActionListener(this);
			JMenuItem allrun = new JMenuItem("전체 실행");
			allrun.addActionListener(this);
			JMenuItem alloff = new JMenuItem("전체 종료");
			alloff.addActionListener(this);
			pMenu.add(selrun);
			pMenu.add(seloff);
			pMenu.add(selcal);
			pMenu.add(allrun);
			pMenu.add(alloff);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Iterator<SeatPanel> it;
			SeatPanel[] seatPanels = seatBoard.getsPanel();

			if (e.getActionCommand().equals("선택 실행")) {
				it = selectPanels.iterator();
				while (it.hasNext()) {
					it.next().setOnOff(true, "관리자");
				}
			} else if (e.getActionCommand().equals("선택 종료")) {
				it = selectPanels.iterator();
				while (it.hasNext()) {
					it.next().setOnOff(false, "관리자");
				}
			}

			else if (e.getActionCommand().equals("전체 실행")) {
				for (SeatPanel seatPanel : seatPanels) {
					seatPanel.setOnOff(true, "관리자");
				}

			}

			else if (e.getActionCommand().equals("전체 종료")) {
				for (SeatPanel seatPanel : seatPanels) {
					seatPanel.setOnOff(false, "관리자");
				}

			}
		}

	}
}