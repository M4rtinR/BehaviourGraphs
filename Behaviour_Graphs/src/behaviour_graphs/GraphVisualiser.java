package behaviour_graphs;

import com.mxgraph.layout.*;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.*;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

import org.jgrapht.ext.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.jgrapht.Graph;

/**
 * @author Martin
 * Class responsible for formatting the graph in a readable way.
 */
public class GraphVisualiser extends JApplet{
	
	Graph<BehaviourVertex, BehaviourEdge> g; // Graph to be formatted
	int graphNumber;
	
	// Maps from behaviour code to behaviour names and associated colours.
	Map<Integer, String> names = new HashMap<Integer, String>();
	Map<String, String> colours = new HashMap<String, String>();
	
	
	private static final long serialVersionUID = 2202072534703043194L;
	private static final Dimension DEFAULT_SIZE = new Dimension(1000, 600); // Size of graph window
    private JGraphXAdapter<BehaviourVertex, BehaviourEdge> jgxAdapter; // Graph visualisation
    private static final int multiplier = 3; // Scale of the graph (increase if not many behaviours, decrease of big graph).
    
	/**
	 * Constructor
	 * @param graph - the JGraph to be visualised.
	 */
	public GraphVisualiser(Graph<BehaviourVertex, BehaviourEdge> graph, int graphNumber) {
		this.graphNumber = graphNumber;
		
		//Initialise names
		names.put(0, "Start");
		names.put(1, "Pre-Instruction");
		names.put(2, "Concurrent Instruction (Positive)");
		names.put(3, "Concurrent Instruction (Negative)");
		names.put(4, "Post-Instruction (Positive)");
		names.put(5, "Post-Instruction (Negative)");
		names.put(6, "Manual Manipulation");
		names.put(7, "Questioning");
		names.put(8, "Positive Modelling");
		names.put(9, "Negative Modelling");
		names.put(10, "First Name");
		names.put(11, "Hustle");
		names.put(12, "Praise");
		names.put(13, "Scold");
		names.put(14, "Console");
		names.put(15,  "End");
		
		//Initialise colours
		colours.put("Start", "#FFFFFF"); // Start - White
		colours.put("Pre-Instruction", "#0000FF"); // Pre-instruction - Blue
		colours.put("Concurrent Instruction (Positive)", "#FF9933"); // Concurrent Instruction (Positive) - Orange
		colours.put("Concurrent Instruciton (Negative)", "#FF3399"); // Concurrent Instruction (Negative) - Electric Pink
		colours.put("Post-Instruction (Positive)", "#66FFFF"); // Post-instruction (Positive) - Light Blue
		colours.put("Post-Instruction (Negative)", "#7F00FF"); // Post-instruction (Negative) - Purple
		colours.put("Manual Manipulation", "#FF9999"); // Manual Manipulation - Faded Salmon
		colours.put("Questioning", "#000000"); // Questioning - Black
		colours.put("Positive Modelling", "#039408"); // Positive Modelling - Green
		colours.put("Negative Modelling", "#FF0000"); // Negative Modelling - Red
		colours.put("First Name", "#FFFF00"); // First Name - Yellow
		colours.put("Hustle", "#67AB9F"); // Hustle - Turquoise
		colours.put("Praise", "#99FF99"); // Praise - Lime
		colours.put("Scold", "#B3B3B3"); // Scold - Grey
		colours.put("Console", "#C3ABD0"); // Console - Lavender
		colours.put("End", "#FFFFFF"); // End - White
		
		g = graph;
        
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 * Initialise the applet.
	 */
	@Override
	public void init() {

        jgxAdapter = new JGraphXAdapter<>(g);
        
        //System.out.println(jgxAdapter.toString());
        
        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter); // Make the visualisation an mxGraph so that it can be displayed.
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        
        // Set the amount of customisation we want to allow the user to do on the graph.
        component.getGraph().setCellsMovable(true);
        component.getGraph().setCellsResizable(false);
        component.getGraph().setEdgeLabelsMovable(true);
        
        // Alter the style of the graph
        mxStylesheet style = new mxStylesheet();
        
        // Vertexes
        style.getDefaultVertexStyle().put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.getDefaultVertexStyle().put(mxConstants.STYLE_STROKEWIDTH, "2");
        //style.getDefaultEdgeStyle().put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CURVE);
        //style.getDefaultVertexStyle().put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
        
        
        component.getGraph().setStylesheet(style);
        
        getContentPane().add(component);
        resize(DEFAULT_SIZE);
        
        // Position vertices using mxGraphLayout
        mxGraphLayout layout = new mxHierarchicalLayout(jgxAdapter); // Hierarchical seems to give the most readable results.

        // Helper variables for displaying concurrent behaviours.
        ArrayList<mxCell> parents = new ArrayList<mxCell>();
        int[] sizes = new int[16];
        ArrayList<Integer> extraSizes = new ArrayList<Integer>();
        ArrayList<Integer> extraYs = new ArrayList<Integer>();
        ArrayList<BehaviourVertex> extraParents = new ArrayList<BehaviourVertex>();
        int[] contains = new int[15];
        //ArrayList<ArrayList<String>> toAdd = new ArrayList<ArrayList<String>>();
        ArrayList<String> toAddColours = new ArrayList<String>();
        
        boolean[] justPrint = new boolean[15]; // Used if we just want to print out the concurrent behaviours rather than show them in the graph.
        ArrayList<Object> notAddParents = new ArrayList<Object>(); // Used to store the behaviours that contain 3 or more concurrent behaviours.
        
        jgxAdapter.getModel().beginUpdate();
        try {
        	// Get all "cells" (vertexes and edges)
        	jgxAdapter.clearSelection();
            jgxAdapter.selectAll();
            jgxAdapter.setStylesheet(style);
            Object[] cells = jgxAdapter.getSelectionCells(); //here you have all cells
            jgxAdapter.setCellStyle(style.toString());
            

            // Iterate into graph to change cells
            int count = 0;
        	for(BehaviourVertex v:g.vertexSet()) {
        		//System.out.println("count = " + count);
        		System.out.println("\nbehaviour = " + v.getbehaviour());
        		if(count == 0 || count == g.vertexSet().size() - 1) { // Change this to "(count == 0 || count == <no_of__behaviours>)"
        			// The size of start and end will always be 30
        			sizes[count] = 10;
        			count++;
        		} else {
        			// The size of the other vertices will be proportional to the number of occurrences of that behaviour.
        			//System.out.println(count + ": " + v.getOccurences());
        			sizes[count] = v.getOccurences();
        			
        			// Concurrent behaviours
        			int parentSize = sizes[count];
        			contains[count] = 0;
        			HashMap<Float, String> tempUnordered = new HashMap<Float, String>();
        			ArrayList<String> temp = new ArrayList<String>();
        			ArrayList<Float> tempPercentage = new ArrayList<Float>();
        			float[] containing = v.getContaining();
        			for (int i = 0; i <= 14; i++) {
        				
        				
        				if (containing[i] > 0.05) { // If a behaviour is used concurrently more than 5% of the time
        					// Store the information needed to display this concurrent behaviour.
        					
        					contains[count]++;
        					if(contains[count]>2) {
        						justPrint[count] = true;
        					}
        					System.out.println("Adding: " + names.get(i));
        					//temp.add(names.get(i));
        					//tempPercentage.add(containing[i]);
        					//System.out.println("\n" + tempUnordered + "\n");
        					addToHashMap(containing[i], names.get(i), tempUnordered);
        					//System.out.println("\n" + tempUnordered + "\n");
        					/*float key = containing[i];
        					if(tempUnordered.containsKey(containing[i])) {
        						
        					} else {
        						tempUnordered.put(containing[i], names.get(i));
        					}*/
        					//toAddColours.add(colours.get(i));
        					
        					
        					
        					//Comment out the next 3 lines if you don't want the graph condensed.
        					//int temp = (int) (parentSize*containing[i]*multiplier);
        					//double percentHeight = (double) temp/ (double) (parentSize*multiplier);
        					//int actualHeight = (int) (20*percentHeight);
        					
        				}
        			}
        			if(justPrint[count]) {
        				//System.out.println(v.getbehaviour());
        				notAddParents.add(v);
        				for(int i = 0; i < temp.size(); i++) {
        					//System.out.println(temp.get(i) + ": " + tempPercentage.get(i)*100 + "%");
        				}
        			}
        			
        			//System.out.println(v.getbehaviour());
        			/*System.out.println(tempPercentage);
        			//Collections.sort(tempPercentage, Collections.reverseOrder());
        			Collections.sort(tempPercentage, Collections.reverseOrder());
        			System.out.println(tempPercentage);
        			ArrayList<String> tempOrdered = new ArrayList<String>();
        			for(int i = 0; i < temp.size(); i++) {
        				tempOrdered.add("");
        			}
        			//System.out.println(tempOrdered);
        			//int identical = 0;
        			ArrayList<String> added = new ArrayList<String>();
        			for(int i = 0; i <= 14; i++) {
        				if(temp.contains(names.get(i)) && !added.contains(names.get(i))) {
        					for(int j = 0; j < tempPercentage.size(); j++) {
        						System.out.println("tempPercentage.get(" + j +"): " + tempPercentage.get(j));
        						if(tempPercentage.get(j) == containing[i] && !tempOrdered.contains(names.get(i))) {
        							if(!tempOrdered.contains(names.get(i))) {
        								System.out.println("Adding: " + names.get(i) + " at position " + (j));
            							tempOrdered.set(j, names.get(i));
            							added.add(names.get(i));
        							}
        						} else if(tempPercentage.get(j) == containing[i] && tempOrdered.contains(names.get(i))) {
    								System.out.println("Adding 1 to identical");
    								//identical++; // This is used to avoid adding 2 of the same behaviour when 2 concurrent behaviours have the same percentage.
    								tempOrdered.set(j, names.get(i));
    								added.add(names.get(i));
        						}
        					}
        				}
        				/*if(tempPercentage.get(i))
        				float min = tempPercentage.get(i);
        				int minPos = i;
        				for(int j = i; j <= 14; j++) {
	        				
        					if(temp.contains(names.get(j)) && tempPercentage.get(j)<min){
	        					min = tempPercentage.get(j);
	        					minPos = j;
	        				}
	        			}
        				System.out.println("Adding: " + names.get(minPos));
        				tempOrdered.add(names.get(minPos));
        			}*/
        			System.out.println("tempUnordered.size(): " + tempUnordered.size());
        			int size = tempUnordered.size();
        			for(int j = 0; j < size; j++) {
        				System.out.println("j = " + j + ", tempUnordered = " + tempUnordered);
	        			Iterator<Map.Entry<Float, String>> it = tempUnordered.entrySet().iterator();
	        			float max = (float) 0;
	        			while (it.hasNext()) {
	        				Map.Entry<Float, String> entry = it.next();
	        				if ((float) entry.getKey() > max) {
	        					max = (float) entry.getKey();
	        				}
	        			}
	        			System.out.println("Adding Colour: " + colours.get(tempUnordered.get(max)));
	        			toAddColours.add(colours.get(tempUnordered.get(max)));
	        			for(int i = 0; i <= 14; i++) {
	        				if(tempUnordered.get(max).equals(names.get(i))) {
	        					extraSizes.add(/*actualHeight*/(int) (parentSize*containing[i]*multiplier));
	        					extraYs.add((int) (parentSize + (parentSize*containing[i])));
	        					extraParents.add(v);
	        				}
	        			}
	        			tempUnordered.remove(max);
        			}
        			//System.out.println("temp = " + temp);
        			//System.out.println("tempOrdered = " + tempOrdered);
        			/*for(String s:tempOrdered) {
	        			for(int i = 0; i <= 14; i++) {
	        				if(s.equals(names.get(i))) {
	        					System.out.println("Adding Colour: " + colours.get(i));
		        				//toAddColours.add(colours.get(i));
		        				//System.out.println("Adding to extra sizes: " + (int) (parentSize*containing[i]*multiplier));
	        					extraSizes.add(/*actualHeight(int) (parentSize*containing[i]*multiplier));
	        					extraYs.add((int) (parentSize + (parentSize*containing[i])));
	        					extraParents.add(v);
	        				}
	        			}
        			}*/
        			//toAdd.add(tempOrdered);
        			count++;
        		}
        	}
        	
        	// Scale the visual elements of the graph (text, lines etc) up and down based on the biggest vertex.
        	int biggest = 0;
            for(int size : sizes) {
            	if (size > biggest) {
            		biggest = size*multiplier;
            	}
            }
            style.getDefaultVertexStyle().put(mxConstants.STYLE_FONTSIZE, biggest*30/100); // This changes the text size in the boxes.
            style.getDefaultVertexStyle().put(mxConstants.STYLE_FONTCOLOR, Color.BLACK);
            style.getDefaultEdgeStyle().put(mxConstants.STYLE_FONTSIZE, biggest*20/100); // This changes the text size on the arrows.
            //style.getDefaultEdgeStyle().put(mxConstants.STYLE_STROKEWIDTH, biggest*1/100);
        	
            
            // Set the sizes of the vertices using the geometry of the associated cell.
            int count2 = 0;
            //boolean addedParent = false;
            
            for (Object cell : cells) {
            	mxCell c = (mxCell) cell;
            	System.out.println("VertexSet size: " + g.vertexSet().size());
                if (c.isVertex() && count2 != 0 && count2 != g.vertexSet().size() - 1) { //isVertex not start or end.
                	//addedParent = false;
                	mxGeometry geometry = c.getGeometry();
                	System.out.println(c.getValue() + "count: " + count2);
                	/*if(count2 == 0 || count2 == 5) {
            			geometry.setWidth(30);
		                geometry.setHeight(30);
                	} else {*/
            		geometry.setWidth(sizes[count2]*multiplier);
	                geometry.setHeight(sizes[count2]*multiplier);
            		//geometry.setHeight(30);
	                
	                // For each concurrent behaviour associated with this behaviour, add this cell as a parent.
	                for(int i = 0; i < contains[count2]; i++) {
	                	parents.add(c);
	                	
	                }
	                
	                count2++;
		                
                	//}
                } else if(c.isVertex()) {
                	mxGeometry geometry = c.getGeometry();;
                	
                	geometry.setWidth(sizes[count2]*multiplier);
		            geometry.setHeight(sizes[count2]*multiplier);
		            count2++;
                } else if (c.isEdge()) {
                
                	// Set width of edge
                	String width = ""+c.getValue();
                	Hashtable<String, Object> thisStyle = new Hashtable<String, Object>();
                	thisStyle.put(mxConstants.STYLE_STROKEWIDTH, Double.valueOf(width) *10); // This changes the width of the arrows.
                	style.putCellStyle(""+count2, thisStyle);
                	
                	c.setStyle(""+count2);
                }
                
            }
        } finally {
        	jgxAdapter.getModel().endUpdate();
        }

        // Execute the layout we defined earlier.
        layout.execute(jgxAdapter.getDefaultParent());
        
        
        //Put containing behaviours in right place.
        jgxAdapter.getModel().beginUpdate();
        
        try {
	        jgxAdapter.clearSelection(); 
	        jgxAdapter.selectAll();
	        Object[] cells = jgxAdapter.getSelectionCells();
	        
	        int count = 0; // Used to find all the behaviours contained in/concurrent to this one.
	        int contCount = 0; // Used to get the right parent.
	        System.out.println("Parents: " + parents);
	        System.out.println("notAddParents: " + notAddParents);
	        for(Object cell : cells) {
	        	mxCell c = (mxCell) cell;
	        	if(parents.contains(c)) {
	        		
	        		mxGeometry geometry = c.getGeometry();
		        	for(int i = 0; i < contains[count]; i++) {
		        		// Each concurrent behaviour will be a vertex overlapping (i.e. with the same x and y coordinates) the parent behaviour.
		        		
		        		if(!(notAddParents.contains(c.getValue()))) { //If there's not more than 3 concurrent behaviours
		        			System.out.println(c.getValue());
		        			System.out.println("Adding to graph: " + toAddColours.get(contCount));
		        			System.out.println("toAddColours.size: " + toAddColours.size());
		        			System.out.println(toAddColours);
		        			System.out.println("contCount: " + contCount);
		        			jgxAdapter.insertVertex(jgxAdapter.getDefaultParent(), null, "", geometry.getX(), geometry.getY()+geometry.getHeight()-extraSizes.get(contCount), sizes[count]*multiplier, extraSizes.get(contCount), "shape=rectangle;strokeColor=" + toAddColours.get(contCount) + ";dashed=1;fontColor=" + toAddColours.get(contCount) + ";fontSize=" + sizes[count]*40/100 + ";fillColor=" + toAddColours.get(contCount) + ";opacity=50;");
		        			
		        		}
		        		contCount++;
		        		
		        	}
		        	
	        	}
	        	count++;
	        	
	        }
		} finally {
			jgxAdapter.getModel().endUpdate();
		}
        
        // Save the graph as an image to be used elsewhere.
        BufferedImage image = mxCellRenderer.createBufferedImage(jgxAdapter, null, 1, Color.WHITE, true, null);
        try {
			ImageIO.write(image, "PNG", new File("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\ClusteredStyles\\graph" + graphNumber + ".png"));
		} catch (IOException e) {
			System.err.println("Could not save graph as image.");
			e.printStackTrace();
		}

    }
	
	public void saveGraph(String filename) {
		// Save the graph as an image to be used elsewhere.
        BufferedImage image = mxCellRenderer.createBufferedImage(jgxAdapter, null, 1, Color.WHITE, true, null);
        try {
			ImageIO.write(image, "PNG", new File(filename + ".png"));
		} catch (IOException e) {
			System.err.println("Could not save graph as image.");
			e.printStackTrace();
		}
	}
	
	private void addToHashMap(float key, String value, HashMap<Float, String> hm) {
		if (hm.containsKey(key)){
			key = key - (float) 0.000001;
			addToHashMap(key, value, hm);
		} else {
			hm.put(key,  value);
		}
	}
}
