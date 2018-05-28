package robot.robotApp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The TestAll class declaration is annotated using JUnit's @RunWith and @Suite 
 * annotations. The effect of these annotations is to define a test suite (a 
 * collection of named unit test classes). When a test suite is run by the JUnit 
 * TestRunner, all @Test methods in all named test classes are executed.
 * 
 * @author Craig Sutherland
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({robot.TestCarrierRobot.class, 
	robot.views.TestTask1.class, 
	robot.views.TestTask2.class,
	robot.forms.TestCustomRobotFormHandler.class})
public class TestAll {}

