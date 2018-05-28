package robot;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a robot composition. Classes whose instances are 
 * interested in the changing state of a RobotModel instance should implement 
 * the RobotModelListener interface so that their instances can be registered
 * as listeners on a RobotModel. Whenever a Robot is added to or removed from a
 * Robot Model, or whenever a Robot within the model changes (i.e. due to a 
 * move() request) the RobotModel notifies all registered listeners by firing a
 * RobotModelEvent.
 * 
 * @author Craig Sutherland
 *
 */
public class RobotModel {
	
	// Root of the robot composition.
	private CarrierRobot _root;
	
	// Boundaries governing Robot movement within a RobotModel.
	private Dimension _bounds;
	
	// List of RobotModelListeners.
	private List<RobotModelListener> _listeners;
	
	
	/**
	 * Creates a RobotModel with specified height and width bounds.
	 */
	public RobotModel(Dimension bounds) {
		_root = new CarrierRobot(0, 0, 0, 0, bounds.width, bounds.height);
		_bounds = bounds;
		_listeners = new ArrayList<RobotModelListener>();
	}

	/**
	 * Returns the root CarrierRobot of this RobotModel object.
	 */
	public CarrierRobot root() {
		return _root;
	}
	
	/**
	 * Attempts to add a new Robot to a specified CarrierRobot held within the
	 * RobotModel. If the robot cannot be added, no action is taken and this 
	 * method returns false. Possible reasons for this outcome are that the 
	 * robot is too large to fit into the CarrierRobot or that the robot is
	 * already part of a CarrierRobot. If the robot can be added, it is and
	 * this method fires a RobotModelEvent to registered listeners alerting
	 * them of the new robot.
	 * @param robot the new robot to add to this RobotModel.
	 * @param parent the intended parent of the new robot.
	 */
	public boolean add(Robot robot, CarrierRobot parent) {
		boolean success = true;
		
		try {
			parent.add(robot);
			
			// Fire event.
			fire(RobotModelEvent.makeRobotAddedEvent(robot, this));
		} catch(IllegalArgumentException e) {
			success = false;
		}
		return success;
	}
	
	/**
	 * Attempts to remove the specified Robot from this RobotModel instance. If
	 * the robot specified does not have a parent (i.e. it is not part of this
	 * RobotModel), this method has no effect. In other cases, the specified 
	 * robot is removed and registered RobotModelListeners are notified via a
	 * RobotModelEvent.
	 * @param robot the Robot to remove.
	 */
	public void remove(Robot robot) {
		// Remove robot from its parent.
		CarrierRobot parent = robot.parent();
		
		if(parent != null) {
			int index = parent.indexOf(robot);
			parent.remove(robot);
		
			// Fire event.
			fire(RobotModelEvent.makeRobotRemovedEvent(robot, parent, index, this));
		}
	}

	/**
	 * Progresses the animation. Calling this method causes each Robot in this 
	 * RobotModel to move before notifying each registered RobotModelListener 
	 * of the movement. Note that a clock() call results in ONE RobotModelEvent
	 * being fired; the event identifies the root CarrierRobot.
	 */
	public void clock() {
		_root.move(_bounds.width, _bounds.height);
		
		// Fire event.
		fire(RobotModelEvent.makeRobotMovedEvent(_root, this));
	}

	/**
	 * Registers a RobotModelListener on this RobotModel object.
	 */
	public void addRobotModelListener(RobotModelListener listener) {
		_listeners.add(listener);
	}
	
	/**
	 * Deregisters a RobotModelListener from this RobotModel object.
	 */
	public void removeRobotModelListener(RobotModelListener listener) {
		_listeners.remove(listener);
	}
	
	/*
	 * Iterates through registered RobotModelListeners and fires a 
	 * RobotModelEvent to each in turn.
	 */
	private void fire(RobotModelEvent event) {
		for(RobotModelListener listener : _listeners) {
			listener.update(event);
		}
	}
}
