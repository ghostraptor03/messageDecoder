package edu.iastate.cs228.hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * 
 * @author Zach Kehoe 
 * 		   /Users/zachkehoe/Desktop/HW4_Test_Files (1)/cadbard.arch
 *         /Users/zachkehoe/Desktop/HW4_Test_Files (1)/constitution.arch
 *         /Users/zachkehoe/Desktop/HW4_Test_Files (1)/monalisa.arch
 *         /Users/zachkehoe/Desktop/HW4_Test_Files (1)/twocities.arch
 */
public class main {
	public static void main(final String[] args) throws IOException {
		// Scan the file
		System.out.println("Please enter filename to decode: ");
		Scanner scanner = new Scanner(System.in);
		String fileName = scanner.nextLine();
		scanner.close();

		// Read the string and reorder to one line for easier reading
		String content = new String(Files.readAllBytes(Paths.get(fileName))).trim();

		// Find the last newline
		int lastNewlineIndex = content.lastIndexOf('\n');

		// Extract the pattern and code
		String pattern = content.substring(0, lastNewlineIndex);
		String code = content.substring(lastNewlineIndex).trim();

		// Put unique characters excluding '^' into a StringBuilder
		StringBuilder uniqueChars = new StringBuilder();
		for (char c : pattern.toCharArray()) {
			if (c != '^' && uniqueChars.indexOf(String.valueOf(c)) == -1) {
				uniqueChars.append(c);
			}
		}
		String charDict = uniqueChars.toString();

		// Call methods from MsgTree
		MsgTree root = new MsgTree(pattern);
		MsgTree.print(root, charDict);
		root.decode(root, code);
	}

}
