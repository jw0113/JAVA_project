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
	JButton btn_msg = new JButton("����"), btn_nick = new JButton("����");
	JTextField t_msg = new JTextField(25), t_nick = new JTextField(10);
	JPanel panel = new JPanel(), panel2 = new JPanel();
	Socket socket;
	Sender sender;   //���� ���� �� �޽��� ���� Ŭ����
	String msg, nick;      //�ؽ�Ʈ�ʵ忡�� �޽����� ������ ����
	boolean connect = false;
	static String arg_name = null;
	private static com.sun.security.ntlm.Client client;
	static int hint_count;
	//ImageIcon img = new ImageIcon("C:\\Users\\jiwoo\\Desktop\\����\\2�г�\\2�б�\\�ڹپ��ø����̼�(�����Ա�����)\\Project\\picture\\chat.jpg");
	
	nickNameShow(Socket socket){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		this.socket = socket;
		setLocation(200,30);
		setSize(620,1000);
		panel.add(scroll,BorderLayout.CENTER);
		//���� ��ũ�ѹ� ����
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//textArea ���� ����
		ta.setBackground(Color.white);
		ta.setLineWrap(true);
		//javax.swing.border.Border lineBorder = BorderFactory.createLineBorder(Color.pink, 3);
		//javax.swing.border.Border emptyBorder = BorderFactory.createEmptyBorder(7, 7, 7, 7);
		Font font = new Font("arian", Font.BOLD, 15);
		ta.setFont(font);
		ta.append("���ӹ�� : �г��� �Է� \n" + "Hint ��û : !hint �Է� �� ����\n" + "���� ��û: '����:�뷡 ����'����(����� �빮�ڷ�)\n");
		panel.setBackground(Color.gray);
		panel2.setBackground(Color.white);
		//panel2.setBackground(Color.black);
		//�Է� ������Ʈ �г�
		panel2.add(t_msg);
		panel2.add(btn_msg);
		panel2.add(t_nick);
		panel2.add(btn_nick);
		
		
		t_msg.setText("�޽����� �Է��ϼ���.");
		t_nick.setText("�г����� �Է��ϼ���.");
		hint_count =1;
		//�����ӿ� �г� �߰�
		panel.add(panel2,BorderLayout.SOUTH);
		add(panel,BorderLayout.CENTER);
		
		//�ؽ�Ʈ �ʵ� Ű���� �̺�Ʈ
		t_msg.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					msg = t_msg.getText();
					sender.sendMsg(msg);
					t_msg.setText("");
				}
			}
		});
		
		//�ؽ�Ʈ �ʵ� ��Ŀ�� �̺�Ʈ
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
		
		//��ư �̺�Ʈ
		btn_msg.addActionListener(this);
		btn_nick.addActionListener(this);
		
		setVisible(true);	
	}
	// ���� ��ư �̺�Ʈ ������
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
					System.out.println("���� ���ӵ��� �ʾҽ��ϴ�.");
					JOptionPane.showMessageDialog(this, "������ ���ӵ��� �ʾҽ��ϴ�." , "�˸�",
							JOptionPane.ERROR_MESSAGE);
					t_msg.setText("�޽����� �Է��ϼ���.");
				}
			}else { //�������� �ƴҶ� �޽����� �ѱ�ٸ� ���� �޽��� ���
				System.out.println("������ �������� �ʾҽ��ϴ�.");
				JOptionPane.showMessageDialog(this,"������ ���ӵ��� �ʾҽ��ϴ�.","�˸�",JOptionPane.ERROR_MESSAGE);
				t_msg.setText("�޽����� �Է��ϼ���.");
			}
		}
		
		
		//ready��ư�� Ŭ������ ��
		if(e.getSource() == btn_nick) {		
			nick = t_nick.getText();  //���ڿ� �о����
			if(!connect) {
				try {
					sender = new Sender(socket, nick);
					sender.sendMsg(nick);
					
					Thread t2 = new ReceiverThread(socket);
					t2.start();
					btn_nick.setBackground(Color.GREEN);
					btn_nick.setText("������");
					connect = true;
				}catch(Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "���ӿ� �����߽��ϴ�.","�˸�",JOptionPane.ERROR_MESSAGE);
				}
			}
			//�������̶�� ������ ����
			else if(connect) {
				try {
					socket.close();
					System.out.println("������ ������ ���������ϴ�.");
					ta.append("������ ������ ���������ϴ�.\n");
					btn_nick.setBackground(Color.RED);
					btn_nick.setText("���Ӳ���");
					t_msg.setText("�޽����� �Է��ϼ���.");
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
