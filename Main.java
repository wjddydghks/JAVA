package dinoTest;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends JFrame{
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	public static final int NOTE_SPEED = 5;
	public static final int SLEEP_TIME = 10;
	public static final int REACH_TIME = 2;
	
	CardLayout card = new CardLayout();
	JPanel pane = new JPanel(card);
	private Chrome chromePanel = new Chrome(this);
	private RhythmGame rhythm = new RhythmGame(this);
	
	public Main(){
		setTitle("Mini Game Packtory"); // 게임 제목
		setSize(800,600);	// 게임 화면 크기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 프레임 종료시 메모리에서도 제거
		setResizable(false);// 창 크기 변경 불가능
		setLocationRelativeTo(null);// 프레임 중앙에 생성
		
		
		
		
		pane.add(new StartScreen(this), "StartScreen");
		pane.add(new GameSelectScreen(this), "GameSelectScreen");
		 pane.add(chromePanel, "Chrome");
		pane.add(rhythm, "RhythmGame");
		 pane.add(new ChessPanel(this), "Chess");
		
		
		add(pane);
		setVisible(true);

	}

	public void showStartScreen() {
		card.show(pane, "StartScreen");
	}
	
	public void showGameSelectScreen() {
		card.show(pane, "GameSelectScreen");
	}
	
	public void showChrome() {
		card.show(pane, "Chrome");
		chromePanel.dinoMusic = new Music("running_dino_bgm.mp3", true);
        chromePanel.resetGame();
	}
	
	public void showRhythmGame() {
		
		card.show(pane, "RhythmGame");
		rhythm.introMusic.start();
	}
	
	public void showChess() {
		card.show(pane, "Chess");
	}
	
	
	public static void main(String[] args) {
		
		new Main();
		
        
        

	}
	
	
}
