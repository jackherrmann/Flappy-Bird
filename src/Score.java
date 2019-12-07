// Basic class for keeping track of scores, as well as the date on which they were achieved
// Also includes a method for comparing two scores
public class Score {
	
	private int score; 
	private String scoreDate; 
	
	public Score(int score, String scoreDate) {
		this.score = score; 
		this.scoreDate = scoreDate; 
	}
	
	public Score(String scoreDate) {
		this.scoreDate = scoreDate; 
		String[] strings = scoreDate.split(" "); 
		this.score = Integer.parseInt(strings[0]); 
	}
	
	public String toString() {
		return scoreDate; 
	}
	
	public int getScore() {
		return score; 
	}
	
	public boolean isBetterThan(Score that) {
		return this.score > that.getScore(); 
	}
	
	

}
