package robot;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract superclass to represent the general concept of a Robot. This class
 * defines state common to all special kinds of Robot instances and implements
 * a common movement algorithm. Robot subclasses must override method paint()
 * to handle robot-specific painting.
 * 
 * @author Craig Sutherland
 * 
 */
public abstract class Robot {
	// === Constants for default values. ===
	protected static final int DEFAULT_X_POS = 0;
	
	protected static final int DEFAULT_Y_POS = 0;
	
	protected static final int DEFAULT_DELTA_X = 5;
	
	protected static final int DEFAULT_DELTA_Y = 5;
	
	protected static final int DEFAULT_HEIGHT = 35;

	protected static final int DEFAULT_WIDTH = 25;
	// ===

	// === Instance variables, accessible by subclasses.
	protected int _x;

	protected int _y;

	protected int _deltaX;

	protected int _deltaY;

	protected int _width;

	protected int _height;

	protected boolean _didBounceOffVertical = false;

	protected boolean _didBounceOffHorizontal = false;

	protected CarrierRobot _ParentCarrierRobot = null;

	protected String _Name = null;
	// ===

	/**
	 * Creates a Robot object with default values for instance variables.
	 */
	public Robot() {
		this(DEFAULT_X_POS, DEFAULT_Y_POS, DEFAULT_DELTA_X, DEFAULT_DELTA_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	/**
	 * Creates a Robot object with a specified x and y position.
	 */
	public Robot(int x, int y) {
		this(x, y, DEFAULT_DELTA_X, DEFAULT_DELTA_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	/**
	 * Creates a Robot instance with specified x, y, deltaX and deltaY values.
	 * The Robot object is created with a default width and height.
	 */
	public Robot(int x, int y, int deltaX, int deltaY) {
		this(x, y, deltaX, deltaY, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Creates a Robot instance with specified x, y, deltaX, deltaY, width and
	 * height values.
	 */
	public Robot(int x, int y, int deltaX, int deltaY, int width, int height) {
		_x = x;
		_y = y;
		_deltaX = deltaX;
		_deltaY = deltaY;
		_width = width;
		_height = height;
	}
	
	/**
	 * Moves this Robot object within the specified bounds. On hitting a 
	 * boundary the Robot instance bounces off and back into the two- 
	 * dimensional world. 
	 * @param width width of two-dimensional world.
	 * @param height height of two-dimensional world.
	 */
	public void move(int width, int height) {
		int nextX = _x + _deltaX;
		int nextY = _y + _deltaY;
		_didBounceOffHorizontal = false;
		_didBounceOffVertical = false;

		if (nextX <= 0) {
			nextX = 0;
			_deltaX = -_deltaX;
			_didBounceOffVertical = true;
		} else if (nextX + _width >= width) {
			nextX = width - _width;
			_deltaX = -_deltaX;
			_didBounceOffVertical = true;
		}

		if (nextY <= 0) {
			nextY = 0;
			_deltaY = -_deltaY;
			_didBounceOffHorizontal = true;
		} else if (nextY + _height >= height) {
			nextY = height - _height;
			_deltaY = -_deltaY;
			_didBounceOffHorizontal = true;
		}

		_x = nextX;
		_y = nextY;
	}

	/**
	 * Method implemented to paint the text on a robot when called by the GraphicsPainter
	 * if the robot is associated with a name. Once the name is painted, the method calls
	 * the specific robots paint method that paints the overall shape of the robot.
	 * @param painter the Painter object used for drawing
	 */
	public void paint(Painter painter) {
		if (_Name != null) {
			painter.drawCentredText(_Name,_x,_y,_width,_height);
		}
		doPaint(painter);
	}

	/**
	 * Method to be implemented by concrete subclasses to handle subclass
	 * specific painting.
	 * @param paint the Painter object used for drawing.
	 */
	protected abstract void doPaint(Painter paint);

	/**
	 * Returns this Robot object's x position.
	 */
	public int x() {
		return _x;
	}
	
	/**
	 * Returns this Robot object's y position.
	 */
	public int y() {
		return _y;
	}
	
	/**
	 * Returns this Robot object's speed and direction.
	 */
	public int deltaX() {
		return _deltaX;
	}
	
	/**
	 * Returns this Robot object's speed and direction.
	 */
	public int deltaY() {
		return _deltaY;
	}
	
	/**
	 * Returns this Robot's width.
	 */
	public int width() {
		return _width;
	}
	
	/**
	 * Returns this Robot's height.
	 */
	public int height() {
		return _height;
	}

	/**
	 *
	 * @return returns if the robot has bounced off a vertical wall
	 */
	public boolean didBounceOffVertical() {
		return _didBounceOffVertical;
	}

	/**
	 *
	 * @return returns if the robot has bounced off a horizontal wall
	 */
	public boolean didBounceOffHorizontal() {
		return _didBounceOffHorizontal;
	}
	
	/**
	 * @return Returns a String whose value is the fully qualified name of this class
	 * of object. E.g., when called on a WheeledRobot instance, this method 
	 * will return "robot.WheeledRobot".
	 */
	public String toString() {
		return getClass().getName();
	}

	/**
	 * Method to find out the parent of a robot
	 * @return null if there is no parent for the robot or the parent robot instance if there is
	 * a parent robot associated
	 */
	public CarrierRobot parent() {
		return _ParentCarrierRobot;
	}

	/**
	 * Method to get the hierarchy of a robot. This method is useful when there are robots
	 * which can carry other robots
	 * @return a list of 'Robot'(s) that are in the path of the particular robot instance.
	 *
	 */
	public List<Robot> path() {
		List<Robot> temp = new ArrayList<>();
		if (this._ParentCarrierRobot == null) {
			temp.add(this);
			return temp;
		} else {
			temp.addAll(this._ParentCarrierRobot.path());
			temp.add(this);
			return temp;
		}
	}

	/**
	 * Method to set the name of a robot. This method should be called if text needs to be painted
	 * for a robot. Once set, it will always be ensured that the name is printed on the AnimationViewer.
	 * @param name text for the name that needs to be displayed.
	 */
	public void setName(String name) {
		_Name = name;
	}
}
