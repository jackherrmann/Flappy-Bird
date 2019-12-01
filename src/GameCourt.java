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

    // the state of the game logic
    private Bird bird; // the bird, falls and can jump
    private Ground ground1; // the ground, sits
    private Ground ground2; // another ground, also sits
    private LinkedList<PipePair> pipe; // the pipes, move across screen
    private int score; // the players score, incremented when pipes pass
    private int numTicks; // number of ticks since last pipe added

    public boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."

    // Game constants
    public static final int COURT_WIDTH = 465;
    public static final int COURT_HEIGHT = 625;
    public static final int JUMP_VELOCITY = -20;
    
    // high scores
    static final String pathToScores = "files/high_scores.csv";

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    bird.setVy(JUMP_VELOCITY);
                } 
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        bird = new Bird(COURT_WIDTH, COURT_HEIGHT);
        ground1 = new Ground(0); 
        ground2 = new Ground(465); 
        pipe = new LinkedList<PipePair>(); 
        pipe.add(new PipePair(COURT_WIDTH, COURT_HEIGHT, 465)); 
        score = 0; 
        numTicks = 0; 

        playing = true;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    private boolean setHighScore(int score) throws IOException {
    	
    	// read old high scores
    	BufferedReader reader = new BufferedReader(new FileReader(pathToScores));
    	List<Integer> oldScores = new LinkedList<Integer>(); 
    	for (int i = 0; i < 5; i++) {
    		oldScores.add(Integer.parseInt(reader.readLine())); 
    	}
    	reader.close(); 
    	
    	// determine new high scores
    	boolean highScore = (score >= oldScores.get(0)); 
    	boolean usedScore = false; 
    	List<Integer> newScores = new LinkedList<Integer>();
    	for (int i = 0; i < 5; i++) {
    		if (!usedScore && score > oldScores.get(0)) {
    			newScores.add(score); 
    			usedScore = true; 
    		} else {
    			newScores.add(oldScores.remove(0)); 
    		}
    	}

    	
    	// write new high scores
		BufferedWriter bw = new BufferedWriter(new FileWriter(pathToScores, false)); 
		for (int i : newScores) {
			bw.write(Integer.toString(i));
			bw.newLine();
		}
		bw.close(); 
		
		return highScore; 
    	
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     * @throws IOException 
     */
    void tick() throws IOException {
        if (playing) {


        	
        	if (pipe.peek().gone()) {
            	pipe.removeFirst(); 
            	pipe.add(new PipePair(COURT_WIDTH, COURT_HEIGHT, 465)); 
            }
        	
        	//Game state updating
            status.setText("Score: " + score); 
            // check for the game end conditions
            if (pipe.peek().intersects(bird) || bird.getPy() > 525 || bird.getPy() < 0) {
                playing = false;
                status.setText("You lose! Score: " + score);
                if (setHighScore(score)) {
                	status.setText("You Lose: New High Score: " + score);
                }
                return; 
            } 
            
            // reset ground, if necessary
            if (ground1.getPx() <= -1 * COURT_WIDTH + 5) {
            	ground1.reset(); 
            }
            if (ground2.getPx() <= -1 * COURT_WIDTH + 5) {
            	ground2.reset(); 
            }

        	
            // move the bird and pipes
            bird.move();
            ground1.move(); 
            ground2.move(); 
            Iterator<PipePair> pipeIterator = pipe.iterator(); 
            while (pipeIterator.hasNext()) {
            	pipeIterator.next().move();
            }
            
            
            // update score
            if (pipe.peek().scored()) {
            	score++;  
            }

            // update the display
            repaint();
        }
    }
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ground1.draw(g);
        ground2.draw(g);
        bird.draw(g);
        Iterator<PipePair> pipeIterator = pipe.iterator(); 
        while (pipeIterator.hasNext()) {
        	pipeIterator.next().draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}