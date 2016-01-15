package veiw;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.lang.reflect.*;
import Component.JPanel.*;
import assets.Setting;
import main.Main;

public class ManageFrame extends JFrame {
	private JLayeredPane layered = this.getLayeredPane();
	private JPanel backGround = new ImgLoad();
	private JPanel ClockImg = new ClockImage();
	private JPanel ClockTxt = new ClockText();
	private JPanel light = new LightObject();
	private JPanel seatBoard = new SeatBoard();

	// private Main main;

	public SeatBoard getSeatBoard() {
		return (SeatBoard) seatBoard;
	}

	public void setMain(Main main) {
		// this.main = main;
	}

	public static void main(String[] args) {
		ManageFrame manageView = new ManageFrame();
		try {
			manageView.setJComponent(ManageFrame.class, manageView, Setting.class, Setting.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ManageFrame() {

		// ExitProcess(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// 메인 프레임 설정
		setSize(Setting.fscreenSize);
		setResizable(false);
		setTitle("관리 화면");

		setLocation(Setting.fLocation);

		/*
		 * // 배경을 위한 패널 설정 setComponent(backGround); // 시계 이미지를 위한 패널 설정
		 * setComponent(ClockImg); // 시계 텍스트를 위한 패널 설정 setComponent(ClockTxt);
		 * // 광원 오프젝트를 위한 패널 설정 setComponent(light); // 쓰레드가 필요한 객체 실행
		 * runThread(ClockImg, ClockTxt, light);
		 */
		// 첫번째 인자 LayeredPane에 컴퍼넌트 추가. 밑에서부터 순서대로 쌓는다.
		addToJLayerdPane(layered, backGround, light, ClockImg, ClockTxt, seatBoard);
		layered.setVisible(true);
		setVisible(true);

	}

	// 리플렉션을 이용한 클래스 필드 셋팅.
	public void setJComponent(Class<?> clazz, Object instance, Class<?> targetClass, Object target) throws Exception {
		Object tmpObject = null;
		Field[] fields = clazz.getDeclaredFields();
		// clazz가 표현한 모든 필드들을 반환한다.필드는 클래스,인터페이스가 가지고있는 정보들을 저장한다.
		for (Field field : fields) {
			// System.out.println(field.getName());

			// System.out.println(targetClass.getDeclaredField(field.getName()).get(target));
			// 타겟 클래스의 해당 이름값으 필드값을
			// 가져오고(targetClass.getDeclaredField(field.getName())), 그 필드가
			// 표현하는 필드의 값을 Object클 가져온다.(.get(target))

			// ((JComponent)field.get(instance)).setBounds((Rectangle)targetClass.getDeclaredField(field.getName()).get(target));
			tmpObject = field.get(instance);
			if (tmpObject instanceof JComponent) {
				tmpObject = (JComponent) tmpObject;
				// 그 필드가 표현하는 객체를 instance에서 가져온다.
				Rectangle tmpRec = (Rectangle) targetClass.getDeclaredField(field.getName()).get(target);
				// targetClass에서 지금 필드와 이름이 같은 필드를 가져오고 그 필드가 표현한 객체를 가져온다.
				((JComponent) tmpObject).setBounds(tmpRec);
				((JComponent) tmpObject).setLayout(null);
				((JComponent) tmpObject).setOpaque(false);
			}

			if (tmpObject instanceof Runnable) {
				new Thread((Runnable) tmpObject).start();
			}
		}
	}

	/*
	 * // 컴퍼넌트 셋팅 메서드. public JComponent setComponent(JComponent comp) {
	 * comp.setLayout(null); comp.setOpaque(false); return comp; }
	 * 
	 * // Runnable 인터페이스를 구현한 모든 객체들의 쓰레들을 실행하기 위한 메서드 public void
	 * runThread(Object... threads) { for (Object thread : threads) { if (thread
	 * instanceof Runnable) new Thread((Runnable) thread).start(); } }
	 */

	// JLayerdPane 컴퍼넌트에 컴퍼넌트를 추가하기 위한 메서드
	public void addToJLayerdPane(JLayeredPane jp, Component... components) {
		int i = 0;
		for (Component component : components) {
			jp.add(component, new Integer(i));
			i++;
		}
	}
}
