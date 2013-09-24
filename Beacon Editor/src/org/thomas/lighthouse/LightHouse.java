package org.thomas.lighthouse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jsyntaxpane.DefaultSyntaxKit;

import org.thomas.lighthouse.editor.CSSEditorPane;
import org.thomas.lighthouse.editor.EditorPane;
import org.thomas.lighthouse.editor.EditorScrollPane;
import org.thomas.lighthouse.editor.FileEditor;
import org.thomas.lighthouse.editor.JSEditorPane;
import org.thomas.lighthouse.editor.SFTPPanel;
import org.thomas.lighthouse.editor.XLSEditor;
import org.thomas.lighthouse.editor.XMLEditorPane;
import org.thomas.lighthouse.project.NewProjectPanel;
import org.thomas.lighthouse.project.Project;
import org.thomas.lighthouse.project.ProjectPanel;
import org.thomas.lighthouse.scripts.ScriptPanel;

public class LightHouse extends JFrame implements WindowListener, ChangeListener {
	private static final long serialVersionUID = -4406258341931634328L;
	
	public static Project project;
	public static SFTPPanel sftPanel;
	public static NewProjectPanel newProjectPanel;
	public static EditorPane currentPane;
	
	private static Dimension size = new Dimension(800, 600);
	private static boolean isMaximized = false;
	private static File defaultProject = null;
	
	private static LightHouse lh;
	private static JTabbedPane tabPane;
	public static ProjectPanel projectPanel;
	
	public static void setProject(Project p) {
		project = p;
		projectPanel.update();
		lh.revalidate();
	}

	public static void main(String[] args) {
		loadSettings();
		DefaultSyntaxKit.initKit();
		loadConfig(new File("settings.txt"));
		
		if (args.length > 0) {
			project = Project.load(new File(args[0]));
		}
		else if (defaultProject != null) {
			project = Project.load(defaultProject);
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
		tabPane.addChangeListener(lh);
		lh.setVisible(true);
		if (isMaximized) {
			lh.setExtendedState(lh.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		}
		lh.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public LightHouse() {
		super("Light House");
		loadEditorElements();
		setSize(LightHouse.size);
		this.addWindowListener(this);
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
				if (((FileEditor) (((JViewport) (((JScrollPane) comp).getViewport()))).getView()).getFile().equals(f)) {
					tabPane.setSelectedComponent(comp);
					return;
				}
			}
		}
		String path = f.getName();
		String ext = path.substring(path.lastIndexOf('.') + 1);
		EditorPane e = null;
		Component comp = null;
		FileEditor editor = null;
		if (ext.equals("xml")) {
			e = new XMLEditorPane();
			comp = e;
		}
		else if (ext.equals("js") || ext.equals("json")) {
			e = new JSEditorPane();
			comp = e;
		}
		else if (ext.equals("css")) {
			e = new CSSEditorPane();
			comp = e;
		}
		else if (ext.equals("xls")) {
			comp = new XLSEditor();
		}
		else {
			e = new EditorPane();
			comp = e;
		}
		editor = (FileEditor)comp;
		currentPane = e;
		JPanel p = new JPanel(new BorderLayout());
		p.add(comp, BorderLayout.CENTER);
		Component c = new JScrollPane(e);//EditorScrollPane(p, e);
		editor.setup(f);
		Path pathAbsolute = Paths.get(f.getAbsolutePath());
        Path pathBase = Paths.get(LightHouse.project.localDirectory.getAbsolutePath());
        Path pathRelative = pathBase.relativize(pathAbsolute);
        String diff = pathRelative.toString().replace("\\", "/");
        int count = tabPane.getTabCount();
		tabPane.addTab(diff, c);
		tabPane.setTabComponentAt(count, new TabPanel(diff, tabPane));
		tabPane.setSelectedComponent(c);
	}
	
	private static void loadSettings() {
		File inFile = new File("lighthouse.settings");
		if (!inFile.exists()) return;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(inFile));
			Settings settings = (Settings)in.readObject();
			LightHouse.isMaximized = settings.maximized;
			LightHouse.size = settings.windowSize;
			LightHouse.defaultProject = settings.projectFile;
			in.close();
		} catch(Exception exc) {}
	}
	
	private static void writeSettings() {
		Settings settings = new Settings();
		settings.maximized = (lh.getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0;
		settings.windowSize = lh.getSize();
		if (project != null)
			settings.projectFile = project.file;
		File outFile = new File("lighthouse.settings");
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(outFile));
			out.writeObject(settings);
			out.flush();
			out.close();
		} catch(Exception exc) {}
	}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		LightHouse.writeSettings();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void stateChanged(ChangeEvent e) {
		Component comp = tabPane.getSelectedComponent();
		comp = (((JViewport) (((JScrollPane) comp).getViewport()))).getView();
		if (comp instanceof EditorPane) {
			LightHouse.currentPane = (EditorPane)comp;
		}
		else {
			LightHouse.currentPane = null;
		}
	}
}
