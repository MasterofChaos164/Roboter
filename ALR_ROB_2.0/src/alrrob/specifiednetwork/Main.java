package alrrob.specifiednetwork;
import alrrob.generator.TrainingSets;

public class Main {
	public TrainingSets trainingSets;
	public ExOrNetwork exOrNetwork;
	public TwoBoxesNetwork twoBoxesNetwork;
	public RobotNetwork robotNetwork;
	
	public Main() {
		trainingSets = new TrainingSets();
		
		exOrNetwork = new ExOrNetwork(this);
		twoBoxesNetwork = new TwoBoxesNetwork(this);
		robotNetwork = new RobotNetwork(this);
	}
	
	public static void main(String[] args) {
		Main main = new Main();
//		main.exOrNetwork.trainExOr();
//		main.exOrNetwork.testExOr();
//		main.twoBoxesNetwork.trainTwoBoxes();
//		main.twoBoxesNetwork.testTwoBoxes();
//		main.robotNetwork.trainRobot();
		try {
			main.robotNetwork.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
