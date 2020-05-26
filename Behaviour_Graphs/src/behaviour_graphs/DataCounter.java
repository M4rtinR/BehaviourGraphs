package behaviour_graphs;

import java.util.ArrayList;
import java.util.HashMap; // import the HashMap class

/**
 * @author Martin
 * This class is responsible for counting the number of occurrences of each behaviour,
 * and which behaviours precede and are contained within them.
 */
public class DataCounter {

	ArrayList<ArrayList<String>> data; // The given data
	ArrayList<DataFormat> countedData; // The final counted data
	ArrayList<DataFormat> concurrentCountedData; // The counted data for concurrent behaviours
	//int[] totals; // Total behaviour count including concurrent behaviours.

	/**
	 * Constructor
	 * 
	 * @param data - the given data to be counted.
	 */
	public DataCounter(ArrayList<ArrayList<String>> data) {
		this.data = data;
		this.countedData = new ArrayList<DataFormat>();
		//totals = new int[15];

		for (ArrayList<String> segment : data) {
			// For each line of data, the first 3 values will be the coach ID number,
			// session ID number and segment of session.
			int coachNum = Integer.valueOf(segment.get(0));
			int sessionNum = Integer.valueOf(segment.get(1));
			int segmentNum = Integer.valueOf(segment.get(2));

			// Count the current line of data and store it using the DataFormat class.
			/*DataFormat segmentData = new DataFormat(coachNum, sessionNum, segmentNum,
					countDataForSegment(segment, coachNum, sessionNum, segmentNum));*/
			DataFormat segmentData = countDataForSegment(segment, coachNum, sessionNum, segmentNum);

			countedData.add(segmentData);
		}
	}

