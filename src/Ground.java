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
 * The ground in the game, an example of a GameObj
 * 
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
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);    
    }

    /**
     * These methods do not apply to Ground, and they are never called on it
     */
	@Override
	protected void scored() {
		throw new IllegalArgumentException(); 
		
	}

	@Override
	protected boolean getScored() {
		throw new IllegalArgumentException(); 
	}
}