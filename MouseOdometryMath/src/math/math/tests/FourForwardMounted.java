package math.math.tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import Jama.Matrix;
import math.math.math.Mouse;
import math.math.math.OdometryMath;

import org.junit.Test;



public class FourForwardMounted {
	
	//TODO add code to stop accounting for a mouse getting disconected, not getting an image, or glitching
	
	//robot calculated change in position variables
	public double robot_dx;
	public double robot_dy;
	public double robot_dw;
	
	public double world_dx;
	public double world_dy;
	
	public static final double TOLERANCE = 0.0001;
	
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
		
		assertEquals(5.0, result[0], TOLERANCE);
		assertEquals(0.0, result[1], TOLERANCE);
		assertEquals(0.0, result[2], TOLERANCE);
	}
	
	public static double[][] append2DArray(double[][] a, double[][] b) {
        double[][] result = new double[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
	
	//Refers to figure III.9 in the paper by Stephen Bell (page 10)
		//Reverse method
		@Test
		public void straightSidewaysSensorMotionReverse() {
			
			//hard-coded robot motion values
			robot_dx = 5;
			robot_dy = 0;
			robot_dw = 0;
			
			Mouse m1 = new Mouse(Math.PI/4, 2.0, Math.PI/4);
			Mouse m2 = new Mouse((5*Math.PI)/4, 2.0, Math.PI/4);
			
			//old way of doing this. Now it's integrated into the Mouse class
			//double[] sensorInfo = {sensor_dx1, sensor_dy1, sensor_dx2, sensor_dy2};
			double[] xArray = {Math.sin(m1.getAlpha()), Math.cos(m1.getAlpha()), Math.sin(m2.getAlpha()), Math.cos(m2.getAlpha())};
			double[] yArray = {-1 * Math.cos(m1.getAlpha()), Math.sin(m1.getAlpha()), -1 * Math.cos(m2.getAlpha()), Math.sin(m2.getAlpha())};
			double[] wArray = {m1.getR() * Math.cos(m1.getPhi()), m1.getR() * Math.sin(m1.getPhi()), m2.getR() * Math.cos(m2.getPhi()), m2.getR() * Math.sin(m2.getPhi())};
			
			double[][] infoArray = {xArray, yArray, wArray};
			Matrix a = new Matrix(infoArray);
			a = a.transpose();
			
			OdometryMath math = new OdometryMath();
			double[] result = math.calculateSensorMotion(a, robot_dx, robot_dy, robot_dw);
			
			for(double d : result){
				System.out.println(d);
			}
			
			assertEquals(5.0, result[0], TOLERANCE);
			assertEquals(0.0, result[1], TOLERANCE);
			assertEquals(-5.0, result[2], TOLERANCE);
			assertEquals(0.0, result[3], TOLERANCE);
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
			
			assertEquals(5.0, result[0], TOLERANCE);
			assertEquals(0.0, result[1], TOLERANCE);
			assertEquals(0.0, result[2], TOLERANCE);
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
			
			assertEquals(5.0, result[0], TOLERANCE);
			assertEquals(0.0, result[1], TOLERANCE);
			assertEquals(0.0, result[2], TOLERANCE);
		}

	*/
}
