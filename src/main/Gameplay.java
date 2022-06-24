package main;

import java.util.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

public class Gameplay extends JPanel implements ActionListener {
	private Brick br;

	private ImageIcon player1;
	private static int player1X = 200;
	private static int player1Y = 550;
	private static boolean player1right = false;
	private static boolean player1left = false;
	private static boolean player1down = false;
	private static boolean player1up = true;
	private static int player1score = 0;


	private int player1lives = 5;
	private static boolean player1Shoot = false;
	private String bulletShootDir1 = "";

	private ImageIcon player2;
	private static int player2X = 400;
	private static int player2Y = 550;
	private static boolean player2right = false;
	private static boolean player2left = false;
	private static boolean player2down = false;
	private static boolean player2up = true;
	private static int player2score = 0;
	private int player2lives = 5;
	private static boolean player2Shoot = false;
	private String bulletShootDir2 = "";

	private Timer timer;
	private int delay = 8;

	private Player1Listener player1Listener;
	private Player2Listener player2Listener;

	private static Player1Bullet player1Bullet = null;
	private static Player2Bullet player2Bullet = null;

	private boolean play = true;
	
	int portNumber = 12000;
	String adress = "localhost";
	//Socket socketForServerCommunication;
	BufferedReader inputFromServer;
	PrintStream outputToServer;
	private ToServer toServer;
	private static boolean statusP1 = false;
	private static boolean statusP2 = false;

