package com.gulshansingh.snake;

import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
	private static int numConnections = 0;

	private JFrame frame;
	private Snake snake;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	
	public static void main(String[] args) {
		new Main();
	}

	public void createGui() {
		frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		@SuppressWarnings("unused")
		JWindow window = new JWindow(frame);

		JLabel IPLabel = new JLabel("IP: ");
		final JTextField IPField = new JTextField();
		IPField.setColumns(15);
		JButton startButton = new JButton("Start");

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						start(IPField.getText());
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
		new Thread (new Runnable() {

			@Override
			public void run() {
				listenForConnection();
			}
			
		}).start();
		createGui();
		
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyDispatcher());
	}

	private void start(String IP) {
		if (IP != null && !IP.equals("")) {
			connect(IP);
			numConnections++;
		}
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

	private void connect(String IP) {
		try {
			Socket sock = new Socket(IP, 35267);
			InputStreamReader streamReader = new InputStreamReader(
					sock.getInputStream());
			reader = new ObjectInputStream(sock.getInputStream());
			writer = new ObjectOutputStream(sock.getOutputStream()); // I write snake here
			System.out.println("Snake network established");
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	private void sendSnake() {
		// Serialize data object to a file
		try {
			writer.writeObject(snake);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void receiveSnake() {
		// Serialize data object to a file
			try {
				snake = (Snake) reader.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	  public void listenForConnection() {
          try {
              ServerSocket serverSock = new ServerSocket(35267);
                  Socket clientSocket = serverSock.accept();
                  writer = new ObjectOutputStream(clientSocket.getOutputStream());
              }
          catch (Exception ex) { ex.printStackTrace(); }
      }
}
