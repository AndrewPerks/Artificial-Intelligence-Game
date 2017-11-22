package javagame;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.*;

import javagame.NaiveBayesClassifier.EnemyEmotion;
import javagame.NeuralNetwork.NPCBrain;

/**
 * The Play Class used for the actual game play
 * 
 * @author Andrew Perkins w12015296
 * @version 1.0
 *
 */

public class Play extends BasicGameState{	
	
	Animation player, npc, enemy, explosion, firstAid, movingUp, movingDown, movingLeft, movingRight;
	Image worldMap, playerImage, npcImage, enemyImage, explosionImage, firstAidImage;
	SpriteSheet tiles;
	Rectangle map;	
	LinkedList<Bullet> bullets;
	Bullet enemyBullet;
	NPCBrain npcBrain;
	EnemyEmotion enemyEmotion;
	
	boolean quit = false;
	boolean bulletFired = false;
	boolean changeMovement = false;
	boolean npcChangeMovement = false;
	int[] duration = { 200, 200 };
	int[] enemyDuration = { 1, 1 };
	float hiddenPosition= -100;
	float playerPositionX = 350;
	float playerPositionY = 350;
	float npcPositionX = 500;
	float npcPositionY = 350;
	int maxPlayerHealth = 100;
	int playerHealth = maxPlayerHealth;
	int maxNPCHealth = 80;
	int npcHealth = maxNPCHealth;
	int maxEnemyHealth = 200;	
	int enemyHealth = maxEnemyHealth;	
	int maxNpcAmmo = 15;
	int npcAmmo = maxNpcAmmo;
	float scaledPlayerHealth;
	float scaledNPCHealth;
	float scaledEnemyHealth;
	float scaledNPCAmmo;
	float enemyPositionX = 50;
	float enemyPositionY = 70;	
	float enemySpeed = 1;
	float npcSpeed = 0.25f;
	float explosionPositionX = -100;
	float explosionPositionY = -100;	
	float firstAidPositionX = -100;
	float firstAidPositionY = -100;	
	int npcBehaviour = 0;
	int enemyBehaviour = 0;
	long previousTime = 0;
	long pastTime = 0;
	long pastTime2 = 0;
	String npcSpeech = "";
	String enemySpeech = "";
	String emotionSpeech = "";
	String inputStats[][] = {{}};
	Color transparentBlack = new Color(0f,0f,0f,0.5f);
	Color transparentWhite = new Color(1f,1f,1f,0.5f);
	
	public Play(int state){
	
	}	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		worldMap = new Image("res/world.jpg");
		playerImage = new Image("res/man.png").getScaledCopy(50, 50);
		npcImage = new Image("res/helper.png").getScaledCopy(100, 100);		
		enemyImage = new Image("res/alien.png").getScaledCopy(50, 50);
		explosionImage = new Image("res/explosion.png").getScaledCopy(50, 50);
		firstAidImage = new Image("res/firstAid.png").getScaledCopy(50, 50);
		
		bullets = new LinkedList<Bullet>();		
		enemyBullet = new Bullet(new Vector2f(enemyPositionX, enemyPositionY), new Vector2f(500,500), false, Color.red);		
		npcBrain = new NPCBrain();	
		enemyEmotion = new EnemyEmotion();
		
		Image playerUp = playerImage.getFlippedCopy(false, true);
		Image playerDown = playerImage;
		Image playerLeft = playerImage;
		Image playerRight = playerImage.getFlippedCopy(true, false);			
		
		Image[] walkUp = { playerUp, playerUp };
		Image[] walkDown = { playerDown, playerDown };
		Image[] walkLeft = { playerLeft, playerLeft };
		Image[] walkRight = { playerRight, playerRight };
		
		Image[] npcImageArray = { npcImage, npcImage };
		Image[] enemyImageArray = { enemyImage, enemyImage };
		Image[] explosionImageArray = { explosionImage, explosionImage };
		Image[] firstAidImageArray = { firstAidImage, firstAidImage };

		movingUp = new Animation(walkUp, duration, false);
		movingDown = new Animation(walkDown, duration, false);
		movingLeft = new Animation(walkLeft, duration, false);
		movingRight = new Animation(walkRight, duration, false);
		npc = new Animation(npcImageArray, duration, false);
		enemy = new Animation(enemyImageArray, duration, false);		
		explosion = new Animation(explosionImageArray, duration, false);
		firstAid = new Animation(firstAidImageArray, duration, false);
		
		player = movingDown;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		
		// draw world map		
		g.fillRect(0, 0, 1000, 1000, worldMap, 512, 512);
		player.draw(playerPositionX, playerPositionY);
		npc.draw(npcPositionX, npcPositionY);
		enemy.draw(enemyPositionX, enemyPositionY);
		explosion.draw(explosionPositionX, explosionPositionY);
		firstAid.draw(firstAidPositionX, firstAidPositionY);		
		
		// player bullets		
		for(Bullet b : bullets){
			b.render(gc, g);
		}			
				
