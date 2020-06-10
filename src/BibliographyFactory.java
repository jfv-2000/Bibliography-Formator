//-------------------------------------------------------------------
//Assignment 3
//Question: 3,4,5,6,7
//Written by: Jean-Francois Vo (40132554)
//-------------------------------------------------------------------
//This program generates a bibliography by reading information from multiple files. The program then generates that bibliography in multiple format (IEEE,ACM,NJ) by generating that bibliography
//in multiple files.
/**
 * @author Jean-Francois Vo (40132554)
 * COMP 249
 * Assignment 3
 * 30 March 2020
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This is the BibliographyFactory class to generate bibliography from a file
 */
public class BibliographyFactory {

	//Static variable for valid counter and invalid counter
	static int validFilesCounter=0;
	static int invalidFilesCounter=0;

	/**
	 * Method to delete invalid files
	 * @param IEEEfile The IEEE file that will be deleted
	 * @param ACMfile The ACM file that will be deleted 
	 * @param NJfile The NJ file that will be deleted
	 */
	public static void deleteInvalidFiles(String IEEEfile, String ACMfile, String NJfile) {
		File IEEE = new File(IEEEfile);
		File ACM = new File(ACMfile);
		File NJ = new File(NJfile);
		IEEE.delete();
		ACM.delete();
		NJ.delete();
	}

