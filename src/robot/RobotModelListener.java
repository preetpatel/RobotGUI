package robot;

/**
 * Interface to be implemented by classes whose instances need to be notified
 * of changes to a RobotModel. A RobotModel calls method update() on all
 * registered RobotModelListeners whenever its state changes.
 * 
 * @author Craig Sutherland
 * 
 */
public interface RobotModelListener {
	/**
	 * Notifies a RobotModelListener that a RobotModel that it has registered 
	 * interest in has changed. 
	 * @param event describes the way in which a particular RobotModel object
	 * has changed.
	 */
	void update(RobotModelEvent event);
}
