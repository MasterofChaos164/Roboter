package alrrob.specifiednetwork;

import java.awt.image.BufferedImage;
import java.util.Vector;

import alrrob.generator.ImageAdapter;
import alrrob.neuralnetwork.Network;
import alrrob.ui.TwoBoxesUI;

public class TwoBoxesNetwork {
	public Main main;
	public Network net;
	public TwoBoxesUI twoBoxesUI;
	public int[][] twoBoxesRGB;
	
	public TwoBoxesNetwork(Main main) {
		this.main = main;
		twoBoxesRGB = main.trainingSets.twoBoxesRGBSet;
		twoBoxesUI = new TwoBoxesUI(main);
	}
	
	public void trainTwoBoxes() {
		Vector<Integer> topology = new Vector<Integer>();
		topology.add(2);
		topology.add(3);
		topology.add(1);
		double bias = 1.0;
		
		net = new Network(topology, bias);
		for(int count=0;count<10000;count++) {
		
		for(int row=0;row<twoBoxesRGB.length;row++) {
			for(int col=0;col<twoBoxesRGB[0].length;col++) {
				Vector<Double> inputVals = new Vector<Double>();
				inputVals.add((double)row);
				inputVals.add((double)col);
				net.feedForward(inputVals);
				
				System.out.println(((row*twoBoxesRGB.length+col)+1)+". Trainingsdurchlauf\nInput 1: "+inputVals.get(0)+" Input 2: "+inputVals.get(1));
				
				Vector<Double> targetVals = new Vector<Double>();
				targetVals.add((double)(main.trainingSets.twoBoxesRGBSet[row][col] == -1?0:1));
				net.backProp(targetVals);
				
				Vector<Double> resultVals = new Vector<Double>();
				net.getResults(resultVals);
				
				System.out.println("Result: "+String.format("%.2f", resultVals.get(0))+" target: "+targetVals.get(0));
				net.showRecentAverageError("%.2f");
				System.out.println();
			}
		}
		
		}
	}
	
	public void testTwoBoxes() {
		BufferedImage image = twoBoxesUI.image;
		for(int row=0;row<twoBoxesRGB.length;row++) {
			for(int col=0;col<twoBoxesRGB[0].length;col++) {
				Vector<Double> inputVals = new Vector<Double>();
				inputVals.add((double)row);
				inputVals.add((double)col);
				net.feedForward(inputVals);

				Vector<Double> resultVals = new Vector<Double>();
				net.getResults(resultVals);
				twoBoxesRGB[row][col] = resultVals.get(0)<0.5?-1:-16777215;
				System.out.println("["+row+"]["+col+"] "+resultVals.get(0));
			}
			//System.out.println();
		}

		image = ImageAdapter.setRGBPixelsOf(image, twoBoxesRGB);
		twoBoxesUI.repaint();
	}
}
