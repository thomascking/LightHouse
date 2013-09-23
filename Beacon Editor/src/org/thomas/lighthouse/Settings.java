package org.thomas.lighthouse;

import java.awt.Dimension;
import java.io.File;
import java.io.Serializable;

public class Settings implements Serializable {
	private static final long serialVersionUID = -1024784849540219807L;
	
	File projectFile;
	Dimension windowSize;
	boolean maximized;
}
