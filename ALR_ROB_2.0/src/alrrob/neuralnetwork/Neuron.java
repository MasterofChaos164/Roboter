package alrrob.neuralnetwork;
import java.util.Vector;

public class Neuron {

	private double outputVal;
	private Vector<Connection> outputWeights = new Vector<Connection>();
	private int myIndex;
	private double gradient;
	private static double eta = 0.15; // [0.0 .. 1.] overall net training rate
	private static double alpha = 0.5; // [0.0 .. n] multiplier of last
										// deltaWeight change (momentum)

	public Neuron(int numOutputs, int myIndex) {
		this.myIndex = myIndex;
		for (int i = 0; i < numOutputs; i++) {
			outputWeights.add(new Connection());
			outputWeights.lastElement().setWeight(randomWeight());
		}
	}

	static private double randomWeight() {
		return Math.random();
	}

	public void setOutputVal(double outputVal) {
		this.outputVal = outputVal;
	}

	public double getOutputVal() {
		return outputVal;
	}

	public void feedForward(Vector<Neuron> prevLayer) {
		// TODO Auto-generated method stub
		double sum = 0.0;

		// Sum the previous layer's outputs
		// Include the bias node from the previous layer

		for (int neuronNum = 0; neuronNum < prevLayer.size(); neuronNum++) {
			sum += prevLayer.get(neuronNum).getOutputVal()
					* prevLayer.get(neuronNum).outputWeights.get(myIndex)
							.getWeight();
		}

		//System.out.println("sum: "+sum);
		outputVal = transferFunction(sum);
		//System.out.println("outputVal: "+outputVal);
	}

	static private double transferFunction(double x) {
		// tanh - output range[-1, 0..1,0]
		double tanhf = Math.tanh(x);
		//System.out.println("tanhf: "+tanhf);
		
		return tanhf;
	}

	static private double transferFunctionDerivative(double x) {
		// tanh derivative
		//System.out.println("transferFunctionDerivative: "+(1.0-x*x));
		return 1.0 - x * x;
	}

	public Vector<Connection> getOutputWeights() {
		return outputWeights;
	}

	public void calcOutputGradients(double targetVal) {
		double delta = targetVal - outputVal;
		//System.out.println("delta: "+delta);
		gradient = delta * transferFunctionDerivative(outputVal);
		//System.out.println("gradient: "+gradient);
	}

	public void calcHiddenGradients(Vector<Neuron> nextLayer) {
		double dow = sumDOW(nextLayer);
		gradient = dow * transferFunctionDerivative(outputVal);
	}

	private double sumDOW(Vector<Neuron> nextLayer) {
		double sum = 0.0;

		// Sum our contributions of the errors at the nodes we need

		for (int neuronNum = 0; neuronNum < nextLayer.size() - 1; neuronNum++) {
			sum += outputWeights.get(neuronNum).getWeight()
					* nextLayer.get(neuronNum).getGradient();
		}
		return sum;
	}

	public double getGradient() {
		return gradient;
	}

	public void updateInputWeights(Vector<Neuron> prevLayer) {
		// The weights to be update are in the Connection container
		// In the neurons in the preceding layer

		for (int neuronNum = 0; neuronNum < prevLayer.size(); neuronNum++) {
			Neuron neuron = prevLayer.get(neuronNum);
			double oldDeltaWeight = neuron.outputWeights.get(myIndex)
					.getDeltaWeight();

			double newDeltaWeight =
			// Individual input, magnified by the gradient and train rate:
			eta * neuron.getOutputVal() * gradient
			// Also add momentum = a fraction of the previous delta weight
					+ alpha * oldDeltaWeight;
			neuron.getOutputWeights().get(myIndex)
					.setDeltaWeight(newDeltaWeight);
			neuron.getOutputWeights().get(myIndex).addWeight(newDeltaWeight);
		}
	}
}
