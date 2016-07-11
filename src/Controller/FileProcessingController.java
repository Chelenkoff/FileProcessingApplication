/**
 * Controls the main workflow of the program - part of MVC pattern
 */

package Controller;

import java.util.Scanner;

import Model.FileFormatter;
import Model.NumericTextFile;

public class FileProcessingController {
	private NumericTextFile file;
	private Scanner scanner;
	private FileFormatter formatter;

	public FileProcessingController(){
		scanner = new Scanner(System.in);

		
	}
	public void showInteractionMenu(){

		do{

			System.out.println("Specify path to the file: (ex: C:\\Users\\Chelenkoff\\Desktop\\text.txt):");


			String pathToFile = scanner.next();
			System.out.printf("You have specified the following path: %s\n", pathToFile);

			file = new NumericTextFile(pathToFile);

			if(file.getContentLines() != null && file.getContentLines().size() != 0){
				System.out.println("The content of the file is:");
				file.toString();
				
				formatter = new FileFormatter(file);
				
				formatter.showFormatterMenu();
			}
			else{
				System.out.println("The file is empty!");
			}
		}while(true);

	}

}
