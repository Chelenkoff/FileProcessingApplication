/**
 * NumericTextFile class - main entity part of Model from MVC
 */

package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class NumericTextFile {

	private ArrayList<String> contentLines;
	private boolean isContentValid;
	private String txtFilePath;

	public NumericTextFile(String pathToFile){
		loadFile(pathToFile);
	}


	public NumericTextFile(NumericTextFile file){
		this.txtFilePath = file.txtFilePath;
		this.isContentValid = file.isContentValid;
		
		this.contentLines = file.contentLines;
	}

	private void loadFile(String pathToFile){

		txtFilePath = pathToFile;
		
		try(BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {

			if(isExtensionValid(pathToFile) == false){
				System.out.println("Choose a .txt file!");
				return;
			}

			contentLines = new ArrayList<String>();

			String line = br.readLine();

			while (line != null) {
				contentLines.add(line);
				line = br.readLine();
			}
			//		    formatter = new FileFormatter();

		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		} catch (IOException e) {
			System.out.println("I/O Exception");
		}

	}

	private boolean isExtensionValid(String path){

		String extension = path.substring(path.lastIndexOf(".") + 1, path.length());

		if (!extension.equals("txt")) {
			return false;
		}
		return true;

	}
	public void validateContent(){
		String delim = " \t\r"; //insert here all delimitators
		isContentValid = true;
		for(int i = 0; i < contentLines.size(); i++){

			if (contentLines.get(i) != null) {

				if(contentLines.get(i).trim().length() == 0){
					System.out.printf("\nLine %d is empty!",i+1);
					isContentValid = false;
					continue;
				}

				if( contentLines.get(i).charAt(0) == '\t' ){
					System.out.printf("\nLine %d starts with tabs!",i+1);
					isContentValid = false;
				}
				else if(Character.isWhitespace(contentLines.get(i).charAt(0))){
					System.out.printf("\nLine %d starts with whitespaces!",i+1);
					isContentValid = false;

				}

				StringTokenizer st = new StringTokenizer(contentLines.get(i),delim);
				while (st.hasMoreTokens()) {
					char[] chars = st.nextToken().toCharArray();

					if(chars[0] == '0'){
						System.out.printf("\nNumber at line %d starts with 0",i+1);
						isContentValid = false;

					}

					for (char c : chars) {
						if (!Character.isDigit(c)){
							System.out.printf("\nCharacter '%c' at line %d is not allowed",c,i+1);
							isContentValid = false;

						}
					}

				}


			}


		}
		
		if(isContentValid){
			System.out.println("\nThe content is valid!");
		}
		else{
			System.out.println("\nThe content is invalid!");
		}
	}


	
	public boolean getIsContentValid(){
		return isContentValid;
	}


	public String getFilePath(){
		return txtFilePath;
	}
	
	public ArrayList<String> getContentLines(){
		return contentLines;
	}

	
	public char getCharAt(int row, int charIndex){

		try{
			return contentLines.get(row).charAt(charIndex);
		}
		catch (IndexOutOfBoundsException e) {
			return '*'; //OutOfBound escape symbol
		}
	}
	
	public void swapChars(int rowFrom, int numRowIndexFrom,int rowTo, int numRowIndexTo){
		
		if(rowTo == rowFrom){
			System.out.println("You cannot swap numbers from same row!");
			return;
		}
		
		char firstChar = getCharAt(rowFrom-1, numRowIndexFrom-1);
		char secondChar = getCharAt(rowTo-1, numRowIndexTo-1);
		char terminalSymbol ='*';
		
		if(firstChar == terminalSymbol){
	    	System.out.println("On the first coordinates there is no symbol available");
	    }
	    if(secondChar == terminalSymbol){
	    	System.out.println("On the second coordinates there is no symbol available");
	    }
	    
	    if(firstChar == terminalSymbol || secondChar == terminalSymbol) return;
	    		
	    System.out.printf("You are going to swap '%c' from row %d pos %d with '%c' from row %d pos %d\n",
	    		firstChar,rowFrom,numRowIndexFrom,secondChar,rowTo,numRowIndexTo);
		
		StringBuilder updatedRow  = null;

		updatedRow =new StringBuilder(contentLines.get(rowFrom-1));
		updatedRow.setCharAt(numRowIndexFrom-1, secondChar);
		contentLines.set(rowFrom-1,updatedRow.toString());
		
		updatedRow = new StringBuilder(contentLines.get(rowTo-1));
		updatedRow.setCharAt(numRowIndexTo-1, firstChar);
		contentLines.set(rowTo-1, updatedRow.toString());
				
		System.out.println("The updated content after the exchange is: ");
		
		this.toString();
	}
	
	
	public void swapLines(int firstLineIndex, int secondLineIndex){
				
		String firstLine = null;
		String secondLine = null;
		
		try{
		firstLine = contentLines.get(firstLineIndex);
		secondLine = contentLines.get(secondLineIndex);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("There are no such rows!");
			return;
		}
				
		contentLines.set(firstLineIndex, secondLine);
		contentLines.set(secondLineIndex, firstLine);
		
		System.out.println("\nThe updated content after exchange is: ");
		this.toString();
	}
	
	@Override
	public String toString() {
		
		for(String line : contentLines){
			System.out.println(line);
		}
		
		return super.toString();
	}


}
