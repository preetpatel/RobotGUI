package robot.forms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import robot.CarrierRobot;
import robot.Robot;
import robot.RobotModel;
import robot.forms.util.Form;
import robot.forms.util.FormHandler;

/**
 * FormHandler implementation for reading form data and using this to 
 * instantiate a specified Robot subclass.
 * 
 * @author Craig Sutherland
 *
 */
public class RobotFormHandler implements FormHandler {
	private Class<? extends Robot> _classToInstantiate;
	private RobotModel _model;
	private CarrierRobot _parentOfNewRobot;

	/**
	 * Creates a RobotFormHandler.
	 * 
	 * @param cls the Robot subclass to instantiate.
	 *
	 * @param model the RobotModel to which the handler should add a newly 
	 *        constructed Robot instance. 
	 * 
	 * @param parent the CarrierRobot object that will serve as the parent for
	 *        a new Robot object.
	 */
	public RobotFormHandler(Class<? extends Robot> cls,
			RobotModel model,
			CarrierRobot parent) {
		_classToInstantiate = cls;
		_model = model;
		_parentOfNewRobot = parent;
	}
	
	/**
	 * Reads form data that describes a robot. Based on the data, this 
	 * RobotFormHandler creates a new instance of a Robot subclass (specified 
	 * at construction time). The new instance is then added to a RobotModel 
	 * and to a CarrierRobot within the model. The RobotModel and CarrierRobot
	 * objects are supplied when this RobotFormHandler is created.  
	 * 
	 * @param form the Form that contains Robot data.
	 */
	@Override
	public void processForm(Form form) {
		try {
			// Attempt to find a 7-argument constructor for the Robot subclass.
			Constructor<? extends Robot> cons = _classToInstantiate.getConstructor(
					java.lang.Integer.TYPE, java.lang.Integer.TYPE, 
					java.lang.Integer.TYPE, java.lang.Integer.TYPE,
					java.lang.Integer.TYPE, java.lang.Integer.TYPE,
					java.lang.String.class);
	
			int x = 0;
			int y = 0;
			int deltaX = form.getFieldValue(Integer.class, RobotFormElement.DELTA_X);
			int deltaY = form.getFieldValue(Integer.class, RobotFormElement.DELTA_Y);
			int width = form.getFieldValue(Integer.class, RobotFormElement.WIDTH);
			int height = form.getFieldValue(Integer.class, RobotFormElement.HEIGHT);
			String text = form.getFieldValue(String.class, RobotFormElement.TEXT);
			
			// Instantiate robot class, calling the 7-argument constructor.
			Robot newRobot = (Robot)cons.newInstance(x, y, deltaX, deltaY, width, height, text);
			
			_model.add(newRobot, _parentOfNewRobot);
			
		} catch(NoSuchMethodException e) {
			// Thrown if a constructor with the specified arguments is not 
			// defined by the class.
			System.err.println(e);
		} catch(SecurityException e) {
			// Thrown if a security manager is set and the running program is
			// not permitted to load classes at run-time.
			System.err.println(e);
		} catch(InstantiationException e) {
			// Thrown if the loaded class cannot be instantiated, e.g. the
			// class might be abstract.
			System.err.println(e);
		} catch(IllegalAccessException e) {
			// Thrown if the class' constructor is hidden, e.g. private.
			System.err.println(e);
		} catch(IllegalArgumentException e) {
			// Thrown if the actual arguments are incompatible with the formal
			// arguments.
			System.err.println(e);
		} catch(InvocationTargetException e) {
			// Thrown if the constructor itself throws an exception.
			System.err.println(e);
		} 
	}

}
