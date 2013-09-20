package org.thomas.lighthouse.scripts;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;

import org.thomas.lighthouse.LightHouse;
import org.thomas.lighthouse.editor.EditorPane;

public class ScriptAction extends AbstractAction {
	private static final long serialVersionUID = -7377295576987335025L;
	
	String name = "";
	ScriptRunner runner;

	public ScriptAction(File file) {
		this.name = file.getName().replace(".py", "");
		runner = new ScriptRunner(file.getAbsolutePath());
	}
	
	public String toString() {
		return name;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		EditorPane p = LightHouse.currentPane;
		String selected = p.getSelectedText();
		if (selected == null) selected = "";
		String newString = "";
		try {
			newString = runner.run(selected);
		} catch(Exception exc) {
			newString = exc.getMessage();
		}
		p.replaceSelection(newString);
	}
}
