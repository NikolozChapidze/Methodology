import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import acm.program.Program;

public class ExtensionNameSurfer extends Program implements NameSurferConstants {

	private NameSurferDataBase dataBase;

	private ExtensionNameSurferGraph canvas;

	private JLabel lable;

	private JTextField name;

	private JButton graph;

	private JButton clear;

	private JTextField deleteText;

	private JLabel deleteLabel;

	private JButton deleteButton;

	private JComboBox colorBox;
	
	private JRadioButton linesGraph;
	
	private JRadioButton columnsGraph;
	
	private JRadioButton tableGraph;
	
	private ButtonGroup groupGraph;

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT - getHeight() + APPLICATION_HEIGHT);
		// System.out.println(getWidth()+" "+getHeight());
		addComponents();
		addActionListeners();
		addGrapSelection();
	}

	//adding graph selection componenets and action listeners 
	private void addGrapSelection() {
		groupGraph = new ButtonGroup();
		linesGraph= new JRadioButton("Line Graph");
		columnsGraph=new JRadioButton("Column Graph");
		tableGraph=new JRadioButton("Table Graph");
		
		groupGraph.add(linesGraph);
		groupGraph.add(columnsGraph);
		groupGraph.add(tableGraph);
		linesGraph.setSelected(true);
		

		add(linesGraph,NORTH);
		add(columnsGraph,NORTH);
		add(tableGraph,NORTH);
		
		linesGraph.addActionListener(this);
		columnsGraph.addActionListener(this);
		tableGraph.addActionListener(this);
	}

	//adding other components (buttons, teftfields...)
	private void addComponents() {
		canvas = new ExtensionNameSurferGraph();
		lable = new JLabel("Name");
		name = new JTextField(10);
		graph = new JButton("Graph");
		clear = new JButton("Clear");
		deleteText = new JTextField(10);
		deleteLabel = new JLabel("Delete Name");
		deleteButton = new JButton("Delete");
		colorBox = new JComboBox<String>(new String[] {"Green", "Blue", "Gray", "Red", "Purple", "Orange", "Yellow", "Cyan", "Magenta" });
		colorBox.setSelectedIndex(-1);
		
		add(canvas, CENTER);
		add(lable, SOUTH);
		add(name, SOUTH);
		add(colorBox, SOUTH);
		add(graph, SOUTH);
		add(clear, SOUTH);
		add(deleteLabel, SOUTH);
		add(deleteText, SOUTH);
		add(deleteButton, SOUTH);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	
	//acting according to the action that is performed
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object.equals(graph)) {
			if (!name.getText().equals(null) && !(dataBase.findEntry(name.getText()) == null)) {
				// System.out.println(name.getText() + " " +
				// dataBase.findEntry(name.getText()));
				if(colorBox.getSelectedIndex()!=-1) {
					canvas.addEntry(dataBase.findEntry(name.getText()),(String)colorBox.getSelectedItem());
				}else {
					canvas.addEntry(dataBase.findEntry(name.getText()),null);
				}
				
			}
			
			colorBox.setSelectedIndex(-1);
			name.setText("");
		}
		
		if (object.equals(deleteButton)) {
			if (!deleteText.getText().equals(null) && !(dataBase.findEntry(deleteText.getText()) == null)) {
				// System.out.println(name.getText() + " " +
				// dataBase.findEntry(name.getText()));

				canvas.deleteEntry(dataBase.findEntry(deleteText.getText()));
			}
			deleteText.setText("");
		}
		
		if(object.equals(columnsGraph) || object.equals(linesGraph) || object.equals(tableGraph)) {
			canvas.changeGraph(e.getActionCommand());
		}

		if (object.equals(clear)) {
			canvas.clear();
		}

	}
}
