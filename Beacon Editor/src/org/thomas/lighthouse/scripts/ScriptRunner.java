package org.thomas.lighthouse.scripts;

import java.io.IOException;
import java.util.Scanner;

public class ScriptRunner {
	String[] command;
	
	public ScriptRunner(String filename) {
		command = new String[3];
		command[0] = "python";
		command[1] = filename;
		command[2] = "";
	}
	
	public String run(String contents) throws IOException {
		command[2] = contents;
		Process p = Runtime.getRuntime().exec(command);
		@SuppressWarnings("resource")
		Scanner input = new Scanner(p.getInputStream()).useDelimiter("\\A");
		String out = input.hasNext() ? input.next() : "";
		input.close();
		p.destroy();
		
		return out;
	}
}
