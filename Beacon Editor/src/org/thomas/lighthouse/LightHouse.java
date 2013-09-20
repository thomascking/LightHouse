package org.thomas.lighthouse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.thomas.lighthouse.editor.EditorPane;
import org.thomas.lighthouse.editor.EditorScrollPane;
import org.thomas.lighthouse.editor.SFTPPanel;
import org.thomas.lighthouse.project.Project;
import org.thomas.lighthouse.project.ProjectPanel;
import org.thomas.lighthouse.scripts.ScriptPanel;

public class LightHouse extends JFrame {
	private static final long serialVersionUID = -4406258341931634328L;
	
	public static Project project;
	public static SFTPPanel sftPanel;
	public static EditorPane currentPane;
	
	private Dimension size = new Dimension(800, 600);
	private static boolean isMaximized = false;
	private static String defaultProject = "";
	
	private static LightHouse lh;
	private static JTabbedPane tabPane;

	public static void main(String[] args) {
		loadConfig(new File("settings.txt"));
		
		if (args.length > 0) {
			project = Project.load(new File(args[0]));
		}
		else if (defaultProject != "") {
			project = Project.load(new File(defaultProject));
		}
		else {
			project = null;
		}
		sftPanel = new SFTPPanel();
		
		tabPane = new JTabbedPane();
		
		lh = new LightHouse();
		lh.setVisible(true);
		if (isMaximized) {
			lh.setExtendedState(lh.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		}
		lh.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public LightHouse() {
		super("Light House");
		loadEditorElements();
		setSize(size);
	}
	
	public static void loadConfig(File settings) {
		
	}
	
	public void loadEditorElements() {
		this.setLayout(new BorderLayout());
		add(tabPane, BorderLayout.CENTER);
		
		JScrollPane temp = new JScrollPane(new ScriptPanel());
		temp.getVerticalScrollBar().setUnitIncrement(8);
		add(temp, BorderLayout.EAST);
		
		temp = new JScrollPane(new ProjectPanel());
		temp.getVerticalScrollBar().setUnitIncrement(8);
		add(temp, BorderLayout.WEST);
	}
	
	public static void addTab(File f) {
		Component[] comps = tabPane.getComponents();
		for (Component comp : comps) {
			if (comp instanceof EditorScrollPane) {
				if (((EditorScrollPane)comp).getFile().equals(f)) {
					tabPane.setSelectedComponent(comp);
					return;
				}
			}
		}
		Component c = new EditorScrollPane(new EditorPane(f));
		tabPane.addTab(f.getName(), c);
		tabPane.setSelectedComponent(c);
	}
}