		// enemy bullets
		if(enemyBullet.isActive() == false){
			enemyBullet = new Bullet(new Vector2f(enemyPositionX, enemyPositionY), new Vector2f(500,500), false, Color.red);
		}
		enemyBullet.render(gc, g);
			
		// status bars
		g.setColor(transparentBlack);	
		g.fillRect(20, 425, 165, 80);		
		g.setColor(transparentWhite);	
		g.fillRect(595, 465, 155, 40);
				
		//g.drawString("Player\nX:" + playerPositionX + "\nY:" + playerPositionY,	100, 10);		
		//g.drawString("Enemy\nX:" + enemyPositionX + "\nY:" + enemyPositionY, 600, 10);
		g.setColor(Color.green);
		g.drawString("Player Health:" + playerHealth, 25, 425);
		g.drawString("NPC Health:" + npcHealth, 25, 450);
		g.drawString("NPC Ammo:" + npcAmmo, 25, 475);
		g.setColor(Color.red);
		g.drawString("Enemy Health:" + enemyHealth, 600, 475);
		
		g.setColor(Color.white);
		g.drawString(npcSpeech, npcPositionX, npcPositionY);
		g.drawString(enemySpeech, enemyPositionX, enemyPositionY);		
		//g.drawString(emotionSpeech.toString(), 300, 400);

		if (quit == true) {
			g.drawString("Resume (R)", 250, 150);
			g.drawString("Main Menu (M)", 250, 170);
			g.drawString("Quit Game (Q)", 250, 190);
			if (quit == false) {
				g.clear();
			}
		}
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		
		long tmp = System.currentTimeMillis();
		long customDelta = tmp - previousTime;
		previousTime = tmp;		
		
		Input input = gc.getInput();		

