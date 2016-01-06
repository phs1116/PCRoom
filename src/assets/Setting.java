package assets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Setting {

	//////// *ManageView에 필요한 세팅값들*///////
	public static final Dimension fscreenSize = new Dimension(1600, 900);
	// 메인프레임의 위치를 윈도우화면 가운데로.
	public static Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
	// Toolkit 클래스로 윈도우의 스크린 사이즈를 구해온다.
	public static final Point fLocation = new Point((windowSize.width - fscreenSize.width) / 2,
			(windowSize.height - fscreenSize.height) / 2);

	// 프레임과 배경, 광원의 Rectangle 값
	public static final Rectangle BG_RECTANGLE = new Rectangle(0, 0, 1600, 900);
	public static final Rectangle F_RECTANGLE = BG_RECTANGLE;
	public static final Rectangle L_RECTANGLE = BG_RECTANGLE;

	// 시계 이미지의 Rectangle 값
	public static final Rectangle CI_RECTANGLE = new Rectangle(15, 20, 179, 149);
	public static final Rectangle CT_RECTANGLE = new Rectangle(80, 53, 100, 100);

	public static Rectangle layered = BG_RECTANGLE;
	public static Rectangle backGround = BG_RECTANGLE;
	public static Rectangle ClockImg = CI_RECTANGLE;
	public static Rectangle ClockTxt = CT_RECTANGLE;
	public static Rectangle light = BG_RECTANGLE;
	public static Rectangle seatBoard = new Rectangle(165, 109, 1368, 686);
	/////////////////////////////////////

	//////// 로그인 프레임////////////////////

	public static Rectangle loginField = new Rectangle(731, 399, 230, 30);
	public static Rectangle passwordField = new Rectangle(731, 529, 280, 30);
	public static Rectangle loginButton = new Rectangle(755, 689, 104, 48);
	public static Rectangle panel = BG_RECTANGLE;

	public static final Color T_COLOR = new Color(36, 205, 198);
	public static final Rectangle rTimeLabel = new Rectangle(0, 0, 100, 20);
	public static final Rectangle rAmpmLabel = new Rectangle(15, 20, 100, 30);

	private static Setting instance = new Setting();

	public static Setting getInstance() {
		return instance;
	}

}