	/**
	 * Method to process if the file is invalid or valid. If valid, then generates the bibliography in multiple formats
	 * @param fileRead The file that will be read
	 * @param fileWriteIEEE the IEEE file that the bibliography will be generated into
	 * @param fileWriteACM the ACM file that the bibliography will be generated into
	 * @param fileWriteNJ the NJ file that the bibliography will be generated into
	 */
	public static void processFilesForValidation(String fileRead, String fileWriteIEEE, String fileWriteACM, String fileWriteNJ) {

		//Condition to keep method running
		boolean keepMethod = true;

		try
		{
			//Scanner to read file
			Scanner sc = new Scanner(new FileInputStream(fileRead));

			//Number of elements
			final int numberElements = 11;

			//Variable declaration
			String nextLine = null;
			int articleNumber = 0;
			int i = 0;
			String problemField = null;

			//line containing element (Variables)
			String authorLine = null;
			String journalLine = null;
			String titleLine = null;
			String yearLine = null;
			String volumeLine = null;
			String numberLine = null;
			String pagesLine = null;
			String keywordsLine = null;
			String doiLine = null;
			String ISSNLine = null;
			String monthLine = null;

			//right format for element (Variables)
			String author = null;
			String journal = null;
			String title = null;
			String year = null;
			String volume = null;
			String number = null;
			String pages = null;
			String keywords = null;
			String doi = null;
			String ISSN = null;
			String month = null;

			//Process to find number of articles
			while(sc.hasNextLine())
			{				
				String line = sc.nextLine();
				if(line.contains("ARTICLE"))
				{
					articleNumber++;
				}
			}
			sc.close();	//close scanner

			//re open scanner
			sc = new Scanner(new FileInputStream(fileRead));

			//Array of articles and its elements
			String[][] arrArticles = new String[articleNumber][numberElements];

			//Assign line to element
			try
			{
				while(sc.hasNextLine())
				{
					//boolean declaration
					boolean elementsInsideLoop = true;

					//contains @ARTICLE
					nextLine = sc.nextLine();
					if(nextLine.contains("ARTICLE"))
					{
						while(elementsInsideLoop)
						{
							nextLine = sc.nextLine();

							//invalid file
							if(nextLine.contains("{}")) 
							{
								problemField = nextLine.substring(0, nextLine.indexOf("="));
								throw new FileInvalidException("\nError: Detected Empty Field!\n============================");
							}

							//author line
							if(nextLine.contains("author"))
							{
								authorLine=nextLine;
								author = authorLine.substring(authorLine.indexOf("{")+1, authorLine.indexOf("}"));
							}
							//journal
							if(nextLine.contains("journal"))
							{
								journalLine=nextLine;
								journal = journalLine.substring(journalLine.indexOf("{")+1, journalLine.indexOf("}"));
							}
							//title line
							if(nextLine.contains("title"))
							{
								titleLine=nextLine;
								title = titleLine.substring(titleLine.indexOf("{")+1, titleLine.indexOf("}"));
							}
							//year line
							if(nextLine.contains("year"))
							{
								yearLine=nextLine;
								year = yearLine.substring(yearLine.indexOf("{")+1, yearLine.indexOf("}"));
							}
							//volume line
							if(nextLine.contains("volume"))
							{
								volumeLine=nextLine;
								volume = volumeLine.substring(volumeLine.indexOf("{")+1, volumeLine.indexOf("}"));
							}
							//number line
							if(nextLine.contains("number"))
							{
								numberLine=nextLine;
								number = numberLine.substring(numberLine.indexOf("{")+1, numberLine.indexOf("}"));
							}
							//pages line
							if(nextLine.contains("pages"))
							{
								pagesLine=nextLine;
								pages = pagesLine.substring(pagesLine.indexOf("{")+1, pagesLine.indexOf("}"));
							}
							//keywords line
							if(nextLine.contains("keywords"))
							{
								keywordsLine=nextLine;
								keywords = keywordsLine.substring(keywordsLine.indexOf("{")+1, keywordsLine.indexOf("}"));
							}
							//doi line
							if(nextLine.contains("doi"))
							{
								doiLine=nextLine;
								doi = doiLine.substring(doiLine.indexOf("{")+1, doiLine.indexOf("}"));
							}
							//ISSN line
							if(nextLine.contains("ISSN"))
							{
								ISSNLine=nextLine;
								ISSN = ISSNLine.substring(ISSNLine.indexOf("{")+1, ISSNLine.indexOf("}"));
							}
							//month line
							if(nextLine.contains("month"))
							{
								monthLine=nextLine;
								month = monthLine.substring(monthLine.indexOf("{")+1, monthLine.indexOf("}"));
							}

							//end of an article
							if(nextLine.equals("}"))
							{	
								//assign elements to right array of article
								arrArticles[i][0] = author;
								arrArticles[i][1] = journal;
								arrArticles[i][2] = title;
								arrArticles[i][3] = year;
								arrArticles[i][4] = volume;
								arrArticles[i][5] = number;
								arrArticles[i][6] = pages;
								arrArticles[i][7] = keywords;
								arrArticles[i][8] = doi;
								arrArticles[i][9] = ISSN;
								arrArticles[i][10] = month;

								i++;
								//Go to next article
								elementsInsideLoop = false;
							}
						}
					}
				}
				sc.close();
			}
			catch(FileInvalidException e)
			{
				System.out.println(e.getMessage());
				System.out.println("Problem detected with input file "+fileRead);
				System.out.println("File is invalid: Field "+problemField+" is empty. Processing stopped at this point. Other empty fields may be present as well.");
				sc.close();
				invalidFilesCounter++;
				deleteInvalidFiles(fileWriteIEEE,fileWriteACM,fileWriteNJ);
				keepMethod=false;
			}


			//Write to file if file is valid
			if(keepMethod==true)
			{
				//PrintWriter to write file
				PrintWriter pwIEEE = new PrintWriter(new FileOutputStream(fileWriteIEEE,true));
				PrintWriter pwACM = new PrintWriter(new FileOutputStream(fileWriteACM,true));
				PrintWriter pwNJ = new PrintWriter(new FileOutputStream(fileWriteNJ,true));

				validFilesCounter++;
				//Variables declaration for writing to file
				String authors[] = null;
				String journalForm = null;
				String titleForm = null;
				String yearForm = null;
				String volumeForm = null;
				String numberForm = null;
				String pagesForm = null;
				String keywordsForm = null;
				String doiForm = null;
				String ISSNForm = null;
				String monthForm = null;

				//Write to IEEE
				for(i=0;i<articleNumber;i++)
				{
					//Assign to simple form
					journalForm = arrArticles[i][1];
					titleForm = arrArticles[i][2];
					yearForm = arrArticles[i][3];
					volumeForm = arrArticles[i][4];
					numberForm = arrArticles[i][5];
					pagesForm = arrArticles[i][6];
					keywordsForm = arrArticles[i][7];
					doiForm = arrArticles[i][8];
					ISSNForm = arrArticles[i][9];
					monthForm = arrArticles[i][10];

					//if single author
					if(!arrArticles[i][0].contains(" and "))
					{
						String singleAuthor = arrArticles[i][0];
						pwIEEE.print(singleAuthor+". \""+titleForm+"\", "+journalForm+", vol. "+volumeForm+", no. "+numberForm+", p. "+pagesForm+", "+monthForm+" "+yearForm+".");
						pwIEEE.println("\n");
					}
					//if many authors
					else
					{
						//author separation into arrays
						authors = arrArticles[i][0].split(" and ");
						//writing format IEEE
						for(int k=0;k<authors.length;k++)
						{
							//last author to write
							if(k==authors.length-1)
							{
								pwIEEE.print(authors[k]+". ");
							}
							//Not last author
							else 
							{
								pwIEEE.print(authors[k]+", ");

							}
						}
						pwIEEE.print("\""+titleForm+"\", "+journalForm+", vol. "+volumeForm+", no. "+numberForm+", p. "+pagesForm+", "+monthForm+" "+yearForm+".");
						pwIEEE.println("\n");
					}
				}

				pwIEEE.close();

				//Write to ACM
				for(i=0;i<articleNumber;i++)
				{
					//Assign to simple form
					journalForm = arrArticles[i][1];
					titleForm = arrArticles[i][2];
					yearForm = arrArticles[i][3];
					volumeForm = arrArticles[i][4];
					numberForm = arrArticles[i][5];
					pagesForm = arrArticles[i][6];
					keywordsForm = arrArticles[i][7];
					doiForm = arrArticles[i][8];
					ISSNForm = arrArticles[i][9];
					monthForm = arrArticles[i][10];

					//if single author
					if(!arrArticles[i][0].contains(" and "))
					{
						//writing format ACM
						String singleAuthor = arrArticles[i][0];
						pwACM.print("["+(i+1)+"]\t"+singleAuthor+". "+yearForm+". "+titleForm+". "+journalForm+". "+volumeForm+", "+numberForm+" ("+yearForm+"), "+pagesForm+
								". DOI:https://doi.org/"+doiForm+".");
						pwACM.println("\n");
					}
					//if many authors
					else
					{
						//author separation into arrays
						authors = arrArticles[i][0].split(" and ");
						//writing format ACM
						pwACM.print("["+(i+1)+"]\t"+authors[0]+". "+yearForm+". "+titleForm+". "+journalForm+". "+volumeForm+", "+numberForm+" ("+yearForm+"), "+pagesForm+
								". DOI:https://doi.org/"+doiForm+".");
						pwACM.println("\n");
					}
				}
				pwACM.close();

				//Write to NJ
				for(i=0;i<articleNumber;i++)
				{
					//Assign to simple form
					journalForm = arrArticles[i][1];
					titleForm = arrArticles[i][2];
					yearForm = arrArticles[i][3];
					volumeForm = arrArticles[i][4];
					numberForm = arrArticles[i][5];
					pagesForm = arrArticles[i][6];
					keywordsForm = arrArticles[i][7];
					doiForm = arrArticles[i][8];
					ISSNForm = arrArticles[i][9];
					monthForm = arrArticles[i][10];

					//if single author
					if(!arrArticles[i][0].contains(" and "))
					{
						String singleAuthor = arrArticles[i][0];
						pwNJ.print(singleAuthor+". "+titleForm+". "+journalForm+". "+volumeForm+", "+pagesForm+"("+yearForm+").");
						pwNJ.println("\n");
					}
					//if many authors
					else
					{
						//author separation into arrays
						authors = arrArticles[i][0].split(" and ");
						//writing format IEEE
						for(int k=0;k<authors.length;k++)
						{
							//last author to write
							if(k==authors.length-1)
							{
								pwNJ.print(authors[k]+". ");
							}
							//Not last author
							else 
							{
								pwNJ.print(authors[k]+" & ");
							}
						}
						pwNJ.print(titleForm+". "+journalForm+". "+volumeForm+", "+pagesForm+"("+yearForm+").");
						pwNJ.println("\n");
					}
				}
				pwNJ.close();
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Could not open input file for reading.");
			System.out.println("Please check if file exists! Program will terminate after closing any opened files.");
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		
		//Variable declaration
		final int numberOfFiles=10;
		String fileName;
		Scanner[] fileReader = new Scanner[numberOfFiles];
		int fileNumber=1;

		//Welcome message
		System.out.println("Welcome to Jean-Francois Vo's Bibliography Factory ! ");

		//Read file
		for(fileNumber=1;fileNumber<=numberOfFiles;fileNumber++)
		{
			//Name of file
			fileName = "Latex"+fileNumber+".bib";
			try
			{ 
				fileReader[fileNumber-1] = new Scanner (new FileInputStream(fileName));
				System.out.println("Successfully read "+fileName);
				fileReader[fileNumber-1].close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("Could not open input file "+fileNumber+" for reading.");
				System.out.println("Please check if file exists! Program will terminate after closing any opened files.");
				for(fileNumber=1;fileNumber<=numberOfFiles;fileNumber++)
				{
					if(fileReader[fileNumber-1] != null)
					{
						fileReader[fileNumber-1].close();
					}
				}
				System.exit(0);
			}
		}

		//Variable declaration
		String fileNameOutputIEEE = null;
		String fileNameOutputACM = null;
		String fileNameOutputNJ = null;
		PrintWriter pwIEEE = null;
		PrintWriter pwACM = null;
		PrintWriter pwNJ = null;

		//Creating/opening IEEE,ACM,NJ files
		for(fileNumber=1;fileNumber<=numberOfFiles;fileNumber++)
		{
			fileNameOutputIEEE = "IEEE"+fileNumber+".json";
			fileNameOutputACM = "ACM"+fileNumber+".json";
			fileNameOutputNJ = "NJ"+fileNumber+".json";
			try
			{
				pwIEEE = new PrintWriter(new FileOutputStream(fileNameOutputIEEE));
				System.out.println(fileNameOutputIEEE+" has been created");
				pwACM = new PrintWriter(new FileOutputStream(fileNameOutputACM));
				System.out.println(fileNameOutputACM+" has been created");
				pwNJ= new PrintWriter(new FileOutputStream(fileNameOutputNJ));
				System.out.println(fileNameOutputNJ+" has been created");

				pwIEEE.close();
				pwACM.close();
				pwNJ.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("Could not create/open "+fileNameOutputIEEE+", "+fileNameOutputACM+" and "+fileNameOutputNJ+".");

				//Delete files
				for(int j=1;j<=numberOfFiles;j++)
				{
					fileNameOutputIEEE = "IEEE"+fileNumber+".json";
					fileNameOutputACM = "ACM"+fileNumber+".json";
					fileNameOutputNJ = "NJ"+fileNumber+".json";

					File IEEE = new File(fileNameOutputIEEE);
					File ACM = new File(fileNameOutputACM);
					File NJ = new File(fileNameOutputNJ);
					if(IEEE.exists())
					{
						IEEE.delete();
					}
					if(ACM.exists())
					{
						ACM.delete();
					}
					if(NJ.exists())
					{
						NJ.delete();
					}
				}
				//close pw
				pwIEEE.close();
				pwACM.close();
				pwNJ.close();
			}
		}

		for(int i=1;i<=numberOfFiles;i++)
		{
			String fileRead = "Latex"+i+".bib";
			String fileWriteIEEE = "IEEE"+i+".json";
			String fileWriteACM = "ACM"+i+".json";
			String fileWriteNJ = "NJ"+i+".json";
			processFilesForValidation(fileRead, fileWriteIEEE,fileWriteACM,fileWriteNJ);
		}

		System.out.println("\nA total of "+invalidFilesCounter+" files were invalid, and could not be processed. All other "+validFilesCounter+" Valid files have been created.");

		//Ask user for name of file

		//Failed attempts counter
		int failedAttempts=0;
		boolean validAttempts = true;

		while(validAttempts)
		{
			try
			{
				//Prompt user for file name
				Scanner mykeyboard = new Scanner(System.in);
				System.out.print("\nPlease enter the name of one of the files that you need to review : ");
				String nameFile = mykeyboard.next();
				File user = new File(nameFile);

				//if file exists
				if(user.exists())
				{
					//Read file
					BufferedReader readFile = new BufferedReader(new FileReader(user));

					//display file to program
					try
					{
						System.out.println("Here are the contents of the successfully created file: "+nameFile+"\n");
						String lineRead=readFile.readLine();
						while(lineRead !=null)
						{
							System.out.println(lineRead);
							lineRead = readFile.readLine();
						}
						readFile.close();
						validAttempts=false;
					}
					catch(IOException e)
					{
						System.out.println("An error has occured. Program will terminate");
						System.exit(0);
					}
				}
				else
				{
					failedAttempts++;
					throw new FileNotFoundException("\nCould not open input file. File does not exist; possibly it could not be created!");
				}
			}
			catch(FileNotFoundException e)
			{
				if(failedAttempts==2)
				{
					System.out.println("\nCould not open input file again! Either file does not exist or could not be created");
					System.out.println("Sorry, I am unable to display your desired files! Program will exit!");
					System.exit(0);
				}
				else
				{
					System.out.println(e.getMessage());
					System.out.println("However, you will be allowed another chance to enter another file name.");
				}
			}
		}
		//Closing message
		System.out.println("Goodbye! Hope you have enjoyed creating the needed files using Jean-Francois Vo's BibliographyFactory!");
	}

}

