package javagame.NeuralNetwork;

/////////////////////////////////////////////////////////////////////////////////////////////////
//NeuralNetwork Class
/////////////////////////////////////////////////////////////////////////////////////////////////

public class NeuralNetwork {

	NeuralNetworkLayer InputLayer;
	NeuralNetworkLayer HiddenLayer;
	NeuralNetworkLayer OutputLayer;

	public NeuralNetwork() {

		InputLayer = new NeuralNetworkLayer();
		HiddenLayer = new NeuralNetworkLayer();
		OutputLayer = new NeuralNetworkLayer();

	}

	public void Initialize(int nNodesInput, int nNodesHidden, int nNodesOutput) {
		InputLayer.NumberOfNodes = nNodesInput;
		InputLayer.NumberOfChildNodes = nNodesHidden;
		InputLayer.NumberOfParentNodes = 0;
		InputLayer.Initialize(nNodesInput, null, HiddenLayer);
		InputLayer.RandomizeWeights();

		HiddenLayer.NumberOfNodes = nNodesHidden;
		HiddenLayer.NumberOfChildNodes = nNodesOutput;
		HiddenLayer.NumberOfParentNodes = nNodesInput;
		HiddenLayer.Initialize(nNodesHidden, InputLayer, OutputLayer);
		HiddenLayer.RandomizeWeights();

		OutputLayer.NumberOfNodes = nNodesOutput;
		OutputLayer.NumberOfChildNodes = 0;
		OutputLayer.NumberOfParentNodes = nNodesHidden;
		OutputLayer.Initialize(nNodesOutput, HiddenLayer, null);

	}

	public void SetInput(int i, double value) {
		if ((i >= 0) && (i < InputLayer.NumberOfNodes)) {
			InputLayer.NeuronValues[i] = value;
		}
	}

	public double GetOutput(int i) {
		if ((i >= 0) && (i < OutputLayer.NumberOfNodes)) {
			return OutputLayer.NeuronValues[i];
		}

		return (double) 10000; // to indicate an error
	}

	public void SetDesiredOutput(int i, double value) {
		if ((i >= 0) && (i < OutputLayer.NumberOfNodes)) {
			OutputLayer.DesiredValues[i] = value;
		}
	}

	public void FeedForward() {
		InputLayer.CalculateNeuronValues();
		HiddenLayer.CalculateNeuronValues();
		OutputLayer.CalculateNeuronValues();
	}

	public void BackPropagate() {
		OutputLayer.CalculateErrors();
		HiddenLayer.CalculateErrors();

		HiddenLayer.AdjustWeights();
		InputLayer.AdjustWeights();
	}

	public int GetMaxOutputID() {
		int i, id;
		double maxval;

		maxval = OutputLayer.NeuronValues[0];
		id = 0;

		for (i = 1; i < OutputLayer.NumberOfNodes; i++) {
			if (OutputLayer.NeuronValues[i] > maxval) {
				maxval = OutputLayer.NeuronValues[i];
				id = i;
			}
		}

		return id;
	}

	public double CalculateError() {
		int i;
		double error = 0;

		for (i = 0; i < OutputLayer.NumberOfNodes; i++) {
			error += Math.pow(OutputLayer.NeuronValues[i]
					- OutputLayer.DesiredValues[i], 2);
		}

		error = error / OutputLayer.NumberOfNodes;

		return error;
	}

	public void SetLearningRate(double rate) {
		InputLayer.LearningRate = rate;
		HiddenLayer.LearningRate = rate;
		OutputLayer.LearningRate = rate;
	}

	public void SetLinearOutput(boolean useLinear) {
		InputLayer.LinearOutput = useLinear;
		HiddenLayer.LinearOutput = useLinear;
		OutputLayer.LinearOutput = useLinear;
	}

	public void SetMomentum(boolean useMomentum, double factor) {
		InputLayer.UseMomentum = useMomentum;
		HiddenLayer.UseMomentum = useMomentum;
		OutputLayer.UseMomentum = useMomentum;

		InputLayer.MomentumFactor = factor;
		HiddenLayer.MomentumFactor = factor;
		OutputLayer.MomentumFactor = factor;

	}

	public void DumpData() {

		int i, j;

		System.out
				.println("--------------------------------------------------------");
		System.out.println("Input Layer");
		System.out
				.println("--------------------------------------------------------");
		System.out.println("\n");
		System.out.println("Node Values:");
		System.out.println("\n");

		for (i = 0; i < InputLayer.NumberOfNodes; i++)
			System.out.println(i + " " + InputLayer.NeuronValues[i]);
		System.out.println("\n");
		System.out.println("Weights:");
		System.out.println("\n");

		for (i = 0; i < InputLayer.NumberOfNodes; i++)
			for (j = 0; j < InputLayer.NumberOfChildNodes; j++)
				System.out
						.println(i + " " + j + " " + InputLayer.Weights[i][j]);
		System.out.println("\n");
		System.out.println("Bias Weights:");
		System.out.println("\n");

		for (j = 0; j < InputLayer.NumberOfChildNodes; j++)
			System.out.println(j + " " + InputLayer.BiasWeights[j]);

		System.out.println("\n");
		System.out.println("\n");

		System.out
				.println("--------------------------------------------------------");
		System.out.println("Hidden Layer");
		System.out
				.println("--------------------------------------------------------");
		System.out.println("\n");
		System.out.println("Weights:");
		System.out.println("\n");

		for (i = 0; i < HiddenLayer.NumberOfNodes; i++)
			for (j = 0; j < HiddenLayer.NumberOfChildNodes; j++)
				System.out.println(i + " " + j + " "
						+ HiddenLayer.Weights[i][j]);
		System.out.println("\n");
		System.out.println("Bias Weights:");
		System.out.println("\n");

		for (j = 0; j < HiddenLayer.NumberOfChildNodes; j++)
			System.out.println(j + " " + HiddenLayer.BiasWeights[j]);

		System.out.println("\n");
		System.out.println("\n");

		System.out
				.println("--------------------------------------------------------");
		System.out.println("Output Layer");
		System.out
				.println("--------------------------------------------------------");
		System.out.println("\n");
		System.out.println("Node Values:");
		System.out.println("\n");

		for (i = 0; i < OutputLayer.NumberOfNodes; i++)
			System.out.println(i + " " + OutputLayer.NeuronValues[i]);
		System.out.println("\n");

	}

}
