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
 * A pipe object displayed using an image.
 * 
 * Note that the image is read from the file when the object is constructed, and that all objects
 * created by this constructor share the same image data (i.e. img is static). This is important for
 * efficiency: your program will go very slowly if you try to create a new BufferedImage every time
 * the draw method is invoked.
 */
public class UpperPipe extends GameObj {
    public static final String IMG_FILE_LID = "files/PipeLid.png";
    public static final String IMG_FILE_SHAFT = "files/PipeShaft.png"; 
    public static final int WIDTH = 100;
    public static final int INIT_POS_Y = 0;
    public static final int INIT_VEL_X = -8;
    public static final int INIT_VEL_Y = 0;
    
    
    public static final int ACC_X = 0; 
    public static final int ACC_Y = 0; 

    private static BufferedImage imgLid;
    private static BufferedImage imgShaft; 
    
    private boolean scored; 

    public UpperPipe(int courtWidth, int courtHeight, int height, int xPos) {
        super(INIT_VEL_X, INIT_VEL_Y, xPos, INIT_POS_Y, WIDTH, height, ACC_X, ACC_Y);
        
        scored = false; 

        try {
            if (imgLid == null) {
                imgLid = ImageIO.read(new File(IMG_FILE_LID));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        
        try {
            if (imgShaft == null) {
                imgShaft = ImageIO.read(new File(IMG_FILE_SHAFT));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    
    public boolean getScored() {
    	return scored; 
    }
    
    public void scored() {
    	scored = true; 
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(imgShaft, this.getPx()+5, this.getPy(), this.getWidth()-10, this.getHeight()-40, null);
        g.drawImage(imgLid, this.getPx(), this.getPy() + this.getHeight()-40, this.getWidth(), 
        		40, null);
    }
}