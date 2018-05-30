package robot;

/**
 * Class to represent a tracked robot.
 *
 * @author Preet Patel
 *
 */

public class TrackedRobot extends Robot{
    /**
     * Default constructor that creates a TrackedRobot instance whose instance
     * variables are set to default values.
     */
    public TrackedRobot() {
        super();
    }

    /**
     * Creates a TrackedRobot instance with specified values for instance
     * variables.
     * @param x x position.
     * @param y y position.
     * @param deltaX speed and direction for horizontal axis.
     * @param deltaY speed and direction for vertical axis.
     */
    public TrackedRobot(int x, int y, int deltaX, int deltaY) {
        super(x,y,deltaX,deltaY);
    }

    /**
     * Creates a TrackedRobot instance with specified values for instance
     * variables.
     * @param x x position.
     * @param y y position.
     * @param deltaX speed (pixels per move call) and direction for horizontal
     *        axis.
     * @param deltaY speed (pixels per move call) and direction for vertical
     *        axis.
     * @param width width in pixels.
     * @param height height in pixels.
     */
    public TrackedRobot(int x, int y, int deltaX, int deltaY, int width, int height) {
        super(x,y,deltaX,deltaY,width,height);
    }

    public TrackedRobot(int x, int y, int deltaX, int deltaY, int width, int height, String name) {
        super(x,y,deltaX,deltaY,width,height, name);
    }

    /**
     * Paints this TrackedRobot object using the supplied Painter object.
     */
    protected void doPaint(Painter painter) {
        int xStart = this.x();
        int yStart = this.y();

        if (_height >= 40 && _width >= 40) {
            painter.drawLine(xStart, (_height/2)+yStart, xStart+20, yStart);
            painter.drawLine(xStart + 20, yStart, xStart + (width() - 20), yStart);
            painter.drawLine(xStart + (width() - 20), yStart, xStart + width(), (_height / 2) + yStart);
            painter.drawLine(xStart + width(), (_height / 2) + yStart, xStart + width() - 20, _height + yStart);
            painter.drawLine(xStart + width() - 20, _height + yStart, xStart + 20, _height + yStart);
            painter.drawLine(xStart + 20, _height + yStart, xStart, (_height / 2) + yStart);
        } else {
            painter.drawLine(xStart, yStart + (_height/2), xStart +(width()/2), yStart);
            painter.drawLine(xStart +(width()/2), yStart, xStart + width(), yStart +(_height / 2));
            painter.drawLine(xStart + width(), yStart +(_height / 2), xStart + (width()/2), yStart + _height);
            painter.drawLine(xStart + (width()/2), yStart + _height,xStart, yStart + (_height/2));



        }

    }
}
