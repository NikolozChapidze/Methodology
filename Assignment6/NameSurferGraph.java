/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {
	//GCanvas canvas;
	ArrayList<NameSurferEntry> entries;
	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		entries=new ArrayList<NameSurferEntry>();
		
		addComponentListener(this);
		drawDefault();
	}
	
	//drawing default look of the Graph
	private void drawDefault() {
		GLine line=new GLine(0, 0, 0, getHeight());
		GLabel year=new GLabel(START_DECADE+"");
		year.setLocation(5,getHeight()-(GRAPH_MARGIN_SIZE-year.getHeight())/2-year.getDescent());
		//System.out.println("Seadare "+getHeight()+" " +getHeight());
		for (int i = 0; i < NDECADES; i++) {
			
			add(year);
			add(line);
			line=new GLine(line.getX()+getWidth()/NDECADES, 0, line.getX()+getWidth()/NDECADES, this.getHeight());
			year=new GLabel(START_DECADE+(i+1)*10+"",year.getX()+getWidth()/NDECADES,year.getY());
		}
		
		line= new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		for (int i = 0; i < 2; i++) {
			add(line);
			line=new GLine(0,getHeight()-GRAPH_MARGIN_SIZE, getWidth(), getHeight()-GRAPH_MARGIN_SIZE);
		}
	}

	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		entries.clear();
		update();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		if(!entry.equals(null) && !entries.contains(entry)) {
			entries.add(entry);
			update();
		}
		
	}
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		drawDefault();
		for (int i = 0; i < entries.size(); i++) {
			drawEntry(entries.get(i));
		}
	}
	
	
	
	//drawing every entry
	private void drawEntry(NameSurferEntry nameSurferEntry) {
		GLine line=null;
		GLabel lable=new GLabel(nameSurferEntry.getName());
		double x=0;
		Color color=generateColor(nameSurferEntry);
		for (int i = 0; i < NDECADES-1; i++) {
			line=new GLine(x, getCoordinant(nameSurferEntry.getRank(i)), x+getWidth()/NDECADES, getCoordinant(nameSurferEntry.getRank(i+1)));
			lable=generateLable(nameSurferEntry,nameSurferEntry.getRank(i),line.getLocation());
			line.setColor(color);
			lable.setColor(color);
			add(line);
			add(lable);
			x+=getWidth()/NDECADES;
		}
		lable=generateLable(nameSurferEntry,nameSurferEntry.getRank(NDECADES-1),line.getEndPoint());
		lable.setColor(color);
		add(lable);
	}

	//generating the label according to the rank 
	private GLabel generateLable(NameSurferEntry nameSurferEntry, int rank, GPoint point) {
		GLabel lable=new GLabel(nameSurferEntry.getName()+" "+rank);
		if(rank==0) {
			char star=42;
			lable=new GLabel(nameSurferEntry.getName()+" "+ star);
		}
		lable.setLocation(point.getX()+2,point.getY()-lable.getDescent());
		return lable;
	}

	//generate the color of the label
	private Color generateColor(NameSurferEntry nameSurferEntry) {
		entries.indexOf(nameSurferEntry);
		switch (entries.indexOf(nameSurferEntry)%5) {
		case 0:
			return Color.BLACK;
		case 1:
			return Color.RED;
		case 2:
			return Color.BLUE;
		case 3:
			return Color.CYAN;
		case 4:
			return Color.GREEN;
		default:
			return Color.ORANGE;
		}
	}

	//calculating the coordinant according to the given rank
	private double getCoordinant(int rank) {
		double h=getHeight()-2*GRAPH_MARGIN_SIZE;
		double gap=h/(MAX_RANK-1);
		if(rank==0) {
			return gap*(MAX_RANK-1)+GRAPH_MARGIN_SIZE;
		}
		return (rank-1)*gap+GRAPH_MARGIN_SIZE;
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
