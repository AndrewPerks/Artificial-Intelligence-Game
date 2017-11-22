package javagame.NaiveBayesClassifier;

/**
 * The Enemy Emotion class used to calculate the enemies current emotion
 * based on inputs
 * 
 * @author Andrew Perkins w12015296
 * @version 1.0
 *
 */

public class EnemyEmotion {
	
	static BayesClassifier enemyBrain = new BayesClassifier();
	
	private static int num_category = 3;
	private static int test_size = 1;
	
	public EnemyEmotion() {
		
	}
	
	public int GetEmotion(String[][] inputs){
		
		 double result[] = new double[num_category];
		 String category[] = {"confident", "normal", "scared"};
		 
		 for (int k = 0; k< test_size; k++)
		 {
		   System.out.println("********************* the " + k + " test data ********************");
		   for (int i=0; i<num_category; i++)
			 {
			   result[i] = enemyBrain.CalculateProbability(inputs[k], category[i]);
			   System.out.println(category[i] + ": " + result[i]);
			 
			 }
		 
		   double max = -1000.0;
		   int max_position = -1;
		   for (int i=0; i < num_category; i++)
			 if (result[i]> max)
			 { 
				 max_position = i;
				 max = result[i];
			 
			 }
		   
		   System.out.println("the " + k + " test data is classified as: " + category[max_position] + " with " + max);
		   
		   return max_position;
		 }		
		 
		return -1;		
	}
	
	public String[][] ConvertScaledValuesToBehaviours(double scaledPlayerHealth, double scaledNPCHealth, double scaledEnemyHealth, double scaledNPCAmmo)
	{
		String BehaviourList[][] = new String[1][4];
		
		if(scaledPlayerHealth > 0.65){
			BehaviourList[0][0] = "high";
		}
		else if(scaledPlayerHealth < 0.45){
			BehaviourList[0][0] = "low";
		}
		else
		{
			BehaviourList[0][0] = "medium";
		}
		
		if(scaledNPCHealth > 0.65){
			BehaviourList[0][1] = "high";
		}
		else if(scaledNPCHealth < 0.45){
			BehaviourList[0][1] = "low";
		}
		else
		{
			BehaviourList[0][1] = "medium";
		}
		
		if(scaledEnemyHealth > 0.65){
			BehaviourList[0][2] = "high";
		}
		else if(scaledEnemyHealth < 0.45){
			BehaviourList[0][2] = "low";
		}
		else
		{
			BehaviourList[0][2] = "medium";
		}
		
		if(scaledNPCAmmo > 0.65){
			BehaviourList[0][3] = "high";
		}
		else if(scaledNPCAmmo < 0.45){
			BehaviourList[0][3] = "low";
		}
		else
		{
			BehaviourList[0][3] = "medium";
		}
		
		return BehaviourList;
		
	}	

}
