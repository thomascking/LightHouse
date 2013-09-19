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
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Keymap;
import javax.swing.text.StyledDocument;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class EditorPane extends JTextPane implements 
KeyListener, DocumentListener, UndoableEditListener {
	private static final long serialVersionUID = 8420314647717129823L;
	
	String tab = "  ";
	
	StyledDocument doc;
	
	UndoManager undo;
	UndoAction undoAction;
	RedoAction redoAction;
	
	JFileChooser fc;
	SaveAction save;
	SaveAction saveAs;
	OpenAction open;
	
	SFTPPanel sftpPanel;
	UploadAction upload;
	DownloadAction download;
	
	String validCharacters = "[A-Za-z0-9_ =\"\'()&;]";
	
	public EditorPane() {
		this.addKeyListener(this);
		doc = this.getStyledDocument();
		doc.addDocumentListener(this);
		doc.addUndoableEditListener(this);
		((AbstractDocument)doc).setDocumentFilter(new EditorFilter());
		this.setFont(new Font("Courier New", Font.PLAIN, 14));
		
		undo = new UndoManager();
		
		fc = new JFileChooser();
		save = new SaveAction(this, fc);
		saveAs = new SaveAction(this, fc, true);
		open = new OpenAction(this, fc);
		
		sftpPanel = new SFTPPanel();
		upload = new UploadAction(this, sftpPanel);
		download = new DownloadAction(this, sftpPanel);
		
		undoAction = new UndoAction();
		redoAction = new RedoAction();
		
		Keymap parent = this.getKeymap();
		Keymap custom = JTextPane.addKeymap("custom", parent);
		KeyStroke saveStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK, false);
		custom.addActionForKeyStroke(saveStroke, save);
		KeyStroke saveAsStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK, false);
		custom.addActionForKeyStroke(saveAsStroke, saveAs);
		KeyStroke openStroke = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK, false);
		custom.addActionForKeyStroke(openStroke, open);
		
		KeyStroke uploadStroke = KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK, false);
		custom.addActionForKeyStroke(uploadStroke, upload);
		KeyStroke downloadStroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK, false);
		custom.addActionForKeyStroke(downloadStroke, download);
		
		KeyStroke undoStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK, false);
		custom.addActionForKeyStroke(undoStroke, undoAction);
		KeyStroke redoStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK, false);
		custom.addActionForKeyStroke(redoStroke, redoAction);
		
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
			out = sftp.put("survey.xml");
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
			in = sftp.get("survey.xml");
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
	
	public boolean closeTag() {
		try {
			int startPosition = this.getCaretPosition();
			String text = this.getText(0, startPosition);
			int startIndex = text.lastIndexOf("<");
			if (startIndex == -1) {
				return false;
			}
			String tag = text.substring(startIndex + 1);
			if (tag.endsWith("/")) {
				return false;
			}
			int endIndex = tag.indexOf(" ");
			if (endIndex == -1) {
				endIndex = tag.length();
			}
			tag = tag.substring(0, endIndex);
			doc.insertString(this.getCaretPosition(), "></" + tag + ">", null);
			this.setCaretPosition(startPosition + 1);
			return true;
		} catch(BadLocationException exc) {
			System.err.println("Bad Location");
			exc.printStackTrace(System.err);
		}
		return false;
	}
	
	public void doTabbing(boolean isShiftDown) {
		try {
			doc.insertString(this.getCaretPosition(), tab, null);
		} catch(BadLocationException exc) {
			System.err.println("Bad Location");
			exc.printStackTrace(System.err);
		}
	}
	
	public void doNewLine() {
		try {
			int caretPosition = this.getCaretPosition();
			String currentLine = this.getText(0, caretPosition);
			int index = currentLine.lastIndexOf('\n') + 1;
			currentLine = currentLine.substring(index);
			boolean closed = false;
			if (currentLine.matches(".*<" + validCharacters + "*/" + validCharacters + "*>.*") || !currentLine.contains("<")) {
				closed = true;
			}
			int tabCount = closed ? 0 : 1;
			while (currentLine.startsWith(tab)) {
				tabCount++;
				currentLine = currentLine.substring(tab.length());
			}
			if (!closed) {
				for (int i = 0; i < tabCount - 1; i++) {
					doc.insertString(caretPosition, tab, null);
				}
				char last = '\n';
				try {
					last = this.getText().charAt(caretPosition);
				} catch(Exception exc) {}
				if (last != '\n')
					doc.insertString(caretPosition, "\n", null);
			}
			for (int i = 0; i < tabCount; i++) {
				doc.insertString(caretPosition, tab, null);
			}
			doc.insertString(caretPosition, "\n", null);
			this.setCaretPosition(caretPosition + tab.length() * tabCount + 1);
		} catch(BadLocationException exc) {
			System.err.println("Bad Location");
			exc.printStackTrace(System.err);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == '\t') {
			doTabbing(e.isShiftDown());
			e.consume();
		}
		if (e.getKeyChar() == '\n') {
			doNewLine();
			e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == '>') {
			if (closeTag()) {
				e.consume();
			}
		}
	}

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
