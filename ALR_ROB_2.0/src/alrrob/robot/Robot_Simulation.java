package alrrob.robot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class Robot_Simulation implements Robot{
	
	private Point robotLocation;
	private Dimension robotSize;
	private Color robotColor;
	
	private Point sensorLocation;
	private Dimension sensorSize;
	private Color sensorColor;
	
	// Anzahl der Schritte pro Sekunde
	private static final int speed = 5;
	
	public Robot_Simulation() {

		robotSize = new Dimension(20,20);
		robotLocation = new Point(20,20);
		robotColor = Color.GREEN;
		
		sensorSize = new Dimension(6, 6);
		sensorLocation = new Point(robotLocation.x + 15 /* TODO */, robotLocation.y + 15 /*(robotSize.height / 2) - (sensorSize.height / 2)*/);
		sensorColor = Color.BLUE;
	}
	
	@Override
	public void moveRobotForMS(double ms) throws Exception {
		
		if (ms < 0)
			throw new Exception("Exception: Only positive values allowed!");
		
		double distance = speed * (ms / 1000);
		
		// Verh�ltnis von Sensormittelpunkt und Robotermittelpunkt (-> Blickrichtung)
		double x = (sensorLocation.getX() + (sensorSize.getWidth() / 2)) - (robotLocation.getX() + (robotSize.getWidth() / 2));
		double y = (sensorLocation.getY() + (sensorSize.getHeight() / 2)) - (robotLocation.getY() + (robotSize.getHeight() / 2));
		
		// Berechnung der Entfernung vom momentanen Standpunkt in x und y Richtung
		double proportion = x / y;
		double yLength = Math.sqrt(Math.pow(distance, 2) / (Math.pow(proportion, 2) + 1));
		double xLength = Math.sqrt(Math.pow(distance, 2) - Math.pow(yLength, 2));
		
		// Setzt Roboter und Sensor auf ihre neue Position
		moveRobotInDirection(xLength, yLength);
		moveSensorInDirection(xLength, yLength);
	}
	
	public void rotateRobot(double angle) throws Exception {
		
		// Macht keinen Sinn sich im Kreis zu drehen
		if (angle < -360 || angle > 360)
			throw new Exception("Exception: Angle out of range!");
		
		// Transform into radiant value
		double radiantAngle = (angle * Math.PI) / 180;
				
		double robotCenterX = robotLocation.getX() + robotSize.getWidth() / 2;
		double robotCenterY = robotLocation.getY() + robotSize.getHeight() / 2;
		
		double sensorCenterX = sensorLocation.getX() + sensorSize.getWidth() / 2;
		double sensorCenterY = sensorLocation.getY() + sensorSize.getHeight() / 2;
		
		double xNew = robotCenterX + (sensorCenterX - robotCenterX) * Math.cos(radiantAngle) - (sensorCenterY - robotCenterY) * Math.sin(radiantAngle);
		double yNew = robotCenterY + (sensorCenterX - robotCenterX) * Math.sin(radiantAngle) + (sensorCenterY - robotCenterY) * Math.cos(radiantAngle);
		
		sensorLocation.setLocation(xNew - (sensorSize.getWidth() / 2), yNew - (sensorSize.getHeight() / 2));
	}
	
	public void moveRobotInDirection(double xLength, double yLength) {
		robotLocation.setLocation(robotLocation.getX() + xLength, robotLocation.getY() + yLength);
	}
	
	public Point getRobotLocation(){
		return robotLocation;
	}
	
	public void moveSensorInDirection(double xLength, double yLength) {
		sensorLocation.setLocation(sensorLocation.getX() + xLength, sensorLocation.getY() + yLength);		
	}
	
	public Point getSensorLocation() {
		return sensorLocation;
	}
	
	public Dimension getRobotSize() {
		return robotSize;
	}
	
	public Color getRobotColor() {
		return robotColor;
	}
	
	public Dimension getSensorSize() {
		return sensorSize;
	}
	
	public Color getSensorColor() {
		return sensorColor;
	}
}
