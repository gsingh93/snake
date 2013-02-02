package com.gulshansingh.snake;

import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SnakeBody extends JDialog {

	private static JFrame frame;

	public static final int DIM = 32;

	// TODO Remove ability to focus
	public SnakeBody(int x, int y) {
		super(frame);
		if (frame == null) {
			throw new NullPointerException();
		}
		setUndecorated(true);
		setSize(DIM, DIM);
		setAlwaysOnTop(true);
		setLocation(x, y);
		setVisible(true);
	}

	public static void setJFrame(JFrame frame) {
		SnakeBody.frame = frame;
	}
}
