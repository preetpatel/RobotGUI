package robot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Class to test class CarrierRobot according to its specification.
 * @author Craig Sutherland
 * 
 */
public class TestCarrierRobot {
	
	private CarrierRobot _topLevelNest;
	private CarrierRobot _midLevelNest;
	private CarrierRobot _bottomLevelNest;
	private Robot _simpleRobot;


	/**
	 * Creates a Robot composition hierarchy with the following structure:
	 *   CarrierRobot (topLevelNest)
	 *     |
	 *     --- CarrierRobot (midLevelNest)
	 *           |
	 *           --- CarrierRobot (bottomLevelNest)
	 *           |
	 *           --- RectangleRobot (simpleRobot)
	 */
	@Before
	public void setUpNestedStructure() throws Exception {
		_topLevelNest = new CarrierRobot(0, 0, 2, 2, 100, 100);
		_midLevelNest = new CarrierRobot(0, 0, 2, 2, 50, 50);
		_bottomLevelNest = new CarrierRobot(5, 5, 2, 2, 10, 10);
		_simpleRobot = new WheeledRobot(1, 1, 1, 1, 5, 5);
		
		_midLevelNest.add(_bottomLevelNest);
		_midLevelNest.add(_simpleRobot);
		_topLevelNest.add(_midLevelNest);
	}
	
	/**
	 * Checks that methods move() and paint() correctly move and paint a 
	 * CarrierRobot's contents.
	 */
	@Test
	public void testBasicMovementAndPainting() {
		Painter painter = new MockPainter();
		
		_topLevelNest.move(500, 500);
		_topLevelNest.paint(painter);
		assertEquals("(rectangle 2,2,100,100)(rectangle 2,2,50,50)(rectangle 7,7,10,10)(rectangle 2,2,5,5)", painter.toString());
	}
	
	/**
	 * Checks that method add successfuly adds a valid Robot, supplied as 
	 * argument, to a CarrierRobot instance. 
	 */
	@Test
	public void testAdd() {
		// Check that topLevelNest and midLevelNest mutually reference each other.
		assertSame(_topLevelNest, _midLevelNest.parent());
		assertTrue(_topLevelNest.contains(_midLevelNest));
		
		// Check that midLevelNest and bottomLevelNest mutually reference each other.
		assertSame(_midLevelNest, _bottomLevelNest.parent());
		assertTrue(_midLevelNest.contains(_bottomLevelNest));
	}
	
	/**
	 * Check that method add throws an IlegalArgumentException when an attempt 
	 * is made to add a Robot to a CarrierRobot instance where the Robot 
	 * argument is already part of some CarrierRobot instance.
	 */
	@Test
	public void testAddWithArgumentThatIsAChildOfSomeOtherCarrierRobot() {
		try {
			_topLevelNest.add(_bottomLevelNest);
			fail();
		} catch(IllegalArgumentException e) {
			// Expected action. Ensure the state of topLevelNest and 
			// bottomLevelNest has not been changed.
			assertFalse(_topLevelNest.contains(_bottomLevelNest));
			assertSame(_midLevelNest, _bottomLevelNest.parent());
		}
	}
	
	/**
	 * Check that method add throws an IllegalArgumentException when an attempt
	 * is made to add a robot that will not fit within the bounds of the 
	 * proposed CarrierRobot object.
	 */
	@Test
	public void testAddWithOutOfBoundsArgument() {
		Robot rectangle = new WheeledRobot(80, 80, 2, 2, 50, 50);
		
		try {
			_topLevelNest.add(rectangle);
			fail();
		} catch(IllegalArgumentException e) {
			// Expected action. Ensure the state of topLevelNest and 
			// rectangle has not been changed.
			assertFalse(_topLevelNest.contains(rectangle));
			assertNull(rectangle.parent());
		}
	}
	
	/**
	 * Check that method remove breaks the two-way link between the Robot 
	 * object that has been removed and the CarrierRobot it was once part of.
	 */
	@Test
	public void testRemove() {
		_topLevelNest.remove(_midLevelNest);
		assertFalse(_topLevelNest.contains(_midLevelNest));
		assertNull(_midLevelNest.parent());
	}
	
	/**
	 * Check that method robotAt returns the Robot object that is held at a
	 * specified position within a CarrierRobot instance.
	 */
	@Test
	public void testRobotAt() {
		assertSame(_midLevelNest, _topLevelNest.robotAt(0));
	}
	
	/**
	 * Check that method robotAt throws a IndexOutOfBoundsException when called
	 * with an invalid index argument.
	 */
	@Test
	public void testRobotAtWithInvalidIndex() {
		try {
			_topLevelNest.robotAt(1);
			fail();
		} catch(IndexOutOfBoundsException e) {
			// Expected action.
		}
	}
	
	/**
	 * Check that method robotCount returns zero when called on a CarrierRobot
	 * object without children.
	 */
	@Test
	public void testRobotCountOnEmptyParent() {
		assertEquals(0, _bottomLevelNest.robotCount());
	}
	
	/**
	 * Check that method robotCount returns the number of children held within 
	 * a CarrierRobot instance - where the number of children > 0.
	 */
	@Test
	public void testRobotCountOnNonEmptyParent() {
		assertEquals(2, _midLevelNest.robotCount());
	}
	
	/**
	 * Check that method indexOf returns the index position within a
	 * CarrierRobot instance of a Robot held within the CarrierRobot. 
	 */
	@Test
	public void testIndexOfWith() {
		assertEquals(0, _topLevelNest.indexOf(_midLevelNest));
		assertEquals(1, _midLevelNest.indexOf(_simpleRobot));
	}
	
	/**
	 * Check that method indexOf returns -1 when called with an argument that
	 * is not part of the CarrierRobot callee object.
	 */
	@Test
	public void testIndexOfWithNonExistingChild() {
		assertEquals(-1, _topLevelNest.indexOf(_bottomLevelNest));
	}
	
	/**
	 * Check that Robot's path method correctly returns the path from the root
	 * CarrierRobot object through to the Robot object that path is called on.
	 */
	@Test
	public void testPath() {
		List<Robot> path = _simpleRobot.path();
		
		assertEquals(3, path.size());
		assertSame(_topLevelNest, path.get(0));
		assertSame(_midLevelNest, path.get(1));
		assertSame(_simpleRobot, path.get(2));
	}
	
	/**
	 * Check that Robot's path method correctly returns a singleton list
	 * containing only the callee object when this Robot object has no parent.
	 */
	@Test
	public void testPathOnRobotWithoutParent() {
		List<Robot> path = _topLevelNest.path();
		
		assertEquals(1, path.size());
		assertSame(_topLevelNest, path.get(0));
	}
}
