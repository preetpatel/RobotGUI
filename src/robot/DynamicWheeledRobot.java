package robot;

import java.awt.*;

/**
 * Class to represent a DynamicWheeled robot.
 *
 * @author Preet Patel
 *
 */

public class DynamicWheeledRobot extends WheeledRobot{
    private Color _RobotColor;
    private boolean hasBouncedOffVertical = false;

    public DynamicWheeledRobot() {
        super();
        _RobotColor = Color.black;
    }


    public DynamicWheeledRobot(int x, int y, int deltaX, int deltaY) {
        super(x,y,deltaX,deltaY);
        _RobotColor = Color.black;
    }

    public DynamicWheeledRobot(int x, int y, int deltaX, int deltaY, Color robotColor) {
        super(x,y,deltaX,deltaY);
        _RobotColor = robotColor;
    }


    public DynamicWheeledRobot(int x, int y, int deltaX, int deltaY, int width, int height) {
        super(x,y,deltaX,deltaY,width,height);
        _RobotColor = Color.black;

    }
    
    public DynamicWheeledRobot(int x, int y, int deltaX, int deltaY, int width, int height, String name) {
        super(x,y,deltaX,deltaY,width,height,name);
        _RobotColor = Color.black;

    }

    public DynamicWheeledRobot(int x, int y, int deltaX, int deltaY, int width, int height, Color robotColor) {
        super(x,y,deltaX,deltaY,width,height);
        _RobotColor = robotColor;

    }

    public DynamicWheeledRobot(int x, int y, int deltaX, int deltaY, int width, int height, String name,Color robotColor) {
        super(x,y,deltaX,deltaY,width,height,name);
        _RobotColor = robotColor;

    }

    /**
     * Draws a robot with a filled color specified by the input.
     * @param painter
     */
    protected void drawFilledRobot(Painter painter) {

        Color temp = painter.getColor();
        painter.setColor(_RobotColor);
        painter.fillRect(_x,_y,_width,_height);
        hasBouncedOffVertical = true;
        painter.setColor(temp);
    }

    /**
     * Paints this DynamicWheeledRobot object using the supplied Painter object.
     */
    protected void doPaint(Painter painter) {

        if (this.didBounceOffVertical() && this.didBounceOffHorizontal()) {
           drawFilledRobot(painter);
        } else if (this.didBounceOffVertical()) {
            drawFilledRobot(painter);
        } else if (this.didBounceOffHorizontal()) {
            painter.drawRect(_x, _y, _width, _height);
            hasBouncedOffVertical = false;
        } else {
            if (!hasBouncedOffVertical) {
                painter.drawRect(_x, _y, _width, _height);
            } else {
                drawFilledRobot(painter);
            }
        }
    }
}
