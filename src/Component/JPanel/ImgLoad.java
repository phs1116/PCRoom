package Component.JPanel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class ImgLoad  extends JPanel {
	private Image image;
	public ImgLoad()
	{
		image = Toolkit.getDefaultToolkit().getImage("img/mainHud_back.png");
	}
	public ImgLoad(String path) {
		image = Toolkit.getDefaultToolkit().getImage(path);
		// Toolkit 클래스를 이용하여 운영체제 내부 경로로 이미지를 가져옴
	}

	@Override
	public void paint(Graphics g) {
		// super.paint(g);
		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void update(Graphics g) {
		// update 메서드는 repaint 요청이 있을경우 실행후에 paint 메서드를 실행한다.
		// 즉 연결이 완료되거나 연결이 끊겼을때 이미지를 바꿔주는 역할을 하면 된다.
		// TODO Auto-generated method stub
		super.update(g);
	}

}