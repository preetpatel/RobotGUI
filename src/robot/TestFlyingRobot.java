package robot;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * A class that implements test cases aimed at identifying bugs in the
 * implementations of classes Robot and FlyingRobot.
 *
 * @author Preet Patel
 *
 */

public class TestFlyingRobot {
    // Fixture object that is used by the tests.
    private MockPainter _painter;

    @Before
    public void setUp() {
        _painter = new MockPainter();
    }

    @Test
    public void testSimpleMove() {
        FlyingRobot robot = new FlyingRobot(100, 20, 12, 15);
        robot.paint(_painter);
        robot.move(500, 500);
        robot.paint(_painter);
        assertEquals("(oval 100,20,25,35)(oval 112,35,25,35)",
                _painter.toString());
    }
}