	/**
	 * countDataForSegment Method to count occurrences, preceding and concurrent
	 * behaviours in a given line of data.
	 * 
	 * @param segment   - the line of data to be counted.
	 * @param coachNo   - the coach ID number which the line of data relates to.
	 * @param sessionNo - the session ID number which the line of data relates to.
	 * @param segmentNo - the segment of the session which the line of data relates
	 *                  to.
	 * @return an ArrayList of counted data from the given segment.
	 */
	private DataFormat countDataForSegment(ArrayList<String> segment, int coachNo,
			int sessionNo, int segmentNo) {

		//System.out.println("\nCounting new segment.");
		
		// Temporary variables used in counting process.
		int behaviourNum;
		int previous = 0;
		ArrayList<Integer> temp2;
		ArrayList<Integer> temp3;
		ArrayList<Integer> temp4;
		ArrayList<Integer> tempOcc = new ArrayList<Integer>();
		ArrayList<Integer> tempPre = new ArrayList<Integer>();
		ArrayList<Integer> tempFoll = new ArrayList<Integer>();
		boolean concurrentBehaviours = false;

		/*
		 * The list of counted data to be returned. Stored as occurrences of behaviour,
		 * previous behaviours and containing behaviours for each behaviour.
		 * Example visualisation of data:
		 * [{"1occ":[5], "1pre":[1,0,2,0,0,0,0,1,0,0,0,0,1,0,0], "1cont":[0,0,0,0,0,0,0,0,2,1,0,0,0,0,0]}, {"2occ":[7], ...}, ...]
		 */
		ArrayList<HashMap<String, ArrayList<Integer>>> returnList = new ArrayList<HashMap<String, ArrayList<Integer>>>();
		
		/*ArrayList<*/ArrayList<HashMap<String, ArrayList<Integer>>>/*>*/ concurrentList = new /*ArrayList<*/ArrayList<HashMap<String, ArrayList<Integer>>>/*>*/();
		HashMap<String, ArrayList<Integer>> tempMap = new HashMap<String, ArrayList<Integer>>();
		for (int i = 0; i <= 14; i++) {
			concurrentList.add(tempMap);
			tempOcc.add(0);
			tempPre.add(0);
			tempFoll.add(0);
		}

		// Initialise the occurrences ArrayList
		ArrayList<ArrayList<Integer>> occurrences = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp;
		for (int i = 0; i <= 14; i++) {
			temp = new ArrayList<Integer>();
			temp.add(0);
			occurrences.add(temp);
		}
		
		ArrayList<ArrayList<Integer>> concurrentOccurrences;
		ArrayList<ArrayList<ArrayList<Integer>>> overallConcOcc = new ArrayList<ArrayList<ArrayList<Integer>>>();
		//ArrayList<ArrayList<Integer>> thisTemp = new ArrayList<ArrayList<Integer>>();
		//ArrayList<Integer> thisTemp2 = new ArrayList<Integer>();
		for (int i = 0; i <= 14; i++) {
			concurrentOccurrences = new ArrayList<ArrayList<Integer>>();
			for (int j = 0; j <= 14; j++) {
				
				
					ArrayList<Integer> testMiddle = new ArrayList<Integer>();
					testMiddle.add(0);
					
				concurrentOccurrences.add(testMiddle);
			}
			overallConcOcc.add(concurrentOccurrences);
		}
		
		//System.out.println(overallConcOcc);

		// Initialise the preceding ArrayList
		ArrayList<ArrayList<Integer>> preceding = new ArrayList<ArrayList<Integer>>(14);
		for (int i = 0; i <= 14; i++) {
			temp = new ArrayList<Integer>();
			for (int j = 0; j <= 14; j++) {
				temp.add(0);
			}
			preceding.add(temp);
		}
		
		ArrayList<ArrayList<Integer>> concurrentPreceding = new ArrayList<ArrayList<Integer>>(14);
		ArrayList<ArrayList<ArrayList<Integer>>> overallConcPre = new ArrayList<ArrayList<ArrayList<Integer>>>(14);
		for (int i = 0; i <= 14; i++) {
			concurrentPreceding = new ArrayList<ArrayList<Integer>>();
			for (int j = 0; j <= 14; j++) {
				ArrayList<Integer> testMiddle = new ArrayList<Integer>();
				for(int k = 0; k <= 14; k++) {
					
					testMiddle.add(0);
				}
				concurrentPreceding.add(testMiddle);
			}
			overallConcPre.add(concurrentPreceding);
		}
		//System.out.println("overallConcPre (Start) = " + overallConcPre);

		// Initialise the containing ArrayList
		ArrayList<ArrayList<Integer>> containing = new ArrayList<ArrayList<Integer>>(14);
		for (int i = 0; i <= 14; i++) {
			temp = new ArrayList<Integer>();
			for (int j = 0; j <= 14; j++) {
				temp.add(0);
			}
			containing.add(temp);
		}
		
		// Initialise the following ArrayList
		ArrayList<ArrayList<Integer>> following = new ArrayList<ArrayList<Integer>>(14);
		for (int i = 0; i <= 14; i++) {
			temp = new ArrayList<Integer>();
			for (int j = 0; j <= 14; j++) {
				temp.add(0);
			}
			following.add(temp);
		}
		
		ArrayList<ArrayList<Integer>> concurrentFollowing = new ArrayList<ArrayList<Integer>>(14);
		ArrayList<ArrayList<ArrayList<Integer>>> overallConcFoll = new ArrayList<ArrayList<ArrayList<Integer>>>(14);
		for (int i = 0; i <= 14; i++) {
			concurrentFollowing = new ArrayList<ArrayList<Integer>>();
			for (int j = 0; j <= 14; j++) {
				ArrayList<Integer> testMiddle = new ArrayList<Integer>();
				for(int k = 0; k <= 14; k++) {
					
					testMiddle.add(0);
				}
				concurrentFollowing.add(testMiddle);
			}
			overallConcFoll.add(concurrentFollowing);
		}
		
		// Check we have the correct data.
		if (Integer.valueOf(segment.get(0)) == coachNo && Integer.valueOf(segment.get(1)) == sessionNo
				&& Integer.valueOf(segment.get(2)) == segmentNo) {

			int count = 0;
			int length = segment.size();
			HashMap<String, ArrayList<Integer>> concurrentToAdd;
			
			for (String behaviour : segment) { // For each piece of data in the line.
				if (count >= 4) { // The first 4 strings contain information such as coachID so skip them.
					// Check whether we have just one behaviour or concurrent behaviours
					// (concurrent behaviours will always have 3 or more characters e.g. 1~8)
					if (behaviour.length() < 3) { // single behaviour

						// Add one to occurrences of behaviour
						behaviourNum = Integer.valueOf(behaviour);
						temp2 = occurrences.get(behaviourNum);
						temp2.set(0, occurrences.get(behaviourNum).get(0) + 1);
						occurrences.set(behaviourNum, temp2);
						// If this is the first behaviour in the segment, add 1 to occurrences of 0 (start) as well.
						if (previous == 0) {
							temp2 = occurrences.get(0);
							temp2.set(0, occurrences.get(0).get(0) + 1);
							occurrences.set(0, temp2);
							//System.out.println("\n\n\nJust added 1 to " + (behaviourNum-1) + " following 0.\n\n");
						}
						
						//totals[behaviourNum]+=1;

						// Add one to the behaviour preceding the current one
						temp3 = preceding.get(behaviourNum);
						//System.out.println(temp3);
						temp3.set(previous, preceding.get(behaviourNum).get(previous) + 1);
						preceding.set(behaviourNum, temp3);
						
						// Add one to the behaviour following the previous one
						temp3 = following.get(previous);
						//System.out.println(temp3);
						temp3.set(behaviourNum-1,  following.get(previous).get(behaviourNum-1) + 1);
						following.set(previous, temp3);
						// Deal with the last behaviour of this segment
						if (count == length-1) {
							temp3 = following.get(behaviourNum);
							temp3.set(14,  following.get(behaviourNum).get(14) + 1);
							following.set(behaviourNum, temp3);
						}
						
						
						
						// Set this behaviour as previous for the next time round the loop.
						previous = behaviourNum;

					} else { // multiple behaviours together
						concurrentBehaviours = true;

						// Split string on ~
						String[] behaviourArr = behaviour.split("~");
						int encompassingNum = Integer.valueOf(behaviourArr[0]);

						// Deal with number
						temp2 = occurrences.get(encompassingNum);
						temp2.set(0, occurrences.get(encompassingNum).get(0) + 1);
						occurrences.set(encompassingNum, temp2);
						// If this is the first behaviour in the segment, add 1 to occurrences of 0 (start) as well.
						if (previous == 0) {
							temp2 = occurrences.get(0);
							temp2.set(0, occurrences.get(0).get(0) + 1);
							occurrences.set(0, temp2);
							//System.out.println("\n\n\nJust added 1 to " + (encompassingNum-1) + " following 0.\n\n");
						}
						
						//totals[encompassingNum]+=1;

						temp3 = preceding.get(encompassingNum);
						temp3.set(previous, preceding.get(encompassingNum).get(previous) + 1);
						preceding.set(encompassingNum, temp3);
						
						// Add one to the behaviour following the previous one
						temp3 = following.get(previous);
						temp3.set(encompassingNum-1,  following.get(previous).get(encompassingNum-1) + 1);
						following.set(previous, temp3);
						// Deal with the last behaviour of this segment
						if (count == length-1) {
							temp3 = following.get(encompassingNum);
							temp3.set(14,  following.get(encompassingNum).get(14) + 1);
							following.set(encompassingNum, temp3);
						}
						
						if (previous == 0) {
							//System.out.println("\n\n\nJust added 1 to " + (encompassingNum-1) + " following 0. Multiple behaviours.\n\n");
						}

						temp4 = containing.get(encompassingNum);
						//System.out.println(overallConcOcc);
						concurrentOccurrences = overallConcOcc.get(encompassingNum); //occ
						//System.out.println(overallConcOcc);
						concurrentPreceding = overallConcPre.get(encompassingNum); //preceding
						concurrentFollowing = overallConcFoll.get(encompassingNum); //following
						int previousCont = 0;

						//System.out.println("encompassingNum = " + encompassingNum);
						// Additional Behaviours
						for (int b = 1; b < behaviourArr.length; b++) {
							int number = Integer.valueOf(behaviourArr[b]);
							//System.out.println("number = " + number);

							temp4.set(number, temp4.get(number) + 1);
							
							//if (!(concurrentList.get(encompassingNum).get(number + "occ") == null)) {
							tempOcc = concurrentOccurrences.get(number);
								//tempOcc = concurrentList.get(encompassingNum).get(number + "occ");
							//System.out.println("before add 1 in temp: " + overallConcOcc);
							tempOcc.set(0, concurrentOccurrences.get(number).get(0) + 1);
							//System.out.println("after add 1 in temp: " + overallConcOcc);
								//tempOcc.set(0, concurrentList.get(encompassingNum).get(number + "occ").get(0) + 1);
							//}
							//System.out.println("before set concurrentOccurrences: " + overallConcOcc);
							concurrentOccurrences.set(number, tempOcc);
							//System.out.println("after set concurrentOccurences: " + overallConcOcc);
							
							//overallConcOcc.set(number, tempOcc);
							if (previousCont == 0) {
								tempOcc = concurrentOccurrences.get(0);
								//tempOcc = concurrentList.get(encompassingNum).get(0 + "occ");
								tempOcc.set(0, concurrentOccurrences.get(0).get(0) + 1);
								//tempOcc.set(0, concurrentList.get(encompassingNum).get(0 + "occ").get(0) + 1);
								//System.out.println("before set concurrentOccurrences: " + overallConcOcc);
								concurrentOccurrences.set(0, tempOcc);
								//System.out.println("after set concurrentOccurences: " + overallConcOcc);
							}
							//System.out.println("after if: " + overallConcOcc);
							//System.out.println("concurrentPreceding = " + concurrentPreceding);
							tempPre = concurrentPreceding.get(number);
							//System.out.println("tempPre = " + tempPre);
							tempPre.set(previousCont, concurrentPreceding.get(number).get(previousCont) + 1);
							concurrentPreceding.set(number, tempPre);
							//System.out.println("concurrentPreceding = " + concurrentPreceding);
							
							tempFoll = concurrentFollowing.get(previousCont);
							tempFoll.set(number-1, concurrentFollowing.get(previousCont).get(number-1) + 1); // number-1 here because we don't include start in this array.
							concurrentFollowing.set(previousCont, tempFoll);
							//System.out.println("b = " + b + ", behaviourArr.length -1 = " + (behaviourArr.length - 1) + ", number = " + number);
							if (b == (behaviourArr.length -1)) {
								//System.out.println("\n\n\n\nLast behaviour.\n\n\n\n");
								tempFoll = concurrentFollowing.get(number);
								//System.out.println("BEFORE tempFoll = " + tempFoll);
								tempFoll.set(14, concurrentFollowing.get(number).get(14) + 1);
								//System.out.println("AFTER tempFoll = " + tempFoll);
								concurrentFollowing.set(number, tempFoll);
								//System.out.println("concurrentFollowing = " + concurrentFollowing);
							}
							//System.out.println("end of loop: " + overallConcOcc);
							previousCont = number;
							//totals[number]+=1;
						}
						
						/*concurrentToAdd = new HashMap<String, ArrayList<Integer>>();
						for (int j = 0; j <= 14; j++) {
							
							String occName = j + "occ";
							concurrentToAdd.put(occName, concurrentOccurrences.get(j));
							String preName = j + "pre";
							concurrentToAdd.put(preName, concurrentPreceding.get(j));
							String follName = j + "foll";
							concurrentToAdd.put(follName, concurrentFollowing.get(j));
							
						}*/
						//System.out.println("overallConcOcc: " + overallConcOcc);
						//System.out.println("encompassingBehaviour = " + encompassingNum);
						//System.out.print("behaviourArray = [");
						//for (String b:behaviourArr) {
						//	System.out.print(b + "], [");
						//}
						//System.out.println("]");
						//System.out.println("concurrentOccurrences = " + concurrentOccurrences);
						overallConcOcc.set(encompassingNum, concurrentOccurrences);
						//System.out.println("overallConcOcc: " + overallConcOcc + "\n");
						
						//concurrentList.set(encompassingNum, concurrentToAdd);
						
						//overallConcOcc.set(encompassingNum,  concurrentOccurrences);
						overallConcPre.set(encompassingNum,  concurrentPreceding);
						//System.out.println("overallConcPre = " + overallConcPre);
						overallConcFoll.set(encompassingNum,  concurrentFollowing);
						//System.out.println("overallConcFoll.get(" + encompassingNum + "): " + overallConcFoll.get(encompassingNum));

						containing.set(encompassingNum, temp4);
						previous = encompassingNum;
					}

				}

				count++;

			}
		} else {
			System.err.println("Incorrect segment provided to counter method. coachNo: " + coachNo + ", coach: "
					+ segment.get(0) + ", sessionNo: " + sessionNo + ", session: " + segment.get(1) + ", segmentNo: "
					+ segmentNo + ", segment: " + segment.get(2));
		}

		// Add the counted data to the list to be returned.
		HashMap<String, ArrayList<Integer>> toAdd;

		for (int i = 0; i <= 14; i++) {
			toAdd = new HashMap<String, ArrayList<Integer>>();

			String occName = i + "occ";
			toAdd.put(occName, occurrences.get(i));
			String preName = i + "pre";
			toAdd.put(preName, preceding.get(i));
			String contName = i + "cont";
			toAdd.put(contName, containing.get(i));
			String follName = i + "foll";
			toAdd.put(follName, following.get(i));

			returnList.add(i, toAdd);
			
			//ArrayList<HashMap<String, ArrayList<Integer>>> tempConcList = new ArrayList<HashMap<String, ArrayList<Integer>>>();
			HashMap<String, ArrayList<Integer>> toAddConcurrent = new HashMap<String, ArrayList<Integer>>();
			for (int j = 0; j <= 14; j++) {
				//toAddConcurrent = new HashMap<String, ArrayList<Integer>>();
				
				occName = j + "occ";
				toAddConcurrent.put(occName, overallConcOcc.get(i).get(j));
				preName = j + "pre";
				toAddConcurrent.put(preName, overallConcPre.get(i).get(j));
				follName = j + "foll";
				toAddConcurrent.put(follName, overallConcFoll.get(i).get(j));
				
				//System.out.println("j = " + j + ", toAddConcurrent = " + toAddConcurrent);
				
				//tempConcList.add(j, toAddConcurrent);
				//concurrentList.add(i, toAddConcurrent);
			}
			concurrentList.set(i, toAddConcurrent);
			//System.out.println("In loop " + i + "; " + concurrentList);
		}
		//System.out.println("Out of loop, final list: " + concurrentList);
		//System.out.println(returnList);
		
		DataFormat segmentData;
		
		if (!concurrentBehaviours){
			segmentData = new DataFormat(coachNo, sessionNo, segmentNo, returnList);
		} else {
			// Add the counted data to the list to be returned.
			/*HashMap<String, ArrayList<Integer>> concurrentToAdd;
			
			for (int i = 0; i <= 14; i++) {
				concurrentToAdd = new HashMap<String, ArrayList<Integer>>();
				for (int j = 0; j <= 14; j++) {
					
	
					String occName = j + "occ";
					concurrentToAdd.put(occName, overallConcOcc.get(j));
					String preName = j + "pre";
					concurrentToAdd.put(preName, overallConcPre.get(j));
					String follName = j + "foll";
					concurrentToAdd.put(follName, overallConcFoll.get(j));
	
					
				}
				concurrentList.add(i, concurrentToAdd);
			}
			System.out.println(returnList);*/
			segmentData = new DataFormat(coachNo, sessionNo, segmentNo, returnList, concurrentList);
			//System.out.println("Added to segmentData.");
		}
		
		//System.out.println("Returning");
		return segmentData;
	}

