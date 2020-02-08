import javax.swing.JOptionPane;

public class Main{
	/*
	 * Main Class
	 * Asks you which kind of cube you are using and how many solves you are going to do.
	 * Then creates a cubeFrame using the information you entered.
	 */
	public static void main(String[] args) {
		String[] cubes = {"3x3", "2x2"};
        int cube = JOptionPane.showOptionDialog(null, null, "Which cube are you using?", JOptionPane.NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,cubes,cubes[0]);
		String solves = JOptionPane.showInputDialog("How many solves are you going to do today?","5");
		int numberOfSolves = Integer.parseInt(solves);
		while(numberOfSolves < 5) {
			solves = JOptionPane.showInputDialog("How many solves are you going to do today?","5");
			numberOfSolves = Integer.parseInt(solves);
		}
		CubeFrame frame = new CubeFrame(numberOfSolves, cubes[cube]);
	}
}
