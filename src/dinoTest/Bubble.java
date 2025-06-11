package dinoTest;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Bubble extends JPanel implements KeyListener{
	
	private ImageIcon come = new ImageIcon("Img/dino.png");
	
	JLabel dinoLabel = new JLabel(new ImageIcon("Img/dino-run.gif"));
	private Image backgroundImage;
	private Image dino;
	private boolean runnig = false; 
	int x,y =100;
	
	
	
	public Bubble() {
		backgroundImage = new ImageIcon("Img/select_background.png").getImage();
		dino = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Img/dino-run.gif"))).getImage();
		
		setLayout(null);
		
        dinoLabel.setBounds(x, y, 100, 100);  // 위치, 크기
        add(dinoLabel);
        
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) x -= 15;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) x += 15;
        if (e.getKeyCode() == KeyEvent.VK_UP);
        if (e.getKeyCode() == KeyEvent.VK_DOWN);
        
        dinoLabel.setBounds(x, y, 100, 100);  // JLabel 위치 갱신
        repaint();
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