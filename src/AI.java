/**
 * Very basic AI that tells whether the bird should jump
 */


public class AI {
	
	private boolean flaps; 
	
	
	public AI(Bird bird, GameObj lowerPipe) {

		Bird testBird = new Bird(465, 625, bird.getPy(), bird.getVy()); 
		Bird testBirdJump = new Bird(465, 625, bird.getPy(), -20); 
		LowerPipe testLower = new LowerPipe(465, 625, lowerPipe.getHeight(), lowerPipe.getPx()); 
		UpperPipe testUpper = new UpperPipe(465, 625, 375 - lowerPipe.getHeight(),
				lowerPipe.getPx()); 


		
		flaps = false;

		 
		if(willCollideNoJump(testBird, testLower, 0) <= 8) {
			flaps = true; 
		} 
		boolean dontJump = willCollideJump(testBirdJump, testUpper, 0); 
		if (dontJump) {
			flaps = false; 
		}
	}
	
	
	/**
	 * Recursive helper method to determine when the bird will hit either the lower pipe or the
	 * ground if it does not jump. 
	 */
	public int willCollideNoJump(Bird bird, LowerPipe lower, int iter) {
		
		
		// if will hit lower pipe or floor, return 
		if (bird.intersects(lower) || bird.getPy() > 550) {
			return iter; 
		}
		if (iter >= 10) {
			return iter; 
		}
		bird.move(); 
		lower.move(); 
		
		return willCollideNoJump(bird, lower, iter + 1); 
	}
	
	/**
	 * Recursive helper method to determine if the bird will hit either the upper pipe
	 * or the roof in the next several frames if it does jump 
	 */
	public boolean willCollideJump(Bird bird, UpperPipe upper, int iter) {
		if (bird.intersects(upper) || bird.getPy() < 0) {
			return true; 
		}
		if (iter > 20) {
			return false; 
		}
		bird.move(); 
		upper.move(); 
		return willCollideJump(bird, upper, iter + 1); 
	}
				
				
				
	
	public boolean flaps() {
		return flaps; 
	}

}
