package robot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDynamicWheeledRobot {
    // Fixture object that is used by the tests.
    private MockPainter _painter;

    @Before
    public void setUp() {
        _painter = new MockPainter();
    }

    /**
     * Test to perform a bounce movement off the right-most boundary and to
     * ensure that the Robot's position after the movement is correct.
     */
    @Test
    public void testRobotMoveWithBounceOffRightWall() {
        DynamicWheeledRobot robot = new DynamicWheeledRobot(100, 20, 12, 15);
        robot.paint(_painter);
        robot.move(135, 10000);
        robot.paint(_painter);
        robot.move(135, 10000);
        robot.paint(_painter);
        assertEquals("(rectangle 100,20,25,35)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 110,35,25,35)(Set Colour null)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 98,50,25,35)(Set Colour null)", _painter.toString());
    }

    /**
     * Test to perform a bounce movement off the left-most boundary and to
     * ensure that the Robot's position after the movement is correct.
     */
    @Test
    public void testRobotMoveWithBounceOffLeft() {
        DynamicWheeledRobot robot = new DynamicWheeledRobot(10, 20, -12, 15);
        robot.paint(_painter);
        robot.move(10000, 10000);
        robot.paint(_painter);
        robot.move(10000, 10000);
        robot.paint(_painter);
        assertEquals("(rectangle 10,20,25,35)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 0,35,25,35)(Set Colour null)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 12,50,25,35)(Set Colour null)", _painter.toString());
    }

    @Test
    public void testRobotMoveWithBounceOffBottom() {
        DynamicWheeledRobot robot = new DynamicWheeledRobot(20, 100, 12, 15);
        robot.paint(_painter);
        robot.move(10000, 140);
        robot.paint(_painter);
        robot.move(10000, 140);
        robot.paint(_painter);
        assertEquals("(rectangle 20,100,25,35)(rectangle 32,105,25,35)(rectangle 44,90,25,35)", _painter.toString());
    }

    @Test
    public void testRobotMoveWithBounceOffTop() {
        DynamicWheeledRobot robot = new DynamicWheeledRobot(20, 10, 12, -15);
        robot.paint(_painter);
        robot.move(10000, 140);
        robot.paint(_painter);
        robot.move(10000, 140);
        robot.paint(_painter);
        assertEquals("(rectangle 20,10,25,35)(rectangle 32,0,25,35)(rectangle 44,15,25,35)", _painter.toString());
    }

    /**
     * Test to perform a bounce movement off the bottom right corner and to
     * ensure that the Robot's position after the movement is correct.
     */
    @Test
    public void testRobotMoveWithBounceOffBottomAndRight() {
        DynamicWheeledRobot robot = new DynamicWheeledRobot(95, 95, 12, 15);
        robot.paint(_painter);
        robot.move(125, 135);
        robot.paint(_painter);
        robot.move(125, 135);
        robot.paint(_painter);
        assertEquals("(rectangle 95,95,25,35)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 100,100,25,35)(Set Colour null)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 88,85,25,35)(Set Colour null)", _painter.toString());
    }

    @Test
    public void testRobotMoveWithBounceOffBottomAndLeft() {
        DynamicWheeledRobot robot = new DynamicWheeledRobot(10, 90, -12, 15);
        robot.paint(_painter);
        robot.move(125, 135);
        robot.paint(_painter);
        robot.move(125, 135);
        robot.paint(_painter);
        assertEquals("(rectangle 10,90,25,35)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 0,100,25,35)(Set Colour null)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 12,85,25,35)(Set Colour null)", _painter.toString());
    }

    @Test
    public void testRobotMoveWithBounceOffTopAndRight() {
        DynamicWheeledRobot robot = new DynamicWheeledRobot(95, 5, 12, -15);
        robot.paint(_painter);
        robot.move(125, 135);
        robot.paint(_painter);
        robot.move(125, 135);
        robot.paint(_painter);
        assertEquals("(rectangle 95,5,25,35)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 100,0,25,35)(Set Colour null)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 88,15,25,35)(Set Colour null)", _painter.toString());
    }

    @Test
    public void testRobotMoveWithBounceOffTopAndLeft() {
        DynamicWheeledRobot robot = new DynamicWheeledRobot(5, 5, -12, -15);
        robot.paint(_painter);
        robot.move(125, 135);
        robot.paint(_painter);
        robot.move(125, 135);
        robot.paint(_painter);
        assertEquals("(rectangle 5,5,25,35)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 0,0,25,35)(Set Colour null)(Got Color: null)(Set Colour java.awt.Color[r=0,g=0,b=0])(filled rectangle 12,15,25,35)(Set Colour null)", _painter.toString());
    }


}
