package behaviour_graphs;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Martin
 * The format used to store the behaviours of coaches during counting.
 */
public class DataFormat {
	int coach;
	int session;
	int segment;
	
	/*
	 * The data will be stored as an ArrayList of occurrences of behaviour,
	 * previous behaviours and containing behaviours for each behaviour.
	 * Example visualisation of data:
	 * [{"1occ":[5], "1pre":[1,0,2,0,0,0,0,1,0,0,0,0,1,0,0], "1cont":[0,0,0,0,0,0,0,0,2,1,0,0,0,0,0]}, {"2occ":[7], ...}, ...]
	 */
	ArrayList<HashMap<String, ArrayList<Integer>>> data;
	/*ArrayList<*/ArrayList<HashMap<String, ArrayList<Integer>>>/*>*/ behaviourData;
	
	public DataFormat(int coach, int session, int segment, ArrayList<HashMap<String, ArrayList<Integer>>> data) {
		this.coach = coach;
		this.session = session;
		this.segment = segment;
		this.data = data;
	}
	
	public DataFormat(int coach, int session, int segment, ArrayList<HashMap<String, ArrayList<Integer>>> data, /*ArrayList<*/ArrayList<HashMap<String, ArrayList<Integer>>>/*>*/ behaviourData) {
		this.coach = coach;
		this.session = session;
		this.segment = segment;
		this.data = data;
		this.behaviourData = behaviourData;
	}
	
	public String toString() {
		return "Coach: " + coach + ", Session: " + session + ", Segment: " + segment + ".\n" + data;
	}
}
