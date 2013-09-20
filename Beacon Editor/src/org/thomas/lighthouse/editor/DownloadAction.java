package org.thomas.lighthouse.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.thomas.lighthouse.LightHouse;

public class DownloadAction extends AbstractAction {
	private static final long serialVersionUID = 3269471814489163489L;
	
	private EditorPane p;
	
	public DownloadAction(EditorPane p) {
		this.p = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] options = new String[]{"Download", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, 
				LightHouse.sftPanel, 
				"SFTP Upload",
				JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		if (option != 0) return;
		p.download(LightHouse.sftPanel.getFTPName(), 
				LightHouse.sftPanel.getHost(), 
				LightHouse.sftPanel.getDirectory(), 
				LightHouse.sftPanel.getPassword());
	}
}
