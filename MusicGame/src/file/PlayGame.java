package file;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;
import javax.sound.sampled.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.BorderLayout;
import java.awt.event.*;

public class PlayGame {
	static Socket socket;
	public PlayGame(String filename) throws LineUnavailableException {
		JFrame f = new JFrame();
		
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setSize(200,200);
        f.setVisible(true); 
        JButton button = new JButton("���");
        button.setBounds(300,500,500,200);
        button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		f.add(button);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					File file = new File("C:/Users/jiwoo/Desktop/����/2�г�/2�б�/�ڹپ��ø����̼�(�����Ա�����)/Project/bin/file/"+filename);   //���� �б�
					AudioInputStream audioInputStream = null;
					SourceDataLine auline = null;
					AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				    Clip clip = AudioSystem.getClip();
					clip.open(stream);
					clip.start();
					f.dispose();
				}
				catch (LineUnavailableException e1) {
					e1.printStackTrace();} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		}	
	}

