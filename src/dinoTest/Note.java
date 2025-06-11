package dinoTest;

import java.awt.*;
import javax.swing.ImageIcon;

public class Note extends Thread{
	private Image noteImage = new ImageIcon(Main.class.getResource("/Img/note.png")).getImage();
	private int x, y = 545 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;
	private String noteType;
	private boolean proceeded = true;
	
	public String getNoteType() {
		return noteType;
	}
	
	public boolean isProceeded() {
		return proceeded;
	}
	
	public void close() {
		proceeded = false;
	}
	
	public Note (String noteType) {
		if(noteType.equals("Q")) {
			x = 27;
		}
		else if(noteType.equals("W")) {
			x =  176;
		}
		else if(noteType.equals("E")) {
			x =  325;
		}
		else if(noteType.equals("R")) {
			x =  474;
		}
		this.noteType = noteType;
		 
	}
	public void screenDraw(Graphics g) {
		if(noteType.equals("w")) {
			g.drawImage(noteImage, x, y, null);
		}
		else {
			g.drawImage(noteImage, x, y, null);
		}
	}
	
	public void drop() {
		y += Main.NOTE_SPEED;
		if(y > 620) {
			System.out.println("Miss");
			close();
		}
	}
	
	@Override
	public void run() {
		try {
			while (proceeded) {
				drop();
				Thread.sleep(Main.SLEEP_TIME);
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	public String judge() {
	    int judgementLineY = 450;
	    int yGap = Math.abs(y - judgementLineY);

	    if (yGap <= 10) {
	        System.out.println("Perfect");
	        close();
	        return "Perfect";
	    } else if (yGap <= 40) {
	        System.out.println("Great");
	        close();
	        return "Great";
	    } else {
	        System.out.println("Miss");
	        close();
	        return "Miss";
	    }
	}
	public int getY() {
		return y;
	}
	
}
