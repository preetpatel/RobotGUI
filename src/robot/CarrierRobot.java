package robot;

import java.util.ArrayList;
import java.util.List;

public class CarrierRobot extends Robot {
    protected List<Robot> _ChildRobotList = new ArrayList<>();

    /**
     * Creates a CarrierRobot object with default values for state.
     */
    public CarrierRobot() {
        super();
    }

    /**
     * Creates a CarrierRobot object with a specified location for robot.
     * @param x horizontal position of robot
     * @param y vertical position of robot
     */
    public CarrierRobot(int x, int y){
        super(x,y);
    }

    /**
     * Creates a CarrierRobot object with a specified location and speed
     * @param x horizontal position of robot
     * @param y vertical position of robot
     * @param deltaX the horizontal speed of robot
     * @param deltaY the vertical speed of robot
     */
    public CarrierRobot(int x, int y,int deltaX, int deltaY) {
        super(x,y,deltaX,deltaY);
    }

    /**
     * Creates a CarrierRobot object with a specified location, speed and dimensions
     * @param x horizontal position of robot
     * @param y vertical position of robot
     * @param deltaX the horizontal speed of robot
     * @param deltaY the vertical speed of robot
     * @param width width of robot
     * @param height height of robot
     */
    public CarrierRobot(int x, int y, int deltaX, int deltaY, int width, int height) {
        super(x,y,deltaX,deltaY,width,height);
    }
    
    
    public CarrierRobot(int x, int y, int deltaX, int deltaY, int width, int height, String name) {
        super(x,y,deltaX,deltaY,width,height,name);
    }

    /**
     * Moves a CarrierRobot object (including its children) within the bounds
     * specified by the arguments width and height
     * @param width width of two-dimensional world.
     * @param height height of two-dimensional world.
     */
    public void move(int width, int height) {
        super.move(width,height);
        for(Robot robot: _ChildRobotList) {
            robot.move(_width,_height);
        }
    }

    /**
     * Paints a carrier robot object by drawing a rectangle around the  edge of
     * its bounding box. The CarrierRobots children are then painted.
     * @param painter painter instance for drawing the robot
     */
    protected void doPaint(Painter painter) {
        painter.drawRect(_x, _y, _width, _height);
        painter.translate(_x,_y);
        for (Robot robot : _ChildRobotList) {
            robot.paint(painter);
        }
        painter.translate(-_x,-_y);
    }

    /**
     * Attempts to add a Robot to a CarrierRobot object. If successful, a two way link is
     * established between the carrier robot and the newly added Robot. Note that this method
     * has package level visibility.
     * @param robot the Robot to be added.
     * @throws IllegalArgumentException if an attempt is made to add a robot to a CarrierRobot
     * instance where the Robot is already a child of a CarrierRobot. IllegalArgumentException is
     * also thrown when an attempt to add a Robot that exceeds the dimensions of the carrierRobot's
     * size.
     */
    void add(Robot robot) throws IllegalArgumentException {
            if (robot._ParentCarrierRobot == null) {
                if(robot._x + robot._width > this._width || robot._y + robot._height > this._height) {
                    throw new IllegalArgumentException();
                } else {
                    _ChildRobotList.add(robot);
                    robot._ParentCarrierRobot = this;
                }
            } else {
                throw new IllegalArgumentException();
            }
    }

    /**
     * Removes a particular Robot from the instance of CarrierRobot. Deletes the
     * two-way link between the child and parent relationship.
     * @param robot the robot instance that needs to be removed from the CarrierRobot
     */
    void remove(Robot robot) {
            _ChildRobotList.remove(robot);
            robot._ParentCarrierRobot = null;

    }

    /**
     * Returns the robot at a specified position within a CarrierRobot.
     * @param index the specified index position
     * @return the robot at the specified index
     * @throws IndexOutOfBoundsException if the position specified is less than zero or
     * greater than the number of children stored in the CarrierRobot less one.
     */
    public Robot robotAt(int index) throws IndexOutOfBoundsException {
        if (index <0 || index > _ChildRobotList.size() - 1) {
            throw new IndexOutOfBoundsException();
        } else {
            return _ChildRobotList.get(index);
        }
    }

    /**
     *
     * @return the count of children a CarrierRobot has.
     */
    public int robotCount() {
       return _ChildRobotList.size();
    }

    /**
     *
     * @param robot the instance of robot whose index needs to be found
     * @return index of the position of the Robot. Returns -1 if the Robot does not belong in
     * the CarrierRobot.
     */
    public int indexOf(Robot robot) {
       return _ChildRobotList.indexOf(robot);
    }

    /**
     * Checks if the CarrierRobot contains a particular Robot
     * @param robot instance of Robot that needs to be checked
     * @return True if robot is in the CarrierRobot. False if Robot is not in CarrierRobot.
     */
    public boolean contains(Robot robot) {
        return _ChildRobotList.contains(robot);
    }
}
