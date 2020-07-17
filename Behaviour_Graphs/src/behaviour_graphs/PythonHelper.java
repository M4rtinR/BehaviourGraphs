package behaviour_graphs;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.lang.Math;
import org.jgrapht.Graph;

public class PythonHelper {	
	private ArrayList<ArrayList<ArrayList<Float>>> following;
	private ArrayList<ArrayList<Integer>> sequences;
	
	private ArrayList<ArrayList<ArrayList<Float>>> squashFollowing;
	private ArrayList<ArrayList<Integer>> squashSequences;
	
	private ArrayList<ArrayList<ArrayList<Float>>> physioFollowing;
	private ArrayList<ArrayList<Integer>> physioSequences;
	
	private ArrayList<ArrayList<Float>> squashMatrix;
	private ArrayList<ArrayList<Float>> physioMatrix;
	
	private ArrayList<ArrayList<ArrayList<Float>>> compoundFollowing;
	private ArrayList<ArrayList<Integer>> compoundSequences;
	
	private ArrayList<ArrayList<ArrayList<Float>>> compoundSquashFollowing;
	private ArrayList<ArrayList<Integer>> compoundSquashSequences;
	
	private ArrayList<ArrayList<ArrayList<Float>>> compoundPhysioFollowing;
	private ArrayList<ArrayList<Integer>> compoundPhysioSequences;
	
	private ArrayList<ArrayList<Integer>> testSequences;
	
	private HashMap<Integer, Integer> compoundMappings;
	
	public PythonHelper(int whichData) {
		compoundMappings = new HashMap<Integer, Integer>();
		
		FileReader frSquash;
		FileReader frPhysio;
		
		ArrayList<ArrayList<String>> squashData;
		ArrayList<ArrayList<String>> physioData;
		
		switch(whichData) {
		case 0:
			ArrayList<ArrayList<String>> testData = new ArrayList<ArrayList<String>>(
					Arrays.asList(new ArrayList<String>(Arrays.asList("1", "1", "2", "2", "3", "4", "4~9", "3", "5", "7", "6", "4", "7")),
							new ArrayList<String>(Arrays.asList("1", "2", "3", "5", "4~9", "3", "7", "6", "1", "1", "4", "4")),
							new ArrayList<String>(Arrays.asList("12", "8", "8", "9~2", "9", "12", "12~10", "11", "10", "14", "14", "13", "10", "13")),
							new ArrayList<String>(Arrays.asList("12", "12", "13", "10", "14", "14", "13", "8", "8", "9~2", "9", "10", "11", "10", "13"))));
			
			testSequences = constructSequenceList(testData, true);
			
			break;
		case 1:
			frSquash = new FileReader("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\Squash Coaches\\SPSS\\Observation Study\\Behaviours.csv");
			
			squashData = frSquash.getAllSquashData();
			
			squashSequences = constructSequenceList(squashData, false);
			//squashFollowing = constructFollowingList(squashData);
			
			compoundSquashSequences = constructSequenceList(squashData, true);
			//compoundSquashFollowing = constructFollowingMatrix(compoundSquashSequences);
			
			break;
		case 2:
			frPhysio = new FileReader("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\Stroke Physiotherapists\\Data\\Behaviours.csv");
			
			physioData = frPhysio.getAllSquashData();
			
			physioSequences = constructSequenceList(physioData, false);
			//physioFollowing = constructFollowingList(physioData);
			
			compoundPhysioSequences = constructSequenceList(physioData, true);
			//compoundPhysioFollowing = constructFollowingMatrix(compoundPhysioSequences);
			
			break;
		case 3:
			frSquash = new FileReader("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\Squash Coaches\\SPSS\\Observation Study\\Behaviours.csv");
			frPhysio = new FileReader("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\Stroke Physiotherapists\\Data\\Behaviours.csv");
			
			squashData = frSquash.getAllSquashData();
			physioData = frPhysio.getAllSquashData();
			
			ArrayList<ArrayList<String>> allData = new ArrayList<ArrayList<String>>(squashData.size() + physioData.size());
			allData.addAll(squashData);
			allData.addAll(physioData);
			
			sequences = constructSequenceList(allData, false);
			//following = constructFollowingList(allData);
			
			compoundSequences = constructSequenceList(allData, true);
			//compoundFollowing = constructFollowingMatrix(compoundSequences);
			
			break;
		}
		
		System.out.println(compoundMappings);
	}
	
