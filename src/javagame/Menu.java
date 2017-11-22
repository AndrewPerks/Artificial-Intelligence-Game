package javagame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * The Menu Class used to display the start menu
 * 
 * @author Andrew Perkins w12015296
 * @version 1.0
 *
 */

public class Menu extends BasicGameState{
	
	Image playNow;
	Image exitNow;
	String mousePosition;
	
	public Menu(int state){
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		playNow = new Image("res/playnow.png");
		exitNow = new Image("res/exit.png");
		mousePosition = "";
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{		
		g.drawString("Welcome to the game!", 350, 100);
		g.drawString(mousePosition, 400, 20);
		g.drawString("Developed by Andrew Perkins 12015296", 275, 350);
		g.drawString("Move: Arrow Keys", 350, 400);
		g.drawString("Shoot: Spacebar", 350, 425);
		playNow.draw(350, 150);
		exitNow.draw(380, 250);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		Input input = gc.getInput();
		int xPos = Mouse.getX();
		int yPos = Mouse.getY();
		
		mousePosition = "x: " + xPos + " y: " + yPos;
	
		// Play now button
		if((xPos > 350 && xPos < 500) && (yPos > 300 && yPos < 350)){
			if(input.isMouseButtonDown(0)){
				sbg.enterState(1);
			}
		}
		
		// Exit button
		if((xPos > 380 && xPos < 470) && (yPos > 200 && yPos < 250)){
			if(input.isMouseButtonDown(0)){
				System.exit(0);
			}
		}
	}

	public int getID() {
		return 0;
	}
	
}
