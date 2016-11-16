package HW9;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {

	public String resultsFilePath;
	public String highScoreTime;
	public String highScoreUser;
	
	
	public Integer selectedNumber;
	public Integer selectedNumberOfPanels;
	public Boolean isAddSubtract;
	public String selectedImage;

	private JFrame frame;
	private BufferedImage[] images;
	private JPanel panelContainer;
	private List<Long> times;
	private int wins;
	private int losses;
	private JPanel resultPanel;

	public Main() {
		// Instantiate JFrame
		frame = new JFrame("Homework 9");

		// Set initial properties
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		//add result panel
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
		displayImageComponents();
	}

	public void displayImageComponents() {

		if (selectedNumber != null && selectedNumberOfPanels != null && isAddSubtract != null
				&& selectedImage != null) {

			// re-initialize game variable
			times = new ArrayList<Long>();
			wins = 0;
			losses = 0;

			int size = (int) Math.sqrt(selectedNumberOfPanels);

			images = new ImageSplitter().splitImage(selectedNumberOfPanels, selectedImage, false);
			panelContainer.removeAll();
			panelContainer.setPreferredSize(getImageDimension(selectedImage));
			panelContainer.setLayout(new GridLayout(size, size));
			
			for (BufferedImage img : images) {
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
			JLabel label = new JLabel("Total answered correctly: " + wins + "; Average time to finish: " + getAverageElapsedTime() + "ms");
			resultPanel.removeAll();
			resultPanel.add(label);
			frame.repaint();
		}
	}

	public void increaseGameLevel()	{
		
	}
	
	public void writeRunDataToFile(){
		
	}
	
	public void readRunDataFromFile(){
		
	}
	
	public static void main(String[] args) {
		new Main();
	}

	// Method to get average time for each problem correctly answered
	private long getAverageElapsedTime() {
		long total = 0;

		for (Long t : times) {
			total += t;
		}

		return total / times.size();
	}
	
	private Dimension getImageDimension(String file) {
		BufferedImage image = null;
		
		try {
			File filename = new File(file);
			FileInputStream fis = new FileInputStream(filename);
			image = ImageIO.read(fis);
			
		} catch (

		IOException e) {
			e.printStackTrace();
		}
		
		return new Dimension(image.getWidth(), image.getHeight());
	}
}