	private ArrayList<Integer> getIntegerList(ArrayList<String> line) {
		ArrayList<Integer> sequence = new ArrayList<Integer>(line.size() - 4);
		
		int count = 0;
		for(String s: line) {
			if (count > 3){
				String[] behaviours = s.split("~");
				sequence.add(Integer.valueOf(behaviours[0]));
			}
			count++;
		}
		
		return sequence;
	}
	
	private ArrayList<Integer> getCompoundIntegerList(ArrayList<String> line) {
		ArrayList<Integer> sequence = new ArrayList<Integer>();
		
		int count = 0;
		for(String s: line) {
			if (count > 3){
				String[] behaviours = s.split("~");
				if(behaviours.length == 1) {
					// No concurrent behaviours so just add the behaviour to the sequence.
					sequence.add(Integer.valueOf(behaviours[0]));
				} else {
					// Concurrent behaviour(s) contained so add each one along with the parent to the sequence.
					String parent = behaviours[0];
					boolean isParent = true;
					for(String behaviour: behaviours) {
						if (!isParent) {
							// The value of the compound behaviour is 16*parent + concurrent behaviour.
							// So if the parent behaviour was Post Instruction (Positive) - behaviour 4 -
							// and the concurrent behaviour was Positive Modelling - behaviour 8 -
							// the value of the compound behaviour would be 16 * 4 + 8 = 72.
							sequence.add(16*Integer.valueOf(parent) + Integer.valueOf(behaviour)); 
						} else {
							isParent = false;					
						}
					}
				}
			}
			count++;
		}
		
		return sequence;
	}
	
	private ArrayList<ArrayList<Integer>> constructSequenceList(ArrayList<ArrayList<String>> data, boolean compound){
		ArrayList<ArrayList<Integer>> sequenceList = new ArrayList<ArrayList<Integer>>();
		
		// Add each line of data to the sequenceList.
		for(int i = 0; i < data.size(); i++) {
			if(!compound) {
				sequenceList.add(getIntegerList(data.get(i)));
			} else { 
				sequenceList.add(getCompoundIntegerList(data.get(i)));
			}
		}
		
		if(compound) {
			// Convert the compound numbers into a continuation of the numbering of the original behaviours.
			// e.g. if the parent behaviour pre-instruction - behaviour 1 - and the concurrent behaviour
			// positive modelling - behaviour 8 - were the lowest compound behaviour (i.e. the number 24 represents
			// this behaviour), this would be changed to the number 16. The next lowest will be changed to 17 etc.
			
			// First calculate the replacements.
			ArrayList<Integer> uniqueElements = constructUniques(sequenceList);
			Collections.sort(uniqueElements);
			
			//System.out.println(uniqueElements);
			
			int lowest = 15; // Set to 15 just now, may need to change to 16 to make space for end. Or could just put "end" on the end.
			for(int i = 0; i < uniqueElements.size(); i++) {
				if(uniqueElements.get(i) > 14) {
					compoundMappings.put(lowest, uniqueElements.get(i));
					for(ArrayList<Integer> sequence : sequenceList) {
						Collections.replaceAll(sequence, uniqueElements.get(i), Integer.valueOf(lowest));
					}
					uniqueElements.set(i, lowest);
					lowest++;
				}
			}
			
			/*System.out.println(uniqueElements);
			System.out.print("[");
			for(ArrayList<Integer> sequence : sequenceList) {
				System.out.println(sequence);
			}
			System.out.println("]");*/
			
			
			// Then populate the sequence with the replacements.
			
		}
		
		return sequenceList;
	}
	
