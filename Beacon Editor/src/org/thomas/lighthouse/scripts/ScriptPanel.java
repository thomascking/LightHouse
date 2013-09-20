package org.thomas.lighthouse.scripts;

import java.awt.Component;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScriptPanel extends JPanel {
	private static final long serialVersionUID = 2346116728723944658L;
	
	ScriptAction[] actions;
	
	public ScriptPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		File f = new File(".\\Scripts");
		this.add(getNode(f));
	}
	
	public Component getNode(File file) {
		if (file.isDirectory()) {
			JPanel node = new JPanel();
			node.setLayout(new BoxLayout(node, BoxLayout.Y_AXIS));
			node.add(new JLabel(file.getName()));
			for (File f : file.listFiles()) {
				node.add(getNode(f));
			}
			return node;
		}
		ScriptAction action = new ScriptAction(file);
		ScriptButton node = new ScriptButton(action);
		return node;
	}
}
