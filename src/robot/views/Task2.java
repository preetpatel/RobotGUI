/**
 * 
 */
package robot.views;

import org.junit.experimental.theories.Theories;

import robot.RobotModel;
import robot.RobotModelEvent;
import robot.RobotModelEvent.EventType;
import robot.RobotModelListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

/**
 * @author Preet
 *
 */
public class Task2 extends Task1 implements RobotModelListener {
	
	private List<TreeModelListener> _listeners = new ArrayList<TreeModelListener>();
	
	
	public Task2(RobotModel model) {
		super(model);
	}
	
	@Override
	public void update(RobotModelEvent robotModelEvent) {
		TreeModelEvent treeModelEvent;
		try {
			treeModelEvent = CreateTreeModelEvent(robotModelEvent);
		} catch (NullPointerException e) {
			//throw new NullPointerException();
			return;
		}
		
		switch (robotModelEvent.eventType()) {
		case RobotAdded:
			for(TreeModelListener tListener : _listeners) {
				tListener.treeNodesInserted(treeModelEvent);
			}
			break;
		case RobotRemoved:
			for(TreeModelListener tListener : _listeners) {
				tListener.treeNodesRemoved(treeModelEvent);
			}
			break;
//		case RobotMoved:
//			for(TreeModelListener tListener : _listeners) {
//				tListener.treeStructureChanged(treeModelEvent);
//			}
//			break;
		default:
			break;
		}
		
	}
	
	private TreeModelEvent CreateTreeModelEvent(RobotModelEvent event){
		return new TreeModelEvent(
				event.source(),						// source RobotModelEvent
				new TreePath(event.parent().path().toArray()), 	// Path to root/former parent
				new int[] {event.index()},			// index of Robot added/removed 
				new Object[] {event.operand()}  	// Robot added/removed
				);	
	}
	
	@Override
	public void addTreeModelListener(TreeModelListener TMListner) {
		_listeners.add(TMListner);
	}
	
	@Override
	public void removeTreeModelListener(TreeModelListener TMListner) {
		_listeners.remove(TMListner);
	}
	

}
