package dinoTest;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyListenerClass extends KeyAdapter {
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (RhythmGame.game == null || RhythmGame.game.isGameFinished()) {
	        return;
	    }
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			RhythmGame.game.pressQ();
		}
		else if(e.getKeyCode() == KeyEvent.VK_W) {
			RhythmGame.game.pressW();
		}
		else if(e.getKeyCode() == KeyEvent.VK_E) {
			RhythmGame.game.pressE();
		}
		else if(e.getKeyCode() == KeyEvent.VK_R) {
			RhythmGame.game.pressR();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (RhythmGame.game == null || RhythmGame.game.isGameFinished()) {
	        return;
		}
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			RhythmGame.game.releaseQ();
		}
		else if(e.getKeyCode() == KeyEvent.VK_W) {
			RhythmGame.game.releaseW();
		}
		else if(e.getKeyCode() == KeyEvent.VK_E) {
			RhythmGame.game.releaseE();
		}
		else if(e.getKeyCode() == KeyEvent.VK_R) {
			RhythmGame.game.releaseR();
		}
	}
}
