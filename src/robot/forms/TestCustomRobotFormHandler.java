package robot.forms;

import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import robot.CarrierRobot;
import robot.RobotModel;
import robot.forms.util.Form;
import robot.forms.util.FormElement;
import robot.forms.util.FormHandler;

/** 
 * Test case to determine whether class CustomRobotFormHandler has a 
 * satisfactory response time.
 * 
 * @author Craig Sutherland
 *
 */
public class TestCustomRobotFormHandler {

	private FormHandler _handler;
	
	/**
	 * Creates an CustomRobotFormHandler object for testing.
	 */
	@Before
	public void setUpHandler() {
		// To create an CustomRobotFormHandler, a RobotModel and a CarrierRobot
		// are required.
		RobotModel model = new RobotModel(new Dimension(500,500));
		CarrierRobot nest = new CarrierRobot(0, 0, 1, 1, 100, 100);
		
		_handler = new CustomRobotFormHandler(model, nest);
	}
		
	/**
	 * Tests that CustomRobotFormHandler's processForm() method executes
	 * sufficiently quickly to run on the Event Dispatch thread. The 
	 * processForm() method should delegate image loading and scaling to a
	 * background thread, and so processForm() should return control to the
	 * caller within milliseconds. 
	 */
	@Test
	public void testHandlerExecutionTime() {
		long startTime = System.currentTimeMillis();
		_handler.processForm(new MockForm());
		long elapsedTime = System.currentTimeMillis() - startTime;
		
		assertTrue(elapsedTime <= 50);
		System.out.println("Elapsed time: " + elapsedTime);
	}
	
	/*
	 * Helper class that simulates a GUI form for collecting ImageRectangleRobot
	 * attribute values. The CustomRobotFormHandler under test uses the form to
	 * acquire sufficient data to create an ImageRectangleRobot. Part of the
	 * data returned by the form is a large image file that takes some time for
	 * the CustomRobotFormHandler to load and scale. 
	 */
	private class MockForm implements Form {
		// Location of image file - the user's home directory. For Windows 
		// machines this is C:\Users\<user-id>.
		private static final String FILE_LOCATION = "user.home";
		
		// Name of image file.
		private static final String IMAGE_FILE_PATH = "Puma.png";
		
		@Override
		public void addFormElement(FormElement element) {
			// No implementation necessary
		}

		@Override
		public void setFormHandler(FormHandler handler) {
			// No implementation necessary.
		}

		@Override
		public void prepare() {
			// No implementation necessary.
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getFieldValue(Class<? extends T> type, String name) {
			Object result = null;
			
			if(type.isAssignableFrom(Integer.class)) {
				if(name.equals(RobotFormElement.WIDTH)) {
					result = new Integer(80);
				} else if(name.equals(RobotFormElement.HEIGHT)) {
					result = new Integer(25);
				} else if(name.equals(RobotFormElement.DELTA_X)) {
					result = new Integer(2);
				} else if(name.equals(RobotFormElement.DELTA_Y)) {
					result = new Integer(2);
				}
			} else if(type.isAssignableFrom(String.class) && (name.equals(RobotFormElement.TEXT))) {
				result = null;
			} else if(type.isAssignableFrom(java.io.File.class) && (name.equals(ImageFormElement.IMAGE))) {
				String path = System.getProperty(FILE_LOCATION);
				result = new File(path, IMAGE_FILE_PATH);
			}
			
			return (T)result;
		}
	}
}
