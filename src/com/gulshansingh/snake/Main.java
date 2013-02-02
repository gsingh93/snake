package com.gulshansingh.snake;

import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;

import com.gulshansingh.snake.Snake.Direction;

/**
 * Application entry point
 * 
 * @author Gulshan Singh
 * 
 */
public class Main {

	private static final Random r = new Random();

	private JFrame frame;
	private Snake snake;

	public static void main(String[] args) {
		new Main();
	}

	public void createGui() {
		frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		@SuppressWarnings("unused")
		JWindow window = new JWindow(frame);

		JLabel IPLabel = new JLabel("IP: ");
		JTextField IPField = new JTextField();
		IPField.setColumns(15);
		JButton startButton = new JButton("Start");

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						start();
					}
				}).start();
			}
		});

		JPanel panel = new JPanel();
		panel.add(IPLabel);
		panel.add(IPField);
		panel.add(startButton);

		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	private class KeyDispatcher implements KeyEventDispatcher {
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					snake.setDirection(Direction.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					snake.setDirection(Direction.RIGHT);
					break;
				case KeyEvent.VK_UP:
					snake.setDirection(Direction.UP);
					break;
				case KeyEvent.VK_DOWN:
					snake.setDirection(Direction.DOWN);
					break;
				}
			}

			return false;
		}
	}

	private Main() {
		createGui();

		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyDispatcher());
	}

	private void start() {
		snake = new Snake(frame);

		SnakeBody food = newFood();

		while (true) {
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			snake.update();
			if (snake.collision(food)) {
				food.dispose();
				food = newFood();
				snake.appendSnakeBody();
			}
		}
	}

	private SnakeBody newFood() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (r.nextInt(size.width) + SnakeBody.DIM / 2) / SnakeBody.DIM
				* SnakeBody.DIM;
		int y = (r.nextInt(size.height) + SnakeBody.DIM / 2) / SnakeBody.DIM
				* SnakeBody.DIM;
		return new SnakeBody(x, y);
	}
}
