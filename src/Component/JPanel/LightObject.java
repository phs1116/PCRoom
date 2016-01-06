package Component.JPanel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class LightObject extends JPanel implements Runnable {

	Image light;
	int typeNum = 0;
	int px = 77, py = 30; // 그려나갈 x좌표와 y좌표.

	public LightObject(){
		light = Toolkit.getDefaultToolkit().createImage("img/starDdong.png");
	}
	public LightObject(String path) {
		light = Toolkit.getDefaultToolkit().createImage(path);
		// 이미지 셋팅
	}

	// 광원 그리기
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(light, px, py, this);
	}

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(25);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			switch (typeNum) {
			case 0:
				py += 2; repaint();
				if (py > 791) typeNum = 1;
				break;
			case 1:
				px += 2; repaint();
				if (px > 1507) 	typeNum = 2;
				break;
			case 2:
				py -= 2; repaint();
				if (py < 53) typeNum = 3;
				break;
			case 3:
				px -= 2; repaint();
				if (px < 77) typeNum = 0;
				break;

			}
		}
	}

}