	private ArrayList<ArrayList<ArrayList<Float>>> constructFollowingList(ArrayList<ArrayList<String>> data){
		ArrayList<ArrayList<ArrayList<Float>>> followingList = new ArrayList<ArrayList<ArrayList<Float>>>(data.size());
		
		// Add to the followingList line by line.
		for(int i = 0; i < data.size(); i++) {
			// The data counter will count all the data that was in the file, storing it in another ArrayList.
			DataCounter dc = new DataCounter(data);
			
			ArrayList<HashMap<String, ArrayList<Integer>>> countedData = new ArrayList<HashMap<String, ArrayList<Integer>>>();
			
			countedData = dc.getSequenceData(i);
			
			ArrayList<ArrayList<Float>> actualFollowing = constructSingleFollowing(countedData);
			
			followingList.add(actualFollowing);
		}
		
		return followingList;
	}
	
	private ArrayList<ArrayList<Float>> constructSingleFollowing(ArrayList<HashMap<String, ArrayList<Integer>>> countedData){
		DataAnalyser da = new DataAnalyser(countedData);
		
		float[][] thisFollowingArr = da.getFollowing();
		int[] thisOccurrences = da.getTotals();
		int totalBehaviours = 0;
		for(int thisBehaviour : thisOccurrences) {
			totalBehaviours += thisBehaviour;
		}

		// Convert following array into arraylist.
		ArrayList<ArrayList<Float>> thisFollowingList = new ArrayList<ArrayList<Float>>();
		for(int j = 0; j < thisFollowingArr.length; j++) {
			ArrayList<Float> tempFollowingList = new ArrayList<Float>();
			for(int k = 0; k < thisFollowingArr[j].length; k++) {
				tempFollowingList.add(thisFollowingArr[j][k]);
			}
			thisFollowingList.add(tempFollowingList);
		}

		// Add a column at the beginning for "Start" and a row at the end for "End" and store in actualFollowing
		ArrayList<ArrayList<Float>> actualFollowing = new ArrayList<ArrayList<Float>>(16);
		
		for (int row = 0; row < 16; row++) {
			ArrayList<Float> tempActual = new ArrayList<Float>(16);
			for (int col = 0; col < 16; col++) {
				if(col == 0) {
					if(row != 0 && row != 15) {
						tempActual.add((float) ((float) thisOccurrences[row] / (float) totalBehaviours)); // Use the first column to store the occurrences of this behaviour since otherwise it will always be 0.
						
						/*float concurrent = thisConcurrentArr[row][col];
						if (concurrent > 0) {
							compound.add(e)
						}*/
					} else {
						tempActual.add((float) 0);
					}
				}else if(row == 15) {
					tempActual.add((float) 0);
				} else {
					tempActual.add(thisFollowingList.get(row).get(col - 1));
				}
			}
			actualFollowing.add(tempActual);
			
		}
		
		// Loop through actual following
		// for each row except 0 and 15
		// for each column except 0 and 15
		// if concurrent(row, col) > 0
		// add to new arraylist combo behaviour (row, col) with value of concurrent(row,col)
		// add new arraylist onto end of actualfollowing
		
		return actualFollowing;
	}
	
	ArrayList<ArrayList<Float>> constructMatrix(ArrayList<ArrayList<String>> data){
		ArrayList<ArrayList<Float>> matrix = new ArrayList<ArrayList<Float>>();
		
		// The data counter will count all the data that was in the file, storing it in another ArrayList.
		DataCounter dc = new DataCounter(data);
		
		ArrayList<HashMap<String, ArrayList<Integer>>> countedData = new ArrayList<HashMap<String, ArrayList<Integer>>>();
		
		countedData = dc.getAllData();
		
		matrix = constructSingleFollowing(countedData);
		
		return matrix;
	}
	
