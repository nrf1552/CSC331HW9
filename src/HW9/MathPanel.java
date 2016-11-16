package HW9;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 
 * @author Daniel Emery 
 * This class shows a panel of each instance of MathEngine.
 * These panels cover the bottom layer, which is the actual picture. The
 * panel includes the randomized math problem, a textbox for the user to
 * enter his/her answer, and an enter button for the user to submit the
 * answer.
 *
 */

public class MathPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int attempts = 0; // variable that controls the amount of attempts
								// to answer the math problem
	private int mathAnswer; // variable that holds the answer to the math
							// problem

	private String mathProblem; // variable that holds the string representation
								// of the math problem
	private JTextField fieldAnswer; // variable that holds whatever the user
									// enters in the TextField
	private long solveTime;     //These 3 variables are used to calculate the time 
	private long startTime;     //to solve each math problem
	private long current;

	private MathEngine math;    //math variable for each instance of MathEngine
	private ImageComponent imageComponent;
	
	JLabel problemLabel;
	JPanel entryPanel;
	JLabel textfieldLabel;
	JButton enterButton;

	//Constructor
	public MathPanel(ImageComponent ic) {
		imageComponent = ic;
		math = new MathEngine(ic.viewer.selectedNumber, ic.viewer.isAddSubtract);
		mathProblem = math.getProblem();
		mathAnswer = math.getAnswer();
	}

	//Method to create a JPanel for each split image
	public JPanel showPanel() {

		this.setLayout(new GridLayout(4, 1));

		// Creates a JLabel that shows the math problem
		problemLabel = new JLabel(mathProblem, SwingConstants.CENTER);
		problemLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
		this.add(problemLabel);

		// Creates a JTextField for user input
		entryPanel = new JPanel();
		entryPanel.setLayout(new GridLayout(1, 2));

		textfieldLabel = new JLabel("Enter answer: ", SwingConstants.RIGHT);
		fieldAnswer = new JTextField();
		fieldAnswer.setPreferredSize(new Dimension(200, 20));
		
		//Creates a KeyListener for the user to press enter to submit their answer
		fieldAnswer.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					validateAnswer();
				}
			}
		});
		textfieldLabel.setLabelFor(fieldAnswer);
		entryPanel.add(textfieldLabel);
		entryPanel.add(fieldAnswer);
		this.add(entryPanel);

		// Creates a JButton for the user to submit their answer to the math
		// problem
		enterButton = new JButton("Enter");
		enterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validateAnswer();
			}
		});
		this.add(enterButton);
		startTime = System.nanoTime();

		return this;
	}

	public long getElapsedTime() {
		return solveTime;
	}

	private void validateAnswer() {
		// Creates an ActionListener: When enter button is pressed,
		// check to see if fieldAnswer is equal to math problem solution
		// attempts variable increments by 1 on each attempt
		attempts += 1;

		// If user's answer is correct, record the time, store it in
		// the solveTime variable, and show the image underneath
		if (math.isCorrect(fieldAnswer.getText())) {
			current = System.nanoTime();
			solveTime = current - startTime;

			imageComponent.showImageLayer();
			imageComponent.viewer.recordWin(getElapsedTime());

			// Once the attempts variable is equal to 2, show the correct answer
		} else {
			if (attempts == 2) {
				fieldAnswer.setText(Integer.toString(mathAnswer));
				fieldAnswer.setEditable(false);
				textfieldLabel.setText("Correct answer:");
				enterButton.setEnabled(false);
				imageComponent.viewer.recordLoss();
			}
		}
	}
}