package com.gulshansingh.snake;

import java.util.LinkedList;

import javax.swing.JFrame;

/**
 * A snake that is implemented using multiple JFrames
 * 
 * @author Gulshan Singh
 * 
 */
public class Snake {

	private enum Direction {
		UP, DOWN, RIGHT, LEFT
	}

	public static final int STARTING_X = 600;
	public static final int STARTING_Y = 400;

	private Direction direction = Direction.LEFT;
	private LinkedList<SnakeBody> body = new LinkedList<SnakeBody>();

	public Snake(JFrame parent) {
		SnakeBody.setJFrame(parent);

		for (int i = 0; i < 4; i++) {
			body.add(new SnakeBody(STARTING_X + i * SnakeBody.DIM, STARTING_Y));
		}
	}

	public void update() {
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
			y += SnakeBody.DIM;
			break;
		}
		SnakeBody next = new SnakeBody(x, y);
		body.addFirst(next);

		SnakeBody last = body.removeLast();
		last.dispose();
	}
}
