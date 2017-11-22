package javagame.NeuralNetwork;

/**
 * The NPCBrain Class used to calculate the NPC behaviour using a neural network
 * 
 * @author Andrew Perkins w12015296
 * @version 1.0
 *
 */

public class NPCBrain {
		
	static NeuralNetwork Brain = new NeuralNetwork();
	
	public static double TrainingSet[][] = {
		// playerHealth, health, enemyHealth, ammo, HEAL_PLAYER, SHOOT, HEAL		
		{0.4, 1, 0.2, 0.8, 0.1, 0.9, 0.1}, // SHOOT
		{0.6, 1, 0.6, 0.3, 0.1, 0.9, 0.1}, // SHOOT
		{0.7, 0.75, 0.1, 0.1, 0.1, 0.9, 0.1}, // SHOOT
		{0.8, 0.5, 0.7, 0.6, 0.1, 0.9, 0.1}, // SHOOT
		{0.9, 0.875, 1, 1, 0.1, 0.9, 0.1}, // SHOOT
		{1, 0.4, 0.1, 0.5, 0.1, 0.9, 0.1}, // SHOOT
		{1, 1, 1, 1, 0.1, 0.9, 0.1}, // SHOOT				
				
		{0.4, 0.5, 0.2, 0.1, 0.1, 0.1, 0.9}, //HEAL		
		{0.6, 0.25, 0.4, 0.6, 0.1, 0.1, 0.9}, //HEAL
		{0.8, 0.4, 0.5, 0.4, 0.1, 0.1, 0.9}, //HEAL
		{1, 0.25, 1, 1, 0.1, 0.1, 0.9}, //HEAL		
		{1, 0.75, 0.8, 0, 0.1, 0.1, 0.9}, //HEAL
		{1, 0.875, 0.7, 0, 0.1, 0.1, 0.9}, //HEAL
		
		{0.2, 0.4, 1, 1, 0.9, 0.1, 0.1}, //HEAL_PLAYER		
		{0.2, 1, 1, 1, 0.9, 0.1, 0.1}, //HEAL_PLAYER
		{0.3, 0.5, 0.8, 0.2, 0.9, 0.1, 0.1}, //HEAL_PLAYER
		{0.4, 0.75, 0.2, 0.4, 0.9, 0.1, 0.1}, //HEAL_PLAYER
		{0.4, 0.6, 0.5, 0.1, 0.9, 0.1, 0.1}, //HEAL_PLAYER
		{0.4, 0.4, 0.6, 1, 0.9, 0.1, 0.1}, //HEAL_PLAYER
		{0.4, 0.8, 0.3, 0.3, 0.9, 0.1, 0.1}, //HEAL_PLAYER
		{0.5, 0.7, 0.3, 0, 0.9, 0.1, 0.1}, //HEAL_PLAYER
		{0.6, 1, 0.2, 0, 0.9, 0.1, 0.1}, //HEAL_PLAYER
		{0.8, 1, 1, 0, 0.9, 0.1, 0.1} //HEAL_PLAYER								
		
	};	
	
	public void TrainTheBrain()
	{
		int i; 
		double error = 1;
		int c = 0;
		int trainingDataCount = TrainingSet.length;
		
		Brain.DumpData();
		
		while ((error > 0.05) && (c < 50000))
		{
			error = 0;
			c++;
			
			for(i=0; i < trainingDataCount; i++)
			{
				Brain.SetInput(0, TrainingSet[i][0]);
				Brain.SetInput(1, TrainingSet[i][1]);
				Brain.SetInput(2, TrainingSet[i][2]);
				Brain.SetInput(3, TrainingSet[i][3]);
				
				Brain.SetDesiredOutput(0, TrainingSet[i][4]);
				Brain.SetDesiredOutput(1, TrainingSet[i][5]);
				Brain.SetDesiredOutput(2, TrainingSet[i][6]);				
				
				Brain.FeedForward();
				error += Brain.CalculateError();
				Brain.BackPropagate();
			}
			error = error / 14.0f;
		}
		//c = c * 1
		Brain.DumpData();
	}	
	
	public int GetBehaviour(double healplayer, double shoot, double heal)
	{
		if ((healplayer > shoot) &&  (healplayer > heal))
		{	
			System.out.println("Healing Player!");
			return 1;			
		}
		else if((shoot > healplayer) && (shoot > heal))
		{
			System.out.println("Shooting!");
			return 2;
			
		}
		else if((heal > healplayer) && (heal > shoot))
		{
			System.out.println("Healing!");
			return 3;
		}
		else{
			System.out.println("None");
			return 4;
		}
			
	}
	
	public void InitialiseNPCBehaviour(){
		Brain.Initialize(4, 3, 3);
		Brain.SetLearningRate(0.3);
		Brain.SetMomentum(true, 0.9);
		TrainTheBrain();
	}
	
	public int CalculateNPCBehaviour(double playerHealth, double health, double enemyHealth, double ammo)
	{
		// Current Inputs		
		Brain.SetInput(0, playerHealth);
		Brain.SetInput(1, health);
		Brain.SetInput(2, enemyHealth);
		Brain.SetInput(3, ammo);
		
		Brain.FeedForward();
		return GetBehaviour(Brain.GetOutput(0), Brain.GetOutput(1), Brain.GetOutput(2));		
	}	
	
	public int CalculateFullNPCBehaviour(double playerHealth, double health, double enemyHealth, double ammo){
		
		InitialiseNPCBehaviour();
		return CalculateNPCBehaviour(playerHealth, health, enemyHealth, ammo);		
	}	

}
