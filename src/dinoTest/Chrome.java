package dinoTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Chrome extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{ //게임창 화면 
	
	
	
    int boardWidth = 800;
    int boardHeight = 600;
 
    // 버튼 이미지 크기 가져오기
    int replayWidth = 400;
    int replayHeight = 350;
    int menuWidth = 400;
    int menuHeight = 350;

    // 버튼 클릭 판정 여유 공간
    int clickPadding = 40;

    //소스 이미지
    Image backgroundImg;
    Image dinosaurImg;
    Image dinosaurDeadImg;
    Image dinosaurJumpImg;
    Image jumpCatImg;
    Image jumpAImg;
    Image jumpBImg;
    Image menuImg; 
    Image menuPushImg;
    Image replayImg;
    Image replayPushImg;
    
    Music dinoMusic = new Music("running_dino_bgm.mp3", true);

    class Block {
        int x;
        int y;
        int width;
        int height;
        Image img;

        Block(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    //dinosaur
    int dinosaurWidth = 88;
    int dinosaurHeight = 94;
    int dinosaurX= 50;
    int dinosaurY= 467;

    Block dinosaur;

    //장애물
    int cactus1Width = 50;
    int cactus2Width = 50;
    int cactus3Width = 50;

    int cactusHeight = 60;
    int cactusX = 600;
    int cactusY = 467;
    ArrayList<Block> cactusArray;

    int velocityX = -12;
    int velocityY = 0; //dinosaur jump speed
    int gravity = 1;

    //game over
    boolean gameOver = false;
    int score = 0;

    //replay, menu 버튼
    // 화면 중앙 좌표
    int centerX = boardWidth / 2;
    int centerY = boardHeight / 2;
    int buttonSpacing = 40; // 버튼 사이 여백

    // 버튼 위치 계산
    int replayX = centerX - replayWidth - (buttonSpacing / 2);
    int menuX = centerX + (buttonSpacing / 2);
    int buttonY = centerY - (replayHeight / 2);

    Timer gameLoop;
    Timer placeCactusTimer;
    
    Main chromeCard;

    public Chrome(Main chromeCard) {
    	
    	this.chromeCard = chromeCard;
    	
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.LIGHT_GRAY);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        backgroundImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/running_background.png"))).getImage();
        dinosaurImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/dino-run.gif"))).getImage();
        dinosaurDeadImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/dino-dead.png"))).getImage();
        dinosaurJumpImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/dino-jump.png"))).getImage();
        jumpCatImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/cat.png"))).getImage();
        jumpAImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/jump_a.png"))).getImage();
        jumpBImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/jump_b.png"))).getImage();
        replayImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/replay.png"))).getImage();
        replayPushImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/replay.png"))).getImage();
        menuImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/menu.png"))).getImage();
        menuPushImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/menu.png"))).getImage();

        

        
        
        //dinosaur
        dinosaur = new Block(dinosaurX,dinosaurY,dinosaurWidth,dinosaurHeight, dinosaurImg);


        //cactus
        cactusArray = new ArrayList<Block>();

        //game loop
        gameLoop = new Timer(1000/60, this);
       
      

        //place cactus timer
        placeCactusTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeCactus();
            }
        });
        
        
        


    }
    
    

    void placeCactus(){
        if(gameOver){
            return;
        }

        double placeCactusChance = Math.random();
        if (placeCactusChance> .70){
            Block cactus = new Block(cactusX, cactusY, cactus3Width, cactusHeight, jumpBImg);
            cactusArray.add(cactus);
        }
        else if (placeCactusChance > .50){
            Block cactus = new Block (cactusX, cactusY, cactus2Width, cactusHeight, jumpAImg);
            cactusArray.add(cactus);
        }
        else if (placeCactusChance > .20){
            Block cactus = new Block (cactusX, cactusY, cactus1Width, cactusHeight, jumpCatImg);
            cactusArray.add(cactus);
        }

    }
    
    public void startGame() {
        gameLoop.start();
        placeCactusTimer.start();
        requestFocusInWindow();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), null); // 배경 이미지 그리기
        draw(g);

    }

    public void draw(Graphics g) {
    	System.out.println("Dino x,y = " + dinosaur.x + ", " + dinosaur.y);
        g.drawImage(dinosaur.img,dinosaur.x,dinosaur.y,dinosaur.width,dinosaur.height, null);

        //cactus
        for(int i = 0; i < cactusArray.size(); i++){
            Block cactus = cactusArray.get(i);
            g.drawImage(cactus.img, cactus.x, cactus.y, cactus.width, cactus.height, null);
        }

        g.setColor(Color.PINK);
        g.setFont(new Font("Courier", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over : " + score, 10, 35);
    
            // 80% 투명도의 Light Gray 배경 추가
            g.setColor(new Color(211, 211, 211, 200)); // RGBA(211, 211, 211, 200)
            g.fillRect(0, 0, boardWidth, boardHeight); // 전체 화면에 적용
    
            
            // 버튼 그리기
            g.drawImage(replayImg, replayX, buttonY, replayWidth, replayHeight, null);
            g.drawImage(menuImg, menuX, buttonY, menuWidth, menuHeight, null);
        } else {
            g.drawString(String.valueOf(score), 10, 35);
        }
        
    }
    
    

    public void move(){
        velocityY += gravity;
        dinosaur.y += velocityY;

        if (dinosaur.y > dinosaurY) {
            dinosaur.y = dinosaurY;
            velocityY = 0;
            dinosaur.img = dinosaurImg;
        }

        //cactus
        for(int i= 0; i < cactusArray.size(); i++){
            Block cactus = cactusArray.get(i);
            cactus.x += velocityX;

            if (collision(dinosaur, cactus)){
            	
                gameOver = true;
                dinosaur.img = dinosaurDeadImg;
            }
        }

        //score
        score++;
    }

    boolean collision(Block a, Block b){
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e){
    	System.out.println("Timer tick, gameOver = " + gameOver + ", score = " + score + ", cactus count = " + cactusArray.size());
        move();
        repaint();
        if(gameOver){
            placeCactusTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("JUMP!");
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            if(dinosaur.y == dinosaurY){
                velocityY = -17;
                dinosaur.img = dinosaurJumpImg;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


        public void resetGame() {
            gameOver = false;
            score = 0;
            dinosaur.y = dinosaurY;  // 공룡을 초기 위치로 설정
            dinosaur.img = dinosaurImg;  // 공룡 이미지를 정상 상태로 설정
            cactusArray.clear();  // 장애물 목록 초기화
            requestFocusInWindow();
        
            // 타이머 다시 시작
            gameLoop.restart();  // restart()로 다시 시작하게 함
            placeCactusTimer.restart();  // 장애물 타이머 재시작
            repaint();  // 화면을 다시 그려줍니다
            dinoMusic.start();
        }
        
        
		
		


    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (gameOver) {
            // 🔹 리플레이 버튼 클릭 감지
            if (mouseX >= replayX && mouseX <= replayX + replayWidth &&
                mouseY >= buttonY && mouseY <= buttonY + replayHeight) {
            	new Music("buttonPressedMusic.mp3", false).start();
                resetGame();
            }
            // 🔹 메뉴 버튼 클릭 감지 (메인 페이지로 이동)
            
            if (mouseX >= menuX && mouseX <= menuX + menuWidth &&
                mouseY >= buttonY && mouseY <= buttonY + menuHeight) {
            	new Music("buttonPressedMusic.mp3", false).start();
            	chromeCard.showGameSelectScreen();
            	try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            	dinoMusic.close();
            }
        }

        

 }

    @Override
    public void mouseEntered(MouseEvent e) {
    	
    }
    @Override
    public void mouseExited(MouseEvent e) {
    	

    }


    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
       
    }



	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseMoved(MouseEvent e) {
	    if (!gameOver) return;

	    int mouseX = e.getX();
	    int mouseY = e.getY();


	    if (mouseX >= replayX && mouseX <= replayX + replayWidth &&
	        mouseY >= buttonY && mouseY <= buttonY + replayHeight) {
	    	replayImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/replay_push.png"))).getImage();
	    } else {
	    	replayImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/replay.png"))).getImage();
	    }


	    if (mouseX >= menuX && mouseX <= menuX + menuWidth &&
	        mouseY >= buttonY && mouseY <= buttonY + menuHeight) {
	    	menuImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/menu_push.png"))).getImage();
	    } else {
	    	menuImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/menu.png"))).getImage();
	    }

	    repaint();
	}
   
}
