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
 * A game object displayed using an image.
 * 
 * Note that the image is read from the file when the object is constructed, and that all objects
 * created by this constructor share the same image data (i.e. img is static). This is important for
 * efficiency: your program will go very slowly if you try to create a new BufferedImage every time
 * the draw method is invoked.
 */
public class LowerPipe extends GameObj {
    public static final String IMG_FILE = "files/lowerpipe.png";
    public static final int WIDTH = 100;
    public static final int INIT_VEL_X = -8;
    public static final int INIT_VEL_Y = 0;
    
    
    public static final int ACC_X = 0; 
    public static final int ACC_Y = 0; 

    private static BufferedImage img;

    public LowerPipe(int courtWidth, int courtHeight, int height, int xPos) {
    	
        super(INIT_VEL_X, INIT_VEL_Y, xPos, 625-height, WIDTH, height, ACC_X, ACC_Y);

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
