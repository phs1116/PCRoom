package Component.JPanel;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;

import assets.Setting;



public class ClockText extends JPanel implements Runnable {
	private int ampmIndex = Calendar.getInstance().get(Calendar.AM_PM);
	private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
	private String[] ampmStr = { "AM", "PM" };
	private JLabel clock = new JLabel();
	private JLabel ampmlabel = new JLabel();

	public ClockText() {

		setLayout(null); // 레이아웃 매니저를 설정하지 않고 절대 위치로 배치한다.
		// 시계를 위한 JLabel
		clock.setText(sdf.format(new Date()));
		clock.setBounds(Setting.rTimeLabel);
		clock.setForeground(Setting.T_COLOR);
		add(clock, BorderLayout.NORTH);

		// AM,PM을 위한 JLabel
		ampmlabel.setText(ampmStr[ampmIndex]);
		ampmlabel.setBounds(Setting.rAmpmLabel);
		ampmlabel.setForeground(Setting.T_COLOR);
		add(ampmlabel, BorderLayout.CENTER);

	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			ampmIndex = Calendar.getInstance().get(Calendar.AM_PM);
			clock.setText(sdf.format(new Date()));
			ampmlabel.setText(ampmStr[ampmIndex]);

		}

	}

}
