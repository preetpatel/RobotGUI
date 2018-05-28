package robot;

/**
 * Class to represent a flying robot.
 *
 * @author Preet Patel
 *
 */
public class FlyingRobot extends Robot {
    /**
     * Default constructor that creates a FlyingRobot instance whose instance
     * variables are set to default values.
     */
    public FlyingRobot() {
        super();
    }

    /**
     * Creates a FlyingRobot instance with specified values for instance
     * variables.
     * @param x x position.
     * @param y y position.
     * @param deltaX speed and direction for horizontal axis.
     * @param deltaY speed and direction for vertical axis.
     */
    public FlyingRobot(int x, int y, int deltaX, int deltaY) {
        super(x,y,deltaX,deltaY);
    }

    /**
     * Creates a FlyingRobot instance with specified values for instance
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
    public FlyingRobot(int x, int y, int deltaX, int deltaY, int width, int height) {
        super(x,y,deltaX,deltaY,width,height);
    }

    public FlyingRobot(int x, int y, int deltaX, int deltaY, int width, int height, String name) {
        super(x,y,deltaX,deltaY,width,height);
    }

    /**
     * Paints this FlyingRobot object using the supplied Painter object.
     */
    protected void doPaint(Painter painter) {
        painter.drawOval(_x,_y,_width,_height);
    }
}