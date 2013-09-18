package org.thomas.lighthouse;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.thomas.lighthouse.editor.EditorPane;

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
		add(new JScrollPane(new EditorPane()), BorderLayout.CENTER);
	}
}
