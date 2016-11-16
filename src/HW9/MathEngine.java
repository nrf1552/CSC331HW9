package HW9;

import java.util.Random;

/**
 * 
 * @author Daniel Emery
 * MathEngine class creates a math problem based on the user's 
 * selected number, a randomly generated number between 1 and 12,
 * and whether or not the problems are based on Addition/Subtraction
 * or Multiplication/Division.
 * 
 */

public class MathEngine {

	private int userNumber;      //variable that holds the number selected by the user in the menu
	private int randomNum;       //variable that holds the randomly generated number between 1 and 12
	private int answer;          //variable that holds the answer to the math problem
	private String problem;      //variable that holds the string representation of the math problem
	
	
	//Constructor
	public MathEngine(int userInput, boolean isAddSubtract) {
		
		userNumber = userInput;
		randomNum = getRandomInt();
		
		//if isAddSubtract = true, then math problems will only use + or -
		if (isAddSubtract) {
			addSubtract();
			
		//if isAddSubtract = false, then the math problems will only use * or /
		}
		else {
			multDivide();
		}
	}
	
	//Method that returns string representation of the math problem
	public String getProblem() {
		return problem;
	}
	
	//Method that checks if the user's answer is equal to the actual answer
	//returns true if it is, false otherwise
	public boolean isCorrect(int userAnswer) {
		if (userAnswer == answer) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public boolean isCorrect(String userAnswer) {
		//Parses the string argument userAnswer as a 
		//signed decimal integer
		return isCorrect(Integer.parseInt(userAnswer));
	}

	public int getAnswer() {
		return answer;
	}

	//Method for generating math problems with either + or - at random
	//by randomly generating a boolean
	private void addSubtract() {
		boolean isAddition = getRandomBool();

		if (isAddition) {
			problem = userNumber + " + " + randomNum;
			answer = userNumber + randomNum;
		}
		else {
			problem = userNumber + " - " + randomNum;
			answer = userNumber - randomNum;
		}
	}
	
	//Method for generating math problems with either * or / at random
	//by randomly generating a boolean
	private void multDivide() {
		boolean isMult = getRandomBool();
		if (isMult) {
			problem = userNumber + " * " + randomNum;
			answer = userNumber * randomNum;
		}
		else {
			problem = userNumber + " / " + randomNum;
			answer = userNumber / randomNum;
		}

	}

	//Method to determine random boolean that is used in 
	//multDivide() and addSubtract()
	private boolean getRandomBool() {
		return new Random().nextBoolean();
	}

	//Method that returns a random value between
	//1 and 12 for the math problems
	private int getRandomInt() {
		return new Random().nextInt(12);
	}
}