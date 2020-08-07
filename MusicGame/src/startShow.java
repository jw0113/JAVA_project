import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;

public class startShow extends JFrame {
	//��� �ʵ忡 ImageIcon Ŭ���� ������
	JScrollPane scrollPane;
	ImageIcon icon;
	//ChatServer server;
	
	public startShow() {
		//������ â �����
		setLocation(300,100);
		setPreferredSize(new Dimension(1100,850));
		icon = new ImageIcon("C:\\Users\\jiwoo\\Desktop\\����\\2�г�\\2�б�\\�ڹپ��ø����̼�(�����Ա�����)\\Project\\picture\\background2.jpg");
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(icon.getImage(),0,0, this.getWidth(), this.getHeight(), null);
				setOpaque(false);  //����(����ֱ�)
			}
		};
		
		scrollPane = new JScrollPane(panel);
		setContentPane(scrollPane);

		panel.setLayout(null);
		JButton button = new JButton(new ImageIcon("C:\\Users\\jiwoo\\Desktop\\����\\2�г�\\2�б�\\�ڹپ��ø����̼�(�����Ա�����)\\Project\\picture\\startbutton.png"));
		button.setBounds(300,500,500,200);     //��ġ(����), ��ġ (����)
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
							Socket socket = new Socket("127.0.0.1", 9000);   //start��ư�� ������ ������ ����
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
