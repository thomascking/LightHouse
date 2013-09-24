package org.thomas.lighthouse.editor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.Keymap;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import org.thomas.lighthouse.LightHouse;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class EditorPane extends JEditorPane implements 
KeyListener, DocumentListener, UndoableEditListener, FileEditor {
	private static final long serialVersionUID = 8420314647717129823L;
	
	String tab = "  ";
	
	Document doc;
	
	UndoManager undo;
	UndoAction undoAction;
	RedoAction redoAction;
	
	JFileChooser fileChooser = new JFileChooser();
	SaveAction save;
	//SaveAction saveAs;
	OpenAction open;
	
	UploadAction upload;
	DownloadAction download;
	
	String validCharacters = "[A-Za-z0-9_ =\"\'()&;]";
	
	public File file = null;
	
	public EditorPane() {}
	
	public void setup(File f) {
		open(f);
		init();
		fileChooser.setSelectedFile(f);
		file = f;
	}
	
	public File getFile() {
		return file;
	}
	
	public void init() {
		this.addKeyListener(this);
		doc = this.getDocument();
		doc.addDocumentListener(this);
		doc.addUndoableEditListener(this);
		((AbstractDocument)doc).setDocumentFilter(new EditorFilter());
		this.setFont(new Font("Courier New", Font.PLAIN, 14));
		
		undo = new UndoManager();
		
		save = new SaveAction(this, fileChooser);
		//saveAs = new SaveAction(this, fileChooser, true);
		open = new OpenAction(this, fileChooser);
		
		upload = new UploadAction(this);
		download = new DownloadAction(this);
		
		undoAction = new UndoAction();
		redoAction = new RedoAction();
		
		Keymap parent = this.getKeymap();
		Keymap custom = JTextPane.addKeymap("custom", parent);
		KeyStroke saveStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK, false);
		custom.addActionForKeyStroke(saveStroke, save);
		//KeyStroke saveAsStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK, false);
		//custom.addActionForKeyStroke(saveAsStroke, saveAs);
		KeyStroke openStroke = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK, false);
		custom.addActionForKeyStroke(openStroke, open);
		
		KeyStroke uploadStroke = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK, false);
		custom.addActionForKeyStroke(uploadStroke, upload);
	    KeyStroke downloadStroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK, false);
		custom.addActionForKeyStroke(downloadStroke, download);
		
		//KeyStroke undoStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK, false);
		//custom.addActionForKeyStroke(undoStroke, undoAction);
		//KeyStroke redoStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK, false);
		//custom.addActionForKeyStroke(redoStroke, redoAction);
		
		this.setKeymap(custom);
	}
	
	public void save(File file) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			out.write(this.getText().getBytes());
			out.flush();
			out.close();
		} catch(IOException exc) {}
	}
	
	public void open(File file) {
		try {
			@SuppressWarnings("resource")
			FileInputStream in = new FileInputStream(file);
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			String s = new String(bytes);
			this.setText(s);
		} catch(IOException exc) {}
	}
	
	public void upload(String username, String host, String projectDirectory, String password) {
		JSch jsch = new JSch();
		Session s = null;
		ChannelSftp sftp = null;
		OutputStream out = null;
		try{
			s = jsch.getSession(username, host, 22);
			s.setConfig("StrictHostKeyChecking", "no");
			s.setPassword(password);
			s.connect();
			
			Channel ch = s.openChannel("sftp");
			ch.connect();
			sftp = (ChannelSftp) ch;
			sftp.cd(projectDirectory);
			Path pathAbsolute = Paths.get(file.getAbsolutePath());
	        Path pathBase = Paths.get(LightHouse.project.localDirectory.getAbsolutePath());
	        Path pathRelative = pathBase.relativize(pathAbsolute);
	        String diff = pathRelative.toString().replace("\\", "/");
			out = sftp.put(diff);
			String str = this.getText().replaceAll("\r", "");
			out.write(str.getBytes());
			out.flush();
			out.close();
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
	
	public void download(String username, String host, String projectDirectory, String password) {
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
			sftp.cd(projectDirectory);
			Path pathAbsolute = Paths.get(file.getAbsolutePath());
	        Path pathBase = Paths.get(LightHouse.project.localDirectory.getAbsolutePath());
	        Path pathRelative = pathBase.relativize(pathAbsolute);
	        String diff = pathRelative.toString().replace("\\", "/");
			in = sftp.get(diff);
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(in).useDelimiter("\\A");
			String str = scanner.next();
			this.setText(str);
			scanner.close();
			in.close();
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
	
	

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void changedUpdate(DocumentEvent e) {}

	@Override
	public void insertUpdate(DocumentEvent e) {}

	@Override
	public void removeUpdate(DocumentEvent e) {}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		undo.addEdit(e.getEdit());
	}
	
	public class UndoAction extends AbstractAction {
		private static final long serialVersionUID = 4888611265734610373L;

		public void actionPerformed(ActionEvent e) {
		    try {
		        undo.undo();
		    } catch (CannotUndoException ex) {
		        System.out.println("Unable to undo: " + ex);
		        ex.printStackTrace();
		    }
		    updateUndoState();
		    redoAction.updateRedoState();
		}
		
		public void updateUndoState() {
			setEnabled(undo.canUndo());
		}
	}
	
	public class RedoAction extends AbstractAction {
		private static final long serialVersionUID = -971539696891602812L;

		public void actionPerformed(ActionEvent e) {
		    try {
		        undo.redo();
		    } catch (CannotUndoException ex) {
		        System.out.println("Unable to undo: " + ex);
		        ex.printStackTrace();
		    }
		    updateRedoState();
		    undoAction.updateUndoState();
		}
		
		public void updateRedoState() {
			setEnabled(undo.canRedo());
		}
	}
}
