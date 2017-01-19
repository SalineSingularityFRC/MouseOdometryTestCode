package math.math.math;

import java.util.Arrays;

import Jama.Matrix;

public class OdometryMath {
	
	public double[] calculateSensorMotion(Matrix A, double robot_dx, double robot_dy, double robot_dw) {
		
		Matrix robotMotion = new Matrix(new double[][] {{robot_dx}, {robot_dy}, {robot_dw}});
		
		Matrix result = A.times(robotMotion);
		
		return result.getColumnPackedCopy();
	}
	
	public double[] calculateRobotMotion(Mouse[] mice, double[][] sensorInputsArray) {
		if(2*mice.length == sensorInputsArray.length) {
			
			Matrix b = new Matrix(sensorInputsArray);
			
			double[][] currentArray = {};
			//Coefficient Matrix
			for(Mouse m : mice){
				currentArray = append2DArray(currentArray, m.getCoefficientArray());
			}
			double[][] aArray = currentArray;
			//System.out.println("Coefficient Array:" + Arrays.deepToString(aArray));
			Matrix a = new Matrix(aArray);
			
			System.out.println("A: " + a.getRowDimension() + " x " + a.getColumnDimension());
			System.out.println("B: " + b.getRowDimension() + " x " + b.getColumnDimension());
			
			Matrix robotMotion = a.solve(b);
			double[] robotMotionArray = robotMotion.getColumnPackedCopy();
			
			System.out.println("Robot motion array:" + Arrays.toString(robotMotionArray));
			return robotMotion.getColumnPackedCopy();
		} else {
			System.out.println("Number of sensor inputs and number of mice are incompatible");
			return null;
		}
		
		
	}
	
	public static double[][] append2DArray(double[][] a, double[][] b) {
        double[][] result = new double[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
	
}
