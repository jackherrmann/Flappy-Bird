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
public class Ground extends GameObj {
    public static final String IMG_FILE = "files/Ground.png";

    
    public static final int INIT_VEL_X = -8;

    private static BufferedImage img;


    public Ground(int pos) {
    	
        super(INIT_VEL_X, 0, pos, 575, 465, 50, 0, 0);

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        

    }
    
    
    public void reset() {
    	setPx(465);
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);

        
    }
}