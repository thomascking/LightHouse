package org.thomas.lighthouse.scripts;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;

public class ScriptButton extends JButton {
	private static final long serialVersionUID = 1993114164886208439L;
	
	static Font font = new Font("Courier New", Font.PLAIN, 12);
	
	public ScriptButton(ScriptAction action) {
		super(action);
		this.setText(action.toString());
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setFont(font);
		this.setMargin(new Insets(-5, 0, -5, 0));
	}
}
