package behaviour_graphs;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * @author Martin
 * Type class for edges of the graph.
 */
public class BehaviourEdge extends DefaultWeightedEdge{

	/* (non-Javadoc)
	 * @see org.jgrapht.graph.DefaultWeightedEdge#toString()
	 * The only method needed is a way of displaying the weight of the edge when toString is called.
	 * Everything else is done in DefaultWeightedEdge.
	 */
	public String toString() {
		return String.format("%.3f", this.getWeight());
	}
	
}
