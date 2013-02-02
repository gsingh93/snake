package com.gulshansingh.snake;

import javax.swing.JFrame;
import javax.swing.JWindow;

/**
 * Application entry point
 * 
 * @author Gulshan Singh
 * 
 */
public class Main {

	public static void main(String[] args) {
		new Main();
	}

	private Main() {
		JFrame frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(100, 100);

		@SuppressWarnings("unused")
		JWindow window = new JWindow(frame);

		Snake snake = new Snake(frame);

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			snake.update();
		}
	}
}
