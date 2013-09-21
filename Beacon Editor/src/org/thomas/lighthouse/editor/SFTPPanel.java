package org.thomas.lighthouse.editor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.oracle.swing.utils.SpringUtilities;

public class SFTPPanel extends JPanel {
	private static final long serialVersionUID = -2293180527936719212L;
	
	JTextField[] fields = new JTextField[4];
	
	public SFTPPanel() {
		super(new SpringLayout());
		String[] labels = {"Username: ", "Host: ", "Directory: ", "Password: "};
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
		fields[2].setText("/home/jaminb/v2/");
		
		//Lay out the panel.
		SpringUtilities.makeCompactGrid(this,
										numPairs, 2, //rows, cols
										6, 6,        //initX, initY
										6, 6);       //xPad, yPad
		
		this.setOpaque(true);  //content panes must be opaque
	}
	
	public String getFTPName() {
		return fields[0].getText();
	}
	
	public String getHost() {
		return fields[1].getText();
	}
	
	public String getDirectory() {
		return fields[2].getText();
	}
	
	public String getPassword() {
		return fields[3].getText();
	}
	
	public void setHost(String t) {
		fields[1].setText(t);
	}
	
	public void setDirectory(String t) {
		fields[2].setText(t);
	}
}
