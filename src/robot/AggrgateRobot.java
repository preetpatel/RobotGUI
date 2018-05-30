package robot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Class to represent a Aggrgate robot.
 *
 * @author Preet Patel
 *
 */

public class AggrgateRobot extends Robot{


    public AggrgateRobot() {
        super();
    }

    public AggrgateRobot(int x, int y) {
        super(x, y);
    }

    public AggrgateRobot(int x, int y, Robot first, Robot second) {
        super(x, y);
    }

    public AggrgateRobot(int x, int y, int deltaX, int deltaY) {
        super(x, y, deltaX, deltaY);
    }

    public AggrgateRobot(int x, int y, int deltaX, int deltaY, Robot first, Robot second) {
        super(x, y, deltaX, deltaY);
    }

    public AggrgateRobot(int x, int y, int deltaX, int deltaY, int width, int height) {
        super(x, y, deltaX, deltaY, width, height);
    }

    public AggrgateRobot(int x, int y, int deltaX, int deltaY, int width, int height, String name) {
        super(x, y, deltaX, deltaY, width, height, name);
    }

    /**
     * Paints an image specified.
     * @param painter the Painter object used for drawing.
     */
    @Override
    protected void doPaint(Painter painter) {
        try{
            File image2 = new File("robot.png");
            Image image = ImageIO.read(image2);
            image = image.getScaledInstance(_width, _height, Image.SCALE_DEFAULT);
            painter.drawImage(image, _x,_y,null);

        }
        catch (IOException e){
            System.out.println("Image for AggregateRobot Not found");
        }
    }
}
