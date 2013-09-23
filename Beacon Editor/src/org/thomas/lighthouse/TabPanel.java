package org.thomas.lighthouse;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -9166365568484988570L;
	
	JTabbedPane pane;
	
	public TabPanel(String name, JTabbedPane tabPane) {
		setOpaque(false);
		
		pane = tabPane;
		
		JLabel label = new JLabel(name);
		
		JButton closeButton = new JButton("x");
		closeButton.addActionListener(this);
		closeButton.setContentAreaFilled(false);
		closeButton.setBorderPainted(false);
		closeButton.setMargin(new Insets(-5, -3, -5, -3));
		
		add(label);
		add(closeButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int i = pane.indexOfTabComponent(this);
		if (i != -1) {
			pane.remove(i);
		}
	}
}
