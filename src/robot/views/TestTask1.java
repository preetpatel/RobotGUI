package robot.views;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;

import javax.swing.tree.TreeModel;

import org.junit.Before;
import org.junit.Test;

import robot.CarrierRobot;
import robot.WheeledRobot;
import robot.Robot;
import robot.RobotModel;

/**
 * Class to test the TreeModel implementation of class Task1.
 * 
 * @author Craig Sutherland
 * 
 */
public class TestTask1 {

	private CarrierRobot _root;
	private CarrierRobot _carrierRobot;
	private Robot _simpleRobot;
	private TreeModel _adapter;

	/**
	 * Creates a simple Robot hierarchy as the common fixture for all test
	 * case methods.
	 */
	@Before
	public void setUpRobotModel() {
		RobotModel model = new RobotModel(new Dimension(500, 500));
		_adapter = new Task1(model);
			
		_root = model.root();
		_carrierRobot = new CarrierRobot(0, 0, 2, 2, 100, 100);
		_simpleRobot = new WheeledRobot(0, 0, 1, 1, 50, 50);
			
		model.add(_carrierRobot, _root);
		model.add(_simpleRobot, _root);
	}
	
	/**
	 * Checks whether getRoot() returns the root CarrierRobot of a the 
	 * RobotModel as expected.
	 */
	@Test
	public void test_getRoot() {
		CarrierRobot nest = ( CarrierRobot )_adapter.getRoot();
		assertSame( _root, nest );
	}
	
	/**
	 * Checks whether getChildCount() returns zero for an empty CarrierRobot.
	 */
	@Test
	public void test_getChildCount_OnEmptyCarrierRobot() {
		int numberOfChildren = _adapter.getChildCount( _carrierRobot );
		assertEquals( numberOfChildren, _carrierRobot.robotCount() );
	}
	
	/**
	 * Checks whether getChildCount() returns the actual number of children 
	 * contained in a CarrierRobot.
	 */
	@Test
	public void test_getChildCount_OnNonEmptyCarrierRobot() {
		int expectedNumberOfChildren = _adapter.getChildCount( _root );
		int actualNumberOfChidren = _root.robotCount();
		
		assertEquals( expectedNumberOfChildren, actualNumberOfChidren );
	}
	
	/**
	 * Checks whether getChildCount() returns zero when invoked with an 
	 * argument that refers to a simple Robot instance.
	 */
	@Test
	public void test_getChildCount_OnSimpleRobot() {
		int actualNumberOfChildren = _adapter.getChildCount( _simpleRobot );
		assertEquals( 0, actualNumberOfChildren );
	}
	
	/**
	 * Checks whether isLeaf() returns false, as required, when supplied with
	 * an empty CarrierRobot as argument.
	 */
	@Test
	public void test_isLeaf_OnEmptyCarrierRobot() {
		assertFalse( _adapter.isLeaf( _carrierRobot ) );
	}

	/**
	 * Checks whether isLeaf() returns false, as required, when supplied with
	 * a CarrierRobot that contains children.
	 */	
	@Test
	public void test_isLeaf_OnNonEmptyCarrierRobot() {
		assertFalse( _adapter.isLeaf( _root ) );
	}
	
	/**
	 * Checks whether isLeaf() returns true, as required, when supplied with
	 * a simple Robot as argument.
	 */
	@Test
	public void test_isLeaf_OnSimpleRobot() {
		assertTrue( _adapter.isLeaf( _simpleRobot ) );
	}
	
	/**
	 * Checks whether getChild() correctly returns a reference to a 
	 * particular child Robot object. The arguments supplied to getChild() are
	 * a reference to a CarrierRobot and the index position within the
	 * CarrierRobot's collection of children that identifies the child Robot 
	 * sought. This particular test supplies a valid index argument.
	 */
	@Test
	public void test_getChild_OnCarrierRobotWithInRangeIndex() {
		assertSame( _carrierRobot, _adapter.getChild( _root, 0 ) );
	}
	
	/**
	 * Checks whether getChild() returns null, as specified, when an index
	 * argument value is supplied that is out of range. The argument is out of
	 * range if it is negative or >= the number of children contained in the 
	 * CarrierRobot, which is the first argument.
	 */
	@Test
	public void test_getChild_OnCarrierRobotWithOutOfRangeIndex() {
		assertNull( _adapter.getChild( _root, 2 ) );
	}
	
	/**
	 * Checks whether getChild() returns null, as it should when supplied
	 * with a reference to a simple Robot object as the first argument.
	 */
	@Test
	public void test_getChild_OnSimpleRobot() {
		assertNull( _adapter.getChild( _simpleRobot, 0 ) );
	}
	
	/**
	 * Checks whether getIndexOfChild() returns -1 as specified when supplied
	 * with a reference to a Robot (the second argument) which is not a child of
	 * the CarrierRobot supplied as the first argument.
	 */
	@Test
	public void test_getIndexOfChild_OnCarrierRobotWithNonChild() {
		Robot newRobot = new WheeledRobot( 0, 0, 1, 1, 10, 10 );
		assertEquals( -1, _adapter.getIndexOfChild( _root, newRobot ) );
	}
	
	/**
	 * Checks whether getIndexOfChild() returns the correct index position of
	 * a Robot (second argument) that is a child of a CarrierRobot (the first 
	 * argument).
	 */
	@Test
	public void test_getIndexOfChild_OnCarrierRobotWithChild() {
		assertEquals( 1, _adapter.getIndexOfChild( _root, _simpleRobot ) );
	}
	
	/**
	 * Checks whether getIndexOfChild() returns -1 when the first argument
	 * refers to a simple Robot.
	 */
	@Test
	public void test_getIndexOfChild_OnSimpleRobot() {
		assertEquals( -1, _adapter.getIndexOfChild( _simpleRobot, _root ) );
	}

}


