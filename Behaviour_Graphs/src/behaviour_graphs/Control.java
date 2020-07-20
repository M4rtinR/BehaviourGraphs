package behaviour_graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.jgrapht.Graph;

/**
 * @author Martin
 * The main control of the program. Works through from reading data from
 * a file to displaying a formatted graph from that data.
 */
public class Control{
	
	public static void main(String[] args) {
		
		
		// Test data squash
		// Test Compound Mappings - delete if exporting
		HashMap<Integer, Integer> compoundMappings = new HashMap<Integer, Integer>();
		compoundMappings.put(15, 22);
		compoundMappings.put(16, 23);
		compoundMappings.put(17, 24);
		compoundMappings.put(18, 25);
		compoundMappings.put(19, 28);
		compoundMappings.put(20, 39);
		compoundMappings.put(21, 42);
		compoundMappings.put(22, 70);
		compoundMappings.put(23, 71);
		compoundMappings.put(24, 72);
		compoundMappings.put(25, 73);
		compoundMappings.put(26, 74);
		compoundMappings.put(27, 86);
		compoundMappings.put(28, 87);
		compoundMappings.put(29, 88);
		compoundMappings.put(30, 89);
		compoundMappings.put(31, 92);
		compoundMappings.put(32, 100);
		compoundMappings.put(33, 101);
		compoundMappings.put(34, 121);
		compoundMappings.put(35, 122);
		compoundMappings.put(36, 129);
		compoundMappings.put(37, 132);
		compoundMappings.put(38, 149);
		compoundMappings.put(39, 186);
		compoundMappings.put(40, 202);
		compoundMappings.put(41, 216);
		compoundMappings.put(42, 218);
		compoundMappings.put(43, 234);
		
		float[][][] testGraphsSquash = {{{0.041728991596638655f, 0.8f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.041728991596638655f, 0.0f, 0.4f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.118686974789916f, 0.0f, 0.08f, 0.0f, 0.24f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3333333333333333f, 0.04f, 0.04f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.06666666666666667f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.028000000000000004f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.06815756302521007f, 0.04f, 0.2f, 0.0f, 0.0f, 0.06666666666666667f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2533333333333333f, 0.0f, 0.04f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.027142857142857146f, 0.0f, 0.0f, 0.2f, 0.06666666666666667f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.06666666666666667f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.06666666666666667f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.019428571428571427f, 0.0f, 0.0f, 0.0f, 0.0f, 0.1f, 0.0f, 0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.00625f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0125f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.01801470588235294f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.3749306722689075f, 0.0f, 0.17215229215229216f, 0.0f, 0.04895104895104895f, 0.038181818181818185f, 0.0f, 0.01818181818181818f, 0.015384615384615385f, 0.0f, 0.0f, 0.04f, 0.45738927738927726f, 0.015384615384615385f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.02f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05858585858585859f, 0.0f, 0.0f, 0.0f, 0.11578865578865578f}, {0.014250000000000002f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.032464285714285716f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.06666666666666667f, 0.26666666666666666f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.06666666666666667f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.03514285714285714f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.06666666666666667f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.06666666666666667f, 0.06666666666666667f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f}, {0.005714285714285714f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.005714285714285714f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0225f, 0.0f, 0.0f, 0.0f, 0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.07791596638655461f, 0.0f, 0.05f, 0.0f, 0.1f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.45f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.008f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, {0.041728991596638655f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}}};
		
		PythonHelper PH = new PythonHelper(compoundMappings);
		
		PH.createGraph(testGraphsSquash);
		

		/*ArrayList<ArrayList<ArrayList<Float>>> f = PH.getFollowing();
		ArrayList<ArrayList<Integer>> i = PH.getSequences(0, 1);
		System.out.println(f);
		System.out.println(i);
		
		ArrayList<ArrayList<Float>> sm = PH.getSquashMatrix();
		System.out.println("Squash Matrix:");
		for(ArrayList<Float> arr: sm) {
			System.out.print("[");
			for(Float value: arr) {
				System.out.print(value + ", ");
			}
			System.out.println("]");
		}
		
		ArrayList<ArrayList<Float>> pm = PH.getPhysioMatrix();
		System.out.println("Physio Matrix:");
		for(ArrayList<Float> arr: pm) {
			System.out.print("[");
			for(Float value: arr) {
				System.out.print(value + ", ");
			}
			System.out.println("]");
		}*/
		
		/*
		
		
		
		// The file reader will read the file and store the data in an ArrayList.
		//FileReader fr = new FileReader("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\Squash Coaches\\SPSS\\Observation Study\\Behaviours.csv");
		FileReader fr = new FileReader("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\Stroke Physiotherapists\\Data\\Behaviours.csv");
		
		ArrayList<ArrayList<String>> squashData = fr.getAllSquashData();
		
		// The data counter will count all the data that was in the file, storing it in another ArrayList.
		DataCounter dc = new DataCounter(squashData);
		
		ArrayList<HashMap<String, ArrayList<Integer>>> countedData = new ArrayList<HashMap<String, ArrayList<Integer>>>();
		
		// Find out what data the user wants to see.
		System.out.println("Would you like a graph for a specific coach/session, specific segment or all data?");
		System.out.print(" > ");
		
		Scanner s = new Scanner(System.in);
		String input = s.nextLine();
		if (input.equals("coach") || input.equals("session")){ // Data for a single coach.
		
			System.out.println("Please enter the coach ID of the coach you would like to view the graph for (for a combined graph type 'all'):");
			System.out.print(" > ");
			
			String coachID = s.nextLine();
			
			// Create variables to store which coach, session, and segment we want the data for.
	        String coach = "";
	        String session = "";
	        String segment = "";
	        
	        // Get the coach number from user's input.
	        String first = coachID.substring(0,1);
	        
	        if(first.equals("C") || first.equals("c") || first.equals("P") || first.equals("p")) { // Remove "C" from input
	        	if (coachID.substring(1).length() > 1 && !(coachID.substring(1).equals("10"))) { // Check if they have entered a session as well as a coach.
	        		String[] split = coachID.substring(1).split("\\.");
	        		coach = split[0];
	        		session = split[1];
	        	} else {
	        		coach = coachID.substring(1);
	        	}
	        } else {
	        	if (coachID.length() > 1 && !(coachID.equals("10"))) { // Check if they have entered a session as well as a coach.
	        		String[] split = coachID.split("\\.");
	        		coach = split[0];
	        		session = split[1];
	        	} else {
	        		coach = coachID;
	        	}
	        }
	        
	        // Validate coach ID input.
	        //if(!(Integer.valueOf(coach) < 9 && Integer.valueOf(coach) > 0)) {    //Uncomment for squash coaches
	        if(!(Integer.valueOf(coach) < 11 && Integer.valueOf(coach) > 0)) {     //Uncomment for stroke physios
	        	System.err.println("No data for coach " + coach);
	        	s.close();
	        	return;
	        //} else if (session.length() > 0 && !(Integer.valueOf(session) < 3 && Integer.valueOf(session) > 0)) {     //Uncomment for squash coaches
	        } else if (session.length() > 0 && !(Integer.valueOf(session) < 4 && Integer.valueOf(session) > 0)) {       //Uncomment for stroke physios
	        	System.err.println("No data from session " + session + " for coach " + coach);
	        	s.close();
	        	return;
	        }
	        
	        // Check if the user wants all data for that coach, or just for a particular segment.
	        System.out.println("Thank you. Would you like to view the data for a particular segment of this session/coach?");
			System.out.print(" > ");
			
			input = s.nextLine();
			if(input.equals("y") || input.equals("yes")) { // We want a specific segment.
				System.out.println("Please enter the segment you would like to view the graph for:");
				System.out.print(" > ");
				
		        segment = s.nextLine();
		        
		        if(session.equals("")) {
		        	// Get the data we are interested in from the data counter.
		        	countedData = dc.getDataForCoachAndSegment(Integer.valueOf(coach), Integer.valueOf(segment));
					
					//countedData = dc.addUpData(cData);
		        } else {
		        	// Get the data we are interested in from the data counter.
		        	countedData = dc.getDataForSessionAndSegment(Integer.valueOf(coach), Integer.valueOf(session), Integer.valueOf(segment));
					
					//countedData = dc.addUpData(cData);
				}
			} else { // We want data for all segments of that coach/session.
				if(session.equals("")) {
					// Get the data we are interested in from the data counter.
					countedData = dc.getDataForCoach(Integer.valueOf(coach));
					
					//countedData = dc.addUpData(cData);
				} else {
					// Get the data we are interested in from the data counter.
					countedData = dc.getDataForSession(Integer.valueOf(coach), Integer.valueOf(session));
					
					//countedData = dc.addUpData(cData);
				}
			}
	        
	        
		
		} else if (input.equals("segment")) { // Data for a single segment.
			String segment = "";
			System.out.println("Please enter the segment you would like to view the graph for:");
			System.out.print(" > ");
			segment = s.nextLine();
			
			// Get the data we are interested in from the data counter.
			countedData = dc.getDataForSegment(Integer.valueOf(segment));
			
			//countedData = dc.addUpData(cData);
		} else { // All data
			countedData = dc.getAllData();
			
			//countedData = dc.addUpData(cData);
		}
		
		s.close();
		
		System.out.println("Your requested graph is loading and will appear shortly...");
		
		
		// The data analyser will parse the counted data and obtain
		// total occurrences, preceding behaviours and concurrent behaviours for each behaviour.
		DataAnalyser da = new DataAnalyser(countedData);
		int[] totals = da.getTotals();
		float[][] preceding = da.getPreceding();
		float[][] containing = da.getContaining();
		float[][] following = da.getFollowing();
		
		boolean concurrentGraphNeeded[] = new boolean[15];
		int concGraphsNeeded = 0;
		int total = 0;
		for(int i = 0; i < 14; i++) {
			total = total + totals[i];
		}
		float accept = (float) total * (float) 5 / (float) 100;
		for(int i = 0; i <= 14; i++) {
			concurrentGraphNeeded[i] = false;
			if ((float) totals[i] > accept) {
				int concurrentBehaviours = 0;
				for(int j = 0; j <= 14; j++) {
					if(containing[i][j] > 0.05) {
						concurrentBehaviours++;
					}
				}
				if(concurrentBehaviours > 2) {
					concurrentGraphNeeded[i] = true;
					concGraphsNeeded++;
				}
			}
		}
		
		if(concGraphsNeeded > 0) {
			ArrayList<DataAnalyser> concDA = new ArrayList<DataAnalyser>();
			ArrayList<Integer> concEncompassing = new ArrayList<Integer>();
			ArrayList<Integer> dummyCont = new ArrayList<Integer>();
			for (int i = 0; i <=14; i++) {
				dummyCont.add(0);
			}
			for(int i = 0; i <= 14; i++) {
				System.out.println("i = " + i + ", graph needed = " + concurrentGraphNeeded[i]);
				if (concurrentGraphNeeded[i]) {
					ArrayList<HashMap<String, ArrayList<Integer>>> concCountedData = new ArrayList<HashMap<String, ArrayList<Integer>>>();
					
					HashMap<String, ArrayList<Integer>> toAdd;
					
					for (int j = 0; j <= 14; j++) {
						toAdd = new HashMap<String, ArrayList<Integer>>();
						String occName = j + "occ";
						//System.out.println("occName: " + occName);
						String occGetName = i + "concOcc";
						//System.out.println("occGetName: " + occGetName);
						//System.out.println("countedData.get(" + i + "): " + countedData.get(i));
						//System.out.println("countedData.get(" + i + ").get(" + occGetName + "): " + countedData.get(i).get(occGetName));
						ArrayList<Integer> tempList = new ArrayList<Integer>();
						tempList.add(countedData.get(i).get(occGetName).get(j));
						//System.out.println("Adding " + tempList);
						toAdd.put(occName, tempList);
						
						String preName = j + "pre";
						//System.out.println("preName: " + preName);
						String preGetName = i + "concPre" + j;
						//System.out.println("preGetName: " + preGetName);
						tempList = new ArrayList<Integer>();
						//System.out.println("countedData.get(" + i + ").get(" + preGetName + "): " + countedData.get(i).get(preGetName));
						for(int k = 0; k <= 14; k++) {
							tempList.add(countedData.get(i).get(preGetName).get(k));
						}
						//System.out.println("Adding + " + tempList);
						toAdd.put(preName, tempList);
						
						
						String follName = j + "foll";
						String follGetName = i + "concFoll" + j;
						tempList = new ArrayList<Integer>();
						for(int k = 0; k <= 14; k++) {
							tempList.add(countedData.get(i).get(follGetName).get(k));
							//System.out.println("tempList: " + tempList);
						}
						toAdd.put(follName, tempList);
						
						String contName = j + "cont";
						toAdd.put(contName, dummyCont);
						
						concCountedData.add(toAdd);
					}
					
					concDA.add(new DataAnalyser(concCountedData));
					concEncompassing.add(totals[i]);
				}
			}
			
			int[][] concTotals = new int[concGraphsNeeded][15];
			float[][][] concPreceding = new float[concGraphsNeeded][15][15];
			float[][][] concFollowing = new float[concGraphsNeeded][15][15];
			float[][] concContaining = new float[15][15];
			for (int i = 0; i <= 14; i++) {
				for (int j = 0; j <= 14; j ++) {
					concContaining[i][j] = 0;
				}
			}
			
			int i = 0;
			for(DataAnalyser thisDA:concDA) {
				concTotals[i] = thisDA.getTotals();
				concPreceding[i] = thisDA.getPreceding();
				concFollowing[i] = thisDA.getFollowing();
				
				// The graph creator will create the JGraph from the analysed data.
				GraphCreator gc = new GraphCreator(concTotals[i], concPreceding[i], concContaining, concFollowing[i], thisDA.getTotalOccs(), concEncompassing.get(i));
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
		        
		        i++;
			}
			
			
		}
		
		// The graph creator will create the JGraph from the analysed data.
		GraphCreator gc = new GraphCreator(totals, preceding, containing, following, da.getTotalOccs(), 0);
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
        
        
		*/
	}

}
