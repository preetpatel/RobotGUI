package robot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



/**
 * Simple GUI program to show an animation of robots. Class AnimationViewer is
 * a special kind of GUI component (JPanel), and as such an instance of 
 * AnimationViewer can be added to a JFrame object. A JFrame object is a 
 * window that can be closed, minimized, and maximized. The state of an
 * AnimationViewer object comprises a list of Robots and a Timer object. An
 * AnimationViewer instance subscribes to events that are published by a Timer.
 * In response to receiving an event from the Timer, the AnimationViewer iterates 
 * through a list of Robots requesting that each Robot paints and moves itself.
 * 
 * @author Craig Sutherland
 * 
 */
@SuppressWarnings("serial")
public class AnimationViewer extends JPanel implements ActionListener {
	// Frequency in milliseconds for the Timer to generate events.
	private static final int DELAY = 20;

	// Collection of Robots to animate.
	private List<Robot> _robots;

	private Timer _timer = new Timer(DELAY, this);

	/**
	 * Creates an AnimationViewer instance with a list of Robot objects and 
	 * starts the animation.
	 */
	public AnimationViewer() {
		_robots = new ArrayList<Robot>();
	
		// Populate the list of Robots.
		WheeledRobot withName = new WheeledRobot(0, 0, 2, 3,30,30);
		withName.setName("Hello Darkness My Old Friend");
		//_robots.add(withName);
		//_robots.add(new WheeledRobot(10, 10, 5, 7));
		_robots.add(new FlyingRobot(13,13,4,8));
		_robots.add(new TrackedRobot(13,23,2,5, 60,60));
		//_robots.add(new TrackedRobot(11,13,2,5));
		//_robots.add(new TrackedRobot(11,21,5,1, 40,40));
		//_robots.add(new TrackedRobot(15,12,4,3, 30,30));
		_robots.add(new DynamicWheeledRobot(23,16,2,6, 45,45));
		//_robots.add(new DynamicWheeledRobot(90,1,7,6, 60,45, Color.red));
		_robots.add(new AggrgateRobot(11,19,6,4, 45,45));

		CarrierRobot _topLevelNest = new CarrierRobot(0, 0, 2, 2, 200, 200);
		CarrierRobot _midLevelNest = new CarrierRobot(0, 0, 1, 1, 99, 99);
		CarrierRobot _bottomLevelNest = new CarrierRobot(5, 5, 1, 2, 80, 80);
		AggrgateRobot _simpleRobot = new AggrgateRobot(1, 1, 1, 1, 10, 10);

		_bottomLevelNest.add(withName);
		_midLevelNest.add(_bottomLevelNest);
		_midLevelNest.add(_simpleRobot);
		_topLevelNest.add(_midLevelNest);

		_robots.add(_topLevelNest);

		//_robots.add(new AggrgateRobot(60,11,3,4, 30,30));
		//_robots.add(new DynamicWheeledRobot(5, 5, -12, -15));


		// Start the animation.
		_timer.start();
	}

	/**
	 * Called by the Swing framework whenever this AnimationViewer object
	 * should be repainted. This can happen, for example, after an explicit 
	 * repaint() call or after the window that contains this AnimationViewer 
	 * object has been opened, exposed or moved.
	 * 
	 */
	public void paintComponent(Graphics g) {
		// Call inherited implementation to handle background painting.
		super.paintComponent(g);
		
		// Calculate bounds of animation screen area.
		int width = getSize().width;
		int height = getSize().height;
		
		// Create a GraphicsPainter that Robot objects will use for drawing.
		// The GraphicsPainter delegates painting to a basic Graphics object.
		Painter painter = new GraphicsPainter(g);
		
		// Progress the animation.
		for(Robot robot : _robots) {
			robot.paint(painter);
			robot.move(width, height);
		}
	}

	/**
	 * Notifies this AnimationViewer object of an ActionEvent. ActionEvents are
	 * received by the Timer.
	 */
	public void actionPerformed(ActionEvent e) {
		// Request that the AnimationViewer repaints itself. The call to 
		// repaint() will cause the AnimationViewer's paintComponent() method 
		// to be called.
		repaint();
	}
	
	
	/**
	 * Main program method to create an AnimationViewer object and display this
	 * within a JFrame window.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Animation viewer");
				frame.add(new AnimationViewer());
		
				// Set window properties.
				frame.setSize(500, 500);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
