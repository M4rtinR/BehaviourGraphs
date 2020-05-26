package behaviour_graphs;

import java.util.ArrayList;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;

/**
 * @author Martin
 * This class will create the graph from the counted data.
 */
public class GraphCreator {

	Graph<BehaviourVertex, BehaviourEdge> g;
	ArrayList<DefaultDirectedWeightedGraph<BehaviourVertex, BehaviourEdge>> concurrentGraphs;
	
	/**
	 * Constructor
	 * @param occurrences - array of occurrences of each behaviour.
	 * @param preceding - array of preceding behaviours for each behaviour.
	 * @param containing - array of contained/concurrent behaviours for each behaviour.
	 */
	public GraphCreator(int[] occurrences, float[][] preceding, float[][] containing, float[][] following, int[] totals, int encompassingOccs) {
		// Create a new graph.
		g = new DefaultDirectedWeightedGraph<BehaviourVertex, BehaviourEdge>(BehaviourEdge.class);
		concurrentGraphs = new ArrayList<DefaultDirectedWeightedGraph<BehaviourVertex, BehaviourEdge>>();

		BehaviourVertex[] v = new BehaviourVertex[16]; 
		
		//System.out.println("\nOccurences of behaviour:");
		
		int occsWithCont = 0;
		//System.out.print("totals[]: ");
		for (int i = 0; i <= 14; i++) {
			//System.out.print(i + ": " + totals[i] + ", ");
			occsWithCont+=totals[i];
		}
		//System.out.println("\n");
		//System.out.print("occurrences[]: ");
		for (int i = 0; i <= 14; i++) {
			//System.out.print(i + ": " + occurrences[i] + ", ");
		}
		//System.out.println("\n");
		for (int i = 0; i <= 14; i++) {
			// Print out total percentages.
			double percentage = ((double) totals[i]/ (double) occsWithCont)* (double) 100;
			//System.out.print(i + ": " + String.format("%.2f", percentage) + "%, ");
		}
		
		float acceptOcc;
		//Create and add the vertices
		if (encompassingOccs == 0) { // If this is not a concurrent graph
		
			int totalOcc = 0;
			for (int i = 1; i < 14; i++) {
				totalOcc+=occurrences[i];
			}
			//System.out.println("totalOcc = " + totalOcc);
			acceptOcc = (float) totalOcc * (float) 5 / (float) 100; // Calculate 5% of total behaviours used.
		} else {
			acceptOcc = (float) encompassingOccs * (float) 5 / (float) 100;
		}
		//System.out.println("acceptOcc = " + acceptOcc);
		ArrayList<BehaviourVertex> addedVertices = new ArrayList<BehaviourVertex>();
		for (int i = 0; i <= 15; i++) {			
			// Only add a behaviour to the graph if it accounts for 5% or more of the behaviours used by the coach,
			// or if it is 0 (start) or 15 (end).
			// This keeps the graph more readable.
			/*System.out.print("i = " + i);
			if(i!=15) {
				System.out.println(",  occurences = " + occurrences[i]);
			}*/
			if(i == 15) {
				float[] temp = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
				v[i] = new BehaviourVertex(i, 1, temp);
				g.addVertex(v[i]);
				addedVertices.add(v[i]);
				//System.out.println("Added vertex " + i);
			} else if(occurrences[i] >= acceptOcc || i == 0) { 
				// Check if it has 3 or more concurrent behaviours, in which case create new graph for them.
				int concurrentBs = 0;
				for (float cont:containing[i]) {
					if(cont>0) {
						concurrentBs+=1;
					}
				}
				/*if(concurrentBs >= 3) {
					concurrentGraphs.add(CreateConcurrentGraph(containing[i]));
					
				}*/
				
				v[i] = new BehaviourVertex(i, occurrences[i], containing[i]);
				g.addVertex(v[i]);
				addedVertices.add(v[i]);
				//System.out.println("Added vertex " + i);
			}
		}
		
		//System.out.print("\n\nPreceding Percentages:");

        // Create and add the edges
		
		/*for(int i = 0; i <= 14; i++) {
			//System.out.print("\n" + i + ":     ");
			for(int j = 0; j <= 14; j++) {
				// Print out preceding percentages.
				double percentage = (double) preceding[i][j] * (double) 100;
				//System.out.print(j + ": " + String.format("%.2f", percentage) + "%, ");
				
				// Uncomment the next section if you want the "preceding" graph, rather than the "following" graph.
				
				// Only add a relationship to the graph if it accounts for 5% or more of the behaviours preceding the current behaviour.
				// This keeps the graph more readable.
				/*if (preceding[i][j] > 0.05 && addedVertices.contains(v[i]) && addedVertices.contains(v[j])) {
					g.setEdgeWeight(g.addEdge(v[j], v[i]), preceding[i][j]);
				}
			}
		}*/
		
		// Add edges from start
		for(int i = 0; i <= 14; i++) {
			if(addedVertices.contains(v[i+1]) && following[0][i] > (float) 0) {
				g.setEdgeWeight(g.addEdge(v[0],  v[i+1]), following[0][i]);
			}
		}
		//System.out.print("\n\nFollowing Percentages:");
		for(int i = 1; i <= 14; i++) {
			//System.out.print("\n" + i + ":     ");
			for(int j = 0; j <= 14; j++) {
				// Print out preceding percentages.
				//double percentage = (double) following[i][j] * (double) 100;
				//System.out.print((j+1) + ": " + String.format("%.2f", percentage) + "%, ");
				
				// Only add a relationship to the graph if it accounts for 5% or more of the behaviours following the current behaviour.
				// This keeps the graph more readable.
				if ((following[i][j] > 0.05 || (following[i][j] > 0.00 && j == 14)) && addedVertices.contains(v[i]) && addedVertices.contains(v[j+1])) {
					g.setEdgeWeight(g.addEdge(v[i], v[j+1]), following[i][j]);
					//System.out.println("Added edge from " + i + " to " + (j+1) + " with weight " + following[i][j]);
				}
			}
		}
		
		// Print out concurrent percentages.
		//System.out.print("\n\nConcurrent Behaviour Percentages:");
		for(int i = 0; i <= 14; i++) {
			//System.out.print("\n" + i + ":     ");
			for(int j = 0; j <= 14; j++) {
				double percentage = (double) containing[i][j] * (double) 100;
				//System.out.print(j + ": " + String.format("%.2f", percentage) + "%, ");
			}
		}
	}
	
