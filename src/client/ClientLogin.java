package client;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.SecondaryLoop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JWindow;

import DAO.DAO_Login;

public class ClientLogin extends JFrame {
	ClientLoginPan loginPan = new ClientLoginPan();
	ClientBackground background = new ClientBackground();
	DAO_Login dao_Login = null;
	ClientMain main = null;
	

	public void setMain(ClientMain main) {
		this.main = main;
	}

	public void setDao_Login(DAO_Login dao_Login) {
		this.dao_Login = dao_Login;
	}

	public static void main(String[] args) {
		new ClientLogin();
	}

	public ClientLogin() {

		
		// 로그인 프레임 설정
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension whindowsize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension framesize = new Dimension(200, 170);
		this.setResizable(false);
		setSize(framesize);
		setLocation(whindowsize.width - framesize.width, 0);

		// loginPan.setBounds(0,0,200,100);
		loginPan.setOuter(this);
		loginPan.setOpaque(false);

		add(loginPan);
		setVisible(true);
	}
	
	public void UsingMessage(){
		JOptionPane.showMessageDialog(null, "사용중인 좌석이거나 ID이어서 계속 할 수 없습니다.");
	}

	class ClientLoginPan extends JPanel implements ActionListener {
		JFrame Outer;
		
		JLabel id = new JLabel("아이디");
		JLabel pass = new JLabel("비밀번호");
		JLabel seatNum = new JLabel("좌석번호");
		JTextField idTextField = new JTextField(12);
		JPasswordField passField = new JPasswordField(12);
		JTextField seatNumField = new JTextField(2);

		JButton login = new JButton("로그인");
		JButton join = new JButton("회원가입");

		public void setOuter(JFrame frame){
			Outer = frame;
		}
		public ClientLoginPan() {
			// setSize(200,100);

			setLayout(null);

			id.setBounds(25, 10, 60, 40);
			add(id);

			pass.setBounds(20, 40, 60, 40);
			add(pass);

			seatNum.setBounds(20, 70, 60, 40);
			add(seatNum);

			idTextField.setBounds(80, 17, 100, 25);
			idTextField.addActionListener(this);
			add(idTextField);

			passField.setBounds(80, 47, 100, 25);
			passField.addActionListener(this);
			add(passField);

			seatNumField.setBounds(80, 77, 100, 25);
			seatNumField.addActionListener(this);
			add(seatNumField);

			login.setBounds(7, 110, 80, 25);
			login.addActionListener(this);
			add(login);

			join.setBounds(90, 110, 90, 25);
			join.addActionListener(this);
			add(join);

		}
		

		@Override
		public void actionPerformed(ActionEvent e) {
			String id, pass;
			int sNum;

			// 액선이벤트 발생 소스가 join이 아니면 로그인 실행.
			if (!e.getSource().equals(join)) {

				id = idTextField.getText();
				pass = new String(passField.getPassword());
				
				try {
					sNum = Integer.parseInt(seatNumField.getText());
				} catch (NumberFormatException ne) {
					JOptionPane.showMessageDialog(null, "좌석번호를 올바르게 입력해주세요.");
					seatNumField.setText("");
					return;
				}

				if (id.equals("") || pass.equals("")) {
					JOptionPane.showMessageDialog(null, "아이디나 비밀번호를 입력해주세요.");
					return;
				}
				if (dao_Login.loginService(id, pass)) {
					//JOptionPane.showMessageDialog(null, "로그인 성공");
					//로그인 시도.
					main.tryLogin(id, sNum-1);
				} else {
					JOptionPane.showMessageDialog(null, "로그인 실패");
				}

			}
			
			else if(e.getSource().equals(join)){
				main.ShowJoin(Outer);
			}

		}

	}
}