	/**
	 * getDataForCoach Getter method to retrieve counted data for a specific coach.
	 * 
	 * @param coach - the coach ID of the desired coach.
	 * @return the counted data for the desired coach.
	 */
	public ArrayList<HashMap<String, ArrayList<Integer>>> getDataForCoach(int coach) {
		ArrayList<HashMap<String, ArrayList<Integer>>> returnData = new ArrayList<HashMap<String, ArrayList<Integer>>>();
		ArrayList<DataFormat> toBeAdded = new ArrayList<DataFormat>();

		for (DataFormat countedSegment : countedData) {
			if (countedSegment.coach == coach) {
				toBeAdded.add(countedSegment);
			}
		}

		returnData = addUpData(toBeAdded);
		return returnData;
	}

	/**
	 * getDataForSession Getter method to retrieve counted data for a specific
	 * coaching session.
	 * 
	 * @param coach   - the coach ID of the coach who ran the desired session.
	 * @param session - the session ID of the desired session.
	 * @return the counted data for the desired session.
	 */
	public ArrayList<HashMap<String, ArrayList<Integer>>> getDataForSession(int coach, int session) {
		ArrayList<HashMap<String, ArrayList<Integer>>> returnData = new ArrayList<HashMap<String, ArrayList<Integer>>>();
		ArrayList<DataFormat> toBeAdded = new ArrayList<DataFormat>();

		for (DataFormat countedSegment : countedData) {
			if (countedSegment.coach == coach && countedSegment.session == session) {
				toBeAdded.add(countedSegment);
			}
		}

		returnData = addUpData(toBeAdded);
		return returnData;
	}

