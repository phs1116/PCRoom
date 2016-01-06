package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawRectangle extends JFrame {
	public DrawRectangle() {
		setTitle("gg");
		setSize(1600, 900);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new SeatBoard());
		setVisible(true);
		setLayout(null);

	}

	public static void main(String[] args) {
		new DrawRectangle();
	}

}

class SeatBoard extends JPanel implements MouseMotionListener, MouseListener {
	int psPosX = 0, psPosY = -140;
	Graphics graphics;

	int x, y, width, height;
	boolean isDrag = false;

	public SeatBoard() {
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		graphics = this.getGraphics();
		setFocusable(true);
		setSize(1600, 900);

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawRect(x, y, width, height);
		setOpaque(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
			
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == 1){
		isDrag = true;
		System.out.println("클릭");
		x = e.getX();
		y = e.getY();
		//repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		isDrag = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (isDrag) {
			System.out.println("드래그");
			width = e.getX()-x;
			height = e.getY()-y;
			repaint();
			// repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
