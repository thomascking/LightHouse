package org.thomas.lighthouse.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class Project implements Serializable {
	private static final long serialVersionUID = 1963765184365440379L;
	
	public String name;
	public String host;
	public String directory;
	public File localDirectory;
	
	// create a project with specified name in this location
	public Project(String name, File directory, String host, String remoteDirectory) {
		this.name = name;
		this.host = host;
		this.directory = remoteDirectory;
		File projectDir = new File(directory, name);
		localDirectory = projectDir;
		if (!projectDir.exists()) {
			if (!projectDir.mkdirs()) {
				JOptionPane.showMessageDialog(null, "Error making project directories", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		File settingsFile = new File(projectDir, name + ".proj");
		ObjectOutputStream fos;
		try {
			fos = new ObjectOutputStream(new FileOutputStream(settingsFile));
			fos.writeObject(this);
			fos.flush();
			fos.close();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}
	
	public static Project load(File project) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(project));
			Object o = in.readObject();
			in.close();
			if (o instanceof Project) {
				return (Project)o;
			}
			return null;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}
	
	public File getWorkingDirectory() {
		return localDirectory;
	}
}