	/**
	 * Local method to create the behaviour graphs for all behaviours containing 3 or more concurrent behaviours.
	 * @return the graph created.
	 */
	/*DefaultDirectedWeightedGraph<BehaviourVertex, BehaviourEdge> CreateConcurrentGraph(float[] occurrences){
		Graph<BehaviourVertex, BehaviourEdge> concurrentG = new DefaultDirectedWeightedGraph<BehaviourVertex, BehaviourEdge>(BehaviourEdge.class);
		
		BehaviourVertex[] v = new BehaviourVertex[16]; 
		
		//Create and add the vertices
		int totalOcc = 0;
		for (int i = 0; i <= 14; i++) {
			totalOcc+=occurrences[i];
		}
		int acceptOcc = totalOcc*5/100; // Calculate 5% of total behaviours used.
		//System.out.println("acceptOcc = " + acceptOcc);
		ArrayList<BehaviourVertex> addedVertices = new ArrayList<BehaviourVertex>();
		for (int i = 0; i <= 15; i++) {			
			// Only add a behaviour to the graph if it accounts for 5% or more of the behaviours used by the coach,
			// or if it is 0 (start) or 15 (end).
			// This keeps the graph more readable.
			//System.out.print("i = " + i);
			/*if(i!=15) {
				System.out.println(",  occurences = " + occurrences[i]);
			}*/
			/*if(i == 15) {
				float[] temp = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
				v[i] = new BehaviourVertex(i, 1, temp);
				g.addVertex(v[i]);
				addedVertices.add(v[i]);
				//System.out.println("Added vertex " + i);
			} else if(occurrences[i] >= acceptOcc || i == 0) { 
				// Check if it has 3 or more concurrent behaviours, in which case create new graph for them.
				int concurrentBs = 0;
				for (float cont:containing[i]) {
					if(cont>0) {
						concurrentBs+=1;
					}
				}
				if(concurrentBs >= 3) {
					concurrentGraphs.add(CreateConcurrentGraph());
					
				}
				
				v[i] = new BehaviourVertex(i, occurrences[i], containing[i]);
				g.addVertex(v[i]);
				addedVertices.add(v[i]);
				//System.out.println("Added vertex " + i);
			}
		}
				
		return g;
	}*/
	
	/**
	 * Getter method.
	 * @return the graph created.
	 */
	public Graph<BehaviourVertex, BehaviourEdge> getGraph() {
		Set<BehaviourVertex> s1 = g.vertexSet();
		/*for(BehaviourVertex v:s1) {
			System.out.println(v.toString() + ": " + v.getOccurences());
		}*/
		Set<BehaviourEdge> s2 = g.edgeSet();
		/*for(BehaviourEdge e:s2) {
			System.out.println(e.toString());
		}*/
		return g;
	}
}
