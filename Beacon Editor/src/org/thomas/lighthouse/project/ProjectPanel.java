package org.thomas.lighthouse.project;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTree;

import org.thomas.lighthouse.LightHouse;

public class ProjectPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = -144568226928061600L;
	
	private JTree tree;
	
	public ProjectPanel() {
		update();
	}
	
	public void update() {
		removeAll();
		if (LightHouse.project != null) {
			tree = new JTree(createNode(LightHouse.project.getWorkingDirectory()));
			tree.addMouseListener(this);
			add(tree);
		}
		repaint();
	}
	
	public ProjectFileNode createNode(File file) {
		if (file.getName().endsWith(".proj")) return null;
		ProjectFileNode node = new ProjectFileNode(file);
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				ProjectFileNode node2 = createNode(f);
				if (node2 != null)
					node.add(node2);
			}
		}
		return node;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
			ProjectFileNode node = (ProjectFileNode)tree.getLastSelectedPathComponent();
			if (node.isLeaf() && !node.isRoot()) {
				LightHouse.addTab((File)node.getUserObject());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
