package robot.views;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import robot.GraphicsPainter;
import robot.CarrierRobot;
import robot.Painter;
import robot.RobotModelEvent;
import robot.RobotModelListener;

/**
 * Class that presents an animation view of a RobotModel. This class implements
 * the RobotModelListener interface, hence an AnimationViewer instance is
 * notified via a RobotModelEvent whenever the model has changed. A registered
 * AnimationViewer responds to a RobotModelEvent by adding any new robot to the
 * animation, removing a deleted robot from the animation, or updating the 
 * positions of robots that have moved.
 * 
 * @author Craig Sutherland
 *
 */
public class AnimationView extends JPanel implements RobotModelListener {

	// Reference to root CarrierRobot.
	private CarrierRobot _root;
	
	/**
	 * Creates an AnimationView object with specified bounds.
	 */
	public AnimationView(Dimension bounds) {
		_root = null;
		setSize(bounds.width, bounds.height);
	}
	
	/**
	 * Implements custom painting to display the animation.
	 */
	public void paintComponent(Graphics g) {
		// Call inherited implementation to handle background painting.
		super.paintComponent(g);
		
		// Create a GraphicsPainter to paint the Swing component.
		Painter painter = new GraphicsPainter(g);
		
		/*
		 * Paint the robots, starting with the root and recursively work
		 * through the composition structure.
		 */
		if(_root != null) {
			_root.paint(painter);
		}
	}
	
	/**
	 * Updates this AnimationView so that it is consistent with the RobotModel
	 * that made the update() call.
	 */
	public void update(RobotModelEvent event) {
		_root = event.source().root();
		repaint();
	}
	
}