	ArrayList<ArrayList<ArrayList<Float>>> constructFollowingMatrix(ArrayList<ArrayList<Integer>> sequences) {
		int uniqueBehaviours = uniqueElements(sequences);
		//System.out.println("Unique behaviours: " + uniqueBehaviours);
		
		ArrayList<ArrayList<ArrayList<Float>>> finalMatrix = new ArrayList<ArrayList<ArrayList<Float>>>(sequences.size());
		
		//ArrayList<ArrayList<ArrayList<Integer>>> followingMatrix = new ArrayList<ArrayList<ArrayList<Integer>>>(sequences.size());
		/*for (int i = 0; i < sequences.size(); i++) {
			ArrayList<ArrayList<Float>> tempLvl1 = new ArrayList<ArrayList<Float>>(uniqueBehaviours);
			for(int j = 0; j < uniqueBehaviours; j++) {
				ArrayList<Float> tempLvl2 = new ArrayList<Float>(uniqueBehaviours);
				for(int k = 0; k < uniqueBehaviours; k++) {
					tempLvl2.add(0.0f);
				}
				tempLvl1.add(tempLvl2);
			}
			finalMatrix.add(tempLvl1);
		}*/
		
		for(int s = 0; s < sequences.size(); s++) {
			
			// Initialise matrices
			ArrayList<ArrayList<Integer>> currentSequenceMatrix = new ArrayList<ArrayList<Integer>>(uniqueBehaviours);
			ArrayList<Integer> occurrences = new ArrayList<Integer>(uniqueBehaviours);
			for(int i = 0; i <= uniqueBehaviours; i++) {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				for(int j = 0; j <= uniqueBehaviours; j++) {
					temp.add(0);
				}
				currentSequenceMatrix.add(temp);
				occurrences.add(0);
			}
			
			// for each behaviour in the sequence
			// add one to row = previous, col = behaviour.
			
			// Create the following matrix for each sequence.
			ArrayList<Integer> sequence = sequences.get(s);
			/*int oldPrevious = 0;
			int newPrevious = 0;*/
			int previous = 0;
			for(int behaviour : sequence) {
				//System.out.println("matrix before: " + currentSequenceMatrix);
				//System.out.println("previous = " + previous + ", behaviour = " + behaviour);
				int previousValue = currentSequenceMatrix.get(previous).get(behaviour);
				//System.out.println("previousValue = " + previousValue);
				ArrayList<Integer> temp = currentSequenceMatrix.get(previous);
				temp.set(behaviour, previousValue + 1);
				currentSequenceMatrix.set(previous, temp);
				//System.out.println("matrix after: " + currentSequenceMatrix);
				previous = behaviour;
			}
			
			//System.out.println("currentSequenceMatrix " + s + ":");
			//System.out.println(currentSequenceMatrix);
				
				
				
				
				
			/*	ArrayList<ArrayList<Integer>> temp = currentSequenceMatrix.get();
				for(int col = 0; col <= uniqueBehaviours; col++) {
					System.out.println("newPrevious: " + newPrevious + ", behaviour: " + behaviour);
					System.out.println("tempBefore: " + temp);
					int previousTotal = temp.get(behaviour);
					temp.set(behaviour, previousTotal+1);
					System.out.println("tempAfter: " + temp);
					newPrevious = behaviour;
				}
				currentSequenceMatrix.set(oldPrevious, temp);
				oldPrevious = newPrevious;
			}*/
			
			ArrayList<ArrayList<Float>> currentFollowingMatrix = new ArrayList<ArrayList<Float>>();
			for(int row = 0; row < currentSequenceMatrix.size(); row++) {
				// Add up all the currentMatrix.
				int total = 0;
				for (int col = 0; col < currentSequenceMatrix.get(row).size(); col++) {
					total+=currentSequenceMatrix.get(row).get(col);
				}
				// Calculate following percentages
				ArrayList<Float> tempFollowing = new ArrayList<Float>();
				for (int col = 0; col < currentSequenceMatrix.get(row).size(); col++) {
					if(total != 0) {
						tempFollowing.add((float) Float.valueOf(currentSequenceMatrix.get(row).get(col)) / Float.valueOf(total));
					} else {
						tempFollowing.add(0.0f);
					}
				}
				currentFollowingMatrix.add(tempFollowing);
			}
			
			//System.out.println("currentFollowingMatrix " + s + ":");
			//System.out.println(currentFollowingMatrix);
			
			// Add currentMatrix to followingMatrices as float
			finalMatrix.add(currentFollowingMatrix);
			/*System.out.print("finalMatrix.get(0):\n[");
			for(int row = 0; row < finalMatrix.get(0).size(); row++) {
				System.out.println(finalMatrix.get(0).get(row));
			}
			System.out.println("]");*/
		}
		
		/*System.out.print("\n\n\n\n\n\n\n\n\n\nAbout to retun:\nfinalMatrix.get(0):\n[");
		for(int row = 0; row < finalMatrix.get(0).size(); row++) {
			System.out.println(finalMatrix.get(0).get(row));
		}
		System.out.println("]");*/
		
		
		
		return finalMatrix;
	}
	
