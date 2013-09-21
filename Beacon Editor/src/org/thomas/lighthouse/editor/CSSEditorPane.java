package org.thomas.lighthouse.editor;

import java.io.File;

public class CSSEditorPane extends EditorPane {
	private static final long serialVersionUID = -2434147820785174571L;
	
	public CSSEditorPane() {
	}
	
	@Override
	public void setup(File f) {
		this.setContentType("text/css");
		super.setup(f);
	}
}
