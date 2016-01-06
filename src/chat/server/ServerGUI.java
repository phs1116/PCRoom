package chat.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerGUI extends JFrame implements ActionListener {
	ServerBackground server = new ServerBackground();

	JTextArea chatArea = null;
	JTextField chatField = null;

	public static void main(String[] args) {
		SeatGUI seatGUI = new SeatGUI();
		ServerGUI sg= new ServerGUI();
		sg.server.setSeatGUI(seatGUI);
		sg.server.ServerSet();
		
	}

	public ServerGUI() {
		
		server.setGUI(this);
	
		
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);;
				e.getWindow().dispose();
				System.exit(0);
			}
		});
		setBounds(200, 100, 400, 600);
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
		server.sendMessage("★관리자>>> "+msg);
		if(chatField.getText().equals("showlist")){
			server.showlist();
		}
		chatField.setText("");
	}

}
