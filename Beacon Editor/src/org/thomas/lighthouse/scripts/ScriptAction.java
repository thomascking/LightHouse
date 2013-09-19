package org.thomas.lighthouse.scripts;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.thomas.lighthouse.editor.EditorPane;

public class ScriptAction extends AbstractAction {
	private static final long serialVersionUID = -7377295576987335025L;
	
	String name = "";
	ScriptRunner runner;
	EditorPane p;

	public ScriptAction(String name, EditorPane p) {
		this.p = p;
		this.name = name.replace(".py", "");
		runner = new ScriptRunner("scripts\\" + name);
	}
	
	public String toString() {
		return name;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
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
