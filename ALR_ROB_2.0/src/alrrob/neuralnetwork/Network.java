package alrrob.neuralnetwork;
import java.util.Vector;

public class Network {

	private Vector<Vector<Neuron>> layers = new Vector<Vector<Neuron>>(); // layers[layerNum][neuronNum]
	private double error;
	private double recentAverageError;
	private double recentAverageSmoothingFactor;

	public Network(Vector<Integer> topology, double bias) {
		int numLayers = topology.size();
		for (int layerNum = 0; layerNum < numLayers; layerNum++) {
			// init a new Layer
			layers.add(new Vector<Neuron>());
			System.out.println("Made Layer: " + (layerNum + 1));
			// each neuron has a number of outputs like the number of neurons on
			// next layer except the last neuron
			int numOutputs = layerNum == topology.size() - 1 ? 0 : topology
					.get(layerNum + 1);

			// init new Neurons of one new Layer with bias
			int neuronNum;
			for (neuronNum = 0; neuronNum <= topology.get(layerNum); neuronNum++) {
				layers.lastElement().add(new Neuron(numOutputs, neuronNum));
				System.out.println("Made Neuron: " + (neuronNum + 1));
			}
			
			// init Output Value of bias
			layers.lastElement().lastElement().setOutputVal(bias);
		}
	}
	
	public void showRecentAverageError(String format) {
		System.out.println("Recent Average Error: "+String.format(format, recentAverageError));
	}

	public void feedForward(Vector<Double> inputVals) {
		assert (inputVals.size() == layers.get(0).size() - 1);
		
		// Assign the input values into the input neurons
		for (int i = 0; i < inputVals.size(); i++) {
			layers.get(0).get(i).setOutputVal(inputVals.get(i));
		}

		// Forward propagate
		for (int layerNum = 1; layerNum < layers.size(); layerNum++) {
			Vector<Neuron> prevLayer = layers.get(layerNum - 1);
			for (int neuronNum = 0; neuronNum < layers.get(layerNum).size() - 1; neuronNum++) {
				layers.get(layerNum).get(neuronNum).feedForward(prevLayer);
			}
		}
	}

	public void backProp(Vector<Double> targetVals) {
		// Calculate overall net error (RMS of output neuron errors)
		// RMS = "Root Mean Square Error"
		Vector<Neuron> outputLayer = layers.lastElement();
		error = 0.0;

		for (int neuronNum = 0; neuronNum < outputLayer.size() - 1; neuronNum++) {
			double delta = targetVals.get(neuronNum)
					- outputLayer.get(neuronNum).getOutputVal();
			error += delta * delta;
		}
		error /= (outputLayer.size() - 1); // Get average error squared
		error = Math.sqrt(error); // RMS

		// Implement a recent average

		recentAverageError = (recentAverageError * recentAverageSmoothingFactor + error)
				/ (recentAverageSmoothingFactor + 1.0);
		// Calculate output layer gradients

		for (int neuronNum = 0; neuronNum < outputLayer.size() - 1; neuronNum++) {
			outputLayer.get(neuronNum).calcOutputGradients(
					targetVals.get(neuronNum));
		}

		// Calculate gradients on hidden layers

		for (int layerNum = layers.size() - 2; layerNum > 0; layerNum--) {
			Vector<Neuron> hiddenLayer = layers.get(layerNum);
			Vector<Neuron> nextLayer = layers.get(layerNum + 1);

			for (int neuronNum = 0; neuronNum < hiddenLayer.size(); neuronNum++) {
				hiddenLayer.get(neuronNum).calcHiddenGradients(nextLayer);
			}
		}

		// For all layers from outputs to first hidden layer
		// update connection weights

		for (int layerNum = layers.size() - 1; layerNum > 0; layerNum--) {
			Vector<Neuron> layer = layers.get(layerNum);
			Vector<Neuron> prevLayer = layers.get(layerNum - 1);

			for (int neuronNum = 0; neuronNum < layer.size() - 1; neuronNum++) {
				layer.get(neuronNum).updateInputWeights(prevLayer);
			}
		}
	}

	public void getResults(Vector<Double> resultVals) {
		resultVals.clear();
		for (int neuronNum = 0; neuronNum < layers.lastElement().size() - 1; neuronNum++) {
			resultVals.add(layers.lastElement().get(neuronNum).getOutputVal());
		}
	}

}
