package dinoTest;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;


public class RhythmGame extends JPanel {

    private Image screenImage;
    private Graphics screenGraphic;

    private ImageIcon startButtonImage = new ImageIcon(Main.class.getResource("/Img/startButton.png"));
    private ImageIcon exitButtonImage = new ImageIcon(Main.class.getResource("/Img/exitButton.png"));
    private ImageIcon leftButtonImage = new ImageIcon(Main.class.getResource("/Img/arrowLeft.png"));
    private ImageIcon rightButtonImage = new ImageIcon(Main.class.getResource("/Img/arrowRight.png"));
    private ImageIcon backButtonImage = new ImageIcon(Main.class.getResource("/Img/xbut.png"));
    private ImageIcon easyButtonImage = new ImageIcon(Main.class.getResource("/Img/easy.png"));
    private ImageIcon hardButtonImage = new ImageIcon(Main.class.getResource("/Img/hard.png"));

    private ImageIcon startButtonPushImage = new ImageIcon(Main.class.getResource("/Img/start_push.png"));
    private ImageIcon exitButtonPushImage = new ImageIcon(Main.class.getResource("/Img/exit_push.png"));
    private ImageIcon leftButtonPushImage = new ImageIcon(Main.class.getResource("/Img/arrowLeft_push.png"));
    private ImageIcon rightButtonPushImage = new ImageIcon(Main.class.getResource("/Img/arrowRight_push.png"));
    private ImageIcon easyButtonPushImage = new ImageIcon(Main.class.getResource("/Img/easy_push.png"));
    private ImageIcon hardButtonPushImage = new ImageIcon(Main.class.getResource("/Img/hard_push.png"));
    private ImageIcon backButtonPushImage = new ImageIcon(Main.class.getResource("/Img/xbut_push.png"));

    private Image background = new ImageIcon(Main.class.getResource("/Img/rhythm.png")).getImage();

    private JButton startButton = new JButton(startButtonImage);
    private JButton exitButton = new JButton(exitButtonImage);
    private JButton leftButton = new JButton(leftButtonImage);
    private JButton rightButton = new JButton(rightButtonImage);
    private JButton easyButton = new JButton(easyButtonImage);
    private JButton hardButton = new JButton(hardButtonImage);
    private JButton back1Button = new JButton(backButtonImage);
    private JButton back2Button = new JButton(backButtonImage);

    private boolean isMainScreen = false;
    private boolean isGameScreen = false;

    ArrayList<Track> trackList = new ArrayList<Track>();

    private Image titleImage;
    private Image selectedImage;
    private Music selectedMusic;
    Music introMusic = new Music("intromusic.mp3", true);
    private int nowSelected = 0;

    public static Game game;

    public RhythmGame(Main rhythmCard) {
        trackList.add(new Track("title.png", "newjeans.png", "rhythm_background.png",
                "Supernatural Selected.mp3", "NewJeans - Supernatural.mp3", "NewJeans - Supernatural"));
        trackList.add(new Track("title.png", "aespa.png", "rhythm_background.png",
                "Supernova Selected.mp3", "aespa - Supernova.mp3", "aespa - Supernova"));
        trackList.add(new Track("title.png", "touched.jpg", "rhythm_background.png",
                "Hi Bully Selected.mp3", "Touched - Hi Bully.mp3", "Touched - Hi Bully"));


        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);

        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        addKeyListener(new KeyListenerClass());

        

