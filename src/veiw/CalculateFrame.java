package veiw;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Component.JPanel.DragPanel;
import DAO.DAO_User;
import DAO.H2DB_init;

public class CalculateFrame extends JFrame {

	DragPanel dragPanel;

	JTable table;
	Vector<Vector<String>> rowData;
	DAO_User dao_User = new DAO_User();
	JPanel calc = new JPanel();
	JButton calcbt = new JButton("정산");

	public static void main(String[] args) {
		new H2DB_init();
		CalculateFrame ad = new CalculateFrame();

	}

	public void setDragPanel(DragPanel dragPanel) {
		this.dragPanel = dragPanel;
	}

	public CalculateFrame() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// 프레임의 Bounds 설정
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension(600, 400);
		Point setLoc = new Point((screen.width - frame.width) / 2, (screen.height - frame.height) / 2);
		setLocation(setLoc);
		setSize(frame);
		// setResizable(false);

		// 테이블 설정
		JScrollPane jsp = setTable();
		this.add(jsp, BorderLayout.CENTER);

		calc.setLayout(new BorderLayout());
		calc.add(calcbt, BorderLayout.CENTER);
		this.add(calc, BorderLayout.SOUTH);

		setVisible(true);
	}

	JScrollPane setTable() {
		rowData = new Vector<>();
		Vector<String> colData = new Vector<>();
		colData.add("좌석번호");
		colData.add("아이디");
		colData.add("금액");
		table = new JTable(rowData, colData);
		JScrollPane jScrollPane = new JScrollPane(table);

		return jScrollPane;

	}

	void AddTable(int seatNum) {
		rowData.add(dao_User.UserList(seatNum));
	}

}
