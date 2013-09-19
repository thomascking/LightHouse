package org.thomas.lighthouse.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

public class OpenAction extends AbstractAction {
	private static final long serialVersionUID = 3269471814489163489L;
	
	private EditorPane p;
	private JFileChooser fc;
	
	public OpenAction(EditorPane p, JFileChooser fc) {
		this.p = p;
		this.fc = fc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (fc.showOpenDialog(p) == JFileChooser.APPROVE_OPTION)
			p.open(fc.getSelectedFile());
	}
}
