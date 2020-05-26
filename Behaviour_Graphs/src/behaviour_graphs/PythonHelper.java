package behaviour_graphs;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class PythonHelper {
	ArrayList<ArrayList<ArrayList<Float>>> following;
	ArrayList<ArrayList<Integer>> sequences;
	
	public PythonHelper() {
		//System.out.println("Test1");
		//FileReader fr = new FileReader("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\Squash Coaches\\SPSS\\Observation Study\\Behaviours.csv");
		FileReader fr = new FileReader("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\Stroke Physiotherapists\\Data\\Behaviours.csv");
		
		ArrayList<ArrayList<String>> squashData = fr.getAllSquashData();
		sequences = new ArrayList<ArrayList<Integer>>(squashData.size());
		following = new ArrayList<ArrayList<ArrayList<Float>>>(squashData.size());
		
		// Get following data line by line.
		for(int i = 0; i < squashData.size(); i++) {
			sequences.add(getIntegerList(squashData.get(i)));
			/*System.out.println(squashData.get(i));
			System.out.print("[");
			for (int j: sequences[i]) {
				System.out.print(j + ", ");
			}
			System.out.println("]");*/
			
			// The data counter will count all the data that was in the file, storing it in another ArrayList.
			DataCounter dc = new DataCounter(squashData);
			
			ArrayList<HashMap<String, ArrayList<Integer>>> countedData = new ArrayList<HashMap<String, ArrayList<Integer>>>();
			
			
			
			countedData = dc.getSequenceData(i);
			/*System.out.println("Size: " + countedData.size());
			System.out.println(countedData.get(0));
			System.out.println(countedData.get(1));*/
			
			DataAnalyser da = new DataAnalyser(countedData);
			
			// Add a column at the beginned for "Start" and a row at the end for "End"
			float[][] thisFollowingArr = da.getFollowing();
			ArrayList<ArrayList<Float>> thisFollowingList = new ArrayList<ArrayList<Float>>();
			for(int j = 0; j < thisFollowingArr.length; j++) {
				ArrayList<Float> tempFollowingList = new ArrayList<Float>();
				for(int k = 0; k < thisFollowingArr[j].length; k++) {
					tempFollowingList.add(thisFollowingArr[j][k]);
				}
				thisFollowingList.add(tempFollowingList);
			}
			
			ArrayList<ArrayList<Float>> actualFollowing = new ArrayList<ArrayList<Float>>(16);
			//System.out.println("Rows: " + thisFollowing.length + ". Columns: " + thisFollowing[0].length);
			for (int row = 0; row < 16; row++) {
				ArrayList<Float> tempActual = new ArrayList<Float>(16);
				for (int col = 0; col < 16; col++) {
					if(row == 15 || col == 0) {
						tempActual.add((float) 0);
					} else {
						tempActual.add(thisFollowingList.get(row).get(col - 1));
					}
				}
				actualFollowing.add(tempActual);
				
			}
			following.add(actualFollowing);
			
			/*System.out.print("following["+i+"]: [");
			for (float[] arr: following[i]) {
				System.out.print("\n[");
				for (float f: arr) {
					System.out.print(f + ", ");
				}
				System.out.print("]");
			}
			System.out.println("]");*/
		}
		
		
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
	
	public ArrayList<ArrayList<ArrayList<Float>>> getFollowing(){
		return following;
	}
	
	public ArrayList<ArrayList<Integer>> getSequences(){
		// Return an array of the sequence of each segment of each session.
		return sequences;
	}
	
	public ArrayList<Float> testGet() {
		ArrayList<Float> returnList = new ArrayList<Float>(3);
		returnList.add((float) 0);
		returnList.add((float) 1);
		returnList.add((float) 2);
		return returnList;
	}
}
