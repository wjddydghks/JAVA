package dinoTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicScrollBarUI;

class Piece{
		String name;
		Image img;
		Piece(String name, String image) {
			this.name=  name;
			ImageIcon icon = new ImageIcon(image);
			this.img = icon.getImage();
			if (icon.getIconWidth() == -1) {
			    System.out.println("Image failed to load: " + image);
			}
		}
	}
public class ChessPanel extends JPanel implements MouseListener, KeyListener{
	Music sound = new Music("chess_sound.mp3", false);
	String selectedKey = null; //현재 선택된 위치를 담는 변수
    String leftWP = null;
    String rightWP = null;
    String leftBP = null;
    String rightBP = null;
    String fowardBP = null;
    String dFowardBP = null;
    String fowardWP = null;
    String dFowardWP = null;
    boolean running = true;
    boolean turn = true; //true = white turn,     false = black turn
    boolean whiteKingMove = false;
    boolean blackKingMove = false;
    boolean blackRookLMove = false;
    boolean blackRookRMove = false;
    boolean whiteRookLMove = false;
    boolean whiteRookRMove = false;
    boolean whitePromotion = false;
    boolean blackPromotion = false;
    boolean enPassant = false;
    String beforeEnPassant;
    String lastDoubleStepPawnKey = null;
    String lastDoubleMovedPawnKey = null; // 마지막 2칸 이동한 폰의 위치
    String enPassantCaptureSquare = null;   // 도착한 위치
    boolean enPassantAvailable = false;   // 앙파상 가능 여부
    int x;
    int y ;
    int indexX;
    int indexY;
    String key; //a1...a2.. 클릭한 위치
    String piece;   //클릭한 위치의 기물 이름
	private Image bg = new ImageIcon("Images/rhythm_background.png").getImage(); // 전체 배경 이미지
	private int blockSize = 60;
	private int boardSize = blockSize*8;
	private String [][] Index = new String[8][8];
	private HashMap<String, String> boardSet = new HashMap<String, String>();
	private JPanel LeftPanel = new JPanel(new BorderLayout());
	private JPanel boardPanel;
	private JPanel rightPanel = new JPanel(new BorderLayout());
	private JPanel resetPanel = new JPanel(new BorderLayout());
		private ImageIcon resetImg = new ImageIcon("Images/reset.png");
		private ImageIcon resetPImg = new ImageIcon("Images/reset_push.png");	
		private JButton resetButton = new JButton(resetImg);
	private JPanel notationPanel = new JPanel(new BorderLayout());	
		private JTextArea notation = new JTextArea();	
		private JScrollPane notationScroll = new JScrollPane(notation);
		private String notationText;
	private JPanel buttonPanel = new JPanel(null);
		private ImageIcon backImg = new ImageIcon("Images/back.png");
		private ImageIcon backPImg = new ImageIcon("Img/back_push.png");
		private JButton backButton = new JButton(backImg);
		
		
		
	private Image whitePawn = new ImageIcon("Images/white_Pawn.png").getImage();
	private Image blackPawn = new ImageIcon("Images/black_Pawn.png").getImage();
	
