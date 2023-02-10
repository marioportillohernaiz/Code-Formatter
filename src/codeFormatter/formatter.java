package codeFormatter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class formatter {
	
	public static void main(String[] args) throws Exception {
		/*
		 * Steps:
		 * 	Read file 
		 * 	Format code
		 * 		- Rewrite code in one line
		 * 		- Remove all indents
		 * 		+ Input new lines (after '{' or before & after '{' depending if brace location is off/on)
		 * 		+ Indent (either 2 or 4 spaces)
		 * 		+ Add/remove parameter spacing (parameter spacing on/off)
		 * 	Output code
		 */

		
		// User requirements 
	    String braceLocation = "sameline"; 	// "sameline" or "ownline"
		int indentation = 4;				// indent spaces
		boolean parametterSpacing = true;	// true = ( int=0 ), false = (int=0)
		
		String fileName = "codeFile";
		String readFile = readFile(fileName);
		//System.out.println(readFile); // Testing readFile
		
		String firstFormat = removeSpacings(readFile);
		//System.out.println(firstFormat); // Testing firstFormat
		
		ArrayList<String> addingNewLine = addinglines(firstFormat, braceLocation);
		//for (int i = 0; i < addingNewLine.size(); i++) {System.out.println(addingNewLine.get(i));} // Testing addingNewLines
		
		ArrayList<String> addingIndents = addingIndent(addingNewLine, indentation, braceLocation);
		//for (int i = 0; i < addingIndents.size(); i++) {System.out.println(addingIndents.get(i));} // Testing addingIndents
	
		ArrayList<String> finalFormat = addingIndents;
		if (parametterSpacing == true) {
			ArrayList<String> addingParameters = addingParamSpac(addingNewLine);
			// for (int i = 0; i < addingParameters.size(); i++) {System.out.println(addingParameters.get(i));} // Testing addingParameters
		
			finalFormat = addingParameters;
		}
		
		// Output of code
		for (int i = 0; i < finalFormat.size(); i++) {
			System.out.println(finalFormat.get(i));
		}
	}
	
	// Saves the file as one long lined string
	public static String readFile(String fileName) throws FileNotFoundException, IOException {
		String returnString = "";
		
		// Buffer reader that reads through the file and adds each line to a string
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = br.readLine();
			while (line != null) {
				returnString += line;
				line = br.readLine();
			}
		}
		return returnString;
	}

	// Removes all the indents in the file string
	public static String removeSpacings(String file) {
		String returnString = "";
		
		// Loops through each character in the string
		for (int index=0; index<=file.length()-1; index++) {
			
			// To minimise "Out of bound" errors, the last 3 characters are added in the else statement
			if (index <= file.length()-4) { 
				
				// Check if the following 4 characters are spaces
				if (file.charAt(index) == ' ' && file.charAt(index+1) == ' ' && file.charAt(index+2) == ' ' && file.charAt(index+3) == ' ') {
					returnString += "";
					index += 3;
				
				// Check if the character is an indent 
				} else if (file.charAt(index) == '	') {
					returnString += "";
					
				// Otherwise it adds the character to the string
				} else {
					returnString += file.charAt(index);
				}
			} else { 
				if (file.charAt(index) != '	') {
					returnString += file.charAt(index);
				}
			}
		}
		return returnString;
	}
	
	// Adding new lines to the 1 line string
	public static ArrayList<String> addinglines(String file, String braceLocation) {
		String row = "";
		
		// Adding each line to an array list for easier use
		ArrayList<String> code = new ArrayList<String>();
		
		for (int index=0; index<=file.length()-1; index++) {
			// Adds each character to a new string 
			row += file.charAt(index);
			
			// If the character is "{" and the brace location is set as ownline then we need to add "{" as its own row
			if (file.charAt(index) == '{' && braceLocation == "ownline") {
				code.add(row.substring(0, row.length()-1));
				code.add("{");
				row = "";
				
			// Otherwise after each "{", ";" or "}" we add the row to the array list
			} else if (file.charAt(index) == ';' || file.charAt(index) == '}' || file.charAt(index) == '{') {
				code.add(row);
				row = "";
			}
		}	
		return code;
	}
	
	// Adds indents to each line of code
	public static ArrayList<String> addingIndent(ArrayList<String> file, int indentation, String braceLocation) {
		String row = "";
		int indent = 0;
		
		for (int index=0; index<=file.size()-1; index++) {
			row = file.get(index);

			// If the row contains "{" we add spaces depending on how much the previous row has been indented ("indent") and how many spaces the user wants ("indentation")
			if (row.contains("{")) {
				row = " ".repeat(indent*indentation) + row; // We multiply the user indent by how many indents the code has
				file.set(index, row);
				indent += 1; // Now we add a new indent
			
			// If it's a closed brace then we un-indent
			} else if (row.contains("}")) {
				indent -= 1;
				row = " ".repeat(indent*indentation) + row;
				file.set(index, row);
			
			// Otherwise we just edit the value in the array list
			} else {
				row = " ".repeat(indent*indentation) + row;
				file.set(index, row);
			}
		}
		return file;
	}
	
	// Finally we edit the parameter spacing if the user wants
	public static ArrayList<String> addingParamSpac(ArrayList<String> file) {
		String row = "";
		String newRow = "";
		
		// Loop through each value in the array list
		for (int index=0; index<=file.size()-1; index++) {
			row = file.get(index);

			if (row.contains("(") || row.contains(")")) {
				
				// Now we have to loop through each character in the value that contains "(" or ")"
				for (int i=0; i<=row.length()-1; i++) {
					
					// If the character is '(', we add the character plus space
					if (i<=row.length()-2 && row.charAt(i) == '(' && row.charAt(i+1) == ')') {
						newRow += "()";
						i += 1;
					} else if (row.charAt(i) == '(') {
						newRow += row.charAt(i) + " ";
						
					// If the character is ')', we add the space plus the character
					} else if (i<=row.length()-2 && row.charAt(i) != '(' && row.charAt(i+1) == ')') {
						newRow += row.charAt(i) + " )";
						i += 1;
					
					// Otherwise we add the character
					} else {
						newRow += row.charAt(i);
					}
				}
				file.set(index, newRow);
				newRow = "";
			}
		}
		
		return file;
	}
}
