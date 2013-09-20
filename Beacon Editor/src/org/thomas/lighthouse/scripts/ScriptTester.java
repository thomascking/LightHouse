package org.thomas.lighthouse.scripts;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class ScriptTester {
	public static void main(String args[]) {
		File f = new File(".\\Scripts");
		DefaultMutableTreeNode root = getNode(f);
		JTree tree = new JTree(root);
		JFrame fr = new JFrame("load test");
		fr.setVisible(true);
		fr.setSize(800, 600);
		fr.add(new JScrollPane(tree));
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static DefaultMutableTreeNode getNode(File file) {
		if (file.isDirectory()) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(file.getName());
			for (File f : file.listFiles()) {
				node.add(getNode(f));
			}
			return node;
		}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(file.getName());
		return node;
	}
}