	public Gameplay() {
		br = new Brick();
		player1Listener = new Player1Listener();
		player2Listener = new Player2Listener();
		setFocusable(true);
		// addKeyListener(this);
		addKeyListener(player1Listener);
		addKeyListener(player2Listener);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();

		DatagramSocket socketForServerCommunication = null;

		try {
			socketForServerCommunication = new DatagramSocket();
		
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//inicijalizacija klasa koje primaju(novi tred za primanje podataka) i salju podatke serveru
		new Thread(new FromServer(socketForServerCommunication,adress,portNumber)).start();
		toServer = new ToServer(socketForServerCommunication,adress,portNumber);

	}

	

	public static void setPlayer1score(int player1score) {
		Gameplay.player1score = player1score;
	}


	


	public static void setPlayer2score(int player2score) {
		Gameplay.player2score = player2score;
	}
	
	
	static public synchronized void setStatusP1(boolean statusPlayer1) {

		statusP1 = statusPlayer1 ;
		System.out.println("method: setStatusP1"); 

	}
		
	static public synchronized void setStatusP2(boolean statusPlayer2) {

		statusP2 = statusPlayer2 ;
		System.out.println("method: setStatusP2");
	}
	
	static public synchronized void setP1X(int x) {

		player1X = x;
		System.out.println("method: setP1X");
	}

	static public synchronized void setP2X(int x) {

		player2X = x;
		System.out.println("method: setP2X");
	}

	static public synchronized void setP1Y(int y) {

		player1Y = y;
		System.out.println("method setP1Y");
	}

	static public synchronized void setP2Y(int y) {

		player2Y = y;
		System.out.println("method setP2Y");
	}
	

	static public  synchronized void setDirectionP1(boolean right, boolean left, boolean down, boolean up) {
		
		player1right = right;
		player1left = left;
		player1down = down;
		player1up = up;
		System.out.println("method setDirectiionP1");
		System.out.println();
		
	}
	
	static public synchronized void setDirectionP2(boolean right, boolean left, boolean down, boolean up) {
		
		player2right = right;
		player2left = left;
		player2down = down;
		player2up = up;
		System.out.println("method setDirectiionP2");
		System.out.println();

		
	}
	
	public static synchronized void handleBullet1() {
		
		player1Shoot = true;
		
		if(player1Shoot)
			if (player1up) {
				player1Bullet = new Player1Bullet(player1X + 20, player1Y);
			} else if (player1down) {
				player1Bullet = new Player1Bullet(player1X + 20, player1Y + 40);
			} else if (player1right) {
				player1Bullet = new Player1Bullet(player1X + 40, player1Y + 20);
			} else if (player1left) {
				player1Bullet = new Player1Bullet(player1X, player1Y + 20);
			}
	}
	
	public static synchronized void handleBullet2() {
		
		player2Shoot = true;
		
		if(player2Shoot)
			if (player2up) {
				player2Bullet = new Player2Bullet(player2X + 20, player2Y);
			} else if (player2down) {
				player2Bullet = new Player2Bullet(player2X + 20, player2Y + 40);
			} else if (player2right) {
				player2Bullet = new Player2Bullet(player2X + 40, player2Y + 20);
			} else if (player2left) {
				player2Bullet = new Player2Bullet(player2X, player2Y + 20);
			}
			
		
	}
	
	//paints every object onto to frame
	public void paint(Graphics g) {
		// play background
		g.setColor(Color.black);
		g.fillRect(0, 0, 650, 600);

		// right side background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(660, 0, 140, 600);

		// draw solid bricks
		br.drawSolids(this, g);

		// draw Breakable bricks
		br.draw(this, g);

		if (play) {
			// crtanje igraca 1
			if (player1up)
				player1 = new ImageIcon("player1_tank_up.png");
			else if (player1down)
				player1 = new ImageIcon("player1_tank_down.png");
			else if (player1right)
				player1 = new ImageIcon("player1_tank_right.png");
			else if (player1left)
				player1 = new ImageIcon("player1_tank_left.png");

			player1.paintIcon(this, g, player1X, player1Y);

			// crtanje igraca 2
			if (player2up)
				player2 = new ImageIcon("player2_tank_up.png");
			else if (player2down)
				player2 = new ImageIcon("player2_tank_down.png");
			else if (player2right)
				player2 = new ImageIcon("player2_tank_right.png");
			else if (player2left)
				player2 = new ImageIcon("player2_tank_left.png");

			player2.paintIcon(this, g, player2X, player2Y);
			
			//crtanje metaka
			if (player1Bullet != null && player1Shoot) {
				if (bulletShootDir1.equals("")) {
					if (player1up) {
						bulletShootDir1 = "up";
					} else if (player1down) {
						bulletShootDir1 = "down";
					} else if (player1right) {
						bulletShootDir1 = "right";
					} else if (player1left) {
						bulletShootDir1 = "left";
					}
				} else {
					player1Bullet.move(bulletShootDir1);
					player1Bullet.draw(g);
				}

				if (new Rectangle(player1Bullet.getX(), player1Bullet.getY(), 10, 10)
						.intersects(new Rectangle(player2X, player2Y, 50, 50))) {
					if(statusP1) {
					
					player1score += 10;
					
					String message = "score:"+player1score+":"+player2score+":";
					toServer.send(message);
					//player2lives -= 1;
					player1Bullet = null;
					bulletShootDir1 = "";
					
					if(player2lives==0) {
						toServer.sendBullet(player1Shoot);
					}
				
					
					}
					player1Shoot = false;
				}

				if (player1Bullet != null && ( br.checkCollision(player1Bullet.getX(), player1Bullet.getY())
						|| br.checkSolidCollision(player1Bullet.getX(), player1Bullet.getY()))) {
					player1Bullet = null;
					player1Shoot = false;
					bulletShootDir1 = "";
				}

				if (player1Bullet != null && (player1Bullet.getY() < 1 || player1Bullet.getY() > 580 || player1Bullet.getX() < 1
						|| player1Bullet.getX() > 630)) {
					player1Bullet = null;
					player1Shoot = false;
					bulletShootDir1 = "";
				}
			}

			if (player2Bullet != null && player2Shoot) {
				if (bulletShootDir2.equals("")) {
					if (player2up) {
						bulletShootDir2 = "up";
					} else if (player2down) {
						bulletShootDir2 = "down";
					} else if (player2right) {
						bulletShootDir2 = "right";
					} else if (player2left) {
						bulletShootDir2 = "left";
					}
				} else {
					player2Bullet.move(bulletShootDir2);
					player2Bullet.draw(g);
				}

				if (new Rectangle(player2Bullet.getX(), player2Bullet.getY(), 10, 10)
						.intersects(new Rectangle(player1X, player1Y, 50, 50))) {
					if(statusP2) {
					
					player2score += 10;
					String message = "score:"+player1score+":"+player2score+":";
					toServer.send(message);
					//player1lives -= 1;
					player2Bullet = null;
					bulletShootDir2 = "";
					
					if(player1lives==0) {
						toServer.sendBullet(player2Shoot);
					}
				
					
					}
					player2Shoot = false;
				}

				if (player2Bullet != null && ( br.checkCollision(player2Bullet.getX(), player2Bullet.getY())
						|| br.checkSolidCollision(player2Bullet.getX(), player2Bullet.getY()))) {
					player2Bullet = null;
					player2Shoot = false;
					bulletShootDir2 = "";
				}

				if (player2Bullet != null && (player2Bullet.getY() < 1 || player2Bullet.getY() > 580 || player2Bullet.getX() < 1
						|| player2Bullet.getX() > 630)) {
					player2Bullet = null;
					player2Shoot = false;
					bulletShootDir2 = "";
				}
			}

		}

		// the scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 15));
		g.drawString("Scores", 700, 30);
		g.drawString("Player 1:  " + player1score, 670, 60);
		g.drawString("Player 2:  " + player2score, 670, 90);

		//g.drawString("Lives", 700, 150);
		//g.drawString("Player 1:  " + player1lives, 670, 180);
		//g.drawString("Player 2:  " + player2lives, 670, 210);

		if (player1lives == 0) {
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 60));
			g.drawString("Game Over", 200, 300);
			g.drawString("Player 2 Won", 180, 380);
			play = false;
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("(Space to Restart)", 230, 430);
		} else if (player2lives == 0) {
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 60));
			g.drawString("Game Over", 200, 300);
			g.drawString("Player 1 Won", 180, 380);
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("(Space to Restart)", 230, 430);
			play = false;
		}

		g.dispose();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		repaint();
	}

	private class Player1Listener implements KeyListener {
		String player1XStr;
		String player1YStr;
		String xy;
		
		public void moveP1() {
			player1XStr = Integer.toString(player1X);
			player1YStr = Integer.toString(player1Y);
			xy = player1XStr + "," + player1YStr + ","+ direction();
			toServer.send(xy);
		}
		
		public String direction() {
			//desno levo dole gore
			
			if(player1right==true) {
				return "1,0,0,0";
			}
			if(player1left==true) {
				return "0,1,0,0";
			}
			if(player1down==true) {
				return "0,0,1,0";
			}
			return "0,0,0,1";
			
		}
		

		public void keyTyped(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
		}
		
		public void keyPressed(KeyEvent e) {

			if (statusP1) { // pogledati komenatar nize kod igraca 2

				if (e.getKeyCode() == KeyEvent.VK_SPACE && (player1lives == 0 || player2lives == 0)) {
					br = new Brick();
					player1X = 200;
					player1Y = 550;
					player1right = false;
					player1left = false;
					player1down = false;
					player1up = true;

					player2X = 400;
					player2Y = 550;
					player2right = false;
					player2left = false;
					player2down = false;
					player2up = true;

					player1score = 0;
					player1lives = 5;
					player2score = 0;
					player2lives = 5;
					play = true;
					repaint();
				}
				if (e.getKeyCode() == KeyEvent.VK_U) {
					if (!player1Shoot) {
						if (player1up) {
							player1Bullet = new Player1Bullet(player1X + 20, player1Y);
						} else if (player1down) {
							player1Bullet = new Player1Bullet(player1X + 20, player1Y + 40);
						} else if (player1right) {
							player1Bullet = new Player1Bullet(player1X + 40, player1Y + 20);
						} else if (player1left) {
							player1Bullet = new Player1Bullet(player1X, player1Y + 20);
						}

						player1Shoot = true;
						toServer.sendBullet(player1Shoot); //sends bullet
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					player1right = false;
					player1left = false;
					player1down = false;
					player1up = true;

					if (!br.checkCollisionBrick(player1X, player1Y-10)&&!(player1Y < 10)) {
						player1Y -= 10;
						
					}
					moveP1();
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					player1right = false;
					player1left = true;
					player1down = false;
					player1up = false;

					if (!br.checkCollisionBrick(player1X-10, player1Y)&&!(player1X < 10)) {
						player1X -= 10;
						
					}
					moveP1();
				}
				if (e.getKeyCode() == KeyEvent.VK_S) {
					player1right = false;
					player1left = false;
					player1down = true;
					player1up = false;

					if (!br.checkCollisionBrick(player1X, player1Y+10)&&!(player1Y > 540)) {
						player1Y += 10;
					
					}
					moveP1();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_D) {
					player1right = true;
					player1left = false;
					player1down = false;
					player1up = false;

					if (!br.checkCollisionBrick(player1X+10, player1Y)&&!(player1X > 590)) {
						player1X += 10;
						
					}
					moveP1();
				}
			}
		}
	}

	private class Player2Listener implements KeyListener {
		
		String player2XStr;
		String player2YStr;
		String xy2;
		
		public void moveP2() {
			player2XStr = Integer.toString(player2X);
			player2YStr = Integer.toString(player2Y);
			xy2 = player2XStr + "," + player2YStr + "," + direction();
			toServer.send(xy2);
		}
		public String direction() {
			//desno levo dole gore
			
			if(player2right==true) {
				return "1,0,0,0";
			}
			if(player2left==true) {
				return "0,1,0,0";
			}
			if(player2down==true) {
				return "0,0,1,0";
			}
			return "0,0,0,1";
			
		}
		
		
		public void keyTyped(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			if (statusP2) { //onemogucuje igrac dva da se pomera ukoliko je false. Stizanjem podataka se pomera
				if (e.getKeyCode() == KeyEvent.VK_M) {
					if (!player2Shoot) {
						if (player2up) {
							player2Bullet = new Player2Bullet(player2X + 20, player2Y);
						} else if (player2down) {
							player2Bullet = new Player2Bullet(player2X + 20, player2Y + 40);
						} else if (player2right) {
							player2Bullet = new Player2Bullet(player2X + 40, player2Y + 20);
						} else if (player2left) {
							player2Bullet = new Player2Bullet(player2X, player2Y + 20);
						}

						player2Shoot = true;
						toServer.sendBullet(player2Shoot);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					player2right = false;
					player2left = false;
					player2down = false;
					player2up = true;

					if (!br.checkCollisionBrick(player2X, player2Y-10)&&!(player2Y < 10)) {
						player2Y -= 10;
						
					
					}
					moveP2();
				}
			
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					player2right = false;
					player2left = true;
					player2down = false;
					player2up = false;

					if (!br.checkCollisionBrick(player2X-10, player2Y)&&!(player2X < 10)) {
						player2X -= 10;
						
					
					}
					moveP2();
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					player2right = false;
					player2left = false;
					player2down = true;
					player2up = false;

					if (!br.checkCollisionBrick(player2X, player2Y+10)&&!(player2Y > 540)) {
						player2Y += 10;
						
					
					}
					moveP2();
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					player2right = true;
					player2left = false;
					player2down = false;
					player2up = false;

					if (!br.checkCollisionBrick(player2X+10, player2Y)&&!(player2X > 590)) {
						player2X += 10;
						
					
					}
					moveP2();
				}

			}
		}
	}


}
