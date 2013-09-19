package org.thomas.lighthouse.scripts;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

public class ScriptTester {
	static String good1 = "import sys";
	
	static String bad1 = "from sys import stdin";
	
	static String good2 = "try:\r\n" + 
    "        input = str(sys.argv[1])\r\n" + 
    "    except:\r\n" + 
    "        input = \"\"";
	
	static String bad2 = "input = stdin.read()";
	
	
	public static void main(String args[]) {
		File f = new File("scripts.txt");
		try {
			Scanner scan = new Scanner(f);
			scan = scan.useDelimiter("H=\"");
			while (scan.hasNext()) {
				String str = scan.next();
				str = str.replaceFirst("\"", "");
				String name = new Scanner(str).next();
				str = str.substring(str.indexOf('\n')+1);
				str = str.replaceAll(bad1, good1);
				str = str.replaceAll(bad2, good2);
				name = name.replaceAll("([^\\p{Lu}])(\\p{Lu})", "$1 $2");
				name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
				File file = new File("scripts\\" + name + ".py");
				FileOutputStream out = new FileOutputStream(file);
				out.write(str.getBytes());
				out.flush();
				out.close();
			}
			scan.close();
		} catch(Exception exc) {}
	}
}
