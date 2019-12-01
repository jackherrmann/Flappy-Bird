/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * square of a specified color.
 */
public class Bird extends GameObj {
	public static final String IMG_FILE = "files/bird.png";
    public static final int SIZE = 50;
    public static final int INIT_POS_X = 100;
    public static final int INIT_POS_Y = 200;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
    public static final int ACC_Y = 2; 
    public static final int ACC_X = 0; 
    
    private static BufferedImage img;


    /**
    * Note that, because we don't need to do anything special when constructing a Square, we simply
    * use the superclass constructor called with the correct parameters.
    */
    public Bird(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, ACC_X, ACC_Y);
        
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }


    }

    @Override
    public void draw(Graphics g) {
    	g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}