	/**
	 * getDataForSegment Getter method to retrieve counted data for a specific
	 * segment of all sessions.
	 * 
	 * @param segment - the desired segment ID.
	 * @return the counted data for the desired segment.
	 */
	public ArrayList<HashMap<String, ArrayList<Integer>>> getDataForSegment(int segment) {
		ArrayList<HashMap<String, ArrayList<Integer>>> returnData = new ArrayList<HashMap<String, ArrayList<Integer>>>();
		ArrayList<DataFormat> toBeAdded = new ArrayList<DataFormat>();

		for (DataFormat countedSegment : countedData) {
			if (countedSegment.segment == segment) {
				toBeAdded.add(countedSegment);
			}
		}

		returnData = addUpData(toBeAdded);
		return returnData;
	}

	/**
	 * getDataForCoachAndSegment Getter method to retrieve counted data for a
	 * specific segment of an individual coach's sessions.
	 * 
	 * @param coach   - the coach ID of the desired coach.
	 * @param segment - the desired segment ID for the given coach.
	 * @return the counted data for the desired coach and segment.
	 */
	public ArrayList<HashMap<String, ArrayList<Integer>>> getDataForCoachAndSegment(int coach, int segment) {
		ArrayList<HashMap<String, ArrayList<Integer>>> returnData = new ArrayList<HashMap<String, ArrayList<Integer>>>();
		ArrayList<DataFormat> toBeAdded = new ArrayList<DataFormat>();

		for (DataFormat countedSegment : countedData) {
			if (countedSegment.coach == coach && countedSegment.segment == segment) {
				toBeAdded.add(countedSegment);
			}
		}

		returnData = addUpData(toBeAdded);
		return returnData;
	}

