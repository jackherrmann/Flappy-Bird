import java.awt.Graphics;

/**
 * A class that holds a 2-D Array of pipes. Allows for creating, moving, drawing, and checking 
 * intersection with all pipes with one method. Mostly created to clean up GameCourt. 
 *
 */

public class PipeArray {
	
	private GameObj[][] pipes; 
	private int courtWidth; 
	private int courtHeight; 
	
	public PipeArray(int courtWidth, int courtHeight) {
		this.pipes = new GameObj[3][2];
		this.courtWidth = courtWidth; 
		this.courtHeight = courtHeight; 
		
		for (int i = 0; i < 3; i++) {
			
			int upperHeight = (int) (Math.random() * 175) + 100; 
			int lowerHeight = 375 - upperHeight; 
			int xPos = 465 + (300 * i); 
			
			pipes[i][0] = new UpperPipe(courtWidth, courtHeight, upperHeight, xPos);			
			pipes[i][1] = new LowerPipe(courtWidth, courtHeight, lowerHeight, xPos);		
		}
		
	}
	
	public boolean needsScore() {
		boolean needs = false; 
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				if (pipes[i][j].getPx() < 100 && !pipes[i][j].getScored()) {
					needs = true; 
					pipes[i][j].scored(); 
				}
			}
		}
		return needs; 
	}
	
	public void move() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				pipes[i][j].move(); 
			}
		}
		for (int i = 0; i < 3; i++) {
			if (pipes[i][0].getPx() < -100) {
				int upperHeight = (int) (Math.random() * 175) + 100; 
				int lowerHeight = 375 - upperHeight; 
				pipes[i][0] = new UpperPipe(courtWidth, courtHeight, upperHeight, 800);			
				pipes[i][1] = new LowerPipe(courtWidth, courtHeight, lowerHeight, 800);
			}
		}
	}
	
	public void draw(Graphics g) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				pipes[i][j].draw(g); 
			}
		}
	}
	
	public boolean intersects(GameObj that) {
		boolean inter = false; 
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				if (pipes[i][j].intersects(that)) {
					inter = true; 
				}
			}
		}
		return inter; 
	}
	
	/**
	 * Class to find the height of the closest LowerPipe to the bird that has not already partially
	 * left the screen. This is used only by the AI, as it needs to see where the pipes are. 
	 * @return
	 */
	public GameObj closestHeight() {
		int closestI = 5; 
		int lowestDist = Integer.MAX_VALUE; 
		for (int i = 0; i < 3; i++) {
			int thisDist = pipes[i][1].getPx(); 
			if (lowestDist > thisDist && thisDist > 0) {
				lowestDist = thisDist; 
				closestI = i; 
			}
		}
		return pipes[closestI][1]; 
	}
	
	

}
