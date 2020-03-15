import java.util.ArrayList;
import java.util.Random;
/*
 * Scrambler Class
 * Generates a randomized scramble and keeps track of the solve times
 */
public class Scrambler{
	private double[] times;
	private int worst = 0;
	private int best = 0;
	private int current = 0;
	private Random rand = new Random();
	private String cube;
	
	/*
	 * Scrambler constructor
	 * Takes a number of solves entered and generates a different scramble depending on the type of cube used.
	 */
	public Scrambler(int solves, String cube) {
		times = new double[solves];
		this.cube = cube;
	}
	
	/*
	 * scramble method
	 * Generates a different scramble depending on the type of cube used
	 */
	public String scramble() {
		if(cube.equals("3x3")) {
			return threeScramble();
		}
		else if(cube.equals("2x2")){
			return twoScramble();
		}
		else if(cube.equals("Skewb")) {
			return skewbScramble();
		}
		else if(cube.equals("Pyraminx")) {
			return pyraminxScramble();
		}
		else {
			return "No Scramble";
		}
	}
	
	/*
	 * threeScramble method
	 * Generates a scramble for 3x3 cubes
	 */
	public String threeScramble() {
		String scramble = "";
		char[] moves = {'U', 'D', 'F', 'B', 'L', 'R'};
		int prev = 6;
		int last = 6;
		for(int i = 0; i < 21; i++) {
			int move = rand.nextInt(6);
			while(move == prev || move == last) {
				move = rand.nextInt(6);
			}
			if (move == 0 && prev == 1 || move == 1 && prev == 0 || 
					move == 2 && prev == 3 || move == 3 && prev == 2 || 
					move == 4 && prev == 5 || move == 5 && prev == 4) {
				last = prev;
			}
			else {
				last = 6;
			}
			prev = move;
			scramble += moves[move];
			int direction = rand.nextInt(3);
			if(direction == 2) {
				scramble += "2";
			}
			else if (direction == 1) {
				scramble += "'";
			}
			scramble += " ";
		}
		return "Solve "+ (current+1) + ": " +scramble;
	}
	
	/*
	 * twoScramble method
	 * Generates a scramble for 2x2 solves
	 */
	public String twoScramble() {
		String scramble = "";
		char[] moves = {'U', 'F', 'R'};
		int prev = 3;
		for(int i = 0; i < 9; i++) {
			int move = rand.nextInt(3);
			while(move == prev) {
				move = rand.nextInt(3);
			}
			prev = move;
			scramble += moves[move];
			int direction = rand.nextInt(3);
			if(direction == 2) {
				scramble += "2";
			}
			else if (direction == 1) {
				scramble += "'";
			}
			scramble += " ";
		}
		return "Solve "+ (current+1) + ": " +scramble;
	}
	
	/*
	 * skewbScramble method
	 * Generates a scramble for Skewb solves
	 */
	public String skewbScramble() {
		String scramble = "";
		char[] moves = {'U', 'B', 'R', 'L'};
		int prev = 4;
		for(int i = 0; i < 8; i++) {
			int move = rand.nextInt(4);
			while(move == prev) {
				move = rand.nextInt(4);
			}
			prev = move;
			scramble += moves[move];
			int direction = rand.nextInt(2);
			if (direction == 1) {
				scramble += "'";
			}
			scramble += " ";
		}
		return "Solve "+ (current+1) + ": " +scramble;
	}
	
	/*
	 * pyraminxScramble method
	 * Generates a scramble for Pyraminx solves
	 */
	public String pyraminxScramble() {
		String scramble = "";
		char[] moves = {'U', 'B', 'R', 'L'};
		int prev = 4;
		for(int i = 0; i < 9; i++) {
			int move = rand.nextInt(4);
			while(move == prev) {
				move = rand.nextInt(4);
			}
			prev = move;
			scramble += moves[move];
			int direction = rand.nextInt(2);
			if (direction == 1) {
				scramble += "'";
			}
			scramble += " ";
		}
		int uTip = rand.nextInt(3);
		if(uTip == 1) {
			scramble += "u ";
		}
		else if(uTip == 2) {
			scramble += "u' ";
		}
		int lTip = rand.nextInt(3);
		if(lTip == 1) {
			scramble += "l ";
		}
		else if(lTip == 2) {
			scramble += "l' ";
		}
		int rTip = rand.nextInt(3);
		if(rTip == 1) {
			scramble += "r ";
		}
		else if(rTip == 2) {
			scramble += "r' ";
		}
		int bTip = rand.nextInt(3);
		if(bTip == 1) {
			scramble += "b ";
		}
		else if(bTip == 2) {
			scramble += "b' ";
		}
		return "Solve "+ (current+1) + ": " +scramble;
	}
	
	/*
	 * setTime method
	 * Adds a new solve time to the solve time list
	 */
	public void setTime(double time) {
		times[current] = time;
		if(time > times[worst]) {
			worst = current;
		}
		if(time < times[best]) {
			best = current;
		}
		current++;
	}
	
	/*
	 * bestAndWorst method
	 * Finds the best and worst times after deleting a solve time
	 */
	private void bestAndWorst() {
		worst = 0;
		best = 0;
		for(int i = 0; i < current; i++) {
			if(times[i] > times[worst]) {
				worst = i;
			}
			else if(times[i] < times[best]) {
				best = i;
			}
		}
	}
	
	/*
	 * getTimes method
	 * Prints a list of solve times for the CubeFrame to display
	 */
	public ArrayList<String> getTimes(){
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < times.length; i++) {
			String s = ""+ (i+1) +". ";
			if(i == worst || i == best) {
				s += "(";
			}
			s += ""+times[i];
			if(i == worst || i == best) {
				s += ")";
			}
			list.add(s);
		}
		return list;
	}
	
	/*
	 * finished method
	 * Returns true if all solve times have been entered
	 */
	public boolean finished() {
		return current == times.length;
	}
	
	/*
	 * getAverage method
	 * Returns average of all of the solve times not including the best and worst times.
	 */
	public double getAverage() {
		double sum = 0;
		for(int i = 0; i < times.length; i++) {
			if(i != worst && i != best) {
				sum += times[i];
			}
		}
		double average = (double)Math.round(sum/(times.length-2)*100)/100;
		return average;
	}
	
	/*
	 * plus2 method
	 * Adds two seconds to the most recent solve time
	 */
	public boolean plus2() {
		if(current > 0) {
			times[current-1] += 2;
			bestAndWorst();
			return true;
		}
		return false;
	}
	
	/*
	 * delete method
	 * Deletes the most recent solve time
	 */
	public boolean delete() {
		if(current > 0) {
			current--;times[current] = 0;
			bestAndWorst();
			return true;
		}
		return false;
	}
	
	/*
	 * reset method
	 * Creates a new list of solve times
	 */
	public void reset(int solves, String cube) {
		times = new double[solves];
		best = 0;
		worst = 0;
		current = 0;
		this.cube = cube;
	}
}
