package org.thomas.lighthouse.project;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

public class ProjectFileNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 1138430997135525556L;
	
	File file;
	
	public ProjectFileNode(File f) {
		super(f);
		file = f;
	}
	
	public String toString() {
		return file.getName();
	}
}
