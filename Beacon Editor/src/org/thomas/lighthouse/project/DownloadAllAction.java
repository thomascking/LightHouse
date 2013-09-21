package org.thomas.lighthouse.project;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.thomas.lighthouse.LightHouse;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class DownloadAllAction extends AbstractAction {
	private static final long serialVersionUID = -4918070212328019660L;
	
	public DownloadAllAction() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] options = new String[]{"Download", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, 
				LightHouse.sftPanel, 
				"SFTP Download",
				JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		if (option != 0) return;
		File localDir = LightHouse.project.localDirectory;
		String remoteDir = LightHouse.sftPanel.getDirectory();
		String host = LightHouse.sftPanel.getHost();
		String username = LightHouse.sftPanel.getFTPName();
		String password = LightHouse.sftPanel.getPassword();
		JSch jsch = new JSch();
		Session s = null;
		ChannelSftp sftp = null;
		InputStream in = null;
		try{
			s = jsch.getSession(username, host, 22);
			s.setConfig("StrictHostKeyChecking", "no");
			s.setPassword(password);
			s.connect();
			
			Channel ch = s.openChannel("sftp");
			ch.connect();
			sftp = (ChannelSftp) ch;
			
			downloadDirectory(sftp, remoteDir, localDir);
			
			System.out.println("Done");
			
			LightHouse.projectPanel.update();
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			if (sftp != null)
				sftp.exit();
			if (s != null)
				s.disconnect();
		}
	}
	
	public void downloadDirectory(ChannelSftp sftp, String dir, File localDir) {
		try {
			sftp.cd(dir);
			Vector<ChannelSftp.LsEntry> files = sftp.ls(".");
			for (ChannelSftp.LsEntry file : files) {
				String name = file.getFilename();
				if (file.getAttrs().isDir()) {
					if (name.equals("..") || name.equals(".")) {
						System.out.println("Skipping dir: " + name);
						continue;
					}
					System.out.println("Going into dir: " + name);
					File newDir = new File(localDir, name);
					newDir.mkdirs();
					downloadDirectory(sftp, name, newDir);
					sftp.cd("..");
					continue;
				}
				String localFile = new File(localDir, name).getAbsolutePath();
				try {
					System.out.println("Getting file: " + name);
					sftp.get(name, localFile);
				} catch(Exception exc) {}
			}
		} catch(Exception exc) {}
	}
}
