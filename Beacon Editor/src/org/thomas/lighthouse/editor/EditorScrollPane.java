package org.thomas.lighthouse.editor;

import java.io.File;

import javax.swing.JScrollPane;

public class EditorScrollPane extends JScrollPane {
	private static final long serialVersionUID = 1344934876711733431L;
	
	EditorPane p;
	
	public EditorScrollPane(EditorPane p) {
		super(p);
		this.p = p;
	}
	
	public File getFile() {
		return p.file;
	}
}
