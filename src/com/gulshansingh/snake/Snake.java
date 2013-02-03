package com.gulshansingh.snake;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.JFrame;

/**
 * A snake that is implemented using multiple JFrames
 * 
 * @author Gulshan Singh
 * 
 */
public class Snake implements Serializable {

	private static final long serialVersionUID = 7162978852128270222L;

	public enum Direction {
		UP, DOWN, RIGHT, LEFT, EAT
	}

	public static final int STARTING_X = 640;
	public static final int STARTING_Y = 480;

	private Direction direction = Direction.LEFT;
	private LinkedList<SnakeBody> body = new LinkedList<SnakeBody>();
	private SnakeBody first;
	public Snake(JFrame parent) {
		SnakeBody.setJFrame(parent);

		for (int i = 0; i < 10; i++) {
			body.add(new SnakeBody(STARTING_X + i * SnakeBody.DIM, STARTING_Y));
			if (STARTING_X + i * SnakeBody.DIM < Main.start_x) {
				body.get(i).setVisible(false);
			} else if (STARTING_X + i * SnakeBody.DIM > Main.start_x
					+ Main.rframe_width) {
				body.get(i).setVisible(false);
			}
		}

		first = body.getFirst();
	}
	
	public int getXCoord() {
		return first.getX();
	}
	
	public int getYCoord() {
		return first.getY();
	}
	
	public void setDirection(Direction dir) {
		direction = dir;
	}

	public void update() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		SnakeBody first = body.getFirst();
		int x = first.getX();
		int y = first.getY();
		switch (direction) {
		case UP:
			y -= SnakeBody.DIM;
			break;
		case DOWN:
			y += SnakeBody.DIM;
			break;
		case LEFT:
			x -= SnakeBody.DIM;
			break;
		case RIGHT:
			x += SnakeBody.DIM;
			break;
		}

		// We know new coordinates of the first square
		if (y < 0) {
			y = Main.vframe_height + SnakeBody.DIM;
		} else if (y > size.height) {
			y = 0;
		} else if (x < 0) {
			x = Main.vframe_width - SnakeBody.DIM;
		} else if (x > Main.vframe_width) {
			x = 0;
		}

		// System.out.println(x);
		// System.out.println(y);
		// System.out.println("");
		// System.out.println(Main.start_x);
		// System.out.println(Main.rframe_width);
		// System.out.println("");

		SnakeBody next = new SnakeBody(x, y);
		body.addFirst(next);

		System.out.println(Main.start_x);

		if (y < 0) {
			next.setVisible(false);
			System.out.println(next.isVisible());
		} else if (y > size.height) {
			next.setVisible(false);
			System.out.println(next.isVisible());
		}
		if (x < Main.start_x) {
			next.setVisible(false);
			System.out.println(next.isVisible());
		} else if (x > Main.start_x + Main.rframe_width) {
			next.setVisible(false);
			System.out.println(next.isVisible());
		}

		this.first = next;

		if (Main.start_x != 0) {
			if (next.isVisible()) {
				next.setLocation(x - Main.start_x, y);
			}
		} else {
			next.setLocation(x, y);
		}

		SnakeBody last = body.removeLast();
		last.dispose();
	}

	public boolean collision(SnakeBody food) {
		SnakeBody first = body.getFirst();
		if (first.getX() - Main.start_x == food.getX()
				&& first.getY() == food.getY()) {
			return true;
		}
		return false;
	}

	public void appendSnakeBody() {
		SnakeBody last = body.getLast();
		int x = last.getX();
		int y = last.getY();
		switch (direction) {
		case UP:
			y += SnakeBody.DIM;
			break;
		case DOWN:
			y -= SnakeBody.DIM;
			break;
		case LEFT:
			x += SnakeBody.DIM;
			break;
		case RIGHT:
			x -= SnakeBody.DIM;
			break;
		}
		SnakeBody next = new SnakeBody(x, y);
		body.addLast(next);
	}
}
