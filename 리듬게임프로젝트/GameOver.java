package file;

import java.awt.*;
import javax.swing.*;

public class GameOver extends JFrame {
	JScrollPane scrollPane;
	ImageIcon icon;
	public GameOver() {
		setLocation(100,300);
		setPreferredSize(new Dimension(300,500));
		icon = new ImageIcon("C:\\Users\\jiwoo\\Desktop\\대학\\2학년\\2학기\\자바어플리케이션(노정규교수님)\\Project\\picture\\gameover.jpg");
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(icon.getImage(),0,0, this.getWidth(), this.getHeight(), null);
				setOpaque(false);  //투명도(색상넣기)
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
