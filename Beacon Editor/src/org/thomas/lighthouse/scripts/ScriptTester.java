package org.thomas.lighthouse.scripts;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

import javax.swing.tree.DefaultMutableTreeNode;

public class ScriptTester {
	public static void main(String args[]) {
		File f = new File(".\\scripts");
		File[] scripts = f.listFiles();
		
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