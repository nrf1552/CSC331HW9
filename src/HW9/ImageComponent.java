package HW9;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageComponent extends JPanel {

	static final long serialVersionUID = 1L;
	
	static final String TOP = "TopPanel";
	static final String MATH = "MathPanel";
	static final String IMAGE = "ImagePanel";

	public BufferedImage finalImage;
	public Viewer viewer;

	int width;
	int height;
	
	CardLayout cardLayout;
	TopPanel topPanel;
	MathPanel mathPanel;
	ImagePanel imagePanel;
	
	public ImageComponent(BufferedImage image, Viewer v) {
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
		add(mathPanel.showPanel(), MATH);
		add(imagePanel, IMAGE);
	}

	public void showTopLayer() {
		cardLayout.show(this, TOP);
	}

	public void showMathLayer() {
		cardLayout.show(this, MATH);
	}

	public void showImageLayer() {
		cardLayout.show(this, IMAGE);
	}
	
	public void playCorrectSound(){
		
	}
	
	public void playIncorrectSound(){
		
	}
}