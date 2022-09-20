package com.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Paddle {

	private int x, y;
	private int vel = 0;;
	private int speed = 10;
	private int width = 22;
	private int height = 85;
	private int score = 0;
	private Color color;
	private boolean left;
	
	
	public Paddle(Color c, boolean left){
		this.color = c;
		this.left = left;
		
		if (left) {
			x = 10;
		} else {
			x = Game.WIDTH - width - 10;
		}
		
		y = Game.HEIGHT/2 - height/2;
	}
	
	public void addPoint() {
		score ++;
	}

	public void draw(Graphics g) {
		// draw paddles
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		// draw score
		int sx;
		String scoreText = Integer.toString(score);
		Font font = new Font("Roboto", Font.PLAIN, 50);
		
		int strWidth = g.getFontMetrics(font).stringWidth(scoreText) + 1;
		
		int padding = 25;
		
		if (left) {
			sx = Game.WIDTH / 2 - padding - strWidth;
		} else {
			sx = Game.WIDTH / 2  + padding;	
		};
		 
		g.setFont(font);
		g.drawString(scoreText, sx, 50);
		
	};
}
