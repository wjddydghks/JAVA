package dinoTest;

import javax.swing.*;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class StartScreen extends JPanel{
	
	
	ImageIcon backgroundImage = new ImageIcon("Img/main.png");
	Image background = backgroundImage.getImage();
	
	ImageIcon img1 = new ImageIcon("Img/start.png");
	//스타트 이미지 선언
	Image Simg = img1.getImage(); //이미지 크기 조절을 위해 이미지 추출
	Image changeSImg = Simg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);// 이미지 크기 조절
	ImageIcon changeSIcon = new ImageIcon(changeSImg);
	//imageIcon 형태로 다시 선언
	
	ImageIcon Pimg1 = new ImageIcon("Img/start_push.png");
	Image SPimg = Pimg1.getImage(); //이미지 크기 조절을 위해 이미지 추출
	Image changeSPImg = SPimg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);// 이미지 크기 조절
	ImageIcon changeSPIcon = new ImageIcon(changeSPImg);
	
	
	
	ImageIcon img2 = new ImageIcon("Img/exit.png");
	Image Eimg = img2.getImage();
	Image changeEImg = Eimg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
	ImageIcon changeEIcon = new ImageIcon(changeEImg);
	
	ImageIcon Pimg2 = new ImageIcon("Img/exit_push.png");
	Image EPimg = Pimg2.getImage(); //이미지 크기 조절을 위해 이미지 추출
	Image changeEPImg = EPimg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);// 이미지 크기 조절
	ImageIcon changeEPIcon = new ImageIcon(changeEPImg);
	
	
	
	
	
	

	
	public StartScreen(Main startCard) {
		
		setLayout(null);
		
		JButton start = new JButton(changeSIcon); 
		start.setBorderPainted(false);//외곽선 제거
		start.setContentAreaFilled(false);//내용영역 채우기 안 함
		start.setFocusPainted(false);//선택됐을 떄 생기는 테두리 사용 안 함
		start.setBounds(510, 260, 260,70); //버튼의 배치와 크기 설정
		start.setRolloverIcon(changeSPIcon);
		add(start);
		
		
		
		
		JButton exit = new JButton(changeEIcon);
		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);
		exit.setFocusPainted(false);
		exit.setBounds(510, 360,260,70);
		exit.setRolloverIcon(changeEPIcon);
		add(exit);
		
		start.addActionListener(new ActionListener() { //스타트 버튼 눌렀을 때 
            @Override
            public void actionPerformed(ActionEvent e) {
            	new Music("buttonPressedMusic.mp3", false).start();
            	startCard.showGameSelectScreen();
            }
        });
		
		exit.addActionListener(new ActionListener() { //exit 버튼을 눌렀을때
            @Override
            public void actionPerformed(ActionEvent e) {
            	new Music("buttonPressedMusic.mp3", false).start();
                System.exit(0); // 게임 종료
            }
        });
		
		

		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		}

	
}
		
		
		
	







