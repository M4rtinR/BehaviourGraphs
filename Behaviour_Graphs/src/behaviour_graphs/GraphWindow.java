package behaviour_graphs;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.jgrapht.ext.JGraphXAdapter;

public class GraphWindow extends JFrame implements ActionListener {

	private JMenu fileMenu = new JMenu("File");
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem saveAsItem = new JMenuItem("Save As");
    private String filename = null;
    private GraphVisualiser gv;
    
	public GraphWindow(GraphVisualiser graph) {
		//Make it so the graph is save-able.
        
        fileMenu.add(saveAsItem);
        saveAsItem.addActionListener(this);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        gv = graph;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == saveAsItem) {
			saveFile(null);
		}
		

	}
	
	// Save named file.  If name is null, prompt user and assign to filename.
	  // Allow user to cancel, leaving filename null.  Tell user if save is
	  // successful.
	  private void saveFile(String name) {
		  String fileName = "C:\\Users\\conta\\eclipse-workspace\\Behaviour_Graphs\\SavePrefs.txt";
		  
		  String lastDir = "C:\\\\Users\\\\conta\\\\OneDrive";
		  
		  try {
			  
			  File prefFile = new File(fileName);
			  if(prefFile.createNewFile()) {
				  FileWriter fw = new FileWriter(fileName);
				  fw.write("C:\\Users\\conta\\OneDrive\\Documents\\Uni\\PhD\\Observation Study\\Stroke Physiotherapists\\Data\\Behaviour Graphs");
				  fw.close();
			  }
			  Scanner scanner = new Scanner(prefFile);
			  lastDir = scanner.nextLine();
			  scanner.close();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
		  
		    
	    if (name == null) {  // get filename from user
	      JFileChooser fc = new JFileChooser();
	      fc.setCurrentDirectory(new File(lastDir));
	      if (fc.showSaveDialog(null) != JFileChooser.CANCEL_OPTION)
	        name = fc.getSelectedFile().getAbsolutePath();
	      try {
	        	lastDir = fc.getCurrentDirectory().getPath();
	        	FileWriter fw = new FileWriter(new File(fileName));
	        	fw.write(lastDir);
	        	fw.close();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	    }
	    
	    if (name != null) {  // else user cancelled
	        //Formatter out = new Formatter(new File(name));  // might fail
	        filename = name;
	        gv.saveGraph(filename);
	        /*BufferedImage img = new BufferedImage(this.getContentPane().getWidth(), this.getContentPane().getHeight(), BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2 = img.createGraphics();
	        try {
                ImageIO.write(img, "png", new File(filename + ".png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }*/
	        //out.format("%s", textArea.getText());
	        //out.close();
	        JOptionPane.showMessageDialog(null, "Saved to " + filename,
	          "Save File", JOptionPane.PLAIN_MESSAGE);
	        
	        
	    }
	    
	  }

}
