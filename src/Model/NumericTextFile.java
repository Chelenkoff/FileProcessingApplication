/**
 * NumericTextFile class - main entity part of Model from MVC
 */

package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class NumericTextFile {


	private StringBuilder content;
	//	private FileFormatter formatter;

	public NumericTextFile(String pathToFile){
		loadFile(pathToFile);
	}

	public NumericTextFile(){
		content = new StringBuilder();
	}
	public NumericTextFile(StringBuilder content){
		this.content = content;
	}

	private void loadFile(String pathToFile){

		try(BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {

			if(validateExtension(pathToFile) == false) return;

			content = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				content.append(line);
				content.append(System.lineSeparator());
				line = br.readLine();
			}
			//		    formatter = new FileFormatter();

		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		} catch (IOException e) {
			System.out.println("I/O Exception");
		}

	}

	private boolean validateExtension(String path){

		String extension = path.substring(path.lastIndexOf(".") + 1, path.length());

		String txtExtension = "txt";
		if (!extension.equals(txtExtension)) {
			System.out.println("Choose a .txt file!");
			return false;
		}
		return true;

	}

	public void validateContent(){
		String[] lines = content.toString().split("\\n");
		String delim = " \t\r"; //insert here all delimitators
		for(int i = 0; i < lines.length; i++){


			if (lines[i] != null) {

				if(lines[i].trim().length() == 0){
					System.out.printf("\nLine %d is empty!",i+1);
					continue;
				}

				if( lines[i].charAt(0) == '\t' ){
					System.out.printf("\nLine %d starts with tabs!",i+1);
				}
				else if(Character.isWhitespace(lines[i].charAt(0))){
					System.out.printf("\nLine %d starts with whitespaces!",i+1);
				}

				StringTokenizer st = new StringTokenizer(lines[i],delim);
				while (st.hasMoreTokens()) {
					char[] chars = st.nextToken().toCharArray();

					if(chars[0] == '0'){
						System.out.printf("\nNumber at line %d starts with 0",i+1);
					}

					for (char c : chars) {
						if (!Character.isDigit(c)){
							System.out.printf("\nCharacter '%c' at line %d is not allowed",c,i+1);
						}
					}

				}


			}

		}
	}

	public StringBuilder getContent() {
		return content;
	}

	public void clearContent(){
		content = new StringBuilder();
	}

	public void setContent(StringBuilder content){
		this.content = content;
	}

	public void appendToContent(String str){
		content.append(str);
	}

	public char getCharAt(int row, int charIndex){

		String[] rows = content.toString().split("\\n");
		try{
			return rows[row].charAt(charIndex);
		}
		catch (IndexOutOfBoundsException e) {
			return '*'; //OutOfBound escape symbol
		}
	}
	
	public void swapChars(int rowFrom, int numRowIndexFrom,int rowTo, int numRowIndexTo){
		
		if(rowTo == rowFrom){
			System.out.println("You cannot swap numbers from same line!");
			return;
		}
		
		char firstChar = getCharAt(rowFrom-1, numRowIndexFrom-1);
		char secondChar = getCharAt(rowTo-1, numRowIndexTo-1);
		char terminalSymbol ='*';
		
	    System.out.printf("You are going to swap '%c' from row %d pos %d with '%c' from row %d pos %d",
	    		firstChar,rowFrom,numRowIndexFrom,secondChar,rowTo,numRowIndexTo);
		
		String[] lines = content.toString().split("\\n");
		

		
		if(firstChar == terminalSymbol){
	    	System.out.println("On the first coordinates there is no symbol available");
	    }
	    if(secondChar == terminalSymbol){
	    	System.out.println("On the second coordinates there is no symbol available");
	    }
	    
	    if(firstChar == terminalSymbol || secondChar == terminalSymbol) return;
		
		clearContent();
		StringBuilder updatedRow  = null;
		for(int i = 0 ; i < lines.length; i++){
			
			if(i == rowFrom-1){
				updatedRow = new StringBuilder(lines[i]);
				updatedRow.setCharAt(numRowIndexFrom-1, secondChar);
				appendToContent(updatedRow.toString()+'\n');
				continue;
			}
			if(i == rowTo-1){
				updatedRow = new StringBuilder(lines[i]);
				updatedRow.setCharAt(numRowIndexTo-1, firstChar);
				appendToContent(updatedRow.toString()+'\n');
				continue;
			}
			
			appendToContent(lines[i]+'\n');
		}
		
		System.out.println("The updated content after exchange is: ");
		System.out.println(content);
	}


}
