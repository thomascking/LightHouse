package org.thomas.lighthouse.editor;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class EditorPane extends JTextPane implements KeyListener, DocumentListener {
	private static final long serialVersionUID = 8420314647717129823L;
	
	String tab = "  ";
	
	Document doc;
	
	public EditorPane() {
		this.addKeyListener(this);
		doc = this.getDocument();
		this.setFont(new Font("Courier New", Font.PLAIN, 12));
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
			text += "></" + tag + ">";
			text += this.getText().substring(startPosition);
			this.setText(text);
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
			/*if (isShiftDown) {
				if (this.getSelectedText() != "") {
					
				}
				else {
					doc.insertString(this.getCaretPosition(), tab, null);
				}
			}
			else{
				if (this.getSelectedText() != "") {
					
				}
				else {*/
					doc.insertString(this.getCaretPosition(), tab, null);
				/*}
			}*/
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
			if (currentLine.matches(".*<[A-Za-z0-9_ =\"\']*/[A-Za-z0-9_ =\"\']*>.*") || !currentLine.contains("<")) {
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
				if (this.getText().charAt(caretPosition) != '\n')
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
}
