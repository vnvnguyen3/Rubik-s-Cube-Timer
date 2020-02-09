import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
/*
 * CubeFrame class
 * Creates a user interface and updates when entering bottom buttons.
 * Press the space bar to start and stop the timer.
 * Modify button asks to either add 2 seconds to your solve time or delete the solve time.
 * Reset button starts a new session with a cube and number of solves of the user's choice
 * Enter time button allows the user to enter their solve time manually
 */
public class CubeFrame extends JFrame implements KeyListener{
	Scrambler scrambler;
	CubeTimer timer = new CubeTimer();
	
	JPanel top = new JPanel();
	JTextField scramble = new JTextField(60);
	
	JPanel right = new JPanel();
	JTextArea solveList = new JTextArea(10,10);
	
	JPanel middle = new JPanel();
	JTextPane solve = new JTextPane();
	
	JPanel bottom = new JPanel();
	JButton modify = new JButton("MODIFY TIME");
	JButton reset = new JButton("RESET");
	JButton enter = new JButton("ENTER TIME");
	
	Font font;
	/*
	 * CubeFrame constructor
	 * Create the user interface
	 */
	public CubeFrame(int solves, String cube) {
		font = new Font("Helvetica", Font.BOLD,25);
		scrambler = new Scrambler(solves, cube);
		setBounds(350,200,800,600);
		setTitle("Scramble Maker");
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		scramble.setHorizontalAlignment(JTextField.CENTER);
		scramble.setFont(font);
		scramble.setEditable(false);
		scramble.setText(scrambler.scramble());
		top.add(scramble);
		
		solveList.setEditable(false);
		solveList.setFocusable(false);
		solveList.setFont(font);
		JScrollPane scroll = new JScrollPane(solveList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		for(String s: scrambler.getTimes()) {
			solveList.append(s+"\n\r");
		}
		right.add(scroll);
		
		solve.setEditable(false);
		solve.setFont(new Font("Helvetica", Font.BOLD,50));
		middle.add(solve);
		
		Dimension dimension = new Dimension(150,100);
		modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] mods = {"+2", "DELETE"};
		        int mod = JOptionPane.showOptionDialog(null, null, "How would you like to modify your time?", JOptionPane.NO_OPTION,
		                JOptionPane.QUESTION_MESSAGE, null,mods,mods);
		        if(mod==0) {
		        	if(scrambler.plus2()) {
		        		solveList.setText("");
		        		for(String s: scrambler.getTimes()) {
							solveList.append(s+"\n\r");
						}
		        	}
		        }
		        else {
		        	if(scrambler.delete()) {
						solve.setText("DELETED");
						solveList.setText("");
						for(String s: scrambler.getTimes()) {
							solveList.append(s+"\n\r");
						}
						scramble.setText(scrambler.scramble());
						enter.setVisible(true);
					}
		        }
				
			}
		});
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset?");
				if(confirm == 0) {
					String[] cubes = {"3x3", "2x2"};
			        int cube = JOptionPane.showOptionDialog(null, null, "Which cube are you using?", JOptionPane.NO_OPTION,
			                JOptionPane.QUESTION_MESSAGE, null,cubes,cubes[0]);
					String number = JOptionPane.showInputDialog("How many solves are you going to do today?","5");
					int numberOfSolves = Integer.parseInt(number);
					while(numberOfSolves < 5) {
						number = JOptionPane.showInputDialog("How many solves are you going to do today?","5");
						numberOfSolves = Integer.parseInt(number);
					}
					scrambler.reset(numberOfSolves, cubes[cube]);
					solve.setText("");
					solveList.setText("");
					for(String s: scrambler.getTimes()) {
						solveList.append(s+"\n\r");
					}
					scramble.setText(scrambler.scramble());
					enter.setVisible(true);
				}
			}
		});
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String time = JOptionPane.showInputDialog("Enter your time.");
				solve.setText(time);
				getTime();
			}
		});
		modify.setFont(new Font("Helvetica", Font.BOLD,12));
		modify.setFocusable(false);
		modify.setPreferredSize(dimension);
		reset.setFont(new Font("Helvetica", Font.BOLD,12));
		reset.setFocusable(false);
		reset.setPreferredSize(new Dimension(100,100));
		enter.setFont(new Font("Helvetica", Font.BOLD,12));
		enter.setFocusable(false);
		enter.setPreferredSize(dimension);
		bottom.add(modify);
		bottom.add(reset);
		bottom.add(enter);
		
		add(top, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		add(right, BorderLayout.EAST);
		add(middle, BorderLayout.CENTER);
		pack();
		
		addKeyListener(this);
	}
	
	/*
	 * getTime method
	 * The scrambler will record the entered time and CubeFrame updates the list of solve times and add a new scramble
	 */
	public void getTime() {
		double solveTime = Double.parseDouble(solve.getText());
		scrambler.setTime(solveTime);
		solveList.setText("");
		for(String s: scrambler.getTimes()) {
			solveList.append(s+"\n\r");
		}
		if(!scrambler.finished()) {
			scramble.setText(scrambler.scramble());
		}else{
			scramble.setText("Your Average is " + scrambler.getAverage() + ".");
			solve.setEditable(false);
			enter.setVisible(false);
		}
	}
	
	/*
	 * When the user presses the space bar, a hidden timer will start.
	 * Press the space bar again to stop the timer.
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			if(!timer.isRunning()) {
				timer.start();
				solve.setText("SOLVING");
			}else {
				timer.stop();
				solve.setText(""+timer.getTime());
				getTime();
			}
		}
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
}
