package org.thomas.lighthouse.editor;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class EditorFilter extends DocumentFilter {
	public EditorFilter() {}
	
	public void updateHighlighting(FilterBypass fb, int offset) {
		//TODO: Get affected area, search that only
		//TODO: Check for keywords
		//TODO: Highlight keywords
	}
	
	public void insertString(FilterBypass fb, int offset, String string, 
			AttributeSet attr) 
			throws BadLocationException {
		super.insertString(fb, offset, string, attr);
		updateHighlighting(fb, offset);
	}
	
	public void remove(FilterBypass fb, int offset, int length) 
			throws BadLocationException {
		super.remove(fb, offset, length);
		updateHighlighting(fb, offset);
	}

	public void replace(FilterBypass fb, int offset, int length, 
			String string, AttributeSet attr) 
			throws BadLocationException {
		super.replace(fb, offset, length, string, attr);
		updateHighlighting(fb, offset);
	}
}
