package HW9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 * @author nickf
 * 
 * Basic menu
 */
public class ViewerMenu extends JMenuBar{

	static final long serialVersionUID = 1L;
	Main main;

	public ViewerMenu(Main main){
		this.main = main;
	}
	
	public JMenuBar get() {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuitem;

		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		menuBar.add(menu);

		// exit menu item
		menuitem = new JMenuItem("Exit");
		menu.add(menuitem);
		menuitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		return menuBar;
	}
}
