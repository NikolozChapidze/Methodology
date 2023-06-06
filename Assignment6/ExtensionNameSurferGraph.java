import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.graphics.GRect;

public class ExtensionNameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {
//GCanvas canvas;
	private ArrayList<NameSurferEntry> entries;
	private ArrayList<Color> entryColors;
	private String graphType;

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public ExtensionNameSurferGraph() {
		entries = new ArrayList<NameSurferEntry>();
		entryColors = new ArrayList<Color>();
		graphType = "Line Graph";

		addComponentListener(this);
		drawDefault();
	}

	//drawing default look of the graph according to the graph type
	private void drawDefault() {
		if (graphType.equals("Table Graph")) {
			drawTableDefault();
		} else {
			drawLineColumnDefault();
		}
	}

	//drawing the table graph's default look
	private void drawTableDefault() {
		GLabel year = new GLabel(START_DECADE + "");
		double rowHeights= (getHeight()-(2*GRAPH_MARGIN_SIZE))/6.0;
		if(entries.size()>5) {
			rowHeights= (getHeight()-(2*GRAPH_MARGIN_SIZE))/(entries.size()+1.0);
		}
		double columnHeightsEnd=GRAPH_MARGIN_SIZE+rowHeights*(entries.size()+1.0);
		double columnGap=(getWidth()-(2*GRAPH_MARGIN_SIZE))/(NDECADES+1.0);
		//System.out.println(columnGap+" "+(getWidth()-GRAPH_MARGIN_SIZE)+" "+((NDECADES+1)*columnGap) );
		GLine line = new GLine(GRAPH_MARGIN_SIZE, GRAPH_MARGIN_SIZE, GRAPH_MARGIN_SIZE, columnHeightsEnd);
		year.setLocation(GRAPH_MARGIN_SIZE+columnGap+(columnGap/2.0)-year.getWidth()/2.0,GRAPH_MARGIN_SIZE+(rowHeights-year.getHeight())/2.0+year.getAscent());
		for (int i = 0; i < NDECADES+2; i++) {

			add(year);
			add(line);
			line = new GLine(line.getX() + columnGap,  GRAPH_MARGIN_SIZE, line.getX() + columnGap,columnHeightsEnd);
			year = new GLabel(START_DECADE + (i + 1) * 10 + "", year.getLocation().getX()+columnGap,year.getLocation().getY());
		}
		
		line = new GLine(GRAPH_MARGIN_SIZE, GRAPH_MARGIN_SIZE, getWidth()-GRAPH_MARGIN_SIZE, GRAPH_MARGIN_SIZE);
		for (int i = 0; i < entries.size()+2; i++) {
			add(line);
			line = new GLine(GRAPH_MARGIN_SIZE,  line.getY()+rowHeights,  getWidth()-GRAPH_MARGIN_SIZE,line.getY()+rowHeights);
			
		}
	}

