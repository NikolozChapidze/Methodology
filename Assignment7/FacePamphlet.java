
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	private FacePamphletDatabase dataBase;

	private FacePamphletCanvas canvas;
	
	//private ExtensionFacePamphletCanvas canvas;

	private JLabel nameLable;

	private JTextField nameTextField;

	private JButton add;

	private JButton delete;

	private JButton lookup;

	private JTextField statusTextField;

	private JButton statusButton;

	private JTextField pictureTextField;

	private JButton pictureButton;

	private JTextField friendTextField;

	private JButton friendButton;

	private FacePamphletProfile recentProfile;

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		dataBase = new FacePamphletDatabase();
		recentProfile = new FacePamphletProfile("");
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT - getHeight() + APPLICATION_HEIGHT);
		// System.out.println(getWidth()+" "+getHeight());
		addComponents();

		friendTextField.addActionListener(this);
		pictureTextField.addActionListener(this);
		statusTextField.addActionListener(this);

		addActionListeners();
	}

	//adding components
	private void addComponents() {

		componentsInit();

		add(nameLable, NORTH);
		add(nameTextField, NORTH);
		add(add, NORTH);
		add(delete, NORTH);
		add(lookup, NORTH);
		add(canvas, CENTER);

		add(statusTextField, WEST);
		add(statusButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(pictureTextField, WEST);
		add(pictureButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friendTextField, WEST);
		add(friendButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
	}

	//components initialization
	private void componentsInit() {

		canvas = new FacePamphletCanvas();
		//canvas=new ExtensionFacePamphletCanvas();
		
		nameLable = new JLabel("Name");
		nameTextField = new JTextField(TEXT_FIELD_SIZE);
		add = new JButton("Add");
		delete = new JButton("Delete");
		lookup = new JButton("Lookup");

		statusTextField = new JTextField(TEXT_FIELD_SIZE);
		statusButton = new JButton("Change Status");

		pictureTextField = new JTextField(TEXT_FIELD_SIZE);
		pictureButton = new JButton("Change Picture");

		friendTextField = new JTextField(TEXT_FIELD_SIZE);
		friendButton = new JButton("Add friend");

	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object.equals(add) && !nameTextField.getText().equals(null)) {
			addProfile();
		}
		if (object.equals(delete) && !nameTextField.getText().equals(null)) {
			deleteProfile();
		}
		if (object.equals(lookup) && !nameTextField.getText().equals(null)) {
			lookupProfile();
		}
		if ((object.equals(statusButton) || object.equals(statusTextField))
				&& !statusTextField.getText().equals(null)) {
			changeStatus();
		}
		if ((object.equals(pictureButton) || object.equals(pictureTextField))
				&& !pictureTextField.getText().equals(null)) {
			changePicture();
		}
		if ((object.equals(friendButton) || object.equals(friendTextField))
				&& !friendTextField.getText().equals(null)) {
			addFriend();
		}
	}

	//adding profile
	private void addProfile() {
		if (dataBase.getProfile(nameTextField.getText()) != null) {
			// System.out.println(nameTextField.getText() + " " +
			// dataBase.getProfile(nameTextField.getText()));
			canvas.displayProfile(dataBase.getProfile(nameTextField.getText()));
			canvas.showMessage("A profile with the name " + nameTextField.getText() + " already exists");
		} else {
			dataBase.addProfile(new FacePamphletProfile(nameTextField.getText()));
			canvas.displayProfile(dataBase.getProfile(nameTextField.getText()));
			canvas.showMessage("New profile created");
		}
		recentProfile = dataBase.getProfile(nameTextField.getText());
	}

	//delete profile
	private void deleteProfile() {
		if (dataBase.getProfile(nameTextField.getText()) != null) {
			// System.out.println(nameTextField.getText() + " " +
			// dataBase.getProfile(nameTextField.getText()));

			dataBase.deleteProfile(nameTextField.getText());
			canvas.displayProfile(null);
			canvas.showMessage("Profile of " + nameTextField.getText() + " deleted");
		} else {
			canvas.showMessage("Profile of " + nameTextField.getText() + " doesn't exists");
		}

		recentProfile = new FacePamphletProfile("");
	
	}

	//look up profile
	private void lookupProfile() {
		if (dataBase.getProfile(nameTextField.getText()) != null) {
			canvas.displayProfile(dataBase.getProfile(nameTextField.getText()));
			canvas.showMessage("Displaying " + nameTextField.getText());
			recentProfile = dataBase.getProfile(nameTextField.getText());
		} else {
			canvas.showMessage("A profile with the name of " + nameTextField.getText() + " doesn't exists");
			recentProfile = new FacePamphletProfile("");
		}
	}

	//changing status
	private void changeStatus() {
		if (recentProfile.getName().equals("")) {
			canvas.showMessage("Please select a profile to change status");
		} else {
			recentProfile.setStatus(statusTextField.getText());
			dataBase.addProfile(recentProfile);
			canvas.displayProfile(recentProfile);
			canvas.showMessage("Status updated to " + statusTextField.getText());
		}
		statusTextField.setText("");
	}

	//changing picture
	private void changePicture() {
		if (recentProfile.getName().equals("")) {
			canvas.showMessage("Please select a profile to change picture");
		} else {
			GImage image = null;
			try {
				image = new GImage(pictureTextField.getText());
			} catch (ErrorException ex) {
				canvas.showMessage("Unable to open image file: " + pictureTextField.getText());
			}
			if (image != null) {
				recentProfile.setImage(image);
				dataBase.addProfile(recentProfile);
				canvas.displayProfile(recentProfile);
				canvas.showMessage("Picture updated");
			}
		}
		pictureTextField.setText("");
	}

	//adding friends
	private void addFriend() {
		if (recentProfile.getName().equals("")) {
			canvas.showMessage("Please select a profile to add friend");
		} else {
			if (!recentProfile.getName().equals(friendTextField.getText())) {
				if (dataBase.containsProfile(friendTextField.getText())) {
					if (dataBase.getProfile(recentProfile.getName()).addFriend(friendTextField.getText())) {
						dataBase.getProfile(friendTextField.getText()).addFriend(recentProfile.getName());
						recentProfile = dataBase.getProfile(recentProfile.getName());
						canvas.displayProfile(recentProfile);
						canvas.showMessage(friendTextField.getText() + " added as a friend");
					} else {
						canvas.showMessage(
								recentProfile.getName() + " already has " + friendTextField.getText() + " as a friend");
					}
				} else {
					canvas.showMessage("Profile of " + friendTextField.getText() + " doesn't exists");
				}
			}
		}
		friendTextField.setText("");
	}
	
}
