package org.thomas.lighthouse.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class DownloadAction extends AbstractAction {
	private static final long serialVersionUID = 3269471814489163489L;
	
	private EditorPane p;
	
	public DownloadAction(EditorPane p) {
		this.p = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = JOptionPane.showInputDialog("What is your username?");
		String host = JOptionPane.showInputDialog("What is the host?");
		String directory = JOptionPane.showInputDialog("What is your project directory (full path please)?");
		String password = JOptionPane.showInputDialog("What is your password?");
		p.download(name, host, directory, password);
	}
}
