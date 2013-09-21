package org.thomas.lighthouse.editor;

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.text.BadLocationException;

public class XMLEditorPane extends EditorPane {
	private static final long serialVersionUID = -2434147820785174571L;
	
	public XMLEditorPane() {
	}
	
	@Override
	public void setup(File f) {
		this.setContentType("text/xml");
		super.setup(f);
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
		/*if (e.getKeyChar() == '\t') {
			doTabbing(e.isShiftDown());
			e.consume();
		}
		if (e.getKeyChar() == '\n') {
			doNewLine();
			e.consume();
		}*/
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {
		/*if (e.getKeyChar() == '>') {
			if (closeTag()) {
				e.consume();
			}
		}*/
	}
}
