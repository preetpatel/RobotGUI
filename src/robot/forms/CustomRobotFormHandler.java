package robot.forms;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

import robot.CustomRobot;
import robot.CarrierRobot;
import robot.RobotModel;
import robot.forms.util.Form;
import robot.forms.util.FormHandler;

public class CustomRobotFormHandler implements FormHandler {

	private RobotModel _model;
	private CarrierRobot _nest;
	private SwingWorker<BufferedImage, Void> _worker;
	private File _imageFile;
	private int _width;
	private int _deltaX;
	private int _deltaY;
	private BufferedImage _bufferedImage;

	public CustomRobotFormHandler (RobotModel model, CarrierRobot carrier){
		_model = model;
		_nest = carrier;
	}


	@Override
	public void processForm(Form form) {
		_imageFile = (File)form.getFieldValue(File.class, ImageFormElement.IMAGE);
		_width = form.getFieldValue(Integer.class, RobotFormElement.WIDTH);
		_deltaX = form.getFieldValue(Integer.class, RobotFormElement.DELTA_X);
		_deltaY = form.getFieldValue(Integer.class, RobotFormElement.DELTA_Y);
		_worker = new ImageShapeWorker();
		_worker.execute(); 
	}

	private class ImageShapeWorker extends SwingWorker<BufferedImage, Void> {

		@Override
		protected BufferedImage doInBackground() throws Exception {

			// Load the original image (ImageIO.read() is a blocking call).
			BufferedImage fullImage = null;
			try {
				fullImage = ImageIO.read(_imageFile);
			} catch(IOException e) {
				System.out.println("Error loading image.");
			}
			
			int fullImageWidth = fullImage.getWidth();
			int fullImageHeight = fullImage.getHeight();
					
			_bufferedImage = fullImage;
					
			// Scale the image if necessary.
			if(fullImageWidth > _width) {
				double scaleFactor = (double)_width / (double)fullImageWidth;
				int height = (int)((double)fullImageHeight * scaleFactor);
						
				_bufferedImage = new BufferedImage(_width,height,BufferedImage.TYPE_INT_RGB); 
				Graphics2D g = _bufferedImage.createGraphics();
						
				// Method drawImage() scales an already loaded image. The 
				// ImageObserver argument is null because we don't need to monitor 
				// the scaling operation.
				g.drawImage(fullImage, 0, 0, _width, height, null);
				
			}
			return _bufferedImage;
		}

		@Override
		protected void done(){
			try {
				BufferedImage image = this.get();
				// Create the new CustomRobot and add it to the model.
				CustomRobot robot = new CustomRobot(_deltaX, _deltaY, image);
				_model.add(robot, _nest);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

	}

}