package behaviour_graphs;

import java.util.List;

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
	
	public PythonHelper() {
		//System.out.println("Test1");
		FileReader frSquash = new FileReader("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\Squash Coaches\\SPSS\\Observation Study\\Behaviours.csv");
		FileReader frPhysio = new FileReader("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\Stroke Physiotherapists\\Data\\Behaviours.csv");
		
		ArrayList<ArrayList<String>> squashData = frSquash.getAllSquashData();
		ArrayList<ArrayList<String>> physioData = frPhysio.getAllSquashData();
		
		ArrayList<ArrayList<String>> allData = new ArrayList<ArrayList<String>>(squashData.size() + physioData.size());
		allData.addAll(squashData);
		allData.addAll(physioData);
		
		sequences = constructSequenceList(allData);
		following = constructFollowingList(allData);
		
		squashSequences = constructSequenceList(squashData);
		squashFollowing = constructFollowingList(squashData);
		
		physioSequences = constructSequenceList(physioData);
		physioFollowing = constructFollowingList(physioData);
		
		squashMatrix = constructMatrix(squashData);
		physioMatrix = constructMatrix(physioData);
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
	
	private ArrayList<ArrayList<Integer>> constructSequenceList(ArrayList<ArrayList<String>> data){
		ArrayList<ArrayList<Integer>> sequenceList = new ArrayList<ArrayList<Integer>>(data.size());
		
		// Add each line of data to the sequenceList.
		for(int i = 0; i < data.size(); i++) {
			sequenceList.add(getIntegerList(data.get(i)));
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
		
		// Add a column at the beginning for "Start" and a row at the end for "End"
		float[][] thisFollowingArr = da.getFollowing();
		int[] thisOccurrences = da.getTotals();
		int totalBehaviours = 0;
		for(int thisBehaviour : thisOccurrences) {
			totalBehaviours += thisBehaviour;
		}
		
		ArrayList<ArrayList<Float>> thisFollowingList = new ArrayList<ArrayList<Float>>();
		for(int j = 0; j < thisFollowingArr.length; j++) {
			ArrayList<Float> tempFollowingList = new ArrayList<Float>();
			for(int k = 0; k < thisFollowingArr[j].length; k++) {
				tempFollowingList.add(thisFollowingArr[j][k]);
			}
			thisFollowingList.add(tempFollowingList);
		}
		
		ArrayList<ArrayList<Float>> actualFollowing = new ArrayList<ArrayList<Float>>(16);
		for (int row = 0; row < 16; row++) {
			ArrayList<Float> tempActual = new ArrayList<Float>(16);
			for (int col = 0; col < 16; col++) {
				if(col == 0) {
					if(row != 0 && row != 15) {
						tempActual.add((float) ((float) thisOccurrences[row] / (float) totalBehaviours)); // Use the first column to store the occurrences of this behaviour since otherwise it will always be 0.
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
	
	public ArrayList<ArrayList<ArrayList<Float>>> getFollowing(){
		return following;
	}
	
	public ArrayList<ArrayList<Integer>> getSequences(){
		// Return an array of the sequence of each segment of each session.
		return sequences;
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
	
	public ArrayList<Float> testGet() {
		ArrayList<Float> returnList = new ArrayList<Float>(3);
		returnList.add((float) 0);
		returnList.add((float) 1);
		returnList.add((float) 2);
		return returnList;
	}
	
	public void createGraph(float[][][] clusters) {
		System.out.println(clusters);
		
		// Set the totals to be the first column of the given data.
		int[][] totals = new int[clusters.length][15];
		
		// Set the containings and precedings arrays to be all 0s.
		float[][][] containings = new float[clusters.length][15][15];
		float[][][] precedings = new float[clusters.length][15][15];
		
		for(int cluster = 0; cluster < clusters.length; cluster++) {
			for(int row = 0; row < clusters[cluster].length; row++) {
				for(int col = 0; col < clusters[cluster][row].length; col++) {
					if(col == 0) {
						totals[cluster][row] = Math.round(clusters[cluster][row][col] *100);
						clusters[cluster][row][col] = 0.00f;
					}
					containings[cluster][row][col] = 0.00f;
					precedings[cluster][row][col] = 0.00f;
				}
			}
		}
		
		// Print out the lists of totals.
		for(int cluster = 0; cluster < totals.length; cluster++) {
			System.out.print("Cluster " + cluster + "\n[");
			for(int i = 0; i < totals[cluster].length; i++) {
				System.out.print(totals[cluster][i] + ", ");
			}
			System.out.println("]");
		}
		
		// Set followings to be the cluster array with the first column of each cluster replaced with 0s.
		//float[][][] followings = (float[][][]) clusters.toArray();
		
		for(int cluster = 0; cluster < clusters.length; cluster++) {
			// The graph creator will create the JGraph from the analysed data.
			GraphCreator gc = new GraphCreator(totals[cluster], precedings[cluster], containings[cluster], clusters[cluster], totals[cluster], 0);
			Graph<BehaviourVertex, BehaviourEdge> g = gc.getGraph();
			
			// The graph visualiser will format the graph to be displayed to the user.
			GraphVisualiser applet = new GraphVisualiser(g);
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
}
