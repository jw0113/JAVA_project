package file;

import java.awt.*;
import javax.swing.*;

public class GameOver extends JFrame {
	JScrollPane scrollPane;
	ImageIcon icon;
	public GameOver() {
		setLocation(100,300);
		setPreferredSize(new Dimension(300,500));
		icon = new ImageIcon("C:\\Users\\jiwoo\\Desktop\\����\\2�г�\\2�б�\\�ڹپ��ø����̼�(�����Ա�����)\\Project\\picture\\gameover.jpg");
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(icon.getImage(),0,0, this.getWidth(), this.getHeight(), null);
				setOpaque(false);  //����(����ֱ�)
			}
		};
		scrollPane = new JScrollPane(panel);
		setContentPane(scrollPane);
		pack();
		setVisible(true);
	}
	public static void main(String[] args) {
		new GameOver();
	}
}
