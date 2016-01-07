package Component.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JPanel;

public class DragPanel extends JPanel {
	SeatBoard seatBoard;
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
		addMouseListener(new MouseAdapter() {
			@Override
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
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if (e.getButton() == 1) {
					allCheckOff();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				isDrag = false;
				curPoint.setLocation(e.getX(), e.getY());
				searchCheck(fPoint, curPoint);

			}
		});
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
		//화면에 있는 모든 패널 객체이 드래그 화면 안에 있는지 검사.
		for (int i = 0; i < seatBoard.sPanel.length; i++) {
			
			//패널 오브젝트의 오른쪽 아래 꼭지점 좌표
			int px = seatBoard.sPanel[i].getLocation().x + seatBoard.sPanel[i].getSize().width - 10;
			int py = seatBoard.sPanel[i].getLocation().y + seatBoard.sPanel[i].getSize().height - 10;

			//패널 오브젝트의 왼쪽 위 꼭지점 좌표
			int cx = seatBoard.sPanel[i].getLocation().x + 10;
			int cy = seatBoard.sPanel[i].getLocation().y + 10;

			//드래그해서 생성된 사각형 내에 꼭지점 두개중 하나라도 들어가있는지를 검사
			if (((px > recPoint.x && py > recPoint.y) && (px < lastPoint.x && py < lastPoint.y))
					|| ((cx > recPoint.x && cy > recPoint.y) && (cx < lastPoint.x && cy < lastPoint.y))) {
				seatBoard.sPanel[i].check(true);
				selectPanels.add(seatBoard.sPanel[i]); // 체크된 패널들을 hashmap에 넣어줌
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
}