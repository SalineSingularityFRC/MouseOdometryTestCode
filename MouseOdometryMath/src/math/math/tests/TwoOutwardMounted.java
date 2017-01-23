package math.math.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import math.math.math.Mouse;
import math.math.math.OdometryMath;

public class TwoOutwardMounted {

	Mouse m1 = new Mouse(Math.PI / 4, 2, 0.0);
	Mouse m2 = new Mouse(5 * (Math.PI / 4), 2, 0.0);

	// reverse method
	@Test
	public void reverseRight1Foot() {

		Robot.dx = 1.0;
		Robot.dy = 0.0;
		Robot.dw = 0.0;

		OdometryMath math = new OdometryMath();
		double[] result = math.calculateSensorMotion(new Mouse[] { m1, m2 }, Robot.dx, Robot.dy, Robot.dw);

		for (double d : result) {
			System.out.println(d);
		}

		assertEquals(1 / Math.sqrt(2.0), result[0], Robot.TOLERANCE); // sensor 1 dx
		assertEquals(1 / Math.sqrt(2.0), result[1], Robot.TOLERANCE); // sensor 1 dy
		assertEquals(-1 / Math.sqrt(2.0), result[2], Robot.TOLERANCE); // sensor 2 dx
		assertEquals(-1 / Math.sqrt(2.0), result[3], Robot.TOLERANCE); // sensor 2 dy

	}

	// forwards method
	@Test
	public void right1Foot() {
		
		//sensor input array
		double[][] bArray = {{1 / Math.sqrt(2.0)}, {1 / Math.sqrt(2.0)}, {-1 / Math.sqrt(2.0)} , {-1 / Math.sqrt(2.0)}};
		
		OdometryMath math = new OdometryMath();
		double[] result = math.calculateRobotMotion(new Mouse[] {m1, m2}, bArray);
		
		assertEquals(1.0, result[0], Robot.TOLERANCE); //dX
		assertEquals(0.0, result[1], Robot.TOLERANCE); //dY
		assertEquals(0.0, result[2], Robot.TOLERANCE); //dw
		
	}

}
