package alrrob.robot;

import java.util.Date;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class Robot_Lego implements Robot {

	LightSensor lightSensor;
	NXTRegulatedMotor motorA;
	NXTRegulatedMotor motorB;

	public Robot_Lego() {
		lightSensor = new LightSensor(SensorPort.S1);
		motorA = Motor.A;
		motorB = Motor.B;
	}
	
	@Override
	public void moveRobotForMS(int ms) throws Exception {
		moveRobot();
		wait(ms);
		stopRobot();
	}
	
	public void moveRobot() {
		motorA.forward();
		motorB.forward();
	}
	
	public void stopRobot() {
		motorA.stop();
		motorB.stop();
	}

	@Override
	public void rotateRobot(double angle) throws Exception {
		if (angle > 360 || angle < -360)
			throw new Exception("Angle out of range");

		// TODO Richtig berechnen
		int ms = (int) angle;

		// motorA.getSpeed();
		// motorA.rotate(90);
		// motorB.rotate(-90);
		if (angle > 0) {
			motorA.forward();
			motorB.backward();
		} else if (angle < 0) {
			motorA.backward();
			motorB.forward();
		}
		wait(ms);

		motorA.stop();
		motorB.stop();
	}

	public void start() throws Exception {

		Date date = new Date();
		int leftRight = 1;
		
		// Checks if the robot was on the line in the past 10 seconds, if not abort
		while (new Date().getTime() - date.getTime() < 10000) {
			if (lightSensor.getLightValue() < 10) {
				moveRobotForMS(100);
				date = new Date();
			}
			else {
				
				// TODO Add value calculated from the neural network
				rotateRobot(leftRight * 45);

				if (leftRight == 1)
					leftRight -= 2;
				else
					leftRight += 2;
				moveRobotForMS(100);
			}
		}
	}
}