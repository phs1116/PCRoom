package veiw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.Background;
import main.Main;

public class ServerChat extends JFrame implements ActionListener {
	Background server = Background.getInstance();
	String seatNum = null;
	JTextArea chatArea = null;
	JTextField chatField = null;
	
	

	public static void main(String[] args) {
		//SeatGUI seatGUI = new SeatGUI();
		//ServerChat sg= new ServerChat();
		//sg.server.setSeatGUI(seatGUI);
	}

	public ServerChat(int seatNum) {
			
		this.seatNum = seatNum+"";
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);;
				e.getWindow().dispose();
			}
		});
		setBounds(200, 100, 300, 300);
		setTitle("Chat Server");

		chatArea = new JTextArea(40, 25);
		chatArea.setEditable(false);
		add(chatArea, "Center");

		chatField = new JTextField(25);
		chatField.addActionListener(this);
		add(chatField, "South");
		
		setVisible(true);
		//server.ServerSet();
	}
	
	
	
	public void receiveMsg(String msg){
		chatArea.append(msg);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = chatField.getText() + "\n";
		
		chatArea.append("★관리자>>> "+msg);
		server.sendMessage("★관리자>>> "+msg, this.seatNum);
	//	if(chatField.getText().equals("showlist")){
		//	server.showlist();
	//	}
		chatField.setText("");
	}

}
