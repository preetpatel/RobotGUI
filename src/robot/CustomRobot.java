package robot;

import java.awt.Image;

/**
 * Class to represent a robot that displays an image.
 * 
 * @author Craig Sutherland
 * 
 */
public class CustomRobot extends WheeledRobot {

	private Image _picture;
	
	/**
	 * Creates a CustomRobot object with a specified x and y position.
	 */
	public CustomRobot(int x, int y, Image image) {
		this(x, y, Robot.DEFAULT_DELTA_X, Robot.DEFAULT_DELTA_Y, image);
	}
	
	/**
	 * Creates a CustomRobot instance with specified x, y, deltaX and deltaY values.
	 * The Robot object is created with a default width and height.
	 */
	public CustomRobot(int x, int y, int deltaX, int deltaY, Image image) {
		// Derive the robot's width and height from the image.
		super(x, y, deltaX, deltaY, image.getWidth(null), image.getHeight(null));
		
		_picture = image;
	}
	
	@Override
	protected void doPaint(Painter painter) {
		painter.drawImage(_picture,_x,_y,_width,_height);
	}
}

