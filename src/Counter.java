/**
 * Basic game object that is simply an incrementing counter, to display the score on screen
 */


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Counter extends GameObj {
	
	public int count; 
	
	// creates a new counter, whose count is 0
	public Counter() {
		super(0, 0, 210, 80, 20, 20, 0, 0); // the counters size and position on screen
		count = 0; 
	}
	
	public int getCount() {
		return count; 
	}
	
	public void incr() {
		count++; 
	}
	
	
	/**
	 * Employs several Graphics methods to draw the number on the screen
	 */
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("TIMESROMAN", Font.BOLD, 40));
		g.drawString(Integer.toString(count), this.getPx(), this.getPy()); 
	}
	
	
	/**
	 * These methods do not apply to Counter, and they are never called on it
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
