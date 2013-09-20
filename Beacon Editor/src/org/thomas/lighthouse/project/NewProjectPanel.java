package org.thomas.lighthouse.project;

import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.oracle.swing.utils.SpringUtilities;

public class NewProjectPanel extends JPanel {
	private static final long serialVersionUID = -2293180527936719212L;
	
	JTextField[] fields = new JTextField[4];
	
	public NewProjectPanel() {
		super(new SpringLayout());
		String[] labels = {"Name: ", "Host: ", "Directory: ", "Remote Directory: "};
		int numPairs = labels.length;
		
		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			this.add(l);
			if (labels[i] == "Password: ") {
				fields[i] = new JPasswordField(10);
			}
			else {
				fields[i] = new JTextField(10);
			}
			l.setLabelFor(fields[i]);
			this.add(fields[i]);
		}
		fields[1].setText("decipherinc.com");
		fields[3].setText("/home/jaminb/v2/");
		
		//Lay out the panel.
		SpringUtilities.makeCompactGrid(this,
										numPairs, 2, //rows, cols
										6, 6,        //initX, initY
										6, 6);       //xPad, yPad
		
		this.setOpaque(true);  //content panes must be opaque
	}
	
	public String getName() {
		return fields[0].getText();
	}
	
	public String getHost() {
		return fields[1].getText();
	}
	
	public File getDirectory() {
		return new File(fields[2].getText());
	}
	
	public String getRemoteDirectory() {
		return fields[3].getText();
	}
}
