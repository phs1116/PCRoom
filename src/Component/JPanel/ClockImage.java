package Component.JPanel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class ClockImage extends JPanel implements Runnable {
	private Image[] roti = new Image[3];
	private Image current = null; // 현재 상태

	public ClockImage() {
		roti[0] = Toolkit.getDefaultToolkit().createImage("img/cl1.png");
		roti[1] = Toolkit.getDefaultToolkit().createImage("img/cl2.png");
		roti[2] = Toolkit.getDefaultToolkit().createImage("img/cl3.png");
		current = roti[0];

	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(current, 0, 0, this);
		// super.paint(g);
	}

	@Override
	public void run() {
		int i = 0;

		while (true) {
			try {
				Thread.sleep(1500);
				switch (i) {
				case 0:
					current = roti[0];
					break;

				case 1:
					current = roti[1];
					break;
				case 2:
					current = roti[2];
					i = -1;
					break;
				}
				repaint();
				i++;
			} catch (InterruptedException e) {
			}
		}

	}

}
