package com.gulshansingh.snake;

import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * A snake that is implemented using multiple JFrames
 * 
 * @author Gulshan Singh
 * 
 */
public class Snake {

	public static final int STARTING_X = 200;
	public static final int STARTING_Y = 200;
	public ArrayList<SnakeBody> body = new ArrayList<SnakeBody>();

	public Snake(JFrame parent) {
		SnakeBody.setJFrame(parent);
		body.add(new SnakeBody(STARTING_X, STARTING_Y));
		body.add(new SnakeBody(STARTING_X + 50, STARTING_Y + 50));
	}
}
