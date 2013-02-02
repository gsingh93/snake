package com.gulshansingh.snake;

import java.awt.Color;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SnakeBody extends JDialog {

	private static JFrame frame;

	public static final int DIM = 32;

	private int x, y;
	// TODO Remove ability to focus
	public SnakeBody(int x, int y) {
		super(frame);
		if (frame == null) {
			throw new NullPointerException();
		}
		setBackground(Color.RED);
		setUndecorated(true);
		setSize(DIM, DIM);
		setAlwaysOnTop(true);
		setLocation(x, y);
		setVisible(true);
		Random r = new Random();
		getContentPane().setBackground(
				new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
	}

	public static void setJFrame(JFrame frame) {
		SnakeBody.frame = frame;
	}

	@Override
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		super.setLocation(x, y);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
}
