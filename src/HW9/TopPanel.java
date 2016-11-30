package HW9;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
/**
 * 
 * @author Nick Fields
 * 
 * Top panel of game piece
 *
 */
public class TopPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ImageComponent imageComponent;

	public TopPanel(ImageComponent ic) {
		imageComponent = ic;
		setPreferredSize(new Dimension(ic.width, ic.height));
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				imageComponent.showMathLayer();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	}

	// paints the grey top panel
	public void paintComponent(Graphics g) {
		String text = "Click anywhere to begin";
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,112,115)); //UNCW teal
		g2.fillRect(0, 0, imageComponent.width, imageComponent.height);
		g2.setPaint(new Color(255,215,0)); //yellow
		g2.setFont(new Font("Sans-serif", Font.BOLD, 20));
		printOutlinedText(g2, text, imageComponent.width / 4, imageComponent.height / 2);
	}
	
	private void printOutlinedText(Graphics2D g2, String textToOutline, int x, int y){
		int outlineThickness = 3;
		g2.setPaint(new Color(0,51,102)); //UNCW Blue outline color
		g2.drawString(textToOutline, x-outlineThickness, y-outlineThickness);
		g2.drawString(textToOutline, x-outlineThickness, y+outlineThickness);
		g2.drawString(textToOutline, x+outlineThickness, y-outlineThickness);
		g2.drawString(textToOutline, x+outlineThickness, y+outlineThickness);
		g2.setPaint(new Color(255,215,0)); //UNCW Yellow inner color
		g2.drawString(textToOutline, x, y);
	}
}