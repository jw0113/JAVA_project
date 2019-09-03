import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import com.sun.security.ntlm.Client;


import file.GameOver;
import file.PlayGame;

public class ReceiverThread extends Thread {
	Socket socket;
	public ReceiverThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			//서버에서 받는 부분
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true) {
				String str = reader.readLine();
				System.out.println(str);
				String[] array = str.split("/");
				System.out.println(array[0]);
				if(array[0].equals("msg")) {
					nickNameShow.ta.append(array[1] + "\n");
					//String[] array1 = array[1].split(":");
					nickNameShow.scroll.getVerticalScrollBar().setValue(nickNameShow.scroll.getVerticalScrollBar().getMaximum());
				}
				else if(array[0].equals("sys")) {
					if(array[1].equals("startframe")) new PlayGame(array[2]);

					if(array[1].equals("gameover")) new GameOver();
				}
			}
			
		}catch(Exception e) {
			System.out.println("out");
			e.printStackTrace();
			
		}
	}
}
