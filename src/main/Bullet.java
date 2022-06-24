package main;

import java.awt.Graphics;

public abstract class Bullet {

	public double x, y;
	
	public abstract void move(String face);
	public abstract void draw(Graphics g);
	public abstract int getX();
	public abstract int getY();
	
}
