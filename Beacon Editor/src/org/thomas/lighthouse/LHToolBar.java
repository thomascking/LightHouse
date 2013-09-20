package org.thomas.lighthouse;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import org.thomas.lighthouse.project.DownloadAllAction;
import org.thomas.lighthouse.project.NewProjectAction;
import org.thomas.lighthouse.project.OpenProjectAction;

public class LHToolBar extends JToolBar {
	private static final long serialVersionUID = 8253642090491933239L;
	
	public LHToolBar() {
		JButton newButton = new JButton(new NewProjectAction());
		newButton.setIcon(new ImageIcon("images/new_project.png"));
		add(newButton);
		
		JButton openButton = new JButton(new OpenProjectAction());
		openButton.setIcon(new ImageIcon("images/open_project.png"));
		add(openButton);
		
		addSeparator();
		
		JButton downloadButton = new JButton(new DownloadAllAction());
		downloadButton.setIcon(new ImageIcon("images/download_all.png"));
		add(downloadButton);
	}
}
