package robot;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Implementation of the Painter interface that delegates drawing to a
 * java.awt.Graphics object.
 * 
 * @author Craig Sutherland
 * 
 */
public class GraphicsPainter implements Painter {
	// Delegate object.
	private Graphics _g;

	/**
	 * Creates a GraphicsPainter object and sets its Graphics delegate.
	 */
	public GraphicsPainter(Graphics g) {
		this._g = g;
	}

	/**
	 * @see robot.Painter.drawRect
	 */
	public void drawRect(int x, int y, int width, int height) {
		_g.drawRect(x, y, width, height);
	}

	/**
	 * @see robot.Painter.drawOval
	 */
	public void drawOval(int x, int y, int width, int height) {
		_g.drawOval(x, y, width, height);
	}

	/**
	 * @see bounce.Painter.drawLine.
	 */
	public void drawLine(int x1, int y1, int x2, int y2) {
		_g.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		_g.fillRect(x, y, width, height);
	}

	@Override
	public void setColor(Color robotColor) {
		_g.setColor(robotColor);
	}

	@Override
	public Color getColor() {
		return _g.getColor();
	}

	public void drawImage(Image image, int x, int y, ImageObserver observer) {
		_g.drawImage(image,x,y,observer);
	}

	@Override
	public void translate(int x, int y) {
		_g.translate(x,y);
	}

	@Override
	public void drawCentredText(String name, int x, int y, int width, int height) {
		FontMetrics metrics = _g.getFontMetrics();
		int xPosition = x+(width/2) - metrics.stringWidth(name)/2;
		int yPosition = y+((height - metrics.getHeight()) / 2) + metrics.getAscent();
		_g.drawString(name,xPosition,yPosition);
	}

	@Override
	public void drawImage(Image img, int x, int y, int width, int height) {
		_g.drawImage(img, x, y, width, height, null);		
	}


}