		// move up
		if (input.isKeyDown(Input.KEY_UP)) {
			player = movingDown;
			playerPositionY -= delta * .1f;
			if (playerPositionY < -0) {
				playerPositionY += delta * .1f;
			}
		}
		// move down
		if (input.isKeyDown(Input.KEY_DOWN)) {
			player = movingUp;
			playerPositionY += delta * .1f;
			if (playerPositionY > 450) {
				playerPositionY -= delta * .1f;
			}
		}		
		// move left
		if (input.isKeyDown(Input.KEY_LEFT)) {
			player = movingRight;
			playerPositionX -= delta * .1f;
			if (playerPositionX < -0) {
				playerPositionX += delta * .1f;
			}
		}		
		// move right
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			player = movingLeft;
			playerPositionX += delta * .1f;
			if (playerPositionX > 750) {
				playerPositionX -= delta * .1f;
			}
		}	
		
		// shoot
		Iterator<Bullet> i = bullets.iterator();
		while(i.hasNext()){
			Bullet b = i.next();
			if(b.isActive()){
				b.update(delta);	
			}
			else
			{
				i.remove();
			}
			
			// bullet collisions
			if((b.xPosition() >= enemyPositionX && b.xPosition() < enemyPositionX + 50) && (b.yPosition() > enemyPositionY && b.yPosition() < enemyPositionY + 25)){
				explosionPositionX = enemyPositionX;
				explosionPositionY = enemyPositionY;
				b.isActive(false);
				enemyHealth -= 10;			
			}
			
		}
		
		if (input.isKeyPressed(Input.KEY_SPACE)) {	
			player = movingDown;
			bulletFired = true;								
			bullets.add( new Bullet(new Vector2f(playerPositionX, playerPositionY), new Vector2f(500,500), true, Color.green));			
		}	
				
		// enemy movement
		if(enemyPositionX < 750 && changeMovement == false){
				enemyPositionX += delta * enemySpeed;
				if(enemyPositionX >= 750){
					changeMovement = true;	
				}				
		}
		else if(enemyPositionX > 0 && changeMovement){
				enemyPositionX -= delta * enemySpeed;
				if(enemyPositionX <= 0){
					changeMovement = false;
				}				
		}	
		
		// npc movement							
		float xDistance = enemyPositionX - npcPositionX;
		float yDistance = enemyPositionY - npcPositionY;
		float angleToTurn = (float) Math.toDegrees(Math.atan2(yDistance, xDistance));
		npcImage.setRotation((float)angleToTurn);				
						
		if(npcPositionX < 700 && npcChangeMovement == false){
			npcPositionX += delta * npcSpeed;
				if(npcPositionX >= 700){
					npcChangeMovement = true;	
					npcSpeed = randomWithRange(0.1f, 0.25f);
				}				
		}
		else if(npcPositionX > 200 && npcChangeMovement){
			npcPositionX -= delta * npcSpeed;
				if(npcPositionX <= 200){
					npcChangeMovement = false;
					npcSpeed = randomWithRange(0.1f, 0.25f);
				}				
		}	
		
		// enemy bullets
		enemyBullet.update(delta);		
		
		if(isReady2(customDelta, 2)){
			firstAidPositionX = hiddenPosition;
			firstAidPositionY = hiddenPosition;
			explosionPositionX = hiddenPosition;
			explosionPositionY = hiddenPosition;
			npcSpeech = "";
		}			
		
		// npc help				
		if(isReady(customDelta, 5)){
			scaledPlayerHealth = (float)playerHealth / (float)maxPlayerHealth;
			scaledNPCHealth = (float)npcHealth / (float)maxNPCHealth;
			scaledEnemyHealth = (float)enemyHealth / (float)maxEnemyHealth;
			scaledNPCAmmo = (float)npcAmmo / (float)maxNpcAmmo;
			
 			npcBehaviour = npcBrain.CalculateFullNPCBehaviour(scaledPlayerHealth, scaledNPCHealth, scaledEnemyHealth, scaledNPCAmmo);
									
 			inputStats = enemyEmotion.ConvertScaledValuesToBehaviours(scaledPlayerHealth, scaledNPCHealth, scaledEnemyHealth, scaledNPCAmmo);
			enemyBehaviour = enemyEmotion.GetEmotion(inputStats);
			emotionSpeech = Arrays.deepToString(inputStats);
			
			switch(npcBehaviour){
				case 1: npcSpeech = "Healing Player!";
						if(playerHealth <= 45){
							firstAidPositionX = playerPositionX;
							firstAidPositionY = playerPositionY;
							playerHealth += 5;
						}					
				break;
				case 2: 
						if(npcAmmo > 0){
							npcSpeech = "Shooting!";
							explosionPositionX = enemyPositionX;
							explosionPositionY = enemyPositionY;
							enemyHealth -= 5;
							npcAmmo -= 1;
						}
						else{
							npcSpeech = "No Ammo!";
						}					
				break;
				case 3: npcSpeech = "Healing!";
						firstAidPositionX = npcPositionX;
						firstAidPositionY = npcPositionY;
						npcHealth += 5;								
				break;
				case 4: npcSpeech = "Nothing";
				break;
			}
			
			switch(enemyBehaviour){
				case 0: 
					enemySpeech = "Confident";	
					enemyImage = new Image("res/angry.png").getScaledCopy(50, 50);
					Image[] enemyImageArray = { enemyImage, enemyImage };
					enemy = new Animation(enemyImageArray, duration, false);
					enemySpeed = 0.25f;
				break;
				case 1: 
					enemySpeech = "Normal";	
					enemyImage = new Image("res/alien.png").getScaledCopy(50, 50);
					Image[] enemyImageArray2 = { enemyImage, enemyImage };
					enemy = new Animation(enemyImageArray2, duration, false);
					enemySpeed = 0.5f;
				break;
				case 2: 
					enemySpeech = "Scared";		
					enemyImage = new Image("res/scared.png").getScaledCopy(50, 50);
					Image[] enemyImageArray3 = { enemyImage, enemyImage };
					enemy = new Animation(enemyImageArray3, duration, false);
					enemySpeed = 0.75f;
				break;
				default: 
					enemySpeech = "Nothing";
				break;
			}
		}
		
		// bullet collisions
		if((enemyBullet.xPosition() >= playerPositionX && enemyBullet.xPosition() < playerPositionX + 50) && (enemyBullet.yPosition() > playerPositionY && enemyBullet.yPosition() < playerPositionY + 25)){
			explosionPositionX = playerPositionX;
			explosionPositionY = playerPositionY;
			enemyBullet.isActive(false);
			playerHealth -= 10;			
		}
		
		if((enemyBullet.xPosition() >= npcPositionX && enemyBullet.xPosition() < npcPositionX + 100) && (enemyBullet.yPosition() > npcPositionY && enemyBullet.yPosition() < npcPositionY + 25)){
			explosionPositionX = npcPositionX;
			explosionPositionY = npcPositionY;			
			enemyBullet.isActive(false);
			npcHealth -= 10;			
		}
		
		// game over
		if(playerHealth <= 0 || npcHealth <= 0){			
			sbg.enterState(2); // player lose
		}
		
		if(enemyHealth <= 0){			
			sbg.enterState(3); // player win
		}
		
		// escape menu 
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			quit = true;
		}
		//when the menu is on the screen
		if(quit == true){
			if(input.isKeyDown(Input.KEY_R)){
				quit = false;
			}
			if(input.isKeyDown(Input.KEY_M)){
				sbg.enterState(0);
			}
			if(input.isKeyDown(Input.KEY_Q)){
				System.exit(0);
			}
		}
	}
	
	public float randomWithRange(float min, float max)
	{
	   float range = (float) ((max - min) + 0.1);     
	   return (float)(Math.random() * range) + min;
	}	
	
	public int getID() {
		return 1;
	}
	
	public boolean isReady(long delta, int seconds) {
	    if(pastTime < seconds * 1000) { //multiply by 1000 to get milliseconds
	        pastTime += delta;
	        return false;
	    }else{
	        pastTime = 0;
	        return true;
	    }
	}
	
	public boolean isReady2(long delta, int seconds) {
	    if(pastTime2 < seconds * 1000) { //multiply by 1000 to get milliseconds
	        pastTime2 += delta;
	        return false;
	    }else{
	        pastTime2 = 0;
	        return true;
	    }
	}
	
}
