import java.io.*;
import java.net.Socket;
import java.util.*;


//각 클리아언트로 메시지를 전송할 서버 쓰레드
public class PerClientThread extends Thread {
	static List<PrintWriter> list =
			Collections.synchronizedList(new ArrayList<PrintWriter>());
	static int hintcount;
	String[] name_Idx = new String [100];
	Socket socket;
	PrintWriter writer;
	ChatServer server;
	//int nickcounter = 0;   //ready 누른 접속자 카운터
	DBManager dBM ;
	static int nowStage;
	String[] answer = {"DNA","나야나","아낙네"};
	

	public PerClientThread(Socket socket, ChatServer server) {
		this.server = server;
		this.socket = socket;
		try {
			writer = new PrintWriter(socket.getOutputStream());
			list.add(writer);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}		
	@Override
	public void run() {
		dBM = new DBManager();
		String name = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			name = reader.readLine();
			name_Idx[server.nickcount] = name;
			server.nickcount++;
			sendAll("msg/" + name + "님이 들어오셨습니다.");
			System.out.println(">>>nick: " + name + "  접속자수 : " + server.nickcount);
			if(server.clientcount == server.nickcount) {
				nowStage = 1;
				String data = "msg/게임을 시작합니다. 3초 후 노래가 시작됩니다.";
				sendAll(data);
				data = "sys/startframe/"+String.valueOf(nowStage)+".wav";
				sendAll(data);
			}
			while(true) {
				String str = reader.readLine();
				//if(str==null) break;
				String[] array = str.split("/");
//				String[] array1;
				str = array[0]+"/"+name+" > "+array[1];
				sendAll(str);
				String str1 = array[1];
				String[] array1 = str1.split(":");
//				if(array[1].contains(":")) {
//					array1 = array[1].split(":");
//					System.out.println("array1[1]");
//				}
//				
				if(array[1].equals("!hint1")){
					String filename = String.valueOf(nowStage)+".wav";
					String hint1 = dBM.select_hint1(filename);
					writer.println("msg/<<HINT>> "+hint1);
					writer.flush();
					}
				else if(array[1].equals("!hint2")){
					String filename = String.valueOf(nowStage)+".wav";
					String hint2 = dBM.select_hint2(filename);
					writer.println("msg/<<HINT>> "+hint2);
					writer.flush();
					}
				else if(array[1].equals("!hint3")){
					String filename = String.valueOf(nowStage)+".wav";
					String hint3 = dBM.select_hint3(filename);
					writer.println("msg/<<HINT>> "+hint3);
					writer.flush();
					writer.println("sys/startframe/"+String.valueOf(nowStage+3)+".wav");
					writer.flush();
					}
//				if (array[1].equals(answer[nowStage-1])) {
//					sendAll("msg/<<SYSTEM>> [ "+name+" ]님께서 정답을 맞추셨습니다.");
//					if(nowStage==1) sendAll("sys/bts");
//					nowStage++;
//					if(nowStage<4)
//						sendAll("sys/startframe/"+String.valueOf(nowStage)+".wav");
//					else {
//						sendAll("msg/    <<모든 게임이 종료되었습니다.>>");
//						sendAll("sys/gameover");
//						System.out.println("msg/모든 게임이 종료되었습니다.");
//						for(PrintWriter writer : list) 
//							writer.close();
//						}
//				}
				if (array1[0].equals("정답")) {
					if (array1[1].equals(answer[nowStage-1])) {
						sendAll("msg/<<SYSTEM>> [ "+name+" ]님께서 정답을 맞추셨습니다.");
//						if(nowStage==1) sendAll("sys/bts");
//						else if(nowStage==2) sendAll("sty/me");
//						else if(nowStage==3) sendAll("sty/mino");
						nowStage++;
						if(nowStage<4)
							sendAll("sys/startframe/"+String.valueOf(nowStage)+".wav");
						else {
							sendAll("msg/    <<모든 게임이 종료되었습니다.>>");
							sendAll("sys/gameover");
							System.out.println("msg/모든 게임이 종료되었습니다.");
							for(PrintWriter writer : list) 
								writer.close();
							}
					}
					else {
						sendAll("msg/<<SYSTEM>> [ "+name+" ]님 틀렸습니다.");
					}
					
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			list.remove(writer);
			sendAll("msg/#" + name + "님이 나가셨습니다.");
			server.nickcount--;
			System.out.println(">>>nick: " + name + " 접속자수 : " + server.nickcount);
			try {
				socket.close();
			}catch(Exception ex) {
			}
		}
		
	}
	private void sendAll(String str) {
		for(PrintWriter writer : list) {
			writer.println(str);
			writer.flush();
		}
	}
}