	/**
	 * getDataForSessionAndSegment Getter method to retrieve counted data for a
	 * specific segment of an individual session.
	 * 
	 * @param coach   - the coach ID of the coach who ran the desired session.
	 * @param session - the session ID of the desired session.
	 * @param segment - the desired segment ID for the given session.
	 * @return the counted data for the desired session and segment.
	 */
	public ArrayList<HashMap<String, ArrayList<Integer>>> getDataForSessionAndSegment(int coach, int session,
			int segment) {
		ArrayList<HashMap<String, ArrayList<Integer>>> returnData = new ArrayList<HashMap<String, ArrayList<Integer>>>();
		ArrayList<DataFormat> toBeAdded = new ArrayList<DataFormat>();

		for (DataFormat countedSegment : countedData) {
			if (countedSegment.coach == coach && countedSegment.session == session
					&& countedSegment.segment == segment) {
				toBeAdded.add(countedSegment);
			}
		}

		returnData = addUpData(toBeAdded);
		return returnData;
	}
	
	public ArrayList<HashMap<String, ArrayList<Integer>>> getSequenceData(int lineNo){
		ArrayList<HashMap<String, ArrayList<Integer>>> returnData = new ArrayList<HashMap<String, ArrayList<Integer>>>();
		ArrayList<DataFormat> toBeAdded = new ArrayList<DataFormat>();
		
		toBeAdded.add(countedData.get(lineNo));
		
		returnData = addUpData(toBeAdded);
		return returnData;
	}

