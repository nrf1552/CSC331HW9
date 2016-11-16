package HW9;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * 
 * @author Nick Fields
 *
 */

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int panelHeight;
	private int panelWidth;
	private BufferedImage img;

	public ImagePanel(ImageComponent ic) {
		panelHeight = ic.height;
		panelWidth = ic.width;
		img = ic.finalImage;
		setPreferredSize(new Dimension(panelWidth, panelHeight));
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.fillRect(0, 0, panelWidth, panelHeight);
		g2.drawImage(img, 0, 0, this);
	}
}