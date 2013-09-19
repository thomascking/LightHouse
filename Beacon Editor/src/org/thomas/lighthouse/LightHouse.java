package org.thomas.lighthouse;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.thomas.lighthouse.editor.EditorPane;
import org.thomas.lighthouse.scripts.ScriptPanel;

public class LightHouse extends JFrame {
	private static final long serialVersionUID = -4406258341931634328L;

	public static void main(String[] args) {
		LightHouse lh = new LightHouse();
		lh.setVisible(true);
		lh.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public LightHouse() {
		super("Light House");
		loadConfigs();
		loadEditorElements();
	}
	
	public void loadConfigs() {
		setSize(800, 600);
	}
	
	public void loadEditorElements() {
		this.setLayout(new BorderLayout());
		EditorPane p = new EditorPane();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(p, BorderLayout.CENTER);
		add(new JScrollPane(panel), BorderLayout.CENTER);
		add(new JScrollPane(new ScriptPanel(p)), BorderLayout.WEST);
	}
}
