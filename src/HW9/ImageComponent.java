package HW9;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class ImageComponent extends JPanel {

	static final long serialVersionUID = 1L;
	
	static final String TOP = "TopPanel";
	static final String MATH = "MathPanel";
	static final String IMAGE = "ImagePanel";

	public BufferedImage finalImage;
	public Main viewer;

	int width;
	int height;
	
	CardLayout cardLayout;
	TopPanel topPanel;
	MathPanel mathPanel;
	ImagePanel imagePanel;
	
	
	public ImageComponent(BufferedImage image, Main v) {
		setLayout(new CardLayout());
		cardLayout = (CardLayout)getLayout();
		
		width = image.getWidth();
		height = image.getHeight();
		finalImage = image;
		viewer = v;
		
		setPreferredSize(new Dimension(width, height));
		topPanel = new TopPanel(this);
		mathPanel = new MathPanel(this);
		imagePanel = new ImagePanel(this);
		
		add(topPanel, TOP);
		add(mathPanel, MATH);
		add(imagePanel, IMAGE);
	}

	public void showTopLayer() {
		cardLayout.show(this, TOP);
	}

	public void showMathLayer() {
		mathPanel.startTimer();
		cardLayout.show(this, MATH);
		mathPanel.setFocusInTextField();
	}

	public void showImageLayer() {
		cardLayout.show(this, IMAGE);
		viewer.showRandomPanel();
	}
	
	public void playCorrectSound(){
		viewer.sounds.playCorrectSound();
	}
	
	public void playIncorrectSound(){
		viewer.sounds.playIncorrectSound();
	}
}