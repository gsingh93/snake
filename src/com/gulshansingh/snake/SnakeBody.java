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
	private static Random r = new Random();

	// TODO Remove ability to focus
	public SnakeBody(int x, int y) {
		super(frame);
		if (frame == null) {
			throw new NullPointerException();
		}
		setUndecorated(true);
		setSize(DIM, DIM);
		setAlwaysOnTop(true);
		mySetLocation(x, y);
		setVisible(true);
		getContentPane().setBackground(
				new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
	}

	public static void setJFrame(JFrame frame) {
		SnakeBody.frame = frame;
	}

	public void mySetLocation(int x, int y) {
		this.x = x;
		this.y = y;
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
