package org.thomas.lighthouse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;

import jsyntaxpane.DefaultSyntaxKit;

import org.thomas.lighthouse.editor.CSSEditorPane;
import org.thomas.lighthouse.editor.EditorPane;
import org.thomas.lighthouse.editor.EditorScrollPane;
import org.thomas.lighthouse.editor.JSEditorPane;
import org.thomas.lighthouse.editor.SFTPPanel;
import org.thomas.lighthouse.editor.XMLEditorPane;
import org.thomas.lighthouse.project.NewProjectPanel;
import org.thomas.lighthouse.project.Project;
import org.thomas.lighthouse.project.ProjectPanel;
import org.thomas.lighthouse.scripts.ScriptPanel;

public class LightHouse extends JFrame implements WindowListener {
	private static final long serialVersionUID = -4406258341931634328L;
	
	public static Project project;
	public static SFTPPanel sftPanel;
	public static NewProjectPanel newProjectPanel;
	public static EditorPane currentPane;
	
	private Dimension size = new Dimension(800, 600);
	private static boolean isMaximized = false;
	private static String defaultProject = "";
	
	private static LightHouse lh;
	private static JTabbedPane tabPane;
	public static ProjectPanel projectPanel;
	
	public static void setProject(Project p) {
		project = p;
		projectPanel.update();
		lh.revalidate();
	}

	public static void main(String[] args) {
		DefaultSyntaxKit.initKit();
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
		if (project != null) {
			sftPanel.setHost(project.host);
			sftPanel.setDirectory(project.directory);
		}
		newProjectPanel = new NewProjectPanel();
		
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
		
		projectPanel = new ProjectPanel();
		temp = new JScrollPane(projectPanel);
		temp.getVerticalScrollBar().setUnitIncrement(8);
		add(temp, BorderLayout.WEST);
		
		add(new LHToolBar(), BorderLayout.NORTH);
	}
	
	public static void addTab(File f) {
		Component[] comps = tabPane.getComponents();
		for (Component comp : comps) {
			if (comp instanceof EditorScrollPane) {
				if (((EditorPane) (((JViewport) (((JScrollPane) comp).getViewport()))).getView()).file.equals(f)) {
					tabPane.setSelectedComponent(comp);
					return;
				}
			}
		}
		String path = f.getName();
		String ext = path.substring(path.lastIndexOf('.') + 1);
		EditorPane e;
		if (ext.equals("xml")) {
			e = new XMLEditorPane();
		}
		else if (ext.equals("js") || ext.equals("json")) {
			e = new JSEditorPane();
		}
		else if (ext.equals("css")) {
			e = new CSSEditorPane();
		}
		else {
			e = new EditorPane();
		}
		currentPane = e;
		JPanel p = new JPanel(new BorderLayout());
		p.add(e, BorderLayout.CENTER);
		Component c = new JScrollPane(e);//EditorScrollPane(p, e);
		e.setup(f);
		Path pathAbsolute = Paths.get(f.getAbsolutePath());
        Path pathBase = Paths.get(LightHouse.project.localDirectory.getAbsolutePath());
        Path pathRelative = pathBase.relativize(pathAbsolute);
        String diff = pathRelative.toString().replace("\\", "/");
        int count = tabPane.getTabCount();
		tabPane.addTab(diff, c);
		tabPane.setTabComponentAt(count, new TabPanel(diff, tabPane));
		tabPane.setSelectedComponent(c);
	}
	
	private void writeSettings() {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		writeSettings();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}
}