	/**
	 * getAllData Getter method to retrieve all counted data.
	 * 
	 * @return the counted for all coaching sessions.
	 */
	public ArrayList<HashMap<String, ArrayList<Integer>>> getAllData() {
		ArrayList<HashMap<String, ArrayList<Integer>>> returnData = new ArrayList<HashMap<String, ArrayList<Integer>>>();

		returnData = addUpData(countedData);
		return returnData;
	}

	/**
	 * addUpData Private method to add up all the data associated with a specific
	 * coach/session/segment which at this point is stored in different instances of
	 * the DataFormat class.
	 * 
	 * @param inputData - an ArrayList of data stored in different instances of the
	 *                  DataFormat class.
	 * @return the combined data. DataFormat is dropped because we no longer need to
	 *         know the coach, session or segment ID.
	 */
	private ArrayList<HashMap<String, ArrayList<Integer>>> addUpData(ArrayList<DataFormat> inputData) {
		// ArrayList to store the cumulative data to be returned.
		ArrayList<HashMap<String, ArrayList<Integer>>> returnData = new ArrayList<HashMap<String, ArrayList<Integer>>>();

		// Initialise individual ArrayLists to store cumulative occurrences, preceding
		// and containing behaviours.
		ArrayList<Integer> countOcc = new ArrayList<Integer>();
		for (int i = 0; i <= 14; i++) {
			countOcc.add(0);
		}

		ArrayList<Integer> temp;
		ArrayList<ArrayList<Integer>> countPre = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i <= 14; i++) {
			temp = new ArrayList<Integer>();
			for (int j = 0; j <= 14; j++) {
				temp.add(0);
			}
			countPre.add(temp);
		}

