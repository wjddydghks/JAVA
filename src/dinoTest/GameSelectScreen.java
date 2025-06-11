package dinoTest;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameSelectScreen extends JPanel{
	
	private ImageIcon backgroundImage = new ImageIcon("Img/rhythm_background.png");
	private Image background = backgroundImage.getImage();
	
	private ImageIcon img3 = new ImageIcon("Img/back.png");
	//Image Bimg = img3.getImage();
		//Image changeBImg = Bimg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
		//ImageIcon changeBIcon = new ImageIcon(changeBImg);
	
	private ImageIcon Pimg3 = new ImageIcon("Img/back_push.png");
	
	
	
	private ImageIcon img4 = new ImageIcon("Img/runningdino.png");
	private Image Dimg = img4.getImage();
	private Image changeDImg = Dimg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
	private ImageIcon changeDIcon = new ImageIcon(changeDImg);
	
	private ImageIcon Pimg4 = new ImageIcon("Img/runningdino_push.png");
	private Image DPimg = Pimg4.getImage();
	private Image changeDPImg = DPimg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
	private ImageIcon changeDPIcon = new ImageIcon(changeDPImg);
	
	
	
	private ImageIcon img5 = new ImageIcon("Img/rhythm.png");
	private Image Rimg = img5.getImage();
	private Image changeRImg = Rimg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
	private ImageIcon changeRIcon = new ImageIcon(changeRImg);
	
	private ImageIcon Pimg5 = new ImageIcon("Img/rhythm_push.png");
	private Image RPimg = Pimg5.getImage();
	private Image changeRPImg = RPimg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
	private ImageIcon changeRPIcon = new ImageIcon(changeRPImg);
	
	
	
	private ImageIcon img6 = new ImageIcon("Img/comeback.png");
	private Image Cimg = img6.getImage();
	private Image changeCImg = Cimg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
	private ImageIcon changeCIcon = new ImageIcon(changeCImg);
	
	private ImageIcon Pimg6 = new ImageIcon("Img/comeback_push.png");
	private Image CPimg = Pimg6.getImage();
	private Image changeCPImg = CPimg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
	private ImageIcon changeCPIcon = new ImageIcon(changeCPImg);
	
	public GameSelectScreen(Main selectCard) {
		
		setLayout(null);
		
		
		
		
		

		
		
		
		
		
		
		
		JButton dino = new JButton(changeDIcon);
		dino.setBorderPainted(false);//외곽선 제거
		dino.setContentAreaFilled(false);//내용영역 채우기 안 함
		dino.setFocusPainted(false);//선택됐을 떄 생기는 테두리 사용 안 함
		dino.setBounds(510, 130, 260,70); //버튼의 배치와 크기 설정
		dino.setRolloverIcon(changeDPIcon);
		add(dino);
		
		JButton rhythm = new JButton(changeRIcon);
		rhythm.setBorderPainted(false);//외곽선 제거
		rhythm.setContentAreaFilled(false);//내용영역 채우기 안 함
		rhythm.setFocusPainted(false);//선택됐을 떄 생기는 테두리 사용 안 함
		rhythm.setBounds(510, 260, 260,70); //버튼의 배치와 크기 설정
		rhythm.setRolloverIcon(changeRPIcon);
		add(rhythm);
		
		JButton chess = new JButton(changeCIcon);
		chess.setBorderPainted(false);//외곽선 제거
		chess.setContentAreaFilled(false);//내용영역 채우기 안 함
		chess.setFocusPainted(false);//선택됐을 떄 생기는 테두리 사용 안 함
		chess.setBounds(510, 390, 260,70); //버튼의 배치와 크기 설정
		chess.setRolloverIcon(changeCPIcon);
		add(chess);
		
		JButton back = new JButton(img3);
		back.setBorderPainted(false);//외곽선 제거
		back.setContentAreaFilled(false);//내용영역 채우기 안 함
		back.setFocusPainted(false);//선택됐을 떄 생기는 테두리 사용 안 함
		back.setBounds(540, 500, 260,70); //버튼의 배치와 크기 설정
		back.setRolloverIcon(Pimg3);
		add(back);
		
		dino.addActionListener(new ActionListener() { //스타트 버튼 눌렀을 때 
            @Override
            public void actionPerformed(ActionEvent e) {
            	selectCard.showChrome();
            	new Music("buttonPressedMusic.mp3", false).start();
            }
        });
		
		rhythm.addActionListener(new ActionListener() { //스타트 버튼 눌렀을 때 
            @Override
            public void actionPerformed(ActionEvent e) {
            	selectCard.showRhythmGame();
            	new Music("buttonPressedMusic.mp3", false).start();
            }
        });
		
		chess.addActionListener(new ActionListener() { //스타트 버튼 눌렀을 때 
            @Override
            public void actionPerformed(ActionEvent e) {
            	selectCard.showChess();
            	new Music("buttonPressedMusic.mp3", false).start();
            }
        });
		
		back.addActionListener(new ActionListener() { //스타트 버튼 눌렀을 때 
            @Override
            public void actionPerformed(ActionEvent e) {
            	selectCard.showStartScreen();
            	new Music("buttonPressedMusic.mp3", false).start();
            }
        });
		
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		}
}