        startButton.setBounds(300, 400, 194, 38);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                startButton.setIcon(startButtonPushImage);
                new Music("buttonPressedMusic.mp3", false).start();
            }

            public void mouseExited(MouseEvent e) {
                startButton.setIcon(startButtonImage);
            }

            public void mousePressed(MouseEvent e) {
                new Music("buttonPressedMusic.mp3", false).start();
                enterMain();
            }
        });
        add(startButton);

        exitButton.setBounds(300, 450, 194, 38);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setPressedIcon(exitButtonPushImage);
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                exitButton.setIcon(exitButtonPushImage);
                exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                new Music("buttonPressedMusic.mp3", false).start();
            }

            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(exitButtonImage);
            }

            public void mousePressed(MouseEvent e) {
                new Music("buttonPressedMusic.mp3", false).start();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                rhythmCard.showGameSelectScreen();
                introMusic.close();
                introMusic = new Music("introMusic.mp3", true);
            }
        });
        add(exitButton);

        leftButton.setVisible(false);
        leftButton.setBounds(80, 310, 100, 70);
        leftButton.setContentAreaFilled(false);
        leftButton.setFocusPainted(false);
        leftButton.setBorderPainted(false);
        leftButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                leftButton.setIcon(leftButtonPushImage);
            }

            public void mouseExited(MouseEvent e) {
                leftButton.setIcon(leftButtonImage);
            }

            public void mousePressed(MouseEvent e) {
                new Music("buttonPressedMusic.mp3", false).start();
                selectLeft();
            }
        });
        add(leftButton);

        rightButton.setVisible(false);
        rightButton.setBounds(630, 310, 100, 70);
        rightButton.setContentAreaFilled(false);
        rightButton.setFocusPainted(false);
        rightButton.setBorderPainted(false);
        rightButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                rightButton.setIcon(rightButtonPushImage);
            }

            public void mouseExited(MouseEvent e) {
                rightButton.setIcon(rightButtonImage);
            }

            public void mousePressed(MouseEvent e) {
                new Music("buttonPressedMusic.mp3", false).start();
                selectRight();
            }
        });
        add(rightButton);

        easyButton.setVisible(false);
        easyButton.setBounds(170, 460, 194, 38);
        easyButton.setContentAreaFilled(false);
        easyButton.setFocusPainted(false);
        easyButton.setBorderPainted(false);
        easyButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                easyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                easyButton.setIcon(easyButtonPushImage);
            }

            public void mouseExited(MouseEvent e) {
                easyButton.setIcon(easyButtonImage);
            }

            public void mousePressed(MouseEvent e) {
                new Music("buttonPressedMusic.mp3", false).start();
                gameStart(nowSelected, "Easy");
            }
        });
        add(easyButton);

        hardButton.setVisible(false);
        hardButton.setBounds(445, 460, 194, 38);
        hardButton.setContentAreaFilled(false);
        hardButton.setFocusPainted(false);
        hardButton.setBorderPainted(false);
        hardButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                hardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                hardButton.setIcon(hardButtonPushImage);
            }

            public void mouseExited(MouseEvent e) {
                hardButton.setIcon(hardButtonImage);
            }

            public void mousePressed(MouseEvent e) {
                new Music("buttonPressedMusic.mp3", false).start();
                gameStart(nowSelected, "Hard");
            }
        });
        add(hardButton);

        back1Button.setVisible(false);
        back1Button.setBounds(650, 20, 194, 38);
        back1Button.setContentAreaFilled(false);
        back1Button.setFocusPainted(false);
        back1Button.setBorderPainted(false);
        back1Button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                back1Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                back1Button.setIcon(backButtonPushImage);
            }

            public void mouseExited(MouseEvent e) {
                back1Button.setIcon(backButtonImage);
            }

            public void mousePressed(MouseEvent e) {
                new Music("buttonPressedMusic.mp3", false).start();
                backStart();
            }
        });
        add(back1Button);
        
        

        setVisible(true);
        
        back2Button.setVisible(false);
        back2Button.setBounds(650, 20, 194, 38);
        back2Button.setContentAreaFilled(false);
        back2Button.setFocusPainted(false);
        back2Button.setBorderPainted(false);
        back2Button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                back2Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                back2Button.setIcon(backButtonPushImage);
            }

            public void mouseExited(MouseEvent e) {
                back2Button.setIcon(backButtonImage);
            }

            public void mousePressed(MouseEvent e) {
                new Music("buttonPressedMusic.mp3", false).start();
                backMain();
            }
        });
        add(back2Button);

        setVisible(true);
    }

    public void paint(Graphics g) {
        screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);
    }

    public void screenDraw(Graphics g) {
        g.drawImage(background, 0, 0, null);
        if (isMainScreen) {
            //g.drawImage(titleImage, 150, 550, null);
            g.drawImage(selectedImage, 180, 100, null);
        }
        if (isGameScreen && game != null) {
            game.screenDraw(g);
        }
        paintComponents(g);
        try {
            Thread.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.repaint();
    }

    public void selectTrack(int nowSelected) {
        if (selectedMusic != null)
            selectedMusic.close();
        titleImage = new ImageIcon(Main.class.getResource("/Img/" + trackList.get(nowSelected).getTitleImage())).getImage();
        selectedImage = new ImageIcon(Main.class.getResource("/Img/" + trackList.get(nowSelected).getStartImage())).getImage();
        selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
        selectedMusic.start();
    }

    public void selectLeft() {
        nowSelected = (nowSelected == 0) ? trackList.size() - 1 : nowSelected - 1;
        selectTrack(nowSelected);
    }

    public void selectRight() {
        nowSelected = (nowSelected == trackList.size() - 1) ? 0 : nowSelected + 1;
        selectTrack(nowSelected);
    }

    void gameStart(int nowSelected, String difficulty) {
        if (selectedMusic != null)
            selectedMusic.close();
        isMainScreen = false;
        leftButton.setVisible(false);
        rightButton.setVisible(false);
        easyButton.setVisible(false);
        hardButton.setVisible(false);
        back1Button.setVisible(false);
        back2Button.setVisible(true);
        background = new ImageIcon(Main.class.getResource("/Img/" + trackList.get(nowSelected).getGameImage())).getImage();
        isGameScreen = true;

        new Thread(() -> {
            game = new Game(trackList.get(nowSelected).getTitleName(), difficulty, trackList.get(nowSelected).getGameMusic());
            game.start();
        }).start();

        setFocusable(true);
    }

    public void backMain() {
        isMainScreen = true;
        leftButton.setVisible(true);
        rightButton.setVisible(true);
        easyButton.setVisible(true);
        hardButton.setVisible(true);
        background = new ImageIcon(Main.class.getResource("/Img/inGame.png")).getImage();
        back1Button.setVisible(true);
        selectTrack(nowSelected);
        isGameScreen = false;
        if (game != null) game.close();
    }

    public void enterMain() {
        startButton.setVisible(false);
        exitButton.setVisible(false);
        back1Button.setVisible(true);
        background = new ImageIcon(Main.class.getResource("/Img/inGame.png")).getImage();
        isMainScreen = true;
        leftButton.setVisible(true);
        rightButton.setVisible(true);
        easyButton.setVisible(true);
        hardButton.setVisible(true);
        introMusic.close();
        selectTrack(0);
    }
    public void backStart() {
    	startButton.setVisible(true);
        exitButton.setVisible(true);
        back1Button.setVisible(false);
        background = new ImageIcon(Main.class.getResource("/Img/rhythm.png")).getImage();
        isMainScreen = false;
        leftButton.setVisible(false);
        rightButton.setVisible(false);
        easyButton.setVisible(false);
        hardButton.setVisible(false);
        selectedMusic.close();
        introMusic = new Music("introMusic.mp3", true);
        introMusic.start();
    }
}
