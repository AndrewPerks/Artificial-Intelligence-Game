package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * The Win Class which appears at the end of the game if the player wins
 * 
 * @author Andrew Perkins w12015296
 * @version 1.0
 *
 */

public class Win extends BasicGameState{	
	
	public Win(int state){
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{

	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{		
		g.drawString("You Win!", 350, 150);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{

	}

	public int getID() {
		return 3;
	}
	
}
