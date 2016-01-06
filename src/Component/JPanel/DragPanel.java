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
	HashSet<SeatPanel> seatPanels = new HashSet<>();

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
		}

	}

	public void searchCheck(Point fPoint, Point cPoint) {

		allCheckOff();
		// 아무대도속하지 않았을 경우를 대비해 -1로 초기화
		int ix = -1, jx = -1;
		int iy = -1, jy = -1;

		int pX, cX, pY, cY;

		// X좌표 양끝 설정
		pX = fPoint.x < curPoint.x ? fPoint.x : curPoint.x;
		cX = pX == fPoint.x ? curPoint.x : fPoint.x;
		pX = pX < 0 ? 1 : pX;
		cX = cX > 1350 ? 1349 : cX;

		
		// Y좌표 양 끝 설정
		pY = fPoint.y < curPoint.y ? fPoint.y : curPoint.y;
		cY = pY == fPoint.y ? curPoint.y : fPoint.y;
		cY = cY > 725 ? 724 : cY;
		pY = pY < 0 ? 1 : pY;

		// x축에 속한 배열들 계산
		if (pX % 135 < 80) {
			ix = pX / 135;
		} else if ((pX % 135 > 99) && (pX / 135 != cX / 135)) {
			ix = (pX / 135) + 1;
		}
		jx = cX / 135;

		// y축에 속한 배열들 계산
		if (pY % 145 < 80) {
			iy = pY / 145;
		} else if ((pY % 145 > 99) && (pY / 145 != cY / 145)) {
			iy = (pY / 145) + 1;
		}
		jy = cY / 145;

		// 드래그 사각형 내부에 있는 객체들을 모두 checkOn으로 변경
		if (ix != -1 && iy != -1) {
			for (int i = ix; i <= jx; i++) {
				for (int j = iy; j <= jy; j++) {
					seatBoard.sPanel[j * 10 + i].check(true);
					seatPanels.add(seatBoard.sPanel[j * 10 + i]);
					//체크된 패널들을 HashSet에 넣어줌.
				}
			}

			System.out.println(seatPanels);
		}
		System.out.println("ix,jx = (" + ix + "," + jx + ")");
		System.out.println("iy,jy = (" + iy + "," + jy + ")");
	}

	public void allCheckOff() {
		//HashSet에 들어간 모든 변수들의 체크를 헤제
		Iterator<SeatPanel> it = seatPanels.iterator();
		while (it.hasNext()) {
			it.next().check(false);
			it.remove();
		}
	}
}