package behaviour_graphs;

/**
 * @author Martin
 * Type class for the vertexes in the graph.
 */
public class BehaviourVertex {
	
	private String behaviour; // Behaviour name
	private int occurrences; // The number of times this behaviour appeared in the data.
	private float[] containing; // The behaviours that happened concurrently with this one.
	
	/**
	 * Constructor
	 * @param behaviourCode - the number associated with this behaviour.
	 * @param occurrences - the amount of times this behaviour occurred in the data.
	 * @param containing - the behaviours that happened concurrently with this one.
	 */
	public BehaviourVertex(int behaviourCode, int occurrences, float[] containing) {
		// Set the name of the behaviour given its number.
		switch(behaviourCode) {
		case 0:
			behaviour = "Start";
			break;
		case 1:
			behaviour = "Pre-instruction";
			break;
		case 2:
			behaviour = "Concurrent Instruction (Positive)";
			break;
		case 3:
			behaviour = "Concurrent Instruction (Negative)";
			break;
		case 4:
			behaviour = "Post-instruction (Positive)";
			break;
		case 5:
			behaviour = "Post-instruction (Negative)";
			break;
		case 6:
			behaviour = "Manual Manipulation";
			break;
		case 7:
			behaviour = "Questioning";
			break;
		case 8:
			behaviour = "Positive Modelling";
			break;
		case 9:
			behaviour = "Negative Modelling";
			break;
		case 10:
			behaviour = "First Name";
			break;
		case 11:
			behaviour = "Hustle";
			break;
		case 12:
			behaviour = "Praise";
			break;
		case 13:
			behaviour = "Scold";
			break;
		case 14:
			behaviour = "Console";
			break;
		case 15:
			behaviour = "End";
		default:
			break;
		}
		
		this.occurrences = occurrences;
		this.containing = containing;
	}
	
	/**
	 * Getter method.
	 * @return the name of this behaviour.
	 */
	public String getbehaviour() {
		return this.behaviour;
	}
	
	/**
	 * Getter method.
	 * @return the number of times this behaviour occurred.
	 */
	public int getOccurences() {
		return this.occurrences;
	}
	
	/**
	 * Getter method.
	 * @return an array of behaviours that happened concurrently with this one.
	 */
	public float[] getContaining() {
		return this.containing;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * toString should just display the name of the behaviour.
	 */
	public String toString() {
		return behaviour;
	}
}
