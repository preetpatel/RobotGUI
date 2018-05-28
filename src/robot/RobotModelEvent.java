package robot;

/**
 * Class to describe a change to the state of a RobotModel object. An instance
 * of RobotModelEvent is sent in a notification message (an update() call) by a
 * RobotModel when communicating updates to its RobotModelListeners.
 * 
 * @author Craig Sutherland
 *
 */
public class RobotModelEvent {

	// Set of event types.
	public enum EventType {RobotAdded, RobotRemoved, RobotMoved};
	
	private EventType _type;      // Type of event.
	private Robot _operand;       // Robot to which the event relates.
	private RobotModel _source;   // RobotModel object that fired the event.
	private int _index;           // Index of fOperand within its parent, 
	                              // -1 if fOperand is the root CarrierRobot.
	private CarrierRobot _parent; // Parent CarrierRobot of fOperand; for 
	                              // RobotRemoved events this is the former 
	                              // parent of fOperand.
	                    
	/**
	 * Creates a RobotAdded RobotModelEvent.
	 * @param robotAdded the Robot that has been added to a RobotModel.
	 * @param source the RobotModel object that fires the event.
	 */
	public static RobotModelEvent makeRobotAddedEvent(
			Robot robotAdded, RobotModel source) {
		CarrierRobot parent = robotAdded.parent();
		int index = parent.indexOf(robotAdded);
		
		return new RobotModelEvent(EventType.RobotAdded, robotAdded, parent, index, source);
	}
	
	
	/**
	 * Creates a RobotRemoved RobotModelEvent.
	 * @param robotRemoved the Robot object that has been removed from a 
	 * RobotModel.
	 * @param formerParent the former parent of robotRemoved. 
	 * @param index the index position that robotRemoved used to be stored at
	 * within formerParent.
	 * @param source the RobotModel object that fires the event.
	 */
	public static RobotModelEvent makeRobotRemovedEvent(
			Robot robotRemoved, CarrierRobot formerParent, int index, RobotModel source) {
		return new RobotModelEvent(
				EventType.RobotRemoved, robotRemoved, formerParent, index, source);
		
	}
	
	/**
	 * Creates a RobotMoved RobotModelEvent. 
	 * @param robotMoved the Robot object that has moved.
	 * @param source the RobotModel object that fires the event.
	 */
	public static RobotModelEvent makeRobotMovedEvent(
			Robot robotMoved, RobotModel source) {
		CarrierRobot parent = robotMoved.parent();
		int index = -1;
		
		if(parent != null) {
			index = parent.indexOf(robotMoved);
		}
		
		return new RobotModelEvent(EventType.RobotMoved, robotMoved, parent, index, source);
	}
	
	/*
	 * Hidden constructor used by the static factory methods. 
	 */
	private RobotModelEvent(EventType type, Robot operand, CarrierRobot parent, int index, RobotModel source) {
		_type = type;
		_operand = operand;
		_parent = parent;
		_index = index;
		_source = source;
	}
	
	/**
	 * Returns the type of the event, one of RobotAdded, RobotRemoved, 
	 * RobotMoved.
	 */
	public EventType eventType() {
		return _type;
	}
	
	/**
	 * Returns the Robot object to which this RobotModelEvent applies.
	 */
	public Robot operand() {
		return _operand;
	}
	
	/**
	 * Returns the parent CarrierRobot of the Robot to which this 
	 * RobotModelEvent applies. If the type of this RobotModelEvent is
	 * RobotRemoval, this method returns the former parent. If the Robot to 
	 * which this RobotModelEvent applies does not have a parent (e.g. it is
	 * the root CarrierRobot) this method returns null.
	 */
	public CarrierRobot parent() {
		return _parent;
	}
	
	/**
	 * Returns the RobotModel that fired this RobotModelEvent.
	 */
	public RobotModel source() {
		return _source;
	}
	
	/**
	 * Returns the index position of the Robot object returned by operand()
	 * within its CarrierRobot parent. If the type of this RobotModelEvent is
	 * RobotRemoved, this method returns the position the Robot occupied within
	 * its parent before it was removed.
	 * @return
	 */
	public int index() {
		return _index;
	}
}
