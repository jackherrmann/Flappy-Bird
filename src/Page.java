import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class with static methods to draw the High Scores and About Pages
 * 
 * Uses Graphics, Color, and Font from awt
 *
 */

public class Page {
	
	//**********DRAW INSTRUCTIONS**********************************	
	public static void drawInstructions(Graphics g) {
		
		// block
    	g.setColor(new Color(235, 237, 180)); 
    	g.fillRect(50, 50, 365, 400);
			
		// title
    	g.setFont(new Font("TIMESROMAN", Font.BOLD, 40));
    	g.setColor(Color.black);
    	g.drawString("Florpy Blord", 115, 100); 
    	
    	// subtitle
    	g.setColor(Color.black);
    	g.setFont(new Font("TIMESROMAN", Font.PLAIN, 18));
    	g.drawString(" \"It\'s like Flappy Bird, but worse!\" ", 105, 130);
    	
    	// body1
    	String intro1 = "Hello, and welcome to my game, Florpy Blord."; 
    	String intro2 = "It’s kindof like the game “Flappy Bird,” from";
    	String intro3 = "2013, except worse. I created it using Java Swing,";
    	String intro4 = "Pixel Art Maker, and everything I learned in CIS"; 
    	String intro5 = "120 from my fantastic professors and TAs, who are";
    	String intro6 = "not at fault for it being bad.";
    	g.setFont(new Font("TIMESROMAN", Font.PLAIN, 14));
    	g.drawString(intro1, 70, 175); 
    	g.drawString(intro2, 70, 190);
    	g.drawString(intro3, 70, 205);
    	g.drawString(intro4, 70, 220);
    	g.drawString(intro5, 70, 235);
    	g.drawString(intro6, 70, 250);
    	
    	// body2
    	String intro7 = "If you’re unfamiliar with Flappy Bird, the"; 
    	String intro8 = "instructions are very easy: just press SPACE to"; 
    	String intro9 = "flap. Don’t touch the pipes, ceiling, or ground though!"; 
    	g.drawString(intro7, 70, 280);
    	g.drawString(intro8, 70, 295);
    	g.drawString(intro9, 70, 310);
    	
    	// body AI
    	String intro10 = "If you don't feel like playing, you can let"; 
    	String intro11 = "an AI play for you by pressing the \"AI\" button"; 
    	g.drawString(intro10, 70, 340);
    	g.drawString(intro11, 70, 355);
    	
    	// body3
    	String intro12 = "Good luck, and if you’re good enough, you might ";
    	String intro13 = "see your score on the \"High Scores\" page!";
    	String outro = "- Jack Herrmann"; 
    	g.drawString(intro12, 70, 385);
    	g.drawString(intro13, 70, 400);
    	g.drawString(outro, 70, 430);
    	
	}
	
	//********DRAW HIGH SCORES*************************************************
	public static void drawScores(Graphics g, String path) throws IOException {
		
		// block
		g.setColor(new Color(235, 237, 180)); 
		g.fillRect(30, 50, 385, 400);
			
		// title
		g.setFont(new Font("TIMESROMAN", Font.BOLD, 40));
		g.setColor(Color.black);
		g.drawString("High Scores", 115, 100); 
		
		// get and print high scores
		g.setFont(new Font("TIMESROMAN", Font.PLAIN, 20));
		
		// read old high scores
    	BufferedReader reader = new BufferedReader(new FileReader(path));
    	for (int i = 0; i < 5; i++) {
    		g.drawString(i+1 + ". " + reader.readLine(), 60, 150 + 30 * i); 
    	}
    	reader.close(); 
	
	}

}
