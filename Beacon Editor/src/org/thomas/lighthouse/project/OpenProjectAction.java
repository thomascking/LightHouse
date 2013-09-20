package org.thomas.lighthouse.project;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.thomas.lighthouse.LightHouse;

public class OpenProjectAction extends AbstractAction {
	private static final long serialVersionUID = 3269471814489163489L;
	
	private JFileChooser fc;
	
	public OpenProjectAction() {
		fc = new JFileChooser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			LightHouse.setProject(Project.load(fc.getSelectedFile()));
	}
}
