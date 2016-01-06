package veiw;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.lang.reflect.Field;
import DAO.DAO_Login;

import main.Main;

public class LoginFrame extends JFrame implements ActionListener {
	BufferedImage image = null;
	JTextField loginField = new JTextField(15);;
	JPasswordField passwordField = new JPasswordField(15);
	JButton loginButton = new JButton(new ImageIcon("img/btLogin_hud.png"));
	BackgroundPanel panel = new BackgroundPanel();

	DAO_Login daoLogin;
	Main main; // 메인과의 연결.

	public LoginFrame setMain(Main main) {
		this.main = main;

		return this;
	}

	public void setDAO_Login(DAO_Login daoLogin) {
		this.daoLogin = daoLogin;
	}

	public void setJComponents(Class<?> clazz, Object instance, Class<?> targetClass, Object target)
			throws IllegalAccessException, NoSuchFieldException {
		Object tmpObject;
		Rectangle tmpRect;
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			tmpObject = field.get(instance);
			if (tmpObject instanceof JComponent) {
				tmpRect = (Rectangle) targetClass.getDeclaredField(field.getName()).get(target);
				((JComponent) tmpObject).setBounds(tmpRect);
				((JComponent) tmpObject).setOpaque(false);
				((JComponent) tmpObject).setLayout(null);

			}

			if (tmpObject instanceof JTextField) {
				((JTextField) tmpObject).setForeground(Color.green);
				((JTextField) tmpObject).setBorder(BorderFactory.createEmptyBorder());
			}
		}
	}

	public LoginFrame() {
		setTitle("로그인 테스트");
		setSize(1600, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		try {
			image = ImageIO.read(new File("img/login.png"));
		} catch (Exception e) {
			System.exit(0);
		}

		JLayeredPane layeredPane = this.getLayeredPane();
		// layeredPane.setBounds(0,0,1600,900);
		// layeredPane.setLayout(null);;

		// login 텍스트 필드
		loginField.addActionListener(this); // 로그인 텍스트 필드에 액션리스너 등록
		passwordField.addActionListener(this); // 패스워드필드에 액션리스너 등록

		// loginButton 버튼
		// 버튼 투명 http://laonatti.net/196 참고
		loginButton.setBorderPainted(false);
		loginButton.setContentAreaFilled(false);
		loginButton.addActionListener(this); // 버튼에 액션리스너 등록
		/// loginButton.setFocusPainted(false);
		layeredPane.setVisible(true);

		addToJLayerdPane(layeredPane, panel, loginField, passwordField, loginButton);
		// add(layeredPane);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String id = loginField.getText();
		char[] pass = passwordField.getPassword();
		String password = new String(pass);
		if (id.equals("") || password.equals("")) {
			JOptionPane.showMessageDialog(null, "ID,비밀번호를  모두 입력해주세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean isOk = daoLogin.loginService(id, password);

		// 로그인 완료
		if (isOk == true) {
			JOptionPane.showMessageDialog(null, "로그인 성공", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
			main.ShowMange();
		}
		// 로그인 실패
		else {
			JOptionPane.showMessageDialog(null, "ID 혹은 비밀번호가 틀렸습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	class BackgroundPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(image, 0, 0, null);
		}
	}

	public void addToJLayerdPane(JLayeredPane jp, Component... components) {
		int i = 0;
		for (Component component : components) {
			jp.add(component, new Integer(i));
			i++;
		}
	}

}
