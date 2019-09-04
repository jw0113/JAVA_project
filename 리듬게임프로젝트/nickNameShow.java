import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import com.sun.glass.events.WindowEvent;
import com.sun.security.ntlm.Client;

import javafx.scene.layout.Border;

public class nickNameShow extends JFrame implements ActionListener {
	static JTextArea ta = new JTextArea(40,35);
	static JScrollPane scroll = new JScrollPane(ta);
	JButton btn_msg = new JButton("전송"), btn_nick = new JButton("접속");
	JTextField t_msg = new JTextField(25), t_nick = new JTextField(10);
	JPanel panel = new JPanel(), panel2 = new JPanel();
	Socket socket;
	Sender sender;   //소켓 생성 및 메시지 전송 클래스
	String msg, nick;      //텍스트필드에서 메시지를 저장할 변수
	boolean connect = false;
	static String arg_name = null;
	private static com.sun.security.ntlm.Client client;
	static int hint_count;
	//ImageIcon img = new ImageIcon("C:\\Users\\jiwoo\\Desktop\\대학\\2학년\\2학기\\자바어플리케이션(노정규교수님)\\Project\\picture\\chat.jpg");
	
	nickNameShow(Socket socket){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		this.socket = socket;
		setLocation(200,30);
		setSize(620,1000);
		panel.add(scroll,BorderLayout.CENTER);
		//수평 스크롤바 제거
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//textArea 배경색 설정
		ta.setBackground(Color.white);
		ta.setLineWrap(true);
		//javax.swing.border.Border lineBorder = BorderFactory.createLineBorder(Color.pink, 3);
		//javax.swing.border.Border emptyBorder = BorderFactory.createEmptyBorder(7, 7, 7, 7);
		Font font = new Font("arian", Font.BOLD, 15);
		ta.setFont(font);
		ta.append("접속방법 : 닉네임 입력 \n" + "Hint 요청 : !hint 입력 후 전송\n" + "정답 요청: '정답:노래 제목'전송(영어는 대문자로)\n");
		panel.setBackground(Color.gray);
		panel2.setBackground(Color.white);
		//panel2.setBackground(Color.black);
		//입력 컴포넌트 패널
		panel2.add(t_msg);
		panel2.add(btn_msg);
		panel2.add(t_nick);
		panel2.add(btn_nick);
		
		
		t_msg.setText("메시지를 입력하세요.");
		t_nick.setText("닉네임을 입력하세요.");
		hint_count =1;
		//프레임에 패널 추가
		panel.add(panel2,BorderLayout.SOUTH);
		add(panel,BorderLayout.CENTER);
		
		//텍스트 필드 키보드 이벤트
		t_msg.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					msg = t_msg.getText();
					sender.sendMsg(msg);
					t_msg.setText("");
				}
			}
		});
		
		//텍스트 필드 포커스 이벤트
		t_msg.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				t_msg.setText("");
			}
		});
		
		t_nick.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				t_nick.setText("");
			}
		});
		
		//버튼 이벤트
		btn_msg.addActionListener(this);
		btn_nick.addActionListener(this);
		
		setVisible(true);	
	}
	// 전송 버튼 이벤트 리스너
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btn_msg) {
			if(connect) {
				try {
					msg = t_msg.getText();
					if(msg.equals("!hint")&& hint_count<4) {
						msg=msg+String.valueOf(hint_count);
						hint_count++;
					}
					msg = "msg/"+msg;
					sender.sendMsg(msg);
					t_msg.setText("");
					t_msg.requestFocus();
				}catch (Exception ex) {
					System.out.println("서버 접속되지 않았습니다.");
					JOptionPane.showMessageDialog(this, "서버에 접속되지 않았습니다." , "알림",
							JOptionPane.ERROR_MESSAGE);
					t_msg.setText("메시지를 입력하세요.");
				}
			}else { //접속중이 아닐때 메시지를 넘긴다면 에러 메시지 출력
				System.out.println("서버에 접속하지 않았습니다.");
				JOptionPane.showMessageDialog(this,"서버에 접속되지 않았습니다.","알림",JOptionPane.ERROR_MESSAGE);
				t_msg.setText("메시지를 입력하세요.");
			}
		}
		
		
		//ready버튼을 클릭했을 때
		if(e.getSource() == btn_nick) {		
			nick = t_nick.getText();  //문자열 읽어오기
			if(!connect) {
				try {
					sender = new Sender(socket, nick);
					sender.sendMsg(nick);
					
					Thread t2 = new ReceiverThread(socket);
					t2.start();
					btn_nick.setBackground(Color.GREEN);
					btn_nick.setText("접속중");
					connect = true;
				}catch(Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "접속에 실패했습니다.","알림",JOptionPane.ERROR_MESSAGE);
				}
			}
			//접속중이라면 소켓을 닫음
			else if(connect) {
				try {
					socket.close();
					System.out.println("서버와 연결이 끊어졌습니다.");
					ta.append("서버와 연결이 끊어졌습니다.\n");
					btn_nick.setBackground(Color.RED);
					btn_nick.setText("접속끊김");
					t_msg.setText("메시지를 입력하세요.");
					connect = false;
					addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							System.exit(0);
						}
					});
				}catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
	
	public class Sender{
		PrintWriter writer;
		Socket socket;
		String name;
		
		Sender(Socket socket, String name){
			this.socket = socket;
			this.name = name;
			try {
				writer = new PrintWriter(socket.getOutputStream());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void sendMsg(String msg) {
			if(writer != null) {
				try {
					writer.println(msg);
					writer.flush();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
