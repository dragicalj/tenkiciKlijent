package main;

import java.awt.Graphics;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ToServer {

	DatagramSocket socketForServerCommunication = null;
	InetAddress inetAdress = null;
	int port;
	
	
	public ToServer(DatagramSocket socket,String adress, int port) {
		
		socketForServerCommunication	 = socket;
		this.port = port;
		
		
		try {
			 inetAdress = InetAddress.getByName(adress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		send("init");
		 
		
	}
	
	//greskom se poziva ovaj metod pa se salju i kordinate tenka, a to ne bi trebalo
	//treba promeniti ime metoda
	public void send(String playerXY) {
		
		System.out.println(playerXY);
		
		byte[] sendData = playerXY.getBytes();
		//System.out.println(playerXY);
		DatagramPacket packetForServer = new DatagramPacket(sendData, sendData.length, inetAdress, port);
		try {
			socketForServerCommunication.send(packetForServer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Neuspesno poslat paket");
		}
	}
	
	public void sendBullet(boolean bullet) {
		
		
		
		String temp = Boolean.toString(bullet);
		byte[] sendData = temp.getBytes();
		DatagramPacket packetForServer = new DatagramPacket(sendData, sendData.length, inetAdress, port);	
		
		try {
			socketForServerCommunication.send(packetForServer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Neuspesno poslat paket");
		}
	}
	
//	public void sendBullet(Graphics g) {
//		
//		outputToServer.println("bulletg: " + g);
//	}
	
	
}
