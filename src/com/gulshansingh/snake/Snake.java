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

		for (int i = 0; i < 4; i++) {
			body.add(new SnakeBody(STARTING_X + i * SnakeBody.DIM, STARTING_Y));
		}
	}
}
