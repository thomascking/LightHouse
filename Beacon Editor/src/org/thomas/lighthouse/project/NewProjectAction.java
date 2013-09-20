package org.thomas.lighthouse.project;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.thomas.lighthouse.LightHouse;

public class NewProjectAction extends AbstractAction {
	private static final long serialVersionUID = 3269471814489163489L;

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] options = new String[]{"Create", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, 
				LightHouse.newProjectPanel, 
				"New Project",
				JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		if (option != 0) return;
		LightHouse.setProject(new Project(LightHouse.newProjectPanel.getName(), 
				LightHouse.newProjectPanel.getDirectory(), 
				LightHouse.newProjectPanel.getHost(), 
				LightHouse.newProjectPanel.getRemoteDirectory()));
	}
}
