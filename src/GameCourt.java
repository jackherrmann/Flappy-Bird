/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.*;
import java.util.Calendar; 


import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // GAME OBJECTS
    private Bird bird; // the bird, falls and can jump
    private LinkedList<Ground> grounds; // the ground, moves across screen
    private PipeArray pipes; // the pipes, move across screen
    
    // GAME STATUS 
    private Counter score; // the players score, incremented when pipes pass
    public boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text

    // Game constants
    public static final int COURT_WIDTH = 465;
    public static final int COURT_HEIGHT = 625;
    public static final int JUMP_VELOCITY = -20;
    
    // high scores file
    static final String pathToScores = "files/high_scores.csv";
    
    // draw instructions/scores
    private boolean instructionsOpen;
    private boolean scoresOpen; 
    
    // is AI mode on?
    private boolean auto; 

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        Color backgroundColor = new Color(204, 245, 255); 
        setBackground(backgroundColor); 

        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					tick();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the player to make the bird jump with the space bar
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !auto) {
                    flap(); 
                } 
            }
        });

        this.status = status;
    }
    
    // method to make the bird flap
    private void flap() {
    	bird.setVy(JUMP_VELOCITY);
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        bird = new Bird(COURT_WIDTH, COURT_HEIGHT, 200, -20);
        grounds = new LinkedList<Ground>(); 
        grounds.add(new Ground(0)); 
        grounds.add(new Ground(465)); 

        pipes = new PipeArray(COURT_WIDTH, COURT_HEIGHT); 
        score = new Counter();  

        playing = true;
        instructionsOpen = false; 
        scoresOpen = false; 
        auto = false; 

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    
    
    
    // ****************************************************************************
    // ************* AUTO MODE ******************************************
    // ****************************************************************************

    /**
     * (Re-)set the game to its initial state in auto mode.
     */
    private void resetAuto() {
        bird = new Bird(COURT_WIDTH, COURT_HEIGHT, 200, -20);
        grounds = new LinkedList<Ground>(); 
        grounds.add(new Ground(0)); 
        grounds.add(new Ground(465)); 

        pipes = new PipeArray(COURT_WIDTH, COURT_HEIGHT); 
        score = new Counter(); 

        playing = true;
        instructionsOpen = false; 
        scoresOpen = false; 

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    // turn on auto mode
    public void setAuto() {
    	this.auto = true; 
    	resetAuto(); 
    }
    
    // ****************************************************************************
    // ************* INSTRUCTIONS PAGE ********************************************
    // ****************************************************************************
    public void instructions() {
    	scoresOpen = false; 
    	instructionsOpen = !instructionsOpen; 
    	repaint(); 
    }
    
    // ****************************************************************************
    // ************* HIGH SCORES PAGE *********************************************
    // ****************************************************************************
    public void scores() {
    	instructionsOpen = false; 
    	scoresOpen = !scoresOpen; 
    	repaint(); 
    }

    // ****************************************************************************
    // ************* HIGH SCORES FUNCTIONALITY ************************************
    // ****************************************************************************
    
    /**
     * Helper function to cut the string returned by Date.toString() into only necessary info 
     * @param s
     * @return the new, cut string of date info
     */
    private String cutDate(String s) {
    	String[] arr = s.split(" ");
    	String sp = " "; 
    	String newS = arr[1] + sp + arr[2] + sp + arr[3] + sp + arr[5];  
    	
    	
    	return newS; 
    }
    
    /** 
     * Method to set high scores once a game is over, reads and writes to files
     * @throws IOException
    */
    private boolean setHighScore(int score) throws IOException {
    	
    	// read old high scores
    	BufferedReader reader = new BufferedReader(new FileReader(pathToScores));
    	List<Score> oldScores = new LinkedList<Score>(); 
    	for (int i = 0; i < 5; i++) {
    		oldScores.add(new Score(reader.readLine())); 
    	}
    	reader.close(); 
    	
    	// Calendar to determine time and date
    	Calendar cal = Calendar.getInstance(); 
    	String date = cal.getTime().toString(); 
    	String cutDate = cutDate(date); 
    	String scoreDate = score + " on " + cutDate; 
    	
    	// determine new high scores
    	boolean highScore = (score > oldScores.get(0).getScore()); 
    	boolean usedScore = false; 
    	List<Score> newScores = new LinkedList<Score>();
    	for (int i = 0; i < 5; i++) {
    		if (!usedScore && score > oldScores.get(0).getScore()) {
    			newScores.add(new Score(score, scoreDate)); 
    			usedScore = true; 
    		} else {
    			newScores.add(oldScores.remove(0)); 
    		}
    	}

    	
    	// write new high scores
		BufferedWriter bw = new BufferedWriter(new FileWriter(pathToScores, false)); 
		for (Score i : newScores) {
			bw.write(i.toString());
			bw.newLine();
		}
		bw.close(); 
		
		return highScore; 
    	
    }
    
    
    
    // ****************************************************************************
    // ************* TICK (RUNS THE GAME) *****************************************
    // ****************************************************************************

    /**
     * This method is called every time the timer defined in the constructor triggers.
     * @throws IOException 
     */
    void tick() throws IOException {
        if (playing) {
        	
        	// if auto mode on, maybe flap
        	if (auto) {
        		AI ai = new AI(bird, pipes.closestHeight()); 
        		if (ai.flaps()) {
        			flap(); 
        		}	
        	}
        	
        	// move the bird and pipes and ground
            bird.move();
            
            Iterator<Ground> groundIterator = grounds.iterator(); 
            while (groundIterator.hasNext()) {
            	groundIterator.next().move();
            }
            
            pipes.move();

        	// update score
            if (pipes.needsScore()) {
            	score.incr();  
            }
            
        	//Game state updating
            status.setText("Playing"); 
            if (auto) {
            	status.setText("AI Playing");
            }
            // check for the game end conditions
            if (pipes.intersects(bird) || bird.getPy() > 550 || bird.getPy() < 0) {
                playing = false;
                status.setText("You lose!");
                if (!auto) {
                	if (setHighScore(score.getCount())) {
                		status.setText("New High Score!");
                	}
                }
                return; 
            } 
            
            // reset ground, if necessary
            if (grounds.peek().getPx() <= -1 * COURT_WIDTH + 5) {
            	grounds.pop(); 
            	grounds.add(new Ground(465)); 
            }
            

        	
            
            

            // update the display
            repaint();
        }
    }
    
    
    
    
    
    // ****************************************************************************
    // ************* PAINTING COMPONENTS ******************************************
    // ****************************************************************************
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Iterator<Ground> groundIterator = grounds.iterator(); 
        while (groundIterator.hasNext()) {
        	groundIterator.next().draw(g);
        }
        bird.draw(g);
        
        pipes.draw(g); 
        
        score.draw(g);
        
        if (instructionsOpen) {
        	Page.drawInstructions(g);
        }
        
        if (scoresOpen) {
        	
        	try {
				Page.drawScores(g, pathToScores);
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}