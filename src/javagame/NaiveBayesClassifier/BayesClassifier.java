package javagame.NaiveBayesClassifier;

/**
 * The original Bayes Classifier code refactored by Andrew Perkins
 * 
 * @author Andrew Perkins w12015296
 * @version 1.0
 *
 */

public class BayesClassifier {
	
	private static String trainingdata[][] = {
		// playerHealth, health, enemyHealth, ammo, EMOTION		
		{"low",    "low",    "medium", "low",    "confident"},
		{"low",    "low",    "high",   "high",   "confident"},
		{"low",    "low",    "high",   "medium", "confident"},		
		{"low",    "low",    "high",   "low",    "confident"},			
		{"low",    "low",    "medium", "low",    "confident"},
		{"low",    "low",    "medium", "medium", "confident"},
		{"low",    "medium", "high",   "low",    "confident"},
		{"low",    "medium", "high",   "medium", "confident"},
		{"low",    "medium", "high",   "high",   "confident"},
		{"low",    "high",   "high",   "high",   "confident"},
		{"medium", "low",    "medium", "low",    "confident"},
		{"medium", "low",    "medium", "medium", "confident"},
		{"medium", "low",    "high",   "medium", "confident"},
		{"high",   "low",    "high",   "high",   "confident"},
		
		{"low",    "low",    "low",    "low",    "normal"},		
		{"medium", "medium", "medium", "medium", "normal"},		
		{"medium", "medium", "high",   "high",   "normal"},		
		{"high",   "low",    "medium", "medium", "normal"},		
		{"high",   "high",   "high",   "high",   "normal"},
		
		{"low",    "low",    "low",    "high",   "scared"},
		{"medium", "high",   "low",    "low",    "scared"},
		{"medium", "medium", "low",    "high",   "scared"},		
		{"high",   "medium", "low",    "medium", "scared"},
		{"high",   "medium", "low",    "low",    "scared"},
		{"high",   "high",   "low",    "high",   "scared"},
		{"high",   "high",   "low",    "medium", "scared"}
		
};

private static double m;
private static double p;
private static int num_attr = 4;
private static int train_size = trainingdata.length;

public BayesClassifier() {
	
	 m = 2.0;
	 p = 0.33; // 1/3
	 
}

public static double CalculateProbability(String[] test, String category) {

int count[] = new int[num_attr];
for (int i=0; i<num_attr; i++)
	count[i] = 0;

double p_category = 0.0;
int num_category = 0;

for (int j=0; j<train_size; j++)	
	if (category.equals(trainingdata[j][num_attr]))
	    	num_category ++;

//System.out.println(category + ": " + num_category);
p_category = (double)num_category/(double)train_size;

for (int i=0; i<num_attr; i++)
{
	for (int j=0; j<train_size; j++)	
	    if ((test[i].equals(trainingdata[j][i])) && (category.equals(trainingdata[j][num_attr])))
	    	count[i] ++;
	
	
    p_category *=  ((double)count[i] + m * p)/((double)num_category + m);
    
    System.out.println(test[i] + " : " + count[i] + " (probability = " + ((double)count[i] + m * p)/((double)num_category + m) + ")");
}

return p_category;
	
}


}
