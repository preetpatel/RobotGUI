package robot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTrackedRobot {

    // Fixture object that is used by the tests.
    private MockPainter _painter;

    @Before
    public void setUp() {
        _painter = new MockPainter();
    }

    @Test
    public void testSimpleMoveWithSmallDimensions() {
        TrackedRobot robot = new TrackedRobot(100, 20, 12, 15);
        robot.paint(_painter);
        robot.move(500, 500);
        robot.paint(_painter);
        assertEquals("(line 100,37,112,20)(line 112,20,125,37)(line 125,37,112,55)(line 112,55,100,37)(line 112,52,124,35)(line 124,35,137,52)(line 137,52,124,70)(line 124,70,112,52)",
                _painter.toString());
    }

    @Test
    public void testSimpleMoveWithRegularDimensions() {
        TrackedRobot robot = new TrackedRobot(100, 20, 12, 15, 50,50);
        robot.paint(_painter);
        robot.move(500, 500);
        robot.paint(_painter);
        assertEquals("(line 100,45,120,20)(line 120,20,130,20)(line 130,20,150,45)(line 150,45,130,70)(line 130,70,120,70)(line 120,70,100,45)(line 112,60,132,35)(line 132,35,142,35)(line 142,35,162,60)(line 162,60,142,85)(line 142,85,132,85)(line 132,85,112,60)",
                _painter.toString());
    }

}
