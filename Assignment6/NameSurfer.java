
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	private NameSurferDataBase dataBase;

	private NameSurferGraph canvas;

	private JLabel lable;

	private JTextField name;

	private JButton graph;

	private JButton clear;

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

	}

	//adding components 
	private void addComponents() {
		canvas = new NameSurferGraph();
		lable = new JLabel("Name");
		name = new JTextField(20);
		graph = new JButton("Graph");
		clear = new JButton("Clear");
		add(canvas, CENTER);
		add(lable, SOUTH);
		add(name, SOUTH);
		add(graph, SOUTH);
		add(clear, SOUTH);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object.equals(graph)) {
			if (!name.getText().equals(null) && 
					!(dataBase.findEntry(name.getText())==null)) {
				//System.out.println(name.getText() + " " + dataBase.findEntry(name.getText()));

				canvas.addEntry(dataBase.findEntry(name.getText()));
			}
			

			name.setText("");
		}
		if (object.equals(clear)) {
			canvas.clear();
		}

	}
}
