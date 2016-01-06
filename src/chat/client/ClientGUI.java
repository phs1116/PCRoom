package chat.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chat.server.ServerGUI;

public class ClientGUI extends JFrame implements ActionListener {
	private JTextArea chatArea = null;
	private JTextField chatField = null;
	private ClientBackground client = new ClientBackground();
	private String nick = null;
	
	public static void main(String[] args) {
		
		new ClientGUI();
	}

	public ClientGUI() {
		
		nick = JOptionPane.showInputDialog("아이디를 입력하세요");
		while(nick == null){}
		
		client.setGUI(this);
		client.setNickname(nick);
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
		setTitle("Chat Client");

		chatArea = new JTextArea(40, 25);
		chatArea.setEditable(false);
		add(chatArea, "Center");

		chatField = new JTextField(25);
		chatField.addActionListener(this);
		add(chatField, "South");

	
		
		setVisible(true);
		client.connect();
		
	}
	
	public void receiveMsg(String msg){
		chatArea.append(msg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = chatField.getText() + "\n";
		//chatArea.append(msg);
		client.sendMsg(nick + " : "+msg);
		chatField.setText("");
	}

	

}
