package robot;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


/**
 * A class that implements test cases aimed at identifying bugs in the 
 * implementations of classes Robot and WheeledRobot.
 * 
 * @author Craig Sutherland
 * 
 */
public class TestRobot {
	// Fixture object that is used by the tests.
	private MockPainter _painter;

	/**
	 * This method is called automatically by the JUnit test-runner immediately
	 * before each @Test method is executed. setUp() recreates the fixture so 
	 * that there no side effects from running individual tests.
	 */
	@Before
	public void setUp() {
		_painter = new MockPainter();
	}

	/**
	 * Test to perform a simple (non-bouncing) movement, and to ensure that a
	 * Robot's position after the movement is correct.
	 */
	@Test
	public void testSimpleMove() {
		WheeledRobot robot = new WheeledRobot(100, 20, 12, 15);
		robot.paint(_painter);
		robot.move(500, 500);
		robot.paint(_painter);
		assertEquals("(rectangle 100,20,25,35)(rectangle 112,35,25,35)", 
				_painter.toString());
	}
	
	/**
	 * Test to perform a bounce movement off the right-most boundary and to
	 * ensure that the Robot's position after the movement is correct.
	 */
	@Test
	public void testRobotMoveWithBounceOffRight() {
		WheeledRobot robot = new WheeledRobot(100, 20, 12, 15);
		robot.paint(_painter);
		robot.move(135, 10000);
		robot.paint(_painter);
		robot.move(135, 10000);
		robot.paint(_painter);
		assertEquals("(rectangle 100,20,25,35)(rectangle 110,35,25,35)"
				+ "(rectangle 98,50,25,35)", _painter.toString());
	}

	/**
	 * Test to perform a bounce movement off the left-most boundary and to
	 * ensure that the Robot's position after the movement is correct.
	 */
	@Test
	public void testRobotMoveWithBounceOffLeft() {
		WheeledRobot robot = new WheeledRobot(10, 20, -12, 15);
		robot.paint(_painter);
		robot.move(10000, 10000);
		robot.paint(_painter);
		robot.move(10000, 10000);
		robot.paint(_painter);
		assertEquals("(rectangle 10,20,25,35)(rectangle 0,35,25,35)"
				+ "(rectangle 12,50,25,35)", _painter.toString());
	}

	/**
	 * Test to perform a bounce movement off the bottom right corner and to
	 * ensure that the Robot's position after the movement is correct.
	 */
	@Test
	public void testRobotMoveWithBounceOffBottomAndRight() {
		WheeledRobot robot = new WheeledRobot(10, 90, -12, 15);
		robot.paint(_painter);
		robot.move(125, 135);
		robot.paint(_painter);
		robot.move(125, 135);
		robot.paint(_painter);
		assertEquals("(rectangle 10,90,25,35)(rectangle 0,100,25,35)"
				+ "(rectangle 12,85,25,35)", _painter.toString());
	}

	/**
	*Test detection of bounce of robot on a horizontal and vertical wall
	 */
	@Test
	public void testRobotDetectBounceOffVerticalWall() {
		String result;
		WheeledRobot robot = new WheeledRobot(100, 20, 12, 15);
		result = "(Horizontal Bounce: " + robot.didBounceOffHorizontal() + " , Vertical Bounce: " + robot.didBounceOffVertical() + ")" ;
		robot.move(135, 10000);
		result = result + "(Horizontal Bounce: " + robot.didBounceOffHorizontal() + " , Vertical Bounce: " + robot.didBounceOffVertical() + ")" ;
		robot.move(135, 10000);
		result = result + "(Horizontal Bounce: " + robot.didBounceOffHorizontal() + " , Vertical Bounce: " + robot.didBounceOffVertical() + ")" ;
		assertEquals("(Horizontal Bounce: false , Vertical Bounce: false)(Horizontal Bounce: false , Vertical Bounce: true)(Horizontal Bounce: false , Vertical Bounce: false)", result);
	}

	@Test
	public void testRobotDetectBounceOffHorizontalWall() {
		String result;
		DynamicWheeledRobot robot = new DynamicWheeledRobot(20, 100, 12, 15);
		result = "(Horizontal Bounce: " + robot.didBounceOffHorizontal() + " , Vertical Bounce: " + robot.didBounceOffVertical() + ")" ;
		robot.move(10000, 140);
		result = result + "(Horizontal Bounce: " + robot.didBounceOffHorizontal() + " , Vertical Bounce: " + robot.didBounceOffVertical() + ")" ;
		robot.move(10000, 140);
		result = result + "(Horizontal Bounce: " + robot.didBounceOffHorizontal() + " , Vertical Bounce: " + robot.didBounceOffVertical() + ")" ;
		assertEquals("(Horizontal Bounce: false , Vertical Bounce: false)(Horizontal Bounce: true , Vertical Bounce: false)(Horizontal Bounce: false , Vertical Bounce: false)", result);
	}
}
