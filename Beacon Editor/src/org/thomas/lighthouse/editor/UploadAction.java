package org.thomas.lighthouse.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.thomas.lighthouse.LightHouse;

public class UploadAction extends AbstractAction {
	private static final long serialVersionUID = 3269471814489163489L;
	
	private EditorPane p;
	
	public UploadAction(EditorPane p) {
		this.p = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] options = new String[]{"Upload", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, 
				LightHouse.sftPanel, 
				"SFTP Upload",
				JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		if (option != 0) return;
		p.upload(LightHouse.sftPanel.getFTPName(), 
				LightHouse.sftPanel.getHost(), 
				LightHouse.sftPanel.getDirectory(), 
				LightHouse.sftPanel.getPassword());
	}
}
