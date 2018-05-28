package robot.forms;

import java.awt.Color;

import robot.DynamicWheeledRobot;
import robot.CarrierRobot;
import robot.RobotModel;
import robot.forms.util.Form;
import robot.forms.util.FormHandler;

public class DynamicWheeledRobotFormHandler implements FormHandler {
	private RobotModel _model;
	private CarrierRobot _parentOfNewRobot;

	public DynamicWheeledRobotFormHandler(RobotModel model,
			CarrierRobot parent) {
		_model = model;
		_parentOfNewRobot = parent;
	}

	@Override
	public void processForm(Form form) {
		int x = 0;
		int y = 0;
		int deltaX = form.getFieldValue(Integer.class, RobotFormElement.DELTA_X);
		int deltaY = form.getFieldValue(Integer.class, RobotFormElement.DELTA_Y);
		int width = form.getFieldValue(Integer.class, RobotFormElement.WIDTH);
		int height = form.getFieldValue(Integer.class, RobotFormElement.HEIGHT);
		String text = form.getFieldValue(String.class, RobotFormElement.TEXT);
		Color colour = (Color) form
				.getFieldValue(Color.class, ColourFormElement.COLOUR);

		DynamicWheeledRobot newRobot = new DynamicWheeledRobot(x, y,
				deltaX, deltaY, width, height, text, colour);
		_model.add(newRobot, _parentOfNewRobot);
	}

}
