package behaviour_graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * @author Martin
 * This class will read the data files into arrays for analysis by the rest of the program.
 */
public class FileReader {
	
	private File squashCoachesDataFile; // The csv file containing the data.
	
	// Where the data will be stored.
	// 1 element for each line of the file, consisting of an ArrayList of behaviours (numbers from 1-14).
	private ArrayList<ArrayList<String>> squashCoachesData; 
	
	/**
	 * Constructor
	 * @param squashCoachFilePath - String containing the file path of the csv file.
	 */
	public FileReader(String squashCoachFilePath) {
		this.squashCoachesDataFile = new File(squashCoachFilePath);
		this.squashCoachesData = new ArrayList<ArrayList<String>>();
		
		try {
			boolean firstLine = true;
			Scanner squashScanner = new Scanner(squashCoachesDataFile);
			while (squashScanner.hasNextLine()) { // Iterate through each line of the file.
				String line = squashScanner.nextLine();
				if(!firstLine) {
					
					String[] fields = line.split(","); // Each behaviour is split with a comma.
					ArrayList<String> data = new ArrayList<String>();
					boolean empty = false;
					int count = 0;
					String dataItem;
					while(!empty) { // Loop until we reach the end of the line.
						dataItem = fields[count];
						if(dataItem.equals("")) { // Check for end of line.
							empty = true;
						} else {
							data.add(dataItem); // Add behaviour to the ArrayList.
							count++;
							if(count==fields.length) { // Check for end of line.
								empty = true;
							}
						}
					}
					squashCoachesData.add(data);
				}
				firstLine = false;
			}
			squashScanner.close();
		} catch (FileNotFoundException e){ // Handle exception.
			System.err.println("Error: could night find squash coaches data file.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Getter method.
	 * @return an ArrayList containing all of the data held in the squash coaches data file.
	 */
	public ArrayList<ArrayList<String>> getAllSquashData() {
		return this.squashCoachesData;
	}

	
}