		ArrayList<ArrayList<Integer>> countCont = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i <= 14; i++) {
			temp = new ArrayList<Integer>();
			for (int j = 0; j <= 14; j++) {
				temp.add(0);
			}
			countCont.add(temp);
		}
		
		ArrayList<ArrayList<Integer>> countFoll = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i <= 14; i++) {
			temp = new ArrayList<Integer>();
			for (int j = 0; j <= 14; j++) {
				temp.add(0);
			}
			countFoll.add(temp);
		}
		
		// For concurrent behaviour graphs
		ArrayList<ArrayList<Integer>> countConcOcc = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i <= 14; i++) {
			temp = new ArrayList<Integer>();
			for (int j = 0; j <= 14; j++) {
				temp.add(0);
			}
			countConcOcc.add(temp);
		}
		
		ArrayList<ArrayList<ArrayList<Integer>>> countConcPre = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<ArrayList<Integer>> temp2;
		for (int k = 0; k <= 14; k++) {
			temp2 = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i <= 14; i++) {
				temp = new ArrayList<Integer>();
				for (int j = 0; j <= 14; j++) {
					temp.add(0);
				}
				temp2.add(temp);
			}
			countConcPre.add(temp2);
		}
		
		ArrayList<ArrayList<ArrayList<Integer>>> countConcFoll = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for (int k = 0; k <= 14; k++) {
			temp2 = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i <= 14; i++) {
				temp = new ArrayList<Integer>();
				for (int j = 0; j <= 14; j++) {
					temp.add(0);
				}
				temp2.add(temp);
			}
			countConcFoll.add(temp2);
		}

		for (DataFormat segmentData : inputData) { // For each line of data

			// Count up occurrences for each behaviour.
			for (int i = 0; i <= 14; i++) {
				countOcc.set(i, countOcc.get(i) + segmentData.data.get(i).get(i + "occ").get(0));
			}

			// Count up the behaviours that precede, are contained within, and follow each behaviour.
			for (int i = 0; i <= 14; i++) {
				//System.out.println("\n\n\n\ni = " + i);
				// Preceding behaviours

				// Initialise ArrayList for current behaviour
				ArrayList<Integer> thisPre = new ArrayList<Integer>();
				for (int j = 0; j <= 14; j++) {
					thisPre.add(0);
				}
				// Count up preceding behaviours.
				for (int j = 0; j <= 14; j++) {
					thisPre.set(j, countPre.get(i).get(j) + segmentData.data.get(i).get(i + "pre").get(j));
				}
				countPre.set(i, thisPre);

				// Concurrent behaviours
				ArrayList<Integer> thisCont = new ArrayList<Integer>();
				for (int j = 0; j <= 14; j++) {
					thisCont.add(0);
				}
				for (int j = 0; j <= 14; j++) {
					thisCont.set(j, countCont.get(i).get(j) + segmentData.data.get(i).get(i + "cont").get(j));
				}
				countCont.set(i, thisCont);
				
				// Following Behaviours
				ArrayList<Integer> thisFoll = new ArrayList<Integer>();
				for (int j = 0; j <= 14; j++) {
					thisFoll.add(0);
				}
				for (int j = 0; j <= 14; j++) {
					thisFoll.set(j, countFoll.get(i).get(j) + segmentData.data.get(i).get(i + "foll").get(j));
				}
				countFoll.set(i, thisFoll);
				
				//System.out.println("behaviourData: " + segmentData.behaviourData);
				if(!(segmentData.behaviourData == null)) {
					if(!(segmentData.behaviourData.get(i) == null)) {
						HashMap<String, ArrayList<Integer>> tempMap = segmentData.behaviourData.get(i);
						//System.out.println("tempMap: " + tempMap);
						// Count up any concurrent behaviours for which we need a new behaviour graph
						// Occurences of Concurrent Behaviours
						ArrayList<Integer> thisConcOcc = new ArrayList<Integer>();
						for (int j = 0; j <= 14; j++) {
							thisConcOcc.add(0);
						}
						//System.out.println("before loop, thisConcOcc: " + thisConcOcc);
						for (int j = 0; j <= 14; j++) {
							String getOcc = j + "occ";
							//System.out.println("getOcc: " + getOcc);
							ArrayList<Integer> tempConcOcc = tempMap.get(getOcc);
							//System.out.println("tempConcOcc: " + tempConcOcc);
							//System.out.println("thisConcOcc.get(" + j + "): " + thisConcOcc.get(j));
							//System.out.println("countConcOcc.get(" + i + ").get(" + j + "): " + countConcOcc.get(i).get(j));
							//System.out.println("segmentData.behaviourData: " + segmentData.behaviourData);
							//System.out.println("segmentData.behaviourData.get(" + i + "): " + segmentData.behaviourData.get(i));
							//System.out.println("segmentData.behaviourData.get(" + i + ").get(" + j  + " + \"occ\"): " + segmentData.behaviourData.get(i).get(j + "occ"));
							
							if(!(tempConcOcc == null)) {
								//System.out.println("In if");
								//System.out.println("tempMap.get(" + getOcc  + ").get(0): " + tempMap.get(getOcc).get(0));
								thisConcOcc.set(j, countConcOcc.get(i).get(j) + tempConcOcc.get(0));
							}// else {
								//System.out.println("not in if");
							//}
							//countConcOcc.set(j, thisConcOcc);
							//System.out.println("End of loop");
						}
						countConcOcc.set(i, thisConcOcc);
						//System.out.println(countConcOcc);
						
						
						// Preceding Concurrent Behaviours
						ArrayList<ArrayList<Integer>> thisConcPrePre = new ArrayList<ArrayList<Integer>>();
						
						//ArrayList<Integer> thisConcTemp = new ArrayList<Integer>();
						/*for (int j = 0; j <= 14; j++) {
							thisConcPre.add(0);
							thisConcTemp.add(0);
						}
						for (int j = 0; j <= 14; j++) {
							thisConcPrePre.add(thisConcTemp);
						}*/
						for (int j = 0; j <= 14; j++) {
							
							ArrayList<Integer> thisConcPre = new ArrayList<Integer>();
							for (int k = 0; k <= 14; k++) {
								String getPre = j + "pre";
								ArrayList<Integer> tempConcPre = tempMap.get(getPre);
								if(!(tempConcPre == null)) {
									//System.out.println("BEFORE thisConcPrePre.get(0) = " + thisConcPrePre.get(0));
									thisConcPre.add(countConcPre.get(i).get(j).get(k) + tempConcPre.get(k));
									//System.out.println("countConcPre.get(" + i + ").get(" + j + ") = " + countConcPre.get(i).get(j) + "\ntempConcPre = " + tempConcPre);
									//System.out.println("k = " + k + ", thisConcPre = " + thisConcPre);
									//System.out.println("AFTER thisConcPrePre.get(0) = " + thisConcPrePre.get(0));
								}
							}
							//System.out.println("thisConcPrePre.get(" + j + ") = " + thisConcPrePre.get(j));
							//System.out.println("thisConcPrePre.get(0) = " + thisConcPrePre.get(0));
							thisConcPrePre.add(thisConcPre);
							//System.out.println("j = " + j + ", thisConcPrePre = " + thisConcPrePre);
						}
						countConcPre.set(i, thisConcPrePre);
						//System.out.println("countConcPre = " + countConcPre);
						
						
						// Following concurrent behaviours
						ArrayList<ArrayList<Integer>> thisConcFollFoll = new ArrayList<ArrayList<Integer>>();
						
						/*for (int j = 0; j <= 14; j++) {
							thisConcFoll.add(0);
						}
						for (int j = 0; j <= 14; j++) {
							thisConcFollFoll.add(thisConcFoll);
						}*/
						for (int j = 0; j <= 14; j++) {
							//System.out.println("\nj = " + j);
							ArrayList<Integer> thisConcFoll = new ArrayList<Integer>();
							for (int k = 0; k <= 14; k++) {
								String getFoll = j + "foll";
								//System.out.println("tempMap.get(" + getFoll + "): " + tempMap.get(getFoll));
								ArrayList<Integer> tempConcFoll = tempMap.get(getFoll);
								if(!(tempConcFoll == null)) {
									thisConcFoll.add(countConcFoll.get(i).get(j).get(k) + tempConcFoll.get(k));
								}
							}
							thisConcFollFoll.add(thisConcFoll);
						}
						countConcFoll.set(i, thisConcFollFoll);
					}
				}
			}
			
			
			
		}

		// Add all of the accumulated data to the return ArrayList.
		HashMap<String, ArrayList<Integer>> tempMap;
		for (int i = 0; i <= 14; i++) {
			//System.out.println("i = " + i);
			tempMap = new HashMap<String, ArrayList<Integer>>();
			ArrayList<Integer> occTemp = new ArrayList<Integer>();
			occTemp.add(countOcc.get(i));
			tempMap.put(i + "occ", occTemp);

			ArrayList<Integer> preTemp = new ArrayList<Integer>();
			ArrayList<Integer> contTemp = new ArrayList<Integer>();
			ArrayList<Integer> follTemp = new ArrayList<Integer>();
			ArrayList<Integer> concOccTemp = new ArrayList<Integer>();
			ArrayList<ArrayList<Integer>> concPreTemp = new ArrayList<ArrayList<Integer>>();
			ArrayList<ArrayList<Integer>> concFollTemp = new ArrayList<ArrayList<Integer>>();
			for (int j = 0; j <= 14; j++) {
				preTemp.add(countPre.get(i).get(j));
				contTemp.add(countCont.get(i).get(j));
				follTemp.add(countFoll.get(i).get(j));
				concOccTemp.add(countConcOcc.get(i).get(j));
				ArrayList<Integer> concPrePreTemp = new ArrayList<Integer>();
				ArrayList<Integer> concFollFollTemp = new ArrayList<Integer>();
				for (int k = 0; k <= 14; k++) {
					concPrePreTemp.add(countConcPre.get(i).get(j).get(k));
					concFollFollTemp.add(countConcFoll.get(i).get(j).get(k));
				}
				concPreTemp.add(concPrePreTemp);
				concFollTemp.add(concFollFollTemp);
			}
			tempMap.put(i + "pre", preTemp);
			tempMap.put(i + "cont", contTemp);
			tempMap.put(i + "foll", follTemp);
			tempMap.put(i + "concOcc", concOccTemp);
			//System.out.println(concOccTemp);
			for (int j = 0; j <= 14; j++) {
				tempMap.put(i + "concPre" + j, concPreTemp.get(j));
				tempMap.put(i + "concFoll" + j, concFollTemp.get(j));
			}
			//System.out.println("tempMap: " + tempMap);
			returnData.add(tempMap);
		}

		return returnData;
	}
}
