package alrrob.robot;

public interface Robot {
	public void moveRobotForMS(double ms) throws Exception;
//	public void moveRobot();
//	public void stopRobot();
	public void rotateRobot(double angle) throws Exception;
}
