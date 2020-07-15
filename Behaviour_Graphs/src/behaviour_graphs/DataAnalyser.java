package behaviour_graphs;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Martin
 * This class will analyse the given counted data and produce percentages of
 * which behaviours precede, follow and are contained within others.
 */
public class DataAnalyser {

	private int[] totals;
	private float[][] preceding;
	private float[][] containing;
	private float [][] following;
	
	int[] totalOccs; // Total behaviour count including concurrent behaviours.
	
	/**
	 * Constructor
	 * @param data - the counted data to be analysed.
	 */
	public DataAnalyser(ArrayList<HashMap<String, ArrayList<Integer>>> data) {
		// System.out.println("data.size() = " + data.size());
		// System.out.println(data);
		totals = new int[15];
		preceding = new float[15][15];
		containing = new float[15][15];
		following = new float[15][15];
		totalOccs = new int[15];
		
		//Total behaviours
		for(int i = 0; i<=14; i++) {
			//System.out.println("data: " + data);
			//System.out.println("data.get(" + i + "): " + data.get(i));
			//System.out.println("data.get(" + i + ").get(" + i + "+\"occ\"): " + data.get(i).get(i+"occ"));
			//System.out.println("data.get(" + i + ").get(" + i + "+\"occ\").get(0): " + data.get(i).get(i+"occ").get(0));
			totals[i] = data.get(i).get(i+"occ").get(0);
			totalOccs[i]+=data.get(i).get(i+"occ").get(0);
			for (int j = 0; j <= 14; j++) {
				totalOccs[j]+=data.get(i).get(i+"cont").get(j);
			}
		}
		
		
		//Percentage of previous', followings and containings
		for(int i = 0; i<=14; i++) {
			int occurrences = totals[i];
			
			for(int j = 0; j<=14; j++) {
				float percentageP = 0;
				float percentageC = 0;
				float percentageF = 0;
				if(occurrences > 0){
					percentageP = (float) data.get(i).get(i+"pre").get(j) / (float) occurrences;
					percentageC = (float) data.get(i).get(i+"cont").get(j) / (float) occurrences;
					percentageF = (float) data.get(i).get(i+"foll").get(j) / (float) occurrences;
				}
				preceding[i][j] = percentageP;
				containing[i][j] = percentageC;
				following[i][j] = percentageF;
			}
		}
	}
	
	/**
	 * Getter method.
	 * @return the total occurrences of each behaviour.
	 */
	public int[] getTotals() {
		return totals;
	}
	
	/**
	 * Getter method.
	 * @return the results of calculating the percentage of which behaviours preceded which other behaviours.
	 */
	public float[][] getPreceding() {
		return preceding;
	}
	
	/**
	 * Getter method.
	 * @return the results of calculating the percentage of which behaviours
	 * happened concurrently with which other behaviours.
	 */
	public float[][] getContaining(){
		return containing;
	}
	
	/**
	 * Getter method.
	 * @return the results of calculating the percentage of which behaviours
	 * followed which other behaviours.
	 */
	public float[][] getFollowing(){
		return following;
	}
	
	/**
	 * Getter method.
	 * @return total ccurrences of each behaviour including concurrent behaviours.
	 */
	public int[] getTotalOccs() {
		return totalOccs;
	}
}
