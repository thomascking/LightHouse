package org.thomas.lighthouse.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class DownloadAction extends AbstractAction {
	private static final long serialVersionUID = 3269471814489163489L;
	
	private EditorPane p;
	private SFTPPanel panel;
	
	public DownloadAction(EditorPane p, SFTPPanel panel) {
		this.p = p;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] options = new String[]{"Download", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, 
				panel, 
				"SFTP Upload",
				JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		if (option != 0) return;
		p.download(panel.getFTPName(), 
				panel.getHost(), 
				panel.getDirectory(), 
				panel.getPassword());
	}
}
