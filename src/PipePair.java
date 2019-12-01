import java.awt.*;

public class PipePair{
	
	public static LowerPipe lower; 
	public static UpperPipe upper; 
	public boolean scored;  
	
	public PipePair(int courtWidth, int courtHeight, int xPos) {
		int upperHeight = (int) (Math.random() * 425); 
		int lowerHeight = 425 - upperHeight; 
		
		
		lower = new LowerPipe(courtWidth, courtHeight, lowerHeight, xPos); 
		upper = new UpperPipe(courtWidth, courtHeight, upperHeight, xPos); 
		scored = false; 
	}
	
	
	public void draw(Graphics g) {
		upper.draw(g);
		lower.draw(g);
	}
	
	public void move() {
		upper.move(); 
		lower.move(); 
	}
	
	public boolean intersects(GameObj a) {
		return lower.intersects(a) || upper.intersects(a); 
	}
	
	public boolean scored() {
		if (lower.getPx() < 100 && !scored) {
			scored = true; 
			return true; 
		} else {
			return false; 
		}
	}

	
	public boolean gone() {
		return lower.getPx() <= -100; 
	}
	
	public int getPx() {
		return lower.getPx(); 
	}
	


}
