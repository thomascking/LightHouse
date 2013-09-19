package org.thomas.lighthouse.scripts;

import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.thomas.lighthouse.editor.EditorPane;

public class ScriptPanel extends JPanel {
	private static final long serialVersionUID = 2346116728723944658L;
	
	ScriptAction[] actions;
	
	public ScriptPanel(EditorPane p) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		File f = new File(".\\scripts");
		String[] scripts = f.list();
		actions = new ScriptAction[scripts.length];
		for (int i = 0; i < scripts.length; i++) {
			actions[i] = new ScriptAction(scripts[i], p);
			add(new ScriptButton(actions[i]));
		}
	}
}
