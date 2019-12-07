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
 * The bird, sits at one x position, moves up and down based on user/AI flapping and gravity
 */
public class Bird extends GameObj {
	public static final String IMG_FILE = "files/bird.png";
    public static final int SIZE = 50;
    public static final int INIT_POS_X = 100;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static final int ACC_Y = 2; 
    public static final int ACC_X = 0; 

    
    private static BufferedImage img;


    /**
    * Constructor, simply inputs standard bird size and fetches the image file
    */
    public Bird(int courtWidth, int courtHeight, int yPos, int yVel) {
        super(INIT_VEL_X, yVel, INIT_POS_X, yPos, 35, 30, ACC_X, ACC_Y);
        
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }


    }
    
    
    /**
     * Draws the picture of the bird at it's current location
     */
    @Override
    public void draw(Graphics g) {
    	g.drawImage(img, this.getPx(), this.getPy(), SIZE, SIZE, null);
    }
    
    
    
    /**
     * These methods do not apply to the bird, and are never called on it
     */
	@Override
	protected boolean getScored() {
		throw new IllegalArgumentException(); 
	}

	@Override
	protected void scored() {
		throw new IllegalArgumentException(); 
	}
}