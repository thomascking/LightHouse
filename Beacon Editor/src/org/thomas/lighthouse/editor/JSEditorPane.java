package org.thomas.lighthouse.editor;

import java.io.File;

public class JSEditorPane extends EditorPane {
	private static final long serialVersionUID = -2434147820785174571L;
	
	public JSEditorPane() {
	}
	
	@Override
	public void setup(File f) {
		this.setContentType("text/javascript");
		super.setup(f);
	}
}