	int uniqueElements(ArrayList<ArrayList<Integer>> sequences) {
		ArrayList<Integer> uniques = constructUniques(sequences);
		
		return uniques.size();
	}
	
	ArrayList<Integer> constructUniques(ArrayList<ArrayList<Integer>> sequences) {
		ArrayList<Integer> uniques = new ArrayList<Integer>();
		
		for(int i = 0; i < sequences.size(); i++) {
			for(int j = 0; j < sequences.get(i).size(); j++) {
				if (!uniques.contains(sequences.get(i).get(j))){
					uniques.add(sequences.get(i).get(j));
				}
			}
		}		
		
		return uniques;
	}
	
	public ArrayList<ArrayList<ArrayList<Float>>> getFollowing(){
		return following;
	}
	
	public ArrayList<ArrayList<Integer>> getSequences(int whichData, int compound){
		// Return an array of the sequence of each segment of each session in the requested data.
		switch(whichData) {
		case 0:
			return testSequences;
		case 1:
			if(compound == 1) return compoundSquashSequences;
			else return squashSequences;
		case 2:
			if(compound == 1) return compoundPhysioSequences;
			else return physioSequences;
		case 3:
			if(compound == 1) return compoundSequences;
			else return sequences;
		}
		
		return null;
	}
	
	public ArrayList<ArrayList<ArrayList<Float>>> getSquashFollowing(){
		return squashFollowing;
	}
	
	public ArrayList<ArrayList<Integer>> getSquashSequences(){
		// Return an array of the sequence of each segment of each session.
		return squashSequences;
	}
	
	public ArrayList<ArrayList<ArrayList<Float>>> getPhysioFollowing(){
		return physioFollowing;
	}
	
	public ArrayList<ArrayList<Integer>> getPhysioSequences(){
		// Return an array of the sequence of each segment of each session.
		return physioSequences;
	}
	
	public ArrayList<ArrayList<Float>> getSquashMatrix(){
		return squashMatrix;
	}
	
	public ArrayList<ArrayList<Float>> getPhysioMatrix(){
		return physioMatrix;
	}
	
	public ArrayList<ArrayList<Integer>> getCompoundSequences(){
		return compoundSequences;
	}
	
	public ArrayList<ArrayList<ArrayList<Float>>> getCompoundFollowing(){
		return compoundFollowing;
	}
	
	public ArrayList<ArrayList<Integer>> getCompoundSquashSequences(){
		return compoundSquashSequences;
	}
	
	public ArrayList<ArrayList<ArrayList<Float>>> getCompoundSquashFollowing(){
		return compoundSquashFollowing;
	}
	
	public ArrayList<ArrayList<Integer>> getCompoundPhysioSequences(){
		return compoundPhysioSequences;
	}
	
	public ArrayList<ArrayList<ArrayList<Float>>> getCompoundPhysioFollowing(){
		return compoundPhysioFollowing;
	}
	
	public ArrayList<ArrayList<Integer>> getTestSequences(){
		return testSequences;
	}
	
