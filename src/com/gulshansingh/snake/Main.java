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
    private BufferedReader reader;
    private PrintWriter writer;
	private int vframe_width, vframe_height, start_x;
	private int rframe_width, rframe_height;
	private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	private IncomingReader incomingReader;
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
					sendToControllerStatus(e.getKeyCode());
					break;
				case KeyEvent.VK_RIGHT:
					sendToControllerStatus(e.getKeyCode());
					break;
				case KeyEvent.VK_UP:
					sendToControllerStatus(e.getKeyCode());
					break;
				case KeyEvent.VK_DOWN:
					sendToControllerStatus(e.getKeyCode());
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

	private void start(String IP) {
		if (IP != null && !IP.equals("")) {
			connect(IP);
			numConnections++;
		}
		snake = new Snake(frame);

		SnakeBody food = newFood();
		incomingReader = new IncomingReader();
		while (true) {
			incomingReader.run();
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			snake.update();
			if (snake.collision(food)) {
				food.dispose();
				food = newFood();
				sendToControllerStatus(666);
			}
		}
	}

	private SnakeBody newFood() {
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
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("Snake network established");
			sendToControllerInit();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	  
	public void sendToControllerStatus(int state) {
		  try {
			  if(snake.getXCoord() >= start_x && snake.getXCoord() <= start_x + rframe_width)
				  if(snake.getYCoord() >= 0 && snake.getYCoord() <= rframe_height) {
					  writer.print(state);
					  writer.flush();  
				  }
		  	}
	            catch (Exception ex) {
	                ex.printStackTrace();
	           }
	    }
	  public void sendToControllerInit() {
		  try {
			  writer.print(size.width);
			  writer.print(size.height);
	          writer.flush();
		  	}
	            catch (Exception ex) {
	                ex.printStackTrace();
	           }
		  
	    }
	   
	  
	  public class IncomingReader implements Runnable {
		  Boolean v_width_set = false;
		  Boolean v_height_set = false;
		  Boolean r_width_set = false;
		  Boolean r_height_set = false;
		  Boolean start_x_set = false;
		  	public void run() {
		  		int dir = 0;
		  		Integer dirWrapper = new Integer(dir);
	            try {
	                while ((dirWrapper = reader.read()) != null) {
	                    if (!v_width_set) {vframe_width = dirWrapper; v_width_set=true; continue;}
	                    if (!v_height_set) {vframe_height = dirWrapper; v_height_set=true; continue;}
	                    if (!start_x_set) {start_x = dirWrapper; start_x_set=true; continue;}
	                    if (!r_width_set) {rframe_width = dirWrapper; r_width_set=true; continue;}
	                    if (!r_height_set) {rframe_height = dirWrapper; r_height_set=true; continue;}
	                     // Read key presses
	                    switch (dirWrapper) {
	    				case KeyEvent.VK_LEFT:
	    					snake.setDirection((Direction.values()[KeyEvent.VK_LEFT]));
	    					break;
	    				case KeyEvent.VK_RIGHT:
	    					snake.setDirection((Direction.values()[KeyEvent.VK_RIGHT]));
	    					break;
	    				case KeyEvent.VK_UP:
	    					snake.setDirection((Direction.values()[KeyEvent.VK_UP]));
	    					break;
	    				case KeyEvent.VK_DOWN:
	    					snake.setDirection((Direction.values()[KeyEvent.VK_DOWN]));
	    					break;
	    				case 666:
	    					snake.appendSnakeBody();
	    					break;
	    				}
	                }
	            } catch (IOException ex)
	            {
	                ex.printStackTrace();
	            }
	        }
	    }
}
