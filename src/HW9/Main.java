package HW9;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Main {
	public ResultHandler resultHander;
	public Sound sounds;

	//game data
	public String[] images = { "Spring.jpg", "Summer.jpg", "Fall.jpg", "Winter.jpg" };
	public int[] numberOfProblems = { 4, 9, 16 };
	public String[] calculationType = { "Add/Subtract", "Multiply/Divide" };
	
	//defaults
	public String selectedImage = images[0];
	public String user = "Nick";
	public Integer selectedNumber = 0;
	public Integer selectedNumberOfPanels = numberOfProblems[0];
	public Boolean isAddSubtract = true;

	//class variables
	private JFrame frame;
	private BufferedImage[] bufferedImages;
	private JPanel panelContainer;
	private List<Long> times;
	private int wins;
	private int losses;
	private JPanel resultPanel;


	public Main() {
		// Initialize variables
		sounds = new Sound();
		resultHander = new ResultHandler();

		// Set defaults
		selectedNumber = 6;
		selectedNumberOfPanels = 4;
		isAddSubtract = true;

		// Instantiate JFrame
		frame = new JFrame("Homework 9");

		// Set initial properties
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// add result panel
		resultPanel = new JPanel();
		frame.add(resultPanel, BorderLayout.PAGE_START);

		// Add menu
		frame.setJMenuBar(new ViewerMenu(this).get());

		// Add container for all image components
		panelContainer = new JPanel();
		panelContainer.setPreferredSize(new Dimension(1200, 800));
		frame.add(panelContainer, BorderLayout.CENTER);

		// Show it
		frame.setVisible(true);
		frame.pack();

		// Show image components
		displayStartScreen();
	}

	public void displayStartScreen() {
		panelContainer.removeAll();
		// panelContainer.setPreferredSize(getImageDimension(selectedImage));
		panelContainer.setLayout(new GridLayout(3, 1));
		panelContainer.add(new StartPanel(this));
		frame.pack();
		panelContainer.revalidate();
	}

	public void startGame() {

		if (selectedNumber != null && selectedNumberOfPanels != null && isAddSubtract != null
				&& selectedImage != null) {

			// re-initialize game variable
			times = new ArrayList<Long>();
			wins = 0;
			losses = 0;

			int size = (int) Math.sqrt(selectedNumberOfPanels);

			bufferedImages = new ImageSplitter().splitImage(selectedNumberOfPanels, selectedImage, false);
			panelContainer.removeAll();
			panelContainer.setPreferredSize(new Dimension(1200, 800));
			panelContainer.setLayout(new GridLayout(size, size));

			for (BufferedImage img : bufferedImages) {
				panelContainer.add(new ImageComponent(img, this));
			}

			frame.pack();
			panelContainer.revalidate();
		}
	}

	public void recordWin(Long timeToSolve) {
		wins += 1;
		times.add(timeToSolve);
		showResults();
	}

	public void recordLoss() {
		losses += 1;
		showResults();
	}

	public void showResults() {
		if (wins + losses == selectedNumberOfPanels) {
			String saved = "Your results have been saved.";
			
			if (losses > 0) {
				saved = "No results were saved because you failed to answer " + losses + " game piece(s).  Please try again";
			} else {
				resultHander.saveResult(
						user, 
						selectedNumberOfPanels, 
						selectedNumber, 
						isAddSubtract,
						getAverageElapsedTime());
			}
			JLabel label = new JLabel(
					"Total answered correctly: " + wins + "; Average time to finish: "
					+ getAverageElapsedTime() + "ms" + "; Your results have been saved.");
			resultPanel.removeAll();
			resultPanel.add(label);
			
			JButton playAgainButton = new JButton("Play again");
			playAgainButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					resultPanel.removeAll();
					startGame();
				}
			});
			
			JButton returnToStartButton = new JButton("Return to start screen");
			playAgainButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					resultPanel.removeAll();
					displayStartScreen();
				}
			});
			
			resultPanel.add(playAgainButton);
			resultPanel.add(returnToStartButton);
			frame.repaint();
		}
	}

	private long getAverageElapsedTime() {
		long total = 0;

		for (Long t : times) {
			total += t;
		}

		long avg = total / times.size();

		return TimeUnit.MILLISECONDS.convert(avg, TimeUnit.NANOSECONDS);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
