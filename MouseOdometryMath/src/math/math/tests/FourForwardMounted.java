package math.math.tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import Jama.Matrix;
import math.math.math.Mouse;
import math.math.math.OdometryMath;

import org.junit.Test;



public class FourForwardMounted {
	
	//TODO add code to stop accounting for a mouse getting disconected, not getting an image, or glitching
	
	@Test
	public void straightSidewaysRobotMotion2(){
		
		//All mice pointing forwards
		Mouse m1 = new Mouse(Math.PI/4, 1.0, Math.PI/4);
		Mouse m2 = new Mouse((3*Math.PI)/4, 1.0, -1 * Math.PI/4);
		Mouse m3 = new Mouse((-3*Math.PI)/4, 1.0, -3 * Math.PI/4);
		Mouse m4 = new Mouse((-1*Math.PI)/4, 1.0, 3 * Math.PI/4);
		
		
		//Sensor input array
		double[][] bArray = {{5.0}, {0.0}, {5.0}, {0.0}, {5.0}, {0.0}, {5.0}, {0.0}};
		//Matrix b = new Matrix(bArray);
		
		
		OdometryMath math = new OdometryMath();
		double[] result = math.calculateRobotMotion(new Mouse[] {m1,m2,m3,m4}, bArray);
		
		assertEquals(5.0, result[0], Robot.TOLERANCE);
		assertEquals(0.0, result[1], Robot.TOLERANCE);
		assertEquals(0.0, result[2], Robot.TOLERANCE);
	}
	
	//Refers to figure III.9 in the paper by Stephen Bell (page 10)
		//Reverse method
		@Test
		public void straightSidewaysSensorMotionReverse() {
			
			//hard-coded robot motion values
			Robot.dx = 5;
			Robot.dy = 0;
			Robot.dw = 0;
			
			Mouse m1 = new Mouse(Math.PI/4, 2.0, Math.PI/4);
			Mouse m2 = new Mouse((5*Math.PI)/4, 2.0, Math.PI/4);
			
			//old way of doing this. Now it's integrated into the Mouse class
			//double[] sensorInfo = {sensor_dx1, sensor_dy1, sensor_dx2, sensor_dy2};
			
			
			OdometryMath math = new OdometryMath();
			double[] result = math.calculateSensorMotion(new Mouse[] {m1, m2}, Robot.dx, Robot.dy, Robot.dw);
			
			for(double d : result){
				System.out.println(d);
			}
			
			assertEquals(5.0, result[0], Robot.TOLERANCE);
			assertEquals(0.0, result[1], Robot.TOLERANCE);
			assertEquals(-5.0, result[2], Robot.TOLERANCE);
			assertEquals(0.0, result[3], Robot.TOLERANCE);
		}
		

		/*
		@Test
		public void straightSidewaysRobotMotion(){
			
			Mouse m1 = new Mouse(Math.PI/4, 2.0, Math.PI/4);
			Mouse m2 = new Mouse((5*Math.PI)/4, 2.0, Math.PI/4);
			
			//Sensor input array
			double[][] bArray = {{5.0}, {0.0}, {-5.0}, {0.0}};
			//Matrix b = new Matrix(bArray);
			
			
			OdometryMath math = new OdometryMath();
			double[] result = math.calculateRobotMotion(new Mouse[] {m1,m2}, bArray);
			
			assertEquals(5.0, result[0], Robot.TOLERANCE);
			assertEquals(0.0, result[1], Robot.TOLERANCE);
			assertEquals(0.0, result[2], Robot.TOLERANCE);
		}

		@Test
		public void straightSidewaysRobotMotion1(){
			
			Mouse m1 = new Mouse(Math.PI/4, 2.0, Math.PI/4);
			Mouse m2 = new Mouse((5*Math.PI)/4, 2.0, Math.PI/4);
			
			//Sensor input array
			double[][] bArray = {{5.0}, {0.0}, {-5.0}, {0.0}};
			//Matrix b = new Matrix(bArray);
			
			
			OdometryMath math = new OdometryMath();
			double[] result = math.calculateRobotMotion(new Mouse[] {m1,m2}, bArray);
			
			assertEquals(5.0, result[0], Robot.TOLERANCE);
			assertEquals(0.0, result[1], Robot.TOLERANCE);
			assertEquals(0.0, result[2], Robot.TOLERANCE);
		}

	*/
}
