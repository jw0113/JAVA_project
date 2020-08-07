import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;

public class startShow extends JFrame {
	//멤버 필드에 ImageIcon 클래스 생성자
	JScrollPane scrollPane;
	ImageIcon icon;
	//ChatServer server;
	
	public startShow() {
		//프레임 창 만들기
		setLocation(300,100);
		setPreferredSize(new Dimension(1100,850));
		icon = new ImageIcon("C:\\Users\\jiwoo\\Desktop\\대학\\2학년\\2학기\\자바어플리케이션(노정규교수님)\\Project\\picture\\background2.jpg");
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(icon.getImage(),0,0, this.getWidth(), this.getHeight(), null);
				setOpaque(false);  //투명도(색상넣기)
			}
		};
		
		scrollPane = new JScrollPane(panel);
		setContentPane(scrollPane);

		panel.setLayout(null);
		JButton button = new JButton(new ImageIcon("C:\\Users\\jiwoo\\Desktop\\대학\\2학년\\2학기\\자바어플리케이션(노정규교수님)\\Project\\picture\\startbutton.png"));
		button.setBounds(300,500,500,200);     //위치(수평), 위치 (수직)
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		panel.add(button);
		button.addActionListener(new ActionListener() {
			nickNameShow sf;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 if(sf == null){
			            try {
							Socket socket = new Socket("127.0.0.1", 9000);   //start버튼을 누르면 서버에 접속
							sf = new nickNameShow(socket);
							dispose();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
			        }
			}
		});
		pack();
		setVisible(true);
	}


	public static void main(String[] args) {
		new startShow();
	}

}
