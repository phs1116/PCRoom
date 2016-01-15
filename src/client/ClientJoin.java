package client;

import java.awt.Dimension;
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

import DAO.DAO_Join;
import DAO.H2DB_init;
import model.member;

public class ClientJoin extends JFrame {
	ClientJoinPan joinPan = new ClientJoinPan();
	DAO_Join dao_Join = null;
	ClientMain cMain = null;
	
	

	public void setcMain(ClientMain cMain) {
		this.cMain = cMain;
	}

	public void setDao_Join(DAO_Join dao_Join) {
		this.dao_Join = dao_Join;
	}

	public static void main(String[] args) {
		H2DB_init h2db_init = new H2DB_init();
		h2db_init.init();
		new ClientJoin();
	}

	public ClientJoin() {
		// 로그인 프레임 설정
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension whindowsize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension framesize = new Dimension(200, 200);
		this.setResizable(false);
		setSize(framesize);
		setLocation(whindowsize.width - framesize.width, 0);
		
		joinPan.setOuter(this);
		joinPan.setBounds(0, 0, 400, 400);
		joinPan.setOpaque(false);

		add(joinPan);
		setVisible(true);
	}

	class ClientJoinPan extends JPanel implements ActionListener {
		JFrame Outer;
		JLabel id = new JLabel("아이디");
		JLabel pass = new JLabel("비밀번호");
		JLabel phoneNum = new JLabel("전화번호");
		JLabel age = new JLabel("나이");
		JTextField idTextField = new JTextField(12);
		JPasswordField passField = new JPasswordField(12);
		JTextField phoneNumField = new JTextField(12);
		JTextField ageField = new JTextField(2);

		JButton joinOk = new JButton("확인");
		JButton joinCancel = new JButton("취소");

		public void setOuter(JFrame frame){
			Outer = frame;
		}
		
		public ClientJoinPan() {
			// setSize(200,100);

			setLayout(null);

			id.setBounds(25, 10, 60, 40);
			add(id);

			pass.setBounds(20, 40, 60, 40);
			add(pass);

			phoneNum.setBounds(20, 70, 60, 40);
			add(phoneNum);

			age.setBounds(35, 100, 60, 40);
			add(age);

			idTextField.setBounds(80, 17, 100, 25);
			idTextField.addActionListener(this);
			add(idTextField);

			passField.setBounds(80, 47, 100, 25);
			passField.addActionListener(this);
			add(passField);

			phoneNumField.setBounds(80, 77, 100, 25);
			phoneNumField.addActionListener(this);
			add(phoneNumField);

			ageField.setBounds(80, 107, 100, 25);
			ageField.addActionListener(this);
			add(ageField);

			joinOk.setBounds(7, 140, 85, 25);
			joinOk.addActionListener(this);
			add(joinOk);

			joinCancel.setBounds(100, 140, 85, 25);
			joinCancel.addActionListener(this);
			add(joinCancel);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			member jMember = null;

			// 액선이벤트 발생 소스가 join이 아니면 로그인 실행.
			if (!e.getSource().equals(joinCancel)) {

				// 멤버 객체 생성
				try {
					jMember = new member(idTextField.getText(), new String(passField.getPassword()),
							phoneNumField.getText(), Integer.parseInt(ageField.getText()));

				} catch (NumberFormatException ne) {
					JOptionPane.showMessageDialog(null, "나이를 올바르게 입력해주세요.");
					ageField.setText("");
					return;
				}

				if (jMember.getId().equals("") || jMember.getPassword().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디나 비밀번호를 입력해주세요.");
					return;
				}

				if (dao_Join.joinService(jMember)) {
					JOptionPane.showMessageDialog(null, "회원가입 성공");
					cMain.ShowLogin(Outer);
				} else {
					JOptionPane.showMessageDialog(null, "회원가입 실패");
				}
			}
			
			else if(e.getSource().equals(joinCancel)){
				cMain.ShowLogin(Outer);
			}

		}

	}
}
