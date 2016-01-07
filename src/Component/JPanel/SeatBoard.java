package Component.JPanel;


import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class SeatBoard extends JPanel {
	private SeatPanel[] sPanel = new SeatPanel[50]; // 좌석들을 위한 패널
	private DragPanel dragPanel = new DragPanel();
	
	
	public SeatPanel[] getsPanel() {
		return sPanel;
	}

	public SeatBoard() {
		JLayeredPane board = new JLayeredPane();
		board.setLayout(null);
		board.setSize(1600, 900);
		board.setOpaque(false);
		setLayout(null);
		setFocusable(true);
		
		setBoard();
		
		//dragPanel 설정
		dragPanel.setSeatBoard(this);
		dragPanel.setLayout(null);
		dragPanel.setOpaque(false);
		
		
		add(board);
		board.add(dragPanel, 1);
		setVisible(true);
	}
	
	public void setBoard(){
		int psPosX = 0, psPosY = -140;
		for (int i = 0; i < sPanel.length; i++) {
			if (i % 10 == 0) {
				psPosX = 0;
				psPosY += 140;
			}
			sPanel[i] = new SeatPanel(i);
			sPanel[i].setBounds(psPosX, psPosY, 99, 99);
			psPosX += 135;
			add(sPanel[i]);
		}
	}

	public void setOn(int seatNum, String id){
		System.out.println("좌석번호: "+seatNum);
		sPanel[seatNum].setOnOff(true,id);
	}
}
