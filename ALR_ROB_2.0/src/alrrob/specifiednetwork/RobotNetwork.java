package alrrob.specifiednetwork;

import java.awt.Point;
import java.util.Vector;

import alrrob.neuralnetwork.Network;
import alrrob.robot.Robot_Simulation;
import alrrob.ui.RobotUI;

public class RobotNetwork {
	public Main main;
	public Network net;
	private Robot_Simulation robot;
	public RobotUI robotUI;
	
	public RobotNetwork(Main main) {
		this.main = main;
		robot = new Robot_Simulation();
		robotUI = new RobotUI(main, robot);
	}
	
	public void start() throws Exception {

		int counter = 0;

		for (int i = 0; i < 10; i++) {
			// TODO Change 3 to something relative to sensor size
			while (robotUI.isOnLine(robot.getSensorLocation().x + 3, robot.getSensorLocation().y + 3) == false) {
				robot.moveRobotForMS(200);
				robotUI.repaint();
				System.out.println("Position: " + robot.getRobotLocation().x + " " + robot.getRobotLocation().y);
				Thread.sleep(50);
				counter++;
			}
			System.out.println("was on line");
		}
	}
	
	public void trainRobot() {
		Point robotLocation = new Point();
		Vector<Integer> topology = new Vector<Integer>();
		topology.add(2);
		topology.add(3);
		topology.add(1);
		double bias = 1.0;
		
		net = new Network(topology, bias);
		while(true) {
			//TODO: Merke Zeit

			try {
				robot.moveRobotForMS(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//if(Roboter is ausserhalb vom Pfad)
				robotLocation.x = main.robotNetwork.robot.getRobotLocation().x;
				robotLocation.y = main.robotNetwork.robot.getRobotLocation().y;
				Vector<Double> inputVals = new Vector<Double>();
				
				inputVals.add((double)robotLocation.x);
				inputVals.add((double)robotLocation.y);
				net.feedForward(inputVals);
				
				Vector<Double> targetVals = new Vector<Double>();
				targetVals.add(1.0); // 1.0 ist Schwarz und 0 ist Weiss
				net.backProp(targetVals);
		
				Vector<Double> resultVals = new Vector<Double>();
				net.getResults(resultVals);
				
				System.out.println("Result: "+String.format("%.2f", resultVals.get(0))+" target: "+targetVals.get(0));
				net.showRecentAverageError("%.2f");
				System.out.println();
				//TODO rotateRobot() aufrufen
			//}
				
			robotUI.repaint();
		}
	}
}
