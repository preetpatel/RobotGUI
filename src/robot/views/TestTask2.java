package robot.views;

import java.awt.Dimension;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import org.junit.Before;
import org.junit.Test;

import robot.CarrierRobot;
import robot.WheeledRobot;
import robot.Robot;
import robot.RobotModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Class to test the event notification mechanism of class 
 * Task3.
 * 
 * @author Craig Sutherland
 * 
 */
public class TestTask2 {

	private RobotModel _model;
	private CarrierRobot _root;
	private CarrierRobot _emptyNest;
	private Robot _simpleRobot;
	private Robot _newRobot;
	private Task2 _adapter;
	private boolean _listenerMethodCalled;

	
	/**
	 * Creates a CarrierRobot structure as the fixture for each test case.
	 */
	@Before
	public void setUpRobotModel() {
		// Create model.
		_model = new RobotModel(new Dimension(500, 500));
		_root = _model.root();
		
		// Create robots.
		_emptyNest = new CarrierRobot(0, 0, 1, 1, 100, 100);
		_simpleRobot = new WheeledRobot(0, 0, 1, 1, 20, 20);
		_newRobot = new WheeledRobot(0, 0, 1, 1, 20, 20);
		
		// Populate model.
		_model.add(_emptyNest, _root);
		_model.add(_simpleRobot, _root);
		
		// Create the adapter.
		_adapter = new Task2(_model);
			
		// Register adapter as a listener of the model.
		_model.addRobotModelListener(_adapter);
			
		_listenerMethodCalled = false;
	}
	
	/**
	 * Checks that calling Task2's update() method with a RobotModelEvent that
	 * describes a Robot's removal from a RobotModel results in a correctly 
	 * constructed TreeModelEvent being sent to a registered TreeModelListener.
	 */	
	@Test
	public void test_RobotRemoval() {
		_adapter.addTreeModelListener( new TreeModelListener() {

			public void treeNodesChanged( TreeModelEvent e ) {
				// Wrong TreeModelListener method called.
				fail();
			}

			public void treeNodesInserted( TreeModelEvent e ) {
				// Wrong TreeModelListener method called.
				fail();
			}

			public void treeNodesRemoved( TreeModelEvent e ) {
				_listenerMethodCalled = true;
				
				/* Unpack event. */
				int[] indices = e.getChildIndices();
				Object[] children = e.getChildren();
				Object[] path = e.getPath();
				
				/* 
				 * Check the indices array identifies the index position of the
				 * removed node BEFORE it was removed. 
				 */
				assertNotNull( indices );
				assertEquals( 1, indices.length );
				assertEquals( 1, indices[ 0 ] );
				
				/* Check the children array contains the single removed Robot. */
				assertNotNull( children );
				assertEquals( 1, children.length );
				assertSame( _simpleRobot, children[ 0 ] );

				/* 
				 * Check the path to the former parent of the changed node is 
				 * correct. 
				 */
				assertEquals( 1, path.length );
				assertSame( _root, path[ 0 ] );
			}

			public void treeStructureChanged( TreeModelEvent e ) {
				fail();
			}
		} );
		
		/*
		 * Cause the RobotModel to fire a RobotModelEvent describing a robot
		 * addition.
		 */ 
		_model.remove( _simpleRobot );
		assertTrue( _listenerMethodCalled );
	}

	/**
	 * Checks that calling Task2's update() method with a RobotModelEvent that 
	 * describes a Robot's addition to a RobotModel results in a correctly 
	 * constructed TreeModelEvent being sent to a registered TreeModelListener.
	 */	
	@Test
	public void test_robotAdded() {
		_adapter.addTreeModelListener( new TreeModelListener() {

			public void treeNodesChanged( TreeModelEvent e ) {
				// Wrong TreeModelListener method called.
				fail();
			}

			public void treeNodesInserted( TreeModelEvent e ) {
				_listenerMethodCalled = true;
				
				/* Unpack event. */
				int[] indices = e.getChildIndices();
				Object[] children = e.getChildren();
				Object[] path = e.getPath();
				
				/* 
				 * Check the indices array identifies the index position of the
				 * inserted node (i.e. after insertion). 
				 */
				assertNotNull( indices );
				assertEquals( 1, indices.length );
				assertEquals( 0, indices[ 0 ] );
				
				/* Check the children array contains the single inserted . */
				assertNotNull( children );
				assertEquals( 1, children.length );
				assertSame( _newRobot, children[ 0 ] );
 
				/* Check the path to the inserted node's parent is correct. */
				assertEquals( 2, path.length );
				assertSame( _root, path[ 0 ] );
				assertSame( _emptyNest, path[ 1 ] ); // Now not empty!			
			}

			public void treeNodesRemoved( TreeModelEvent e ) {
				// Wrong TreeModelListener method called.
				fail();
			}

			public void treeStructureChanged( TreeModelEvent e ) {
				// Wrong TreeModelListener method called.
				fail();
			}
		} );
		
		/*
		 * Cause the RobotModel to fire a RobotModelEvent describing a robot
		 * addition.
		 */ 
		_model.add( _newRobot, _emptyNest );
		assertTrue( _listenerMethodCalled );
	}
}
