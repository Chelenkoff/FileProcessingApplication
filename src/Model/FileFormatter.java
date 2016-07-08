package Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

import Interfaces.Formatter;

public class FileFormatter implements Formatter{
	private Scanner scanner;
	private String choice;
	private NumericTextFile originalFile;
	private NumericTextFile updatedFile;

	public FileFormatter(NumericTextFile file){
		this.originalFile =file;
		
		//Working copy of original file
		updatedFile = new NumericTextFile(file);
		
		scanner = new Scanner(System.in);
	}

	public void showFormatterMenu(){
		System.out.println();

		do{
			scanner = new Scanner(System.in);
			System.out.println();
			System.out.println("-----------------------------");
			System.out.println("Choose from the following menu:");
			System.out.println("a. Validate the file content");
			System.out.println("b. Switch lines");
			System.out.println("c. Switch numbers at specified indexes");
			System.out.println("d. Save changes");
			System.out.print("Your choice: ");

			choice = scanner.nextLine();
			if(choice.length() != 1){
				System.out.println("Insert only 1 character!");
			}
			else{
				switch (choice.toLowerCase().charAt(0)) {
				case 'a':
					System.out.println("You chose 'a' - to validate the file content");
					updatedFile.validateContent();
					break;
				case 'b':
					System.out.println("You chose 'b' - to switch lines");
					switchLines();
					break;
				case 'c':
					System.out.println("You chose 'c' - to switch numbers at specified indexes");
					switchChars();
					break;
				case 'd':
					System.out.println("You chose 'd' - to save changes");
					saveChanges();
					break;
				default:
					System.out.println("Invalid choice!");

					break;
				}
			}
				

		}while(true);
	}
	
	private void switchLines(){
		System.out.println("Enter indexes of the lines you would like to swap (ex. 1 3):");
		
		
	    int[] choice = new int[2];
	    for(int i = 0; i < choice.length; i++){
	    	try{
	    	choice[i] = scanner.nextInt();
	    	}
	    	catch (InputMismatchException e) {
				System.out.println("You have to enter two valid digits (rows) separated by interval!");
				return;
			}
	    }
	    
	    choice[0]--;
	    choice[1]--;
		
		String[] lines = updatedFile.getContent().toString().split("\\n");
		String firstLine = null;
		String secondLine = null ;
		try{
		firstLine = lines[choice[0]];
		secondLine = lines[choice[1]];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("There are no such rows!");
			return;
		}
		updatedFile.clearContent();
		for(int i = 0 ; i < lines.length; i++){
			
			if(i == choice[0]){
				updatedFile.appendToContent(secondLine+'\n');
				continue;
			}
			if(i == choice[1]){
				updatedFile.appendToContent(firstLine+'\n');
				continue;
			}
			
			updatedFile.appendToContent(lines[i]+'\n');
		}
		
		System.out.println("\nThe updated content after exchange is: ");
		System.out.println(updatedFile.getContent());
	}
	
	private void switchChars(){
		System.out.println("Enter indexes of the lines and indexes of chars you would like to swap\n"
				+ "Example user input: <first_line_index> <first_line_number_index> <second_line_index> <second_line_number_index>");
		System.out.println("Your input:");
		
	    int[] choice = new int[4];
	    for(int i = 0; i < choice.length; i++){
	    	try{
	    	choice[i] = scanner.nextInt();
	    	}
	    	catch (InputMismatchException e) {
				System.out.println("You have to enter four valid digits (rows and char row index) separated by interval!");
				return;
			}
	    }
	    

	    updatedFile.swapChars(choice[0],choice[1],choice[2],choice[3]);
	    
	}
	
	private void saveChanges(){
		updatedFile.validateContent();
		if(updatedFile.getIsContentValid() == true){
			System.out.println("The content will be saved...");
			
			
			 File f=new File(updatedFile.getFilePath());
			
			 try{
		            FileWriter fwriter = new FileWriter(f);
		            BufferedWriter bwriter = new BufferedWriter(fwriter);
		            bwriter.write(updatedFile.getContent().toString());
		            bwriter.close();
		         }
		        catch (Exception e){
		              e.printStackTrace();
		         }
			
		}
		else{
			System.out.println("\nThe file has not been saved");
		}

	}

}
