package robot.forms;

import robot.DynamicWheeledRobot;
import robot.CustomRobot;
import robot.CarrierRobot;
import robot.Robot;
import robot.RobotModel;
import robot.forms.util.FormComponent;
import robot.forms.util.FormHandler;

public class FormResolver {

	/**
	 * Returns a FormComponent object for entering attribute values for the 
	 * specified Robot subclass.
	 * 
	 * @param robotClass the subclass of Robot for which a Form is required. 
	 */
	public static FormComponent getForm(Class<? extends Robot> robotClass) {
		FormComponent form = new FormComponent();
		
		form.addFormElement(new RobotFormElement());
		
		if(robotClass == DynamicWheeledRobot.class) {
			form.addFormElement(new ColourFormElement());
		} else if(robotClass == CustomRobot.class) {
			form.addFormElement(new ImageFormElement());
		}
		
		return form;
	}
	
	
	/**
	 * Returns a FormHandler implementation for creating an instance of a 
	 * specified Robot subclass. In response to a process(Form) call, a 
	 * FormHandler extracts form data and uses this to instantiate a Robot 
	 * subclass, to add the new instance to a RobotModel and as a child of an
	 * existing CarrierRobot parent.
	 * 
	 * @param robotClass the subclass of Robot to be instantiated.
	 * @param model the RobotModel to which the new Robot instance should be 
	 *        added.
	 * @param parent the CarrierRobot object that will serve as the parent of
	 *        the newly created Robot object.
	 */
	public static FormHandler getFormHandler(Class<? extends Robot> robotClass, RobotModel model, CarrierRobot parent) {
		FormHandler handler = null;
		
		if(robotClass == DynamicWheeledRobot.class) {
			handler = new DynamicWheeledRobotFormHandler(model, parent);
		} else if(robotClass == CustomRobot.class) {
			handler = new CustomRobotFormHandler(model, parent);
		} else {
			handler = new RobotFormHandler(robotClass, model, parent);
		}
		
		return handler;
	}
}