	private Piece [] whitePiece = {new Piece("whiteRookL", "Images/white_Rook.png"), new Piece("whiteKnight", "Images/white_Knight.png")
						  ,new Piece("whiteBishop", "Images/white_Bishop.png"), new Piece("whiteQueen", "Images/white_Queen.png"), new Piece("whiteKing", "Images/white_King.png")
						  ,new Piece("whiteBishop", "Images/white_Bishop.png"), new Piece("whiteKnight", "Images/white_Knight.png"), new Piece("whiteRookR", "Images/white_Rook.png")};
	private Piece [] blackPiece = {new Piece("blackRookL", "Images/black_Rook.png"), new Piece("blackKnight", "Images/black_Knight.png")
			  			  ,new Piece("blackBishop", "Images/black_Bishop.png"), new Piece("blackQueen", "Images/black_Queen.png"), new Piece("blackKing", "Images/black_King.png")
			  			  ,new Piece("blackBishop", "Images/black_Bishop.png"), new Piece("blackKnight", "Images/black_Knight.png"), new Piece("blackRookR", "Images/black_Rook.png")};
	public ChessPanel(Main chessCard) {
        setLayout(new BorderLayout());
        setFocusable(true);
        addKeyListener(this);
        try {
            Font pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/Minecraftia.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixelFont);
            notation.setFont(pixelFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boardPanel = new JPanel() {
        	private ImageIcon board = new ImageIcon("Images/board.png");
    		private Image chessBoard = board.getImage();
    		public void paintComponent(Graphics g) {
    		    super.paintComponent(g);
    		    g.drawImage(chessBoard, 0, 0, boardSize, boardSize, this);
    		    for (int i = 0; i < 8; i++) {
    		        for (int j = 0; j < 8; j++) {
    		            String pieceName = boardSet.get(Index[i][j]); 
    		            Image img = null;
    		            if (pieceName != null) {
    		                for (Piece p : whitePiece) {
    		                    if (p.name.equals(pieceName)) img = p.img;
    		                }
    		                for (Piece p : blackPiece) {
    		                    if (p.name.equals(pieceName)) img = p.img;
    		                }
    		                if (pieceName.equals("whitePawn")) img = whitePawn;
    		                if (pieceName.equals("blackPawn")) img = blackPawn;
    		            }
    		            if (img != null) {
    		                g.drawImage(img, i * blockSize, j * blockSize, blockSize, blockSize, this);
    		            }
    		        }
    		    }
    		}
        };
        boardPanel.addMouseListener(this);
        LeftPanel.add(boardPanel, BorderLayout.CENTER);
        resetPanel.setLayout(null);
        resetPanel.setOpaque(false);
        resetPanel.setPreferredSize(new Dimension(480, 85));  // 충분한 높이 확보
        resetButton.setBorderPainted(false);//외곽선 제거
        resetButton.setContentAreaFilled(false);//내용영역 채우기 안 함
        resetButton.setFocusPainted(false);//선택됐을 떄 생기는 테두리 사용 안 함
        resetButton.setBounds(-45,5,300,100);
        resetButton.setRolloverIcon(resetPImg);
        resetPanel.add(resetButton);
        resetPanel.setOpaque(false);
        LeftPanel.add(resetPanel, BorderLayout.SOUTH);
        LeftPanel.setOpaque(false);
        notation.setOpaque(false);
        notation.setLineWrap(true);
        notation.setWrapStyleWord(true);
        notation.setEditable(false); 
        notation.setForeground(Color.BLUE.darker());
        notation.append("       WHITE              BLACK \n" );
        notationScroll.setOpaque(false);
        notationScroll.getViewport().setOpaque(false);
        notationScroll.setBorder(null);
        notationScroll.getVerticalScrollBar().setOpaque(false);
        notationScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        notationScroll.setPreferredSize(new Dimension(200, 400));
        notationScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
        	@Override
            protected JButton createDecreaseButton(int orientation) {
                return createInvisibleButton();
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createInvisibleButton();
            }
            private JButton createInvisibleButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                return button;
            }
        	@Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(0, 0, 0, 0);   // thumb 완전 투명
                this.trackColor = new Color(0, 0, 0, 0);   // track 완전 투명
            }
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                // 그리지 않음 = 완전 투명 thumb
            }
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                // 그리지 않음 = 완전 투명 track
            }
        });
        notationPanel.setOpaque(false);
        notationPanel.setBorder(null);
        notationPanel.add(notationScroll);
        notationPanel.setPreferredSize(new Dimension(0, boardSize));
        rightPanel.add(notationPanel,BorderLayout.NORTH);
        buttonPanel.setLayout(null);
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(320, 120));  // 충분한 높이 확보
        backButton.setBorderPainted(false);//외곽선 제거
        backButton.setContentAreaFilled(false);//내용영역 채우기 안 함
        backButton.setFocusPainted(false);//선택됐을 떄 생기는 테두리 사용 안 함
        backButton.setBounds(50, 40,300,100);
        backButton.setRolloverIcon(backPImg);
        buttonPanel.add(backButton);
        rightPanel.setOpaque(false);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(LeftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        backButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new Music("buttonPressedMusic.mp3", false).start();
        		settingBoard();
        		chessCard.showGameSelectScreen();
        	}
        });
        resetButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new Music("buttonPressedMusic.mp3", false).start();
        		settingBoard();
        		repaint();
        		notation.setText("       WHITE              BLACK \n" );
        	}
        });
        settingBoard();
	}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // ChessPanel 전체 크기에 맞춰서 배경 이미지 그리기
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
    public void settingBoard() {
    	whiteKingMove = false;
        blackKingMove = false;
        blackRookLMove = false;
        blackRookRMove = false;
        whiteRookLMove = false;
        whiteRookRMove = false;
    	turn = true;
    	running = true;
    	for(int i =0; i<8; i++) {
			for(int j =0; j<8; j++) {
				Index[i][j] =(char)(i+97)+Integer.toString(8-j);
				if(j==0) {
					boardSet.put(Index[i][j],blackPiece[i].name);
					}
				else if(j==7)
				{
					boardSet.put(Index[i][j],whitePiece[i].name);
					}
				else if(j==1) 
				{
					boardSet.put(Index[i][j],"blackPawn");
				}
				else if(j==6)
				{
					boardSet.put(Index[i][j],"whitePawn");
				}
				else
					boardSet.put(Index[i][j],null);
			}
		}
    }
    public void mousePressed(MouseEvent e) {
    	sound.close();
    	if(running==true)
    	{
        x = e.getX();
        y = e.getY();
        indexX =  x / blockSize;
        indexY =  y / blockSize;
        if (indexX < 0 || indexX >= 8 || indexY < 0 || indexY >= 8) return;
        key = Index[indexX][indexY]; //a1...a2.. 클릭한 위치
        piece = boardSet.get(key);   //클릭한 위치의 기물 이름
        if (selectedKey == null) { //아무 기물도 선택하지 않았다면
            if (piece != null) 
            if ((turn&&piece.contains("white"))||(turn!=piece.contains("black")))
            { //빈 곳을 선택하지 않았다면 
                selectedKey = key; 
                if(indexX!=0&&0<indexY&&indexY<7) {
                	leftWP = Index[indexX-1][indexY-1];
                	leftBP = Index[indexX-1][indexY+1];
                }
                if(indexX!=7&&0<indexY&&indexY<7) {
                	rightWP = Index[indexX+1][indexY-1];
                    rightBP = Index[indexX+1][indexY+1];
                }
                if(0<indexY&&indexY<7)
                {
                	fowardBP = Index[indexX][indexY+1];
                	if(indexY==1) 
                        dFowardBP= Index[indexX][indexY+2];
                	
                	fowardWP = Index[indexX][indexY-1];
                	if(indexY==7) 
                	{
                		
                		dFowardWP= Index[indexX][indexY-2];
                	}
                }
            }
        } 
        else 
        {
        	boolean action= false;
        	if(selectedKey!=key)//같은 곳을 클릭하지 않았다면
        		if(piece==null) {
        			action = true;
        		}
        		else if(boardSet.get(selectedKey).contains("black")!=piece.contains("black")) {
        			action = true;
        		}
        		else if(boardSet.get(selectedKey).contains("whiteKing")&&boardSet.get(key).contains("whiteRookR")) {
        			boolean pieceCheck = false;
    				if((whiteKingMove==false)&&(whiteRookRMove==false)) {
    					for(int i = 5; i<7; i++)
        			if(boardSet.get(Index[i][7])!=null) {
        				pieceCheck = true;
        				break;
        			}
    					if(pieceCheck==false) {
    						boardSet.put(Index[6][7], boardSet.get(selectedKey));
    						boardSet.put(Index[5][7], boardSet.get(key));
                            boardSet.put(selectedKey, null);     
                            boardSet.put(key, null); 
                        	notation.append("         O-O      ");
                            notation.setCaretPosition(notation.getDocument().getLength());
                            Music sound = new Music("chess_sound.mp3", false);
                            sound.start();
                            if(turn)
                            	turn = false;
                            else
                            	turn = true;
                            whiteKingMove=true;
                            whiteRookRMove=true;
                            selectedKey= null;
    					}

        		}
        	}
        		else if(boardSet.get(selectedKey).contains("whiteKing")&&boardSet.get(key).contains("whiteRookL")) {
        			boolean pieceCheck = false;
    				if((whiteKingMove==false)&&(whiteRookLMove==false)) {
    					for(int i = 1; i<4; i++)
        			if(boardSet.get(Index[i][7])!=null) {
        				pieceCheck = true;
        				break;
        			}
    					if(pieceCheck==false) {
    						boardSet.put(Index[2][7], boardSet.get(selectedKey));
    						boardSet.put(Index[3][7], boardSet.get(key));
                            boardSet.put(selectedKey, null);     
                            boardSet.put(key, null);   
                            whiteKingMove=true;
                            whiteRookLMove=true;
                            notation.append("      O-O-O   ");
                            notation.setCaretPosition(notation.getDocument().getLength());
                            Music sound = new Music("chess_sound.mp3", false);
                            sound.start();
                            if(turn)
                            	turn = false;
                            else
                            	turn = true;
                            selectedKey= null;
    					}
        		}
    		}
        		else if(boardSet.get(selectedKey).contains("blackKing")&&boardSet.get(key).contains("blackRookR")) {
        			boolean pieceCheck = false;
    				if((blackKingMove==false)&&(blackRookRMove==false)) {
    					for(int i = 5; i<7; i++)
        			if(boardSet.get(Index[i][0])!=null) {
        				pieceCheck = true;
        				break;
        			}
    					if(pieceCheck==false) {
    						boardSet.put(Index[6][0], boardSet.get(selectedKey));
    						boardSet.put(Index[5][0], boardSet.get(key));
                            boardSet.put(selectedKey, null);     
                            boardSet.put(key, null); 
                            blackKingMove=true;
                            blackRookRMove=true;
                            notation.append("             O-O \n");
                            notation.setCaretPosition(notation.getDocument().getLength());
                            Music sound = new Music("chess_sound.mp3", false);
                            sound.start();
                            if(turn)
                            	turn = false;
                            else
                            	turn = true;
                            selectedKey= null;
    					}
        		}
        	}
        		else if(boardSet.get(selectedKey).contains("blackKing")&&boardSet.get(key).contains("blackRookL")) {
        			boolean pieceCheck = false;
    				if((blackKingMove==false)&&(blackRookLMove==false)) {
    					for(int i = 1; i<4; i++)
        			if(boardSet.get(Index[i][0])!=null) {
        				pieceCheck = true;
        				break;
        			}
    					if(pieceCheck==false) {
    						boardSet.put(Index[2][0], boardSet.get(selectedKey));
    						boardSet.put(Index[3][0], boardSet.get(key));
                            boardSet.put(selectedKey, null);     
                            boardSet.put(key, null);   
                            blackKingMove=true;
                            blackRookLMove=true;
                            notation.append("            O-O-O \n");
                            notation.setCaretPosition(notation.getDocument().getLength());
                            Music sound = new Music("chess_sound.mp3", false);
                            sound.start();
                            if(turn)
                            	turn = false;
                            else
                            	turn = true;
                            selectedKey= null;
    					}
        		}
    		}
        		else selectedKey= key;
        	if(action==true)
        		
        	{ 
        	char keyFirstChar= key.charAt(0);
        	char keyLastChar= key.charAt(key.length()-1);
        	char selectedKeyFirstChar= selectedKey.charAt(0);
        	char selectedKeyLastChar= selectedKey.charAt(selectedKey.length()-1);
           //폰 이동
        	
        	
        	// mousePressed 혹은 폰 이동 관련 메서드 내 폰 이동 + 앙파상 처리 부분

        	 if(boardSet.get(selectedKey).contains("whitePawn")) 
             {
    			 if (enPassantAvailable && key.equals(enPassantCaptureSquare) &&
    				        (key.equals(leftWP) || key.equals(rightWP))) {
    				        
    				        // 앙파상 캡처 처리
    				        boardSet.put(key, boardSet.get(selectedKey));        // 폰 이동
    				        boardSet.put(selectedKey, null);                     // 원래 위치 비우기
    				        boardSet.put(lastDoubleMovedPawnKey, null);          // 상대 폰 삭제 (앙파상)
    				        
    				        notation.append("     " + selectedKey + " to " + key);
    				        
    				        if(keyLastChar=='8') {
    	               			whitePromotion = true;
    	               			running = false;
    	               			notationText = notation.getText();
    	               			notation.setText("               Promotion!!! \n     Queen[1]     Rook[2] \n    Bishop[3]    Knight[4]");
    	               		}
    				        
    				        turn = false;
    				        selectedKey = null;
    				        repaint();
    				        enPassantAvailable = false;  // 초기화
    				        Music sound = new Music("chess_sound.mp3", false);
    		                sound.start();
    				        return;
    				    }
    			 
             	if(key == leftWP&& boardSet.get(leftWP)!=null)
             		System.out.println(); //왼쪽 위에 기물이 있으면 잡기
             	
             	else if (key == rightWP && boardSet.get(rightWP)!=null)
             		System.out.println();//오른쪽 위에 기물이 있으면 잡기
        	
        	else {
        			if(keyFirstChar!=selectedKeyFirstChar) //옆으로 x
        				{
        					selectedKey= null;
        					return;
        					}
        				if(keyLastChar<selectedKeyLastChar)// 후진 x
        					{
        						selectedKey= null;
        						return;
        					}
        					
        					if(selectedKeyLastChar!='2') { //처음 위치가 아니면 한 칸만 가기
        						if(keyLastChar>selectedKeyLastChar+1)
        							{selectedKey= null;
        							return;
        							}
        						}
        					else {
        						
        							if(keyLastChar>selectedKeyLastChar+2)
        							{
        								selectedKey= null;
        								return;
        							}
        					}
        					if(key == fowardBP &&  boardSet.get(fowardBP)!=null) {
        						selectedKey= null;
								return;
        					}
        					if(key == dFowardBP &&  (boardSet.get(dFowardBP)!=null ||boardSet.get(fowardBP)!=null)) {
        						selectedKey= null;
								return;
        					}
        		}
             
             	
             	 // --- 폰 실제 이동 반영 코드 ---
                boardSet.put(key, boardSet.get(selectedKey));
                boardSet.put(selectedKey, null);
                
                Music sound = new Music("chess_sound.mp3", false);
                sound.start();
                // **폰 두 칸 전진했을 때 앙파상 세팅 코드**
                if(selectedKey.charAt(1) == '2' && key.charAt(1) == '4') {
                    lastDoubleMovedPawnKey = key;                             // 두 칸 이동한 위치
                    enPassantCaptureSquare = "" + key.charAt(0) + '3';       // 지나간 칸 (3번째 행)
                    enPassantAvailable = true;
                } else {
                    enPassantAvailable = false;
                }
                if(piece!=null) {
                	if(piece.contains("blackKing")){
                		notation.append("     " + selectedKey + " to " + key);
            			notation.append("\n            White Victory!!!");
            			notation.setCaretPosition(notation.getDocument().getLength());
            			running=false;
            		}
            		else if(piece.contains("whiteKing")){
            			notation.append("     " + selectedKey + " to " + key);
            			notation.append("\n            Black Victory!!!");
            			notation.setCaretPosition(notation.getDocument().getLength());
            			running=false;
            		}	
            		else notation.append("     " + selectedKey + " to " + key);
                }
                else notation.append("     " + selectedKey + " to " + key);
            		
                if(keyLastChar=='8') {
           			whitePromotion = true;
           			running = false;
           			notationText = notation.getText();
           			notation.setText("               Promotion!!! \n     Queen[1]     Rook[2] \n    Bishop[3]    Knight[4]");
           		}
                	
                		
                turn = false;
                
                selectedKey = null;
                repaint();
                
                return;
        }
        
       
        	
        	if(boardSet.get(selectedKey).contains("blackPawn")) 
            {
        		if (enPassantAvailable && key.equals(enPassantCaptureSquare) &&
        		        (key.equals(leftBP) || key.equals(rightBP))) {
        		        
        		        // 앙파상 캡처 처리
        		        boardSet.put(key, boardSet.get(selectedKey));        // 폰 이동
        		        boardSet.put(selectedKey, null);                     // 원래 위치 비우기
        		        boardSet.put(lastDoubleMovedPawnKey, null);          // 상대 폰 삭제 (앙파상)
        		        
        		        notation.append("         "+ selectedKey + " to " + key + "\n");
        		        
        		        if(keyLastChar=='1') {
                   			blackPromotion = true;
                   			running = false;
                   			notationText = notation.getText();
                   			notation.setText("               Promotion!!! \n     Queen[1]     Rook[2] \n    Bishop[3]    Knight[4]");
                   		}
        		        
        		        turn = true;
        		        selectedKey = null;
        		        repaint();
        		        enPassantAvailable = false;  // 초기화
        		        Music sound = new Music("chess_sound.mp3", false);
                        sound.start();
        		        return;
        		    }
        		
            	if(key == leftBP&& boardSet.get(leftBP)!=null)
            		System.out.println(); //왼쪽 위에 기물이 있으면 잡기
            	
            	else if (key == rightBP && boardSet.get(rightBP)!=null)
            		System.out.println();//오른쪽 위에 기물이 있으면 잡기
        	else {
        			if(keyFirstChar!=selectedKeyFirstChar) //옆으로 x
        				{
        					selectedKey= null;
        					return;
        					}
        				if(keyLastChar>selectedKeyLastChar)// 후진 x
        					{
        						selectedKey= null;
        						return;
        					}
        					
        					if(selectedKeyLastChar!='7') { //처음 위치가 아니면 한 칸만 가기
        						if(keyLastChar<selectedKeyLastChar-1)
        							{selectedKey= null;
        							return;
        							}
        						}
        					else {
        						
        							if(keyLastChar<selectedKeyLastChar-2)
        							{
        								selectedKey= null;
        								return;
        							}
        					}
        					if(key == fowardWP &&  boardSet.get(fowardWP)!=null) {
        						selectedKey= null;
								return;
        					}
        					if(key == dFowardWP &&  (boardSet.get(dFowardWP)!=null ||boardSet.get(fowardWP)!=null)) {
        						selectedKey= null;
								return;
        					}
        		}
            	boardSet.put(key, boardSet.get(selectedKey));
                boardSet.put(selectedKey, null);
                Music sound = new Music("chess_sound.mp3", false);
                sound.start();
                // **폰 두 칸 전진했을 때 앙파상 세팅 코드**
                if(selectedKey.charAt(1) == '7' && key.charAt(1) == '5') {
                    lastDoubleMovedPawnKey = key;                             // 두 칸 이동한 위치
                    enPassantCaptureSquare = "" + key.charAt(0) + '6';       // 지나간 칸 (6번째 행)
                    enPassantAvailable = true;
                } else {
                    enPassantAvailable = false;
                }
               
                if(piece!=null) {
                	if(piece.contains("blackKing")){
                		notation.append("         "+ selectedKey + " to " + key + "\n");
            			notation.append("\n            White Victory!!!");
            			notation.setCaretPosition(notation.getDocument().getLength());
            			running=false;
            		}
            		else if(piece.contains("whiteKing")){
            			notation.append("         "+ selectedKey + " to " + key + "\n");
            			notation.append("\n            Black Victory!!!");
            			notation.setCaretPosition(notation.getDocument().getLength());
            			running=false;
            		}	
            		else notation.append("         "+ selectedKey + " to " + key + "\n");
                }
                else notation.append("         "+ selectedKey + " to " + key + "\n");
                
                
                if(keyLastChar=='1') {
           			blackPromotion = true;
           			running = false;
           			notationText = notation.getText();
           			notation.setText("               Promotion!!! \n     Queen[1]     Rook[2] \n    Bishop[3]    Knight[4]");
           		}
                
                turn = true;
                selectedKey = null;
                repaint();
                
                return;
            	
        }
            if(boardSet.get(selectedKey).contains("King")) {
            	
            	if(keyLastChar<selectedKeyLastChar-1) {
            		selectedKey= null;
					return;
            	}
            	if(keyLastChar>selectedKeyLastChar+1) {
            		selectedKey= null;
					return;
            	}
            	if(keyFirstChar<selectedKeyFirstChar-1) {
            		selectedKey= null;
					return;
            	}
            	if(keyFirstChar>selectedKeyFirstChar+1) {
            		selectedKey= null;
					return;
            	}
            	if(boardSet.get(selectedKey).contains("white"))
            	{
            		whiteKingMove = true;
            	}
            	else
            	{
            		blackKingMove = true;
            	}	
            } 
            if(boardSet.get(selectedKey).contains("Knight")) {
            	if(keyLastChar==selectedKeyLastChar-2) {
            		if((keyFirstChar!=selectedKeyFirstChar-1)&&(keyFirstChar!=selectedKeyFirstChar+1))
            		{
            		selectedKey= null;
					return;
            		}
            	}
            	else if(keyLastChar==selectedKeyLastChar+2) {
            		if((keyFirstChar!=selectedKeyFirstChar-1)&&(keyFirstChar!=selectedKeyFirstChar+1))
            		{
            		selectedKey= null;
					return;
            		}
            	}
            	else if(keyFirstChar==selectedKeyFirstChar-2) {
            		if((keyLastChar!=selectedKeyLastChar-1)&&(keyLastChar!=selectedKeyLastChar+1))
            		{
            		selectedKey= null;
					return;
            		}
            	}
            	else if(keyFirstChar==selectedKeyFirstChar+2) {
            		if((keyLastChar!=selectedKeyLastChar-1)&&(keyLastChar!=selectedKeyLastChar+1))
            		{
            		selectedKey= null;
					return;
            		}
            	}
            	else
            	{
            		selectedKey= null;
					return;
            	}
            }
            	if(boardSet.get(selectedKey).contains("Bishop")){
            		if(Math.abs(keyFirstChar-selectedKeyFirstChar)!=Math.abs(keyLastChar-selectedKeyLastChar)) {
            			selectedKey= null;
    					return;
            		}
            		if((keyFirstChar-selectedKeyFirstChar)*(keyLastChar-selectedKeyLastChar)>0) 
            		{
            			if(keyLastChar<selectedKeyLastChar) {
            				int skF=selectedKeyFirstChar-97;
            				int skL = selectedKeyLastChar - '0' ;
            				while(skL>keyLastChar-'0'+1)
            				{
            					skF--;
            					skL--;
            					if(boardSet.get(Index[skF][8-skL])!=null)
            				        {
            				           selectedKey= null;
            				           return;
            				        }
            				}
            			}
            			if(keyLastChar>selectedKeyLastChar) {
            				int skF=selectedKeyFirstChar-97;
            				int skL = selectedKeyLastChar-'0';
            				while(skL<keyLastChar-'0'-1)
            				{
            					skF++;
            					skL++;
            					if(boardSet.get(Index[skF][8-skL])!=null)
            					{
            						selectedKey= null;
                					return;
            					}
            				}
            			}
            		}
            		else if(keyLastChar<selectedKeyLastChar) {
            			int skF = selectedKeyFirstChar-97;
            			int skL = selectedKeyLastChar-'0';
        				while(skL>keyLastChar-'0'+1)
        				{
        					skF++;
        					skL--;
        					if(boardSet.get(Index[skF][8-skL])!=null)
        					{
        						
        						selectedKey= null;
            					return;
        					}
        				}
        			}
            		else if(keyLastChar>selectedKeyLastChar) {
            			int skF = selectedKeyFirstChar-97;
            			int skL = selectedKeyLastChar-'0';
        				while(skL<keyLastChar-'0'-1)
        				{
        					skF--;
        					skL++;
        					if(boardSet.get(Index[skF][8-skL])!=null)
        					{
        						selectedKey= null;
            					return;
        					}
        				}
        			}
            		}	
            	if(boardSet.get(selectedKey).contains("Rook")) {
            		if((keyFirstChar==selectedKeyFirstChar)==(keyLastChar==selectedKeyLastChar))
            		{
            			selectedKey= null;
    					return;
            		}
            		if(keyFirstChar==selectedKeyFirstChar)
            			if(selectedKeyLastChar<keyLastChar)
            			for(int i = selectedKeyLastChar-'0'+1; i<keyLastChar-'0'; i++)
            			{
            				
            				if(boardSet.get(Index[indexX][8-i])!=null)
            				{System.out.println(i);
            					selectedKey= null;
            					return;
            				}
            			}
            			else {
            				for(int i = selectedKeyLastChar-'0'-1; i>keyLastChar-'0'; i--)
                			{
                				System.out.println(i);
                				if(boardSet.get(Index[indexX][8-i])!=null)
                				{
                					selectedKey= null;
                					return;
                				}
                			}
            			}
            		if(keyLastChar==selectedKeyLastChar)
            			if(selectedKeyFirstChar<keyFirstChar)
            			for(int i = selectedKeyFirstChar-97+1; i<keyFirstChar-97; i++)
            			{
            				
            				if(boardSet.get(Index[i][indexY])!=null)
            				{
            					selectedKey= null;
            					return;
            				}
            			}
            			else {
            				for(int i = selectedKeyFirstChar-97-1; i>keyFirstChar-97; i--)
                			{
                				
                				if(boardSet.get(Index[i][indexY])!=null)
                				{
                					selectedKey= null;
                					return;
                				}
                			}
            			}
            		if(boardSet.get(selectedKey).contains("whiteRookL"))
                		whiteRookLMove = true;
                	else if(boardSet.get(selectedKey).contains("whiteRookR"))
                    		whiteRookRMove = true;
                	else if(boardSet.get(selectedKey).contains("blackRookL"))
                		blackRookLMove = true;
                	else 
                		whiteRookRMove = true;
            	}
            	if(boardSet.get(selectedKey).contains("Queen")) {
            		int rookCount=0;
            		if((keyFirstChar==selectedKeyFirstChar)&&(keyLastChar!=selectedKeyLastChar)) 
            		{
            			rookCount++;
            			if(selectedKeyLastChar<keyLastChar)
                			for(int i = selectedKeyLastChar-'0'+1; i<keyLastChar-'0'; i++)
                			{
                				if(boardSet.get(Index[indexX][8-i])!=null)
                				{System.out.println(i);
                					selectedKey= null;
                					return;
                				}
                			}
                			else {
                				for(int i = selectedKeyLastChar-'0'-1; i>keyLastChar-'0'; i--)
                    			{
                    				System.out.println(i);
                    				if(boardSet.get(Index[indexX][8-i])!=null)
                    				{
                    					selectedKey= null;
                    					return;
                    				}
                    			}
                			}
            		}
            		if((keyLastChar==selectedKeyLastChar)&&(keyFirstChar!=selectedKeyFirstChar)) 
            		{
            			rookCount++;
            			if(selectedKeyFirstChar<keyFirstChar)
                			for(int i = selectedKeyFirstChar-97+1; i<keyFirstChar-97; i++)
                			{
                				if(boardSet.get(Index[i][indexY])!=null)
                				{
                					selectedKey= null;
                					return;
                				}
                			}
                			else {
                				for(int i = selectedKeyFirstChar-97-1; i>keyFirstChar-97; i--)
                    			{
                    				if(boardSet.get(Index[i][indexY])!=null)
                    				{
                    					selectedKey= null;
                    					return;
                    				}
                    			}
                			}
            		}
            			if(rookCount>0) {
            				if((keyFirstChar==selectedKeyFirstChar)==(keyLastChar==selectedKeyLastChar))
                    		{
                    			
                    			selectedKey= null;
            					return;
                    		}
            			} else {
            				if(Math.abs(keyFirstChar-selectedKeyFirstChar)!=Math.abs(keyLastChar-selectedKeyLastChar)) {
                    			selectedKey= null;
            					return;
                    		}
                    		if((keyFirstChar-selectedKeyFirstChar)*(keyLastChar-selectedKeyLastChar)>0) 
                    		{
                    			if(keyLastChar<selectedKeyLastChar) {
                    				int skF=selectedKeyFirstChar-97;
                    				int skL = selectedKeyLastChar - '0' ;
                    				while(skL>keyLastChar-'0'+1)
                    				{
                    					skF--;
                    					skL--;
                    					if(boardSet.get(Index[skF][8-skL])!=null)
                    				        {
                    				           selectedKey= null;
                    				           return;
                    				        }
                    				}
                    			}
                    			if(keyLastChar>selectedKeyLastChar) {
                    				int skF=selectedKeyFirstChar-97;
                    				int skL = selectedKeyLastChar-'0';
                    				while(skL<keyLastChar-'0'-1)
                    				{
                    					skF++;
                    					skL++;
                    					if(boardSet.get(Index[skF][8-skL])!=null)
                    					{
                    						selectedKey= null;
                        					return;
                    					}
                    				}
                    			}
                    		}
                    		else if(keyLastChar<selectedKeyLastChar) {
                    			int skF = selectedKeyFirstChar-97;
                    			int skL = selectedKeyLastChar-'0';
                				while(skL>keyLastChar-'0'+1)
                				{
                					skF++;
                					skL--;
                					if(boardSet.get(Index[skF][8-skL])!=null)
                					{
                						selectedKey= null;
                    					return;
                					}
                				}
                			}
                    		else if(keyLastChar>selectedKeyLastChar) {
                    			int skF = selectedKeyFirstChar-97;
                    			int skL = selectedKeyLastChar-'0';
                				while(skL<keyLastChar-'0'-1)
                				{
                					skF--;
                					skL++;
                					if(boardSet.get(Index[skF][8-skL])!=null)
                					{
                						selectedKey= null;
                    					return;
                					}
                				}
                			}
            			}
            	}
            	
            	
            	
            	if(turn)
            		notation.append("     " + selectedKey + " to " + key);
            	else 
            		notation.append("         "+ selectedKey + " to " + key + "\n");
            	notation.setCaretPosition(notation.getDocument().getLength());
            	boardSet.put(key, boardSet.get(selectedKey)); // 목적지에 기물 놓기
            	
                if(piece!=null)
            		if(piece.contains("blackKing")){
            			notation.append("\n            White Victory!!!");
            			notation.setCaretPosition(notation.getDocument().getLength());
            			running=false;
            		}
            		else if(piece.contains("whiteKing")){
            			notation.append("\n            Black Victory!!!");
            			notation.setCaretPosition(notation.getDocument().getLength());
            			running=false;
            		}
                if(turn)
                	turn = false;
                else
                	turn = true;
       
                boardSet.put(selectedKey, null);              // 원래 자리 비우기
            	selectedKey = null;
            	Music sound = new Music("chess_sound.mp3", false);
                sound.start();
        	}
            repaint(); // 화면 갱신
            
        	
        }
    }
}
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_1){
        	if(whitePromotion) {
        		boardSet.put(key, "whiteQueen");
        		whitePromotion = false;
        	}
        		
        	else if(blackPromotion)
        	{
        		boardSet.put(key, "blackQueen");
        		blackPromotion = false;
        	}
            	
            	running = true;
            	notation.setText(notationText);
            	repaint();

        }
        
        if(e.getKeyCode() == KeyEvent.VK_2){
        	if(whitePromotion) {
        		boardSet.put(key, "whiteRookL");
        		whitePromotion = false;
        	}
        		
        	else if(blackPromotion)
        	{
        		boardSet.put(key, "blackRookL");
        		blackPromotion = false;
        	}
            	
            	running = true;
            	notation.setText(notationText);
            	repaint();

        }
        if(e.getKeyCode() == KeyEvent.VK_3){
        	if(whitePromotion) {
        		boardSet.put(key, "whiteBishop");
        		whitePromotion = false;
        	}
        		
        	else if(blackPromotion)
        	{
        		boardSet.put(key, "blackBishop");
        		blackPromotion = false;
        	}
            	
            	running = true;
            	notation.setText(notationText);
            	repaint();

        }
        if(e.getKeyCode() == KeyEvent.VK_4){
        	if(whitePromotion) {
        		boardSet.put(key, "whiteKnight");
        		whitePromotion = false;
        	}
        		
        	else if(blackPromotion)
        	{
        		boardSet.put(key, "blackKnight");
        		blackPromotion = false;
        	}
            	
            	running = true;
            	notation.setText(notationText);
            	repaint();

        }
    }
    	
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();
    }

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
