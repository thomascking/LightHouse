package org.thomas.lighthouse.project;

import java.io.File;

public class ProjectTest {
	public static void main(String args[]) {
		Project p = new Project("test", new File("C:/Projects"), "decipherinc.com", "/home/jaminb/v2/selfserve/9d3/99999");
		System.out.println(p.name);
	}
}
