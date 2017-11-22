package javagame.NeuralNetwork;

/////////////////////////////////////////////////////////////////////////////////////////////////
//NeuralNetworkLayer Class
/////////////////////////////////////////////////////////////////////////////////////////////////

import java.util.*;

public class NeuralNetworkLayer {

	int NumberOfNodes;
	int NumberOfChildNodes;
	int NumberOfParentNodes;
	double[][] Weights;
	double[][] WeightChanges;
	double[] NeuronValues;
	double[] DesiredValues;
	double[] Errors;
	double[] BiasWeights;
	double[] BiasValues;
	double LearningRate;

	boolean LinearOutput;
	boolean UseMomentum;
	double MomentumFactor;

	NeuralNetworkLayer ParentLayer;
	NeuralNetworkLayer ChildLayer;

	public NeuralNetworkLayer()

	{
		ParentLayer = null;
		ChildLayer = null;
		LinearOutput = false;
		UseMomentum = false;
		MomentumFactor = 0.9;
	}

	public void Initialize(int NumNodes, NeuralNetworkLayer parent,
			NeuralNetworkLayer child) {
		int i, j;

		// Allocate memory
		NeuronValues = new double[NumberOfNodes];
		DesiredValues = new double[NumberOfNodes];
		Errors = new double[NumberOfNodes];

		if (parent != null) {
			ParentLayer = parent;
		}

		if (child != null) {
			ChildLayer = child;

			Weights = new double[NumberOfNodes][];
			WeightChanges = new double[NumberOfNodes][];
			for (i = 0; i < NumberOfNodes; i++) {
				Weights[i] = new double[NumberOfChildNodes];
				WeightChanges[i] = new double[NumberOfChildNodes];
			}

			BiasValues = new double[NumberOfChildNodes];
			BiasWeights = new double[NumberOfChildNodes];
		} else {
			Weights = null;
			BiasValues = null;
			BiasWeights = null;
		}

		// Make sure everything contains zeros
		for (i = 0; i < NumberOfNodes; i++) {
			NeuronValues[i] = 0;
			DesiredValues[i] = 0;
			Errors[i] = 0;

			if (ChildLayer != null)
				for (j = 0; j < NumberOfChildNodes; j++) {
					Weights[i][j] = 0;
					WeightChanges[i][j] = 0;
				}
		}

		if (ChildLayer != null)
			for (j = 0; j < NumberOfChildNodes; j++) {
				BiasValues[j] = -1;
				BiasWeights[j] = 0;
			}

	}

	public void RandomizeWeights() {
		int i, j;
		int min = 0;
		int max = 200;
		int number;

		Random rand = new Random();

		for (i = 0; i < NumberOfNodes; i++) {

			for (j = 0; j < NumberOfChildNodes; j++) {

				number = (((Math.abs(rand.nextInt()) % (max - min + 1)) + min));

				if (number > max)
					number = max;

				if (number < min)
					number = min;

				Weights[i][j] = number / 100.0f - 1;
			}
		}

		for (j = 0; j < NumberOfChildNodes; j++) {
			number = (((Math.abs(rand.nextInt()) % (max - min + 1)) + min));

			if (number > max)
				number = max;

			if (number < min)
				number = min;

			BiasWeights[j] = number / 100.0f - 1;
		}
	}

	public void CalculateErrors() {
		int i, j;
		double sum;

		if (ChildLayer == null) // output layer
		{
			for (i = 0; i < NumberOfNodes; i++) {
				Errors[i] = (DesiredValues[i] - NeuronValues[i])
						* NeuronValues[i] * (1.0f - NeuronValues[i]);
			}
		} else if (ParentLayer == null) { // input layer
			for (i = 0; i < NumberOfNodes; i++) {
				Errors[i] = 0.0f;
			}
		} else { // hidden layer
			for (i = 0; i < NumberOfNodes; i++) {
				sum = 0;
				for (j = 0; j < NumberOfChildNodes; j++) {
					sum += ChildLayer.Errors[j] * Weights[i][j];
				}
				Errors[i] = sum * NeuronValues[i] * (1.0f - NeuronValues[i]);
			}
		}
	}

	public void AdjustWeights() {
		int i, j;
		double dw;

		if (ChildLayer != null) {
			for (i = 0; i < NumberOfNodes; i++) {
				for (j = 0; j < NumberOfChildNodes; j++) {
					dw = LearningRate * ChildLayer.Errors[j] * NeuronValues[i];
					Weights[i][j] += dw + MomentumFactor * WeightChanges[i][j];
					WeightChanges[i][j] = dw;
				}
			}

			for (j = 0; j < NumberOfChildNodes; j++) {
				BiasWeights[j] += LearningRate * ChildLayer.Errors[j]
						* BiasValues[j];
			}
		}
	}

	public void CalculateNeuronValues() {
		int i, j;
		double x;

		if (ParentLayer != null) {
			for (j = 0; j < NumberOfNodes; j++) {
				x = 0;
				for (i = 0; i < NumberOfParentNodes; i++) {
					x += ParentLayer.NeuronValues[i]
							* ParentLayer.Weights[i][j];
				}
				x += ParentLayer.BiasValues[j] * ParentLayer.BiasWeights[j];

				if ((ChildLayer == null) && LinearOutput)
					NeuronValues[j] = x;
				else
					NeuronValues[j] = 1.0f / (1 + Math.exp(-x));
			}
		}
	}

}