	public void createGraph(float[][][] clusters) {
		System.out.println(clusters);
		
		// Set the totals to be the first column of the given data.
		int[][] totals = new int[clusters.length][15];
		
		// Set the containings and precedings arrays to be all 0s.
		float[][][] containings = new float[clusters.length][15][15];
		int[][][] concurrent = new int[clusters.length][15][15];
		float[][][] precedings = new float[clusters.length][15][15];
		float[][][] followings = new float[clusters.length][15][15];
		
		// Initialise concurrent array
		for(int cluster = 0; cluster < clusters.length; cluster++) {
			for(int row = 0; row < 15; row++) {
				for(int col = 0; col < 15; col++) {
					concurrent[cluster][row][col] = 0;
				}
			}
		}
		
		for(int cluster = 0; cluster < clusters.length; cluster++) {
			for(int row = 0; row < clusters[cluster].length; row++) {
				if (row < 15) {
					//System.out.println("row < 15");
					for(int col = 0; col < clusters[cluster][row].length; col++) {
						if(col == 0 && row != clusters[cluster].length -1) { // the final row is "end" so we don't need to worry about it.
							//System.out.println("col == 0 && row != clusters[cluster].length -1");
							//System.out.println("Cluster: " + cluster + ", row: " + row + ", column: " + col + ". This: " + clusters[cluster][row][col] + ", Math.round: " + Math.round(clusters[cluster][row][col] *100));
							totals[cluster][row] = Math.round(clusters[cluster][row][col] *100);
							//followings[cluster][row][col] = 0.00f;
						} else if (col < 15 && row != clusters[cluster].length -1) {
							//System.out.println("col < 15 && row != clusters[cluster].length -1");
							//System.out.println("Cluster: " + cluster + ", row: " + row + ", column: " + col + ". This: " + clusters[cluster][row][col]);
							containings[cluster][row][col-1] = 0.00f;
							precedings[cluster][row][col-1] = 0.00f;
							followings[cluster][row][col-1] = clusters[cluster][row][col];
						} else if (col < clusters[cluster][row].length - 1 && row != clusters[cluster].length - 1){
							//System.out.println("col < clusters[cluster][row].length - 1 && row != clusters[cluster].length - 1");
							//Compound behaviour
							//System.out.println("Cluster: " + cluster + ", row: " + row + ", column: " + col + ". This: " + clusters[cluster][row][col] + ", decodeParent(col): " + decodeParent(col));
							followings[cluster][row][decodeParent(col)] += clusters[cluster][row][col];
						} else if (row != clusters[cluster].length - 1) {
							//System.out.println("row != clusters[cluster].length - 1");
							//Final column
							followings[cluster][row][14] = clusters[cluster][row][col];
						}
					}
				} else if(row != clusters[cluster].length -1){
					//System.out.println("row != clusters[cluster].length -1");
					// Compound behaviour
					for(int col = 0; col < clusters[cluster][row].length; col++) {
						if(col == 0) {
							//System.out.println("col == 0");
							//System.out.println("Cluster: " + cluster + ", row: " + row + ", column: " + col + ". This: " + clusters[cluster][row][col] + "decodeParent(row): " + decodeParent(row) + "decodeConcurrent(row): " + decodeConcurrent(row));
							totals[cluster][decodeParent(row)] += Math.round(clusters[cluster][row][col] *100);
							concurrent[cluster][decodeParent(row)][decodeConcurrent(row)] += Math.round(clusters[cluster][row][col] *100);
						} else if (col < 15) {
							//System.out.println("col < 15");
							//System.out.println("Cluster: " + cluster + ", row: " + row + ", column: " + col + ". This: " + clusters[cluster][row][col] + "decodeParent(row): " + decodeParent(row));
							followings[cluster][decodeParent(row)][col] += clusters[cluster][row][col];
						} else if (col < clusters[cluster][row].length - 1){
							//System.out.println("col < clusters[cluster][row].length - 1");
							//System.out.println("Cluster: " + cluster + ", row: " + row + ", column: " + col + ". This: " + clusters[cluster][row][col] + "decodeParent(row): " + decodeParent(row) + "decodeParent(col): " + decodeParent(col));
							//Compound behaviour followed by compound behaviour
							followings[cluster][decodeParent(row)][decodeParent(col)] += clusters[cluster][row][col];
						} else {
							//System.out.println("else");
							//Final column
							followings[cluster][decodeParent(row)][14] += clusters[cluster][row][col];
						}
					}
				}
			}
		}
		
		// Calculate the correct values for containings
		for(int cluster = 0; cluster < concurrent.length; cluster++) {
			for(int parent = 0; parent < concurrent[cluster].length; parent++) {
				for(int conc = 0; conc < concurrent[cluster][parent].length; conc++) {
					if(concurrent[cluster][parent][conc] != 0 && totals[cluster][parent] != 0) {
						containings[cluster][parent][conc] = (float) concurrent[cluster][parent][conc]/ (float) totals[cluster][parent];
					}
				}
			}
		}
		
		// Print out the lists of totals.
		/*for(int cluster = 0; cluster < totals.length; cluster++) {
			System.out.print("Cluster " + cluster + "\n[");
			for(int i = 0; i < totals[cluster].length; i++) {
				System.out.print(totals[cluster][i] + ", ");
			}
			System.out.println("]");
		}*/
		
		for(int cluster = 0; cluster < clusters.length; cluster++) {
			
			// Print arrays for debugging purposes
			System.out.print("Cluster " + cluster + "\nTotals: [");
			for(int i = 0; i < totals[cluster].length; i++) {
				System.out.print(totals[cluster][i] + ", ");
			}
			System.out.println("]");
			
			System.out.print("\nFollowing Behaviours:\n[");
			for(int row = 0; row < followings[cluster].length; row++) {
				System.out.print("[");
				for(int col = 0; col < followings[cluster][row].length; col++) {
					System.out.print(followings[cluster][row][col] + ", ");
				}
				System.out.println("]");
			}
			System.out.println("]");
			
			System.out.print("\nConcurrent Behaviours:\n[");
			for(int parent = 0; parent < containings[cluster].length; parent++) {
				System.out.print("[");
				for(int conc = 0; conc < containings[cluster][parent].length; conc++) {
					System.out.print(containings[cluster][parent][conc] + ", ");
				}
				System.out.println("]");
			}
			System.out.println("]");
			
			// The graph creator will create the JGraph from the analysed data.
			GraphCreator gc = new GraphCreator(totals[cluster], precedings[cluster], containings[cluster], followings[cluster], totals[cluster], 0);
			Graph<BehaviourVertex, BehaviourEdge> g = gc.getGraph();
			
			// The graph visualiser will format the graph to be displayed to the user.
			GraphVisualiser applet = new GraphVisualiser(g, cluster);
	        applet.init();
	        //applet.setVisible(false);
	        
	        // Put the formatted graph in a frame and show it.
	        GraphWindow frame = new GraphWindow(applet);
	        frame.getContentPane().add(applet);
	        frame.setTitle("Graph for coach's behaviour");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.pack();
	        frame.setVisible(true);
	        frame.setLocationRelativeTo(null);
		}
	}
	
	private int decodeParent(int codedBehaviour) {
		//System.out.println("Decoding parent of: " + codedBehaviour);
		int compoundBehaviour;
		if(compoundMappings.containsKey(codedBehaviour)) {
			compoundBehaviour = compoundMappings.get(codedBehaviour);
		} else {
			System.out.println("compoundMappings does not contain this key: " + codedBehaviour);
			compoundBehaviour = 0;
		}
		
		return compoundBehaviour/16;
	}
	
	private int decodeConcurrent(int codedBehaviour) {
		//System.out.println("Decoding concurrent of: " + codedBehaviour);
		int compoundBehaviour;
		if(compoundMappings.containsKey(codedBehaviour)) {
			compoundBehaviour = compoundMappings.get(codedBehaviour);
		} else {
			System.out.println("compoundMappings does not contain this key: " + codedBehaviour);
			compoundBehaviour = 0;
		}
		
		return compoundBehaviour%16;
	}
}
