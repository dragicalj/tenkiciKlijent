package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;


public class Player1Bullet {
	
	private double x, y;
	
	public Player1Bullet() {
		
	}

	public Player1Bullet(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void move(String face)
	{
		if(face.equals("right")) {
			x += 10;
		}
			
		else if(face.equals("left")) {
			x -= 10;
		}
			
		else if(face.equals("up")) {
			y -= 10;
		}
			
		else {
			y +=10;
		}
			
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.green);
		g.fillOval((int) x, (int) y, 10, 10);
	}
	
	public int getX()
	{
		return (int)x;
	}
	public int getY()
	{
		return (int)y;
	}
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

}
