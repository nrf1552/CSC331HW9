package HW9;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 
 * @author Daniel Emery This class shows a panel of each instance of MathEngine.
 *         These panels cover the bottom layer, which is the actual picture. The
 *         panel includes the randomized math problem, a textbox for the user to
 *         enter his/her answer, and an enter button for the user to submit the
 *         answer.
 *
 */

public class MathPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int attempts = 0; // variable that controls the amount of attempts
								// to answer the math problem
	private int mathAnswer;
	private String mathProblem;
	private JTextField fieldAnswer;
	private int userNumber;
	private int randomNum;
	private long startTime;
	private long stopTime;

	private ImageComponent imageComponent;

	JLabel problemLabel;
	JPanel entryPanel;
	JLabel textfieldLabel;
	JButton enterButton;

	// Constructor
	public MathPanel(ImageComponent ic) {
		imageComponent = ic;
		initMathProblem(ic.viewer.selectedNumber, ic.viewer.isAddSubtract);

		this.setLayout(new GridLayout(4, 1));

		// Creates a JLabel that shows the math problem
		problemLabel = new JLabel(mathProblem, SwingConstants.CENTER);
		problemLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
		problemLabel.setFocusable(false);
		this.add(problemLabel);

		// Creates a JTextField for user input
		entryPanel = new JPanel();
		entryPanel.setLayout(new GridLayout(1, 2));
		entryPanel.setFocusable(false);

		textfieldLabel = new JLabel("Enter answer: ", SwingConstants.RIGHT);
		textfieldLabel.setFocusable(false);

		fieldAnswer = new JTextField();
		fieldAnswer.setPreferredSize(new Dimension(200, 20));

		// Creates a KeyListener for the user to press enter to submit their
		// answer
		fieldAnswer.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// for demo purposes, populates the correct answer
				if (e.getKeyCode() == KeyEvent.VK_DEAD_TILDE) {
					fieldAnswer.setText(Integer.toString(mathAnswer));
					validateAnswer();
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!fieldAnswer.getText().isEmpty()) {
						validateAnswer();
					}
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
	}

	public void startTimer() {
		startTime = System.nanoTime();
	}

	public void setFocusInTextField() {
		fieldAnswer.requestFocus();
	}

	private long getElapsedTime() {
		return stopTime - startTime;
	}

	private void validateAnswer() {
		// Creates an ActionListener: When enter button is pressed,
		// check to see if fieldAnswer is equal to math problem solution
		// attempts variable increments by 1 on each attempt
		attempts += 1;

		// If user's answer is correct, record the time, store it in
		// the solveTime variable, and show the image underneath
		
		boolean isNumeric = true;
		try {
			Integer.parseInt(fieldAnswer.getText());
		}
		catch (Exception e){
			isNumeric = false;
		}
		
		//for demo purposes, the tilde will pass
		if(fieldAnswer.getText().equalsIgnoreCase("~")){
			stopTime = System.nanoTime();
			imageComponent.playCorrectSound();
			imageComponent.showImageLayer();
			imageComponent.viewer.recordWin(getElapsedTime());
		} else if (isNumeric && Integer.parseInt(fieldAnswer.getText()) == mathAnswer) {
			stopTime = System.nanoTime();
			imageComponent.playCorrectSound();
			imageComponent.showImageLayer();
			imageComponent.viewer.recordWin(getElapsedTime());
			// Once the attempts variable is equal to 2, show the correct answer
		} else {
			imageComponent.playIncorrectSound();
			if (attempts == 2) {
				fieldAnswer.setText(Integer.toString(mathAnswer));
				fieldAnswer.setEditable(false);
				textfieldLabel.setText("Correct answer:");
				enterButton.setEnabled(false);
				imageComponent.viewer.recordLoss();
			}
		}
	}

	private void initMathProblem(int userInput, boolean isAddSubtract) {
		userNumber = userInput;
		randomNum = new Random().nextInt(12);

		boolean toggle = new Random().nextBoolean();

		if (isAddSubtract) {
			if (toggle) {
				mathProblem = userNumber + " + " + randomNum;
				mathAnswer = userNumber + randomNum;
			} else {
				mathProblem = userNumber + " - " + randomNum;
				mathAnswer = userNumber - randomNum;
			}
		} else {
			if (toggle) {
				mathProblem = userNumber + " * " + randomNum;
				mathAnswer = userNumber * randomNum;
			} else {
				if (randomNum==0){
					randomNum += 1;
				}
				mathProblem = userNumber + " / " + randomNum;
				mathAnswer = userNumber / randomNum;
			}
		}
	}
}