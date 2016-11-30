package HW9;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import HW9.ResultHandler.Result;
/**
 * @author nickf
 */
public class StartPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	JButton startButton;
	JPanel userPanel;
	Main main;
	JPanel panel;
	ButtonGroup calcGroup;
	ButtonGroup numGroup;
	ButtonGroup imgGroup;
	JRadioButton radioButton;
	JComboBox<String> existingUserComboBox;

	public StartPanel(Main main) {
		this.main = main;
		setPreferredSize(new Dimension(800,1200));

		setLayout(new GridLayout(3, 1));

		add(userPanel());
		add(optionsPanel());
		add(playPanel());
				
		enableButtonIfReady();
	}

	private JPanel userPanel() {
		JPanel userPanel = new JPanel(new GridLayout(2, 1));
		userPanel.setBorder(BorderFactory.createTitledBorder("Select previous user or enter name"));
		existingUserComboBox = new JComboBox<>();
		existingUserComboBox.setEditable(true);
		for (String user : main.resultHandler.getUsers()) {
			existingUserComboBox.addItem(user);
		}
		existingUserComboBox.setSelectedItem(main.user);

		existingUserComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) existingUserComboBox.getSelectedItem();
				main.user = name.replace(",", "");
				enableButtonIfReady();
			}
		});
		userPanel.add(existingUserComboBox);
		return userPanel;
	}

	private JPanel optionsPanel() {
		JPanel optionsPanel = new JPanel(new GridLayout(1, 3));
		optionsPanel.setBorder(BorderFactory.createTitledBorder("Select game options"));
		optionsPanel.add(imagesPanel());
		optionsPanel.add(numberOfProblemsPanel());
		optionsPanel.add(calculationTypePanel());

		return optionsPanel;
	}

	private JPanel calculationTypePanel() {
		panel = new JPanel();
		panel.setLayout(new GridLayout(main.calculationType.length + 1, 1));
		panel.setBorder(BorderFactory.createTitledBorder("Select a number and calculation type"));

		// select 0-12 from drop down
		JComboBox<String> numberGroupComboBox = new JComboBox<String>();
		for (int i = 0; i <= 12; i++) {
			final int index = i;
			numberGroupComboBox.addItem(Integer.toString(i));
			numberGroupComboBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					main.selectedNumber = index;
					enableButtonIfReady();
				}
			});
		}
		panel.add(numberGroupComboBox);

		// select calculation type
		calcGroup = new ButtonGroup();
		for (int i = 0; i < main.calculationType.length; i++) {
			radioButton = new JRadioButton(main.calculationType[i]);
			if (i == 0) {
				radioButton.setSelected(true);
			}

			final int index = i;// needed to pass iterator into ActionListener
			radioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					main.isAddSubtract = (main.calculationType[index] == main.calculationType[0]);
					enableButtonIfReady();
				}
			});

			calcGroup.add(radioButton);
			panel.add(radioButton);
		}
		return panel;
	}

	private JPanel numberOfProblemsPanel() {
		panel = new JPanel();
		panel.setLayout(new GridLayout(main.numberOfProblems.length, 1));
		panel.setBorder(BorderFactory.createTitledBorder("Select number of problems"));
		numGroup = new ButtonGroup();
		for (int i = 0; i < main.numberOfProblems.length; i++) {
			radioButton = new JRadioButton(Integer.toString(main.numberOfProblems[i]));
			if (i == 0) {
				radioButton.setSelected(true);
			}

			final int index = i;// needed to pass iterator into ActionListener
			radioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					main.selectedNumberOfPanels = main.numberOfProblems[index];
					enableButtonIfReady();
				}
			});

			numGroup.add(radioButton);
			panel.add(radioButton);
		}
		return panel;
	}

	private JPanel imagesPanel() {
		panel = new JPanel();
		panel.setLayout(new GridLayout(main.images.length, 1));
		panel.setBorder(BorderFactory.createTitledBorder("Select an image"));
		imgGroup = new ButtonGroup();
		for (int i = 0; i < main.images.length; i++) {
			radioButton = new JRadioButton(main.images[i]);

			final int index = i;// needed to pass iterator into ActionListener
			radioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					main.selectedImage = main.images[index];
					enableButtonIfReady();
				}
			});

			imgGroup.add(radioButton);
			panel.add(radioButton);
		}
		return panel;
	}

	private JPanel playPanel() {
		panel = new JPanel(new GridLayout(1,2));
				
		JTable table = new JTable(main.resultHandler.getAllResults(), main.resultHandler.getHeaders());
		panel.add(new JScrollPane(table));
		
		startButton = new JButton("Start");
		startButton.setEnabled(false);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.startGame();
			}
		});
		
		panel.add(startButton);
		
		//panel.add(startButton, BorderLayout.PAGE_END);

				
		return panel;
	}

	private void enableButtonIfReady() {
		String user = (String) existingUserComboBox.getSelectedItem();

		if (numGroup.getSelection() == null || calcGroup.getSelection() == null || imgGroup.getSelection() == null
				|| user.isEmpty()) {
			startButton.setEnabled(false);
		} else {
			startButton.setEnabled(true);
		}
	}
}
