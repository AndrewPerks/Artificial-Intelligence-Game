package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * The End Class which appears at the end of the game if the player loses 
 * 
 * @author Andrew Perkins w12015296
 * @version 1.0
 *
 */

public class End extends BasicGameState{	
	
	public End(int state){
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{

	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{		
		g.drawString("Game Over!", 350, 150);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{

	}

	public int getID() {
		return 2;
	}
	
}
