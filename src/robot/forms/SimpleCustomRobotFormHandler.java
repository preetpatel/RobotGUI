package robot.forms;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import robot.CustomRobot;
import robot.CarrierRobot;
import robot.RobotModel;
import robot.forms.util.Form;
import robot.forms.util.FormHandler;

/**
 * FormHandler implementation for reading form data and using this to 
 * instantiate class CustomRobot.
 * 
 * @author Craig Sutherland
 *
 */
public class SimpleCustomRobotFormHandler implements FormHandler {

	private RobotModel _model;
	private CarrierRobot _parentOfNewRobot;
	
	/**
	 * Creates a SimpleCustomRobotFormHandler.
	 * 
	 * @param model the RobotModel to which the handler should add a newly 
	 *        constructed CustomRobot object. 
	 * @param parent the CarrierRobot object that will serve as the parent for
	 *        a new CustomRobot instance.
	 */
	public SimpleCustomRobotFormHandler(
			RobotModel model,
			CarrierRobot parent) {
		_model = model;
		_parentOfNewRobot = parent;
	}
	
	/**
	 * Reads form data that describes an CustomRobot. Based on the 
	 * data, this SimpleCustomRobotFormHandler creates a new CustomRobot 
	 * object, adds it to a RobotModel and to a CarrierRobot within the model.
	 * 
	 * @param form the Form that contains the CustomRobot data.
	 */
	@Override
	public void processForm(Form form) {
		long startTime = System.currentTimeMillis();
		
		// Read field values from the form.
		File imageFile = (File)form.getFieldValue(File.class, ImageFormElement.IMAGE);
		int width = form.getFieldValue(Integer.class, RobotFormElement.WIDTH);
		int deltaX = form.getFieldValue(Integer.class, RobotFormElement.DELTA_X);
		int deltaY = form.getFieldValue(Integer.class, RobotFormElement.DELTA_Y);
		

		// Load the original image (ImageIO.read() is a blocking call).
		BufferedImage fullImage = null;
		try {
			fullImage = ImageIO.read(imageFile);
		} catch(IOException e) {
			System.out.println("Error loading image.");
		}
		
		int fullImageWidth = fullImage.getWidth();
		int fullImageHeight = fullImage.getHeight();
				
		BufferedImage scaledImage = fullImage;
				
		// Scale the image if necessary.
		if(fullImageWidth > width) {
			double scaleFactor = (double)width / (double)fullImageWidth;
			int height = (int)((double)fullImageHeight * scaleFactor);
					
			scaledImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); 
			Graphics2D g = scaledImage.createGraphics();
					
			// Method drawImage() scales an already loaded image. The 
			// ImageObserver argument is null because we don't need to monitor 
			// the scaling operation.
			g.drawImage(fullImage, 0, 0, width, height, null);
			
			// Create the new Robot and add it to the model.
			CustomRobot imageRobot = new CustomRobot(deltaX, deltaY, scaledImage);
			_model.add(imageRobot, _parentOfNewRobot);
		}
		
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Image loading ans scaling took " + elapsedTime + "ms.");
	}
}
