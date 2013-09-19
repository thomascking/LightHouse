package org.thomas.lighthouse.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

public class SaveAction extends AbstractAction {
	private static final long serialVersionUID = 3269471814489163489L;
	
	private EditorPane p;
	private JFileChooser fc;
	private boolean saveAs = false;
	
	public SaveAction(EditorPane p, JFileChooser fc) {
		this.p = p;
		this.fc = fc;
	}
	
	public SaveAction(EditorPane p, JFileChooser fc, boolean saveAs) {
		this.p = p;
		this.fc = fc;
		this.saveAs = saveAs;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ((!saveAs && fc.getSelectedFile() != null) || fc.showSaveDialog(p) == JFileChooser.APPROVE_OPTION)
			p.save(fc.getSelectedFile());
	}
}
