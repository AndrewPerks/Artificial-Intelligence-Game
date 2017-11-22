package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * The Game Class is used to manage the various game states
 * 
 * @author Andrew Perkins w12015296
 * @version 1.0
 *
 */

public class Game extends StateBasedGame {

	public static final String gamename = "AI Project";
	public static final int menu = 0;
	public static final int play = 1;
	public static final int end = 1;
	public static final int win = 1;
	
	public Game(String gamename) {
		super(gamename);
		this.addState(new Menu(menu));
		this.addState(new Play(play));
		this.addState(new End(end));
		this.addState(new Win(win));
	}		
	
	public void initStatesList(GameContainer gc) throws SlickException{
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.getState(end).init(gc, this);
		this.getState(win).init(gc, this);
		this.enterState(menu);
	}
	
	public static void main(String[] args) {
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Game(gamename));
			appgc.setDisplayMode(800, 500, false);
			appgc.start();
			
			
		} catch (SlickException e) {
				e.printStackTrace();
		}
	}

}
