package alrrob.specifiednetwork;

import java.util.Scanner;
import java.util.Vector;

import alrrob.neuralnetwork.Network;

public class ExOrNetwork {
	public Main main;
	public Network net;
	
	public ExOrNetwork(Main main) {
		this.main = main;
	}
	
	public void trainExOr() {
		Vector<Integer> topology = new Vector<Integer>();
		topology.add(2);
		topology.add(3);
		topology.add(1);
		double bias = 1.0;
		
		net = new Network(topology, bias);
		for(int setNum=0;setNum<main.trainingSets.exOrSet.length;setNum++) {
			Vector<Double> inputVals = new Vector<Double>();
			inputVals.add((double)main.trainingSets.exOrSet[setNum][0]);
			inputVals.add((double)main.trainingSets.exOrSet[setNum][1]);
			net.feedForward(inputVals);
			
			System.out.println((setNum+1)+". Trainingsdurchlauf\nInput 1: "+inputVals.get(0)+" Input 2: "+inputVals.get(1));
			
			Vector<Double> targetVals = new Vector<Double>();
			targetVals.add((double)main.trainingSets.exOrSet[setNum][2]);
			net.backProp(targetVals);

			Vector<Double> resultVals = new Vector<Double>();
			net.getResults(resultVals);
			
			System.out.println("Result: "+String.format("%.2f", resultVals.get(0))+" target: "+targetVals.get(0));
			net.showRecentAverageError("%.2f");
			System.out.println();
		}
	}
	
	public void testExOr() {
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("Zum Abbrechen schreibe '-1' und bestätige mit Enter");
			System.out.print("Erster Input : ");
			int input1 = scanner.nextInt();
			if(input1 == -1)
				break;
			System.out.print("Zweiter Input: ");
			int input2 = scanner.nextInt();
			if(input1 == -2)
				break;
			
			Vector<Double> inputVals = new Vector<Double>();
			inputVals.add((double)input1);
			inputVals.add((double)input2);
			net.feedForward(inputVals);
			
			Vector<Double> resultVals = new Vector<Double>();
			net.getResults(resultVals);
			
			System.out.println("Result: "+String.format("%.2f", resultVals.get(0))+"\n");
		}
	}
}
