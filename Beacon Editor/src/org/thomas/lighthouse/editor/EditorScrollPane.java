package org.thomas.lighthouse.editor;

import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EditorScrollPane extends JScrollPane {
	private static final long serialVersionUID = 1344934876711733431L;
	
	EditorPane p;
	
	public EditorScrollPane(JPanel p, EditorPane e) {
		super(e);
		this.p = e;
		this.getVerticalScrollBar().setUnitIncrement(8);
	}
	
	public File getFile() {
		return p.file;
	}
}
