package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * 
 * @author Zach Kehoe
 */
public class MsgTree {
	// given vars
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	public static String bcode;

	/**
	 * default constructor
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		this.left = null;
		this.right = null;
	}

	/**
	 * constructor for building the tree
	 * 
	 * @param encoding from the read file
	 */
	public MsgTree(String encodingString) {
	    // Base case
	    if (encodingString == null || encodingString.length() < 2) {
	        return;
	    }

	    // Use stack to store values; use recursion
	    Stack<MsgTree> stack = new Stack<>();
	    int i = 0;

	    this.payloadChar = encodingString.charAt(i++);
	    stack.push(this);

	    MsgTree current = this;
	    String lastAction = "in";

	    while (i < encodingString.length()) {
	        MsgTree node = new MsgTree(encodingString.charAt(i++));

	        if (lastAction.equals("in")) {
	            current.left = node;

	            if (node.payloadChar == '^') {
	                current = stack.push(node);
	                lastAction = "in";
	            } else {
	                if (!stack.empty()) {
	                    current = stack.pop();
	                }
	                lastAction = "out";
	            }
	        } else { // lastAction is out
	            current.right = node;

	            if (node.payloadChar == '^') {
	                current = stack.push(node);
	                lastAction = "in";
	            } else {
	                if (!stack.empty()) {
	                    current = stack.pop();
	                }
	                lastAction = "out";
	            }
	        }
	    }
	}


	public static boolean getCode(MsgTree root, char ch, String path) {
	    if (root == null) {
	        return false;
	    }

	    if (root.payloadChar == ch) {
	        bcode = path;
	        return true;
	    }

	    return getCode(root.left, ch, path + "0") || getCode(root.right, ch, path + "1");
	}

	public void decode(MsgTree codes, String msg) {
		System.out.println("MESSAGE:");
		MsgTree current = codes; // Start at the root of the tree
		StringBuilder sb = new StringBuilder();

		// Traverse the message
		for (int i = 0; i < msg.length(); i++) {
			char ch = msg.charAt(i);

			// Determine the next node based on the character
			if (ch == '0') {
				current = current.left;
			} else {
				current = current.right;
			}

			// Check if we've reached a character node
			if (current.payloadChar != '^') {
				// Get the character's code and append it to the result
				getCode(codes, current.payloadChar, bcode = "");
				sb.append(current.payloadChar);
				current = codes; // Reset to the root for the next character
			}
		}

		System.out.println(sb.toString()); // Print the decoded message
	}

	/**
	 * prints the chars and code
	 * 
	 * @param root
	 * @param code
	 */
	public static void print(MsgTree root, String code) {
		System.out.println("character code\n");
		for (int i = 0; i < code.length(); i++) {
			char c = code.charAt(i);
			String bcode = getCodeForCharacter(root, c);
			String character = (c == '\n') ? "\\n" : String.valueOf(c);
			System.out.println("    " + character + "    " + bcode);
		}
	}

	// New method to get the code for a specific character
	private static String getCodeForCharacter(MsgTree root, char ch) {
		String path = "";
		getCode(root, ch, path);
		return bcode;
	}

}