	//drawing the default looks of the line and column type of graph
	private void drawLineColumnDefault() {
		GLine line = new GLine(0, 0, 0, getHeight());
		GLabel year = new GLabel(START_DECADE + "");
		year.setLocation(5, getHeight() - (GRAPH_MARGIN_SIZE - year.getHeight()) / 2 - year.getDescent());
		// System.out.println("Seadare "+getHeight()+" " +getHeight());
		for (int i = 0; i < NDECADES; i++) {

			add(year);
			add(line);
			line = new GLine(line.getX() + getWidth() / NDECADES, 0, line.getX() + getWidth() / NDECADES,
					this.getHeight());
			year = new GLabel(START_DECADE + (i + 1) * 10 + "", year.getX() + getWidth() / NDECADES, year.getY());
		}

		line = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		for (int i = 0; i < 2; i++) {
			add(line);
			line = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		}

	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		entries.clear();
		entryColors.clear();
		update();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is drawn by calling update.
	 * 
	 * @param string
	 */
	public void addEntry(NameSurferEntry entry, String stringColor) {
		if (!entry.equals(null) && !entries.contains(entry)) {
			entries.add(entry);
			entryColors.add(generateColor(stringColor));
			update();
		}

	}

	//deleting entry
	public void deleteEntry(NameSurferEntry entry) {
		if (!entry.equals(null) && entries.contains(entry)) {
			entryColors.remove(entries.indexOf(entry));
			entries.remove(entry);
			update();
		}

	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		drawDefault();
		for (int i = 0; i < entries.size(); i++) {
			drawEntry(entries.get(i));
		}
	}

	//drawing entry according to the selected graph
	private void drawEntry(NameSurferEntry nameSurferEntry) {
		if (graphType.equals("Table Graph"))
			drawTableEntry(nameSurferEntry);
		if (graphType.equals("Column Graph"))
			drawColumnEntry(nameSurferEntry);
		if (graphType.equals("Line Graph"))
			drawLineEntry(nameSurferEntry);
	}

	//drawing the entries for line Graph
	private void drawLineEntry(NameSurferEntry nameSurferEntry) {
		GLine line = null;
		GLabel lable = new GLabel(nameSurferEntry.getName());
		// System.out.println(lable.getFont().getSize()+" "+getHeight()+" "+getWidth());
		double x = 0;
		Color color = entryColors.get(entries.indexOf(nameSurferEntry));
		for (int i = 0; i < NDECADES - 1; i++) {
			line = new GLine(x, getCoordinant(nameSurferEntry.getRank(i)), x + getWidth() / NDECADES,
					getCoordinant(nameSurferEntry.getRank(i + 1)));
			lable = generateLable(nameSurferEntry, nameSurferEntry.getRank(i), line.getLocation());
			line.setColor(color);
			lable.setColor(color);
			add(line);
			add(lable);
			x += getWidth() / NDECADES;
		}
		lable = generateLable(nameSurferEntry, nameSurferEntry.getRank(NDECADES - 1), line.getEndPoint());
		lable.setColor(color);
		add(lable);
	}

	//drawing the entries for column Graph
	private void drawColumnEntry(NameSurferEntry nameSurferEntry) {
		GRect column = null;
		GLabel lable = new GLabel(nameSurferEntry.getName());
		double x = (getWidth() / NDECADES)/(entries.size())*entries.indexOf(nameSurferEntry);
		double width=(getWidth() / NDECADES)/(entries.size());
		double y=0;
		Color color = entryColors.get(entries.indexOf(nameSurferEntry));
		for (int i = 0; i < NDECADES - 1; i++) {
			y= getCoordinant(nameSurferEntry.getRank(i));
			column=new GRect(x,y, width, getHeight() - GRAPH_MARGIN_SIZE-y);
			lable = generateLable(nameSurferEntry, nameSurferEntry.getRank(i), column.getLocation());
			column.setColor(color);
			column.setFilled(true);
			lable.setColor(color);
			
			add(column);
			column.sendToBack();
			add(lable);
			x += getWidth() / NDECADES;
		}
		y= getCoordinant(nameSurferEntry.getRank(NDECADES-1));
		column=new GRect(x,y, width, getHeight() - GRAPH_MARGIN_SIZE-y);
		column.setColor(color);
		column.setFilled(true);
		
		add(column);
		column.sendToBack();
		lable = generateLable(nameSurferEntry, nameSurferEntry.getRank(NDECADES - 1), new GPoint(x, y));
		lable.setColor(color);
		lable.sendToFront();
		add(lable);
	}

	//drawing the entries for table Graph
	private void drawTableEntry(NameSurferEntry nameSurferEntry) {
		double rowHeights= (getHeight()-(2*GRAPH_MARGIN_SIZE))/6.0;
		if(entries.size()>5) {
			rowHeights= (getHeight()-(2*GRAPH_MARGIN_SIZE))/(entries.size()+1.0);
		}
		//double columnHeightsEnd=GRAPH_MARGIN_SIZE+rowHeights*(entries.size()+1.0);
		double columnGap=(getWidth()-(2*GRAPH_MARGIN_SIZE))/(NDECADES+1.0);
		GLabel label=new GLabel(nameSurferEntry.getName());
		double y=GRAPH_MARGIN_SIZE+rowHeights*(entries.indexOf(nameSurferEntry)+1.0)+(rowHeights-label.getHeight())/2.0+label.getAscent();
		label.setLocation(GRAPH_MARGIN_SIZE+(columnGap-label.getWidth())/2.0,y);
		label.setColor(entryColors.get(entries.indexOf(nameSurferEntry)));
		add(label);
		for (int i = 0; i < NDECADES; i++) {
			label=new GLabel(nameSurferEntry.getRank(i)+"");
			label.setLocation(GRAPH_MARGIN_SIZE+columnGap*(i+1)+(columnGap-label.getWidth())/2.0,y);
			label.setColor(entryColors.get(entries.indexOf(nameSurferEntry)));
			add(label);
		}
		
	}

	//generating the label according to the rank
	private GLabel generateLable(NameSurferEntry nameSurferEntry, int rank, GPoint point) {
		GLabel lable = new GLabel(nameSurferEntry.getName() + " " + rank);
		if (rank == 0) {
			char star = 42;
			lable = new GLabel(nameSurferEntry.getName() + " " + star);
		}
		lable.setLocation(point.getX() + 2, point.getY() - lable.getDescent());
		lable.setFont(new Font("Serif", Font.PLAIN, getHeight() / 39));
		return lable;
	}

	//getting the color according to the selection
	private Color generateColor(String stringColor) {
		if (stringColor == null)
			return Color.BLACK;
		if (stringColor.equals("Green"))
			return Color.GREEN;
		if (stringColor.equals("Blue"))
			return Color.BLUE;
		if (stringColor.equals("Gray"))
			return Color.GRAY;
		if (stringColor.equals("Red"))
			return Color.RED;
		if (stringColor.equals("Purple"))
			return new Color(128, 0, 128);
		if (stringColor.equals("Orange"))
			return Color.ORANGE;
		if (stringColor.equals("Yellow"))
			return Color.YELLOW;
		if (stringColor.equals("Cyan"))
			return Color.CYAN;
		if (stringColor.equals("Magenta"))
			return Color.MAGENTA;

		return Color.BLACK;
	}

	//getting the coordinants according to the given rank
	private double getCoordinant(int rank) {
		double h = getHeight() - 2 * GRAPH_MARGIN_SIZE;
		double gap = h / (MAX_RANK - 1);
		if (rank == 0) {
			return gap * (MAX_RANK - 1) + GRAPH_MARGIN_SIZE;
		}
		return (rank - 1) * gap + GRAPH_MARGIN_SIZE;
	}

	//changing graph type as different radioButtons are selected
	public void changeGraph(String actionCommand) {
		graphType = actionCommand;
		update();
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

}
