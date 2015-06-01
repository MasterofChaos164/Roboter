package alrrob.neuralnetwork.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pj.robot.Robot_Simulation;

public class TestRobot_Simulation {
	
	Robot_Simulation robot;

	@Before
	public void setUp() throws Exception {
		robot = new Robot_Simulation();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMoveRobot() {
		
		// Check starting location
		assertEquals (new Point(20, 20), robot.getRobotLocation());
		assertEquals (new Point(40, 27), robot.getSensorLocation());
		
		// Move for 4 seconds
		try {
			robot.moveRobotForMS(4000);
		} catch (Exception e) {
			e.getMessage();
		}
		assertEquals (new Point(40, 20), robot.getRobotLocation());
		assertEquals (new Point(60, 27), robot.getSensorLocation());
		
		// Don't move robot and check if location stays the same
		try {
			robot.moveRobotForMS(0);
			assertEquals (new Point (40, 20), robot.getRobotLocation());
			assertEquals (new Point (60, 27), robot.getSensorLocation());
		} catch (Exception e) {
			e.getMessage();
		}		
		
		// Negative value should throw an exception
		try {
			robot.moveRobotForMS(-1000);
		} catch (Exception e) {
			e.getMessage();
			assertEquals (new Point(40, 20), robot.getRobotLocation());
			assertEquals (new Point(60, 27), robot.getSensorLocation());
		}
	}

	@Test
	public void testRotateRobot() {
		
		// Check starting location
		assertEquals (new Point(20, 20), robot.getRobotLocation());
		assertEquals (new Point(40, 27), robot.getSensorLocation());
		
		// Rotate 90 degree
		try {
			robot.rotateRobot(90);
			assertEquals (new Point(20, 20), robot.getRobotLocation());
			assertEquals (new Point(27, 40), robot.getSensorLocation());
		} catch (Exception e) {
			e.getMessage();
		}
		
		// Rotate back
		try {
			robot.rotateRobot(-90);
			assertEquals (new Point (20, 20), robot.getRobotLocation());
			assertEquals (new Point (40, 27), robot.getSensorLocation());
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	@Test
	public void moveAndRotateRobot() {
		fail("Not yet implemented");
	}

}
