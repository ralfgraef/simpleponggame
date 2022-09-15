package com.main;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = WIDTH * 9/16;
	
	public boolean running = false;
	private Thread gameThread;
	
	private Ball ball;
	private Paddle paddle1;
	private Paddle paddle2;
	
	public Game() {
		canvasSetup();
		initialize();
		
		new Window("Ralfs Simple Pong", this);
	}

	private void initialize() {
		// initialize ball
		ball = new Ball();
		
		// initialize paddles
		paddle1 = new Paddle(Color.green, true);
		paddle2 = new Paddle(Color.red, false);
		
	}

	private void canvasSetup() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		
	}

	@Override
	public void run() {
		this.requestFocus();

		// game timer
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				delta--;
				draw();
				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}

		stop();
		
	}

	private void draw() {
		// Initialize drawing tools
		BufferStrategy buffer = this.getBufferStrategy();
		if (buffer == null) {
			this.createBufferStrategy(3);
			return;	
		}
		Graphics g = buffer.getDrawGraphics();
		
		// draw background
		drawBackground(g);
		
		// draw ball
		ball.draw(g);
		
		// draw paddles
		paddle1.draw(g);
		paddle2.draw(g);
			
		g.dispose();
		buffer.show(); 
	}

	private void drawBackground(Graphics g) {
		// black background
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// set dotted line in the middle
		g.setColor(Color.white);
		Graphics2D g2d =(Graphics2D)g;
		
		
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 15 }, 0);
		g2d.setStroke(dashed);
		g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
		
	}

	private void update() {
		// TODO Auto-generated method stub
		
	}

	public void start() {
		gameThread = new Thread(this);
		gameThread.start();
		running = true;
	}
	
	public static int sign(double d) {
		if (d<=0) {
			return -1;
		}
		return 1;
	}	
	
	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	};
	
	public static void main(String[] args) {
		new Game();
		
	}

}
