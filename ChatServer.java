
import java.io.*;
import java.net.*;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer{
	int nickcount=0,clientcount=0;
	public ChatServer() {
		ServerSocket Socket = null;
		try {
			Socket = new ServerSocket(9000);
			while(true) {
				Socket socket = Socket.accept();
				System.out.println("A new client socket connected...");
				clientcount++;
				System.out.println("connect client count : " + clientcount);
				Thread t = (Thread) new PerClientThread(socket,this);
				t.start();
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public static void main(String args[]) {
		new ChatServer();	
	}
}