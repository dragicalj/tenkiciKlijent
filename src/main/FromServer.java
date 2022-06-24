package main;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class FromServer extends Thread {
	
	DatagramSocket socketForServerCommunication;
	int port;
	InetAddress inetAdress= null;
	
	public FromServer(DatagramSocket socket,String adress, int port) {
		
		socketForServerCommunication	 = socket;
		this.port = port;
		
		
		try {
			 inetAdress = InetAddress.getByName(adress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		
	}
	
	
	public void run() {
		
//		int portNumber = 8090;
		
		System.out.println("FromServer zapocet");
		
		
		
		try {
			
			
			/*
			 * int counter = 0; int x = 550;
			 * 
			 * while(counter<30) { Thread.sleep(1000); x-=5; Gameplay.moveP2(x); counter++;
			 * 
			 * }
			 */
			// citanje podataka sa servera
			while (true) {
				
				byte[] receiveData = new byte[1024];
				DatagramPacket packetFromServer = new DatagramPacket(receiveData, receiveData.length);
				socketForServerCommunication.receive(packetFromServer);
				
				String data = new String(packetFromServer.getData()).trim();
				
			
				//inicijalni string je duzine 1 i u njemu se namesta status
				
				System.out.println(data); 
				
				//INICIJALIZACIJA
				if(data.length() == 1) {
					if(data.contains("1")) {
						Gameplay.setStatusP1(true);
						
					}else {
						Gameplay.setStatusP2(true);	
					}
					
					System.out.println("Podesen status");
				}
				//POCETAK IGRE I PRENOS KORDINATA
				else {
					
					String dataArr[] = data.split(","); // splituju se player,x,y,_,_,_,_ (smer)
				
				 //podesavanje statusa 1 ili 2
					if(dataArr[0].contains("1")) { //provera da li je u pitanju igrac 1
					
						//PRIJEM KORDINATA METKA
						if(dataArr[1].contains("true")) {
							System.out.println("pomeranje bulleta 1");
							System.out.println();
							
							Gameplay.handleBullet1();
						}
						
						else if(dataArr[1].contains("score")) {
							String scoreArr[] = dataArr[1].split(":");
							int score1 = Integer.parseInt(scoreArr[1]);
							int score2 = Integer.parseInt(scoreArr[2]);
							
							
							Gameplay.setPlayer1score(score1);
							Gameplay.setPlayer2score(score2);
						}
						
						//PRIJEM KORDINATA IGRACA
						else {
							System.out.println("Pomeranje igraca 1");
							System.out.println();
							
							int x = Integer.parseInt(dataArr[1]);
							Gameplay.setP1X(x); //podesavanje x kordinate igraca 1
							int y = Integer.parseInt(dataArr[2]);
							Gameplay.setP1Y(y); //podesavanje y kordinate
							if(dataArr[3].contains("1")) {
								Gameplay.setDirectionP1(true, false, false, false);
							}
							if(dataArr[4].contains("1")) {
								Gameplay.setDirectionP1(false, true, false, false);
							}
							if(dataArr[5].contains("1")) {
								Gameplay.setDirectionP1(false, false, true, false);
							}
							
						
							
							if(dataArr[6].charAt(0) == '1') {
								Gameplay.setDirectionP1(false, false, false, true);
							}
						}
							
					}
						
					//igrac 2
					else {
					
						if(dataArr[1].contains("true")) {
							System.out.println("pomeranje bulleta 2");
							System.out.println();
						
							Gameplay.handleBullet2();
						}
						
						else if(dataArr[1].contains("score")) {
							String scoreArr[] = dataArr[1].split(":");
							int score1 = Integer.parseInt(scoreArr[1]);
							int score2 = Integer.parseInt(scoreArr[2]);
							
							
							Gameplay.setPlayer1score(score1);
							Gameplay.setPlayer2score(score2);
						}
						else {
							int x = Integer.parseInt(dataArr[1]);
							Gameplay.setP2X(x); //podesavanje x kordinate igraca 2
							int y = Integer.parseInt(dataArr[2]);
							Gameplay.setP2Y(y); //podesavanje y kordinate
							if(dataArr[3].contains("1")) {
								Gameplay.setDirectionP2(true, false, false, false);
							}
							if(dataArr[4].contains("1")) {
								Gameplay.setDirectionP2(false, true, false, false);
							}
							if(dataArr[5].contains("1")) {
								Gameplay.setDirectionP2(false, false, true, false);
							}
							if(dataArr[6].charAt(0) == '1') {
								Gameplay.setDirectionP2(false, false, false, true);
							}
						}
					}
				}
				//int x = Integer.parseInt(newX);
				//Gameplay.moveP2(x);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // localhost -> because it connects locally
		
	}

}
