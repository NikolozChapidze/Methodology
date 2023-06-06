import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import acm.graphics.GImage;
import acm.program.Program;
import acm.util.ErrorException;

public class ExtensionFacePamphlet extends Program implements FacePamphletConstants {

	private ExtensionFacePamphletDatabase dataBase;

//	private FacePamphletCanvas canvas;

	private ExtensionFacePamphletCanvas canvas;

	private JLabel nameLable;

	private JTextField password;

	private JLabel passwordLable;

	private JTextField nameTextField;

	private JButton add;

	private JButton delete;

	private JButton lookup;

	private JTextField statusTextField;

	private JButton statusButton;

	private JTextField pictureTextField;

	private JButton pictureButton;

	private JTextField friendTextField;

	private JComboBox relationshipBox;

	private JButton friendButton;

	private JButton logIn;

	private boolean logedin;

	private ExtensionFacePamphletProfile recentProfile;

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		dataBase = new ExtensionFacePamphletDatabase();
		recentProfile = new ExtensionFacePamphletProfile("", "");
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT - getHeight() + APPLICATION_HEIGHT);
		// System.out.println(getWidth()+" "+getHeight());
		addComponents();

		friendTextField.addActionListener(this);
		pictureTextField.addActionListener(this);
		statusTextField.addActionListener(this);

		addActionListeners();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				dataBase.saveDataBase();
				System.out.println("System was shutdown");
			}
		});
	}

	// adding components
	private void addComponents() {

		componentsInit();

		add(nameLable, NORTH);
		add(nameTextField, NORTH);
		add(passwordLable, NORTH);
		add(password, NORTH);
		add(add, NORTH);
		add(delete, NORTH);
		add(lookup, NORTH);
		add(logIn, NORTH);
		add(canvas, CENTER);

		add(statusTextField, WEST);
		add(statusButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(pictureTextField, WEST);
		add(pictureButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friendTextField, WEST);
		add(relationshipBox, WEST);
		add(friendButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
	}

	// components initialization
	private void componentsInit() {

		// canvas = new FacePamphletCanvas();
		canvas = new ExtensionFacePamphletCanvas();

		logedin = false;

		nameLable = new JLabel("Name");

		passwordLable = new JLabel("Password");

		password = new JTextField(TEXT_FIELD_SIZE - 6);

		nameTextField = new JTextField(TEXT_FIELD_SIZE - 3);
		add = new JButton("Add");
		delete = new JButton("Delete");
		lookup = new JButton("Lookup");

		logIn = new JButton("Sign in");

		statusTextField = new JTextField(TEXT_FIELD_SIZE);
		statusButton = new JButton("Change Status");

		pictureTextField = new JTextField(TEXT_FIELD_SIZE);
		pictureButton = new JButton("Change Picture");

		friendTextField = new JTextField(TEXT_FIELD_SIZE);

		relationshipBox = new JComboBox<String>(
				new String[] { "Parent", "Child", "Sibling", "Lover", "Wife", "Husband" });
		relationshipBox.setSelectedIndex(-1);

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
			nameTextField.setText("");
			password.setText("");
		}
		if (object.equals(delete) && !nameTextField.getText().equals(null)) {
			deleteProfile();
			nameTextField.setText("");
		}
		if (object.equals(lookup) && !nameTextField.getText().equals(null)) {
			lookupProfile();
			nameTextField.setText("");
		}
		if ((object.equals(statusButton) || object.equals(statusTextField))
				&& !statusTextField.getText().equals(null)) {
			changeStatus();
			statusTextField.setText("");
		}
		if ((object.equals(pictureButton) || object.equals(pictureTextField))
				&& !pictureTextField.getText().equals(null)) {
			changePicture();
			pictureTextField.setText("");
		}
		if ((object.equals(friendButton) || object.equals(friendTextField))
				&& !friendTextField.getText().equals(null)) {
			addFriend();
			friendTextField.setText("");
		}
		if (object.equals(logIn)) {
			signInProfile();
			password.setText("");
		}
	}

	private void signInProfile() {

		if (dataBase.getProfile(nameTextField.getText()) != null) {
			if (password.getText().equals(dataBase.getProfile(nameTextField.getText()).getPassword())) {
				canvas.displayProfile(dataBase.getProfile(nameTextField.getText()));
				canvas.showMessage("Signed into  " + nameTextField.getText());
				recentProfile = dataBase.getProfile(nameTextField.getText());
				logedin = true;
			} else {
				if (password.getText().length() < 1) {
					canvas.showMessage("Please input password");
					logedin = false;
				} else {
					logedin = false;
					canvas.showMessage("Password Incorrect ");
				}
			}
//			if(password.getText().length()<) {
//				
//			}
		} else {
			logedin = false;
			canvas.showMessage("A profile with the name of " + nameTextField.getText() + " doesn't exists");
			recentProfile = new ExtensionFacePamphletProfile("", "");
		}

	}

	// adding profile
	private void addProfile() {
		if (dataBase.getProfile(nameTextField.getText()) != null) {
			// System.out.println(nameTextField.getText() + " " +
			// dataBase.getProfile(nameTextField.getText()));
			canvas.displayProfile(dataBase.getProfile(nameTextField.getText()));
			canvas.showMessage("A profile with the name " + nameTextField.getText() + " already exists");
			logedin = false;
		} else {
			if (password.getText().length() >= 6) {
				dataBase.addProfile(new ExtensionFacePamphletProfile(nameTextField.getText(), password.getText()));

				dataBase.addPhoto(dataBase.getProfile(nameTextField.getText()), null);
				canvas.displayProfile(dataBase.getProfile(nameTextField.getText()));
				canvas.showMessage("New profile created");
				recentProfile = dataBase.getProfile(nameTextField.getText());
				password.setText("");
				logedin = true;
			} else {
				canvas.showMessage("Please input password more than 6 character");
				logedin = false;
			}

		}
		// recentProfile = dataBase.getProfile(nameTextField.getText());
	}

	// delete profile
	private void deleteProfile() {
		if (dataBase.getProfile(nameTextField.getText()) != null) {
			// System.out.println(nameTextField.getText() + " " +
			// dataBase.getProfile(nameTextField.getText()));
			if (logedin == true) {
				dataBase.deleteProfile(nameTextField.getText());
				canvas.displayProfile(null);
				canvas.showMessage("Profile of " + nameTextField.getText() + " deleted");
				logedin = false;
			} else {
				canvas.showMessage("Please log in");
			}
		} else {
			canvas.showMessage("Profile of " + nameTextField.getText() + " doesn't exists");
			logedin = false;
		}
		recentProfile = new ExtensionFacePamphletProfile("", "");
	}

	// look up profile
	private void lookupProfile() {
		if (dataBase.getProfile(nameTextField.getText()) != null) {
			canvas.displayProfile(dataBase.getProfile(nameTextField.getText()));
			canvas.showMessage("Displaying " + nameTextField.getText());
			logedin = false;
		} else {
			canvas.showMessage("A profile with the name of " + nameTextField.getText() + " doesn't exists");
			recentProfile = new ExtensionFacePamphletProfile("", "");
		}
	}

	// changing status
	private void changeStatus() {
		if (recentProfile.getName().equals("")) {
			canvas.showMessage("Please select a profile to change status");
		} else if (logedin == true) {

			recentProfile.setStatus(statusTextField.getText());
			dataBase.addProfile(recentProfile);
			canvas.displayProfile(recentProfile);
			canvas.showMessage("Status updated to " + statusTextField.getText());
		}
		if (logedin == false) {
			canvas.showMessage("Please log in");
		}
	}

	// changing picture
	private void changePicture() {
		if (recentProfile.getName().equals("")) {
			canvas.showMessage("Please select a profile to change picture");
		} else if (logedin == true) {
			GImage image = null;
			try {
				image = new GImage(pictureTextField.getText());
			} catch (ErrorException ex) {
				canvas.showMessage("Unable to open image file: " + pictureTextField.getText());
			}
			if (image != null) {
				recentProfile.setImage(image);

				dataBase.addProfile(recentProfile);
				dataBase.addPhoto(recentProfile, pictureTextField.getText());

				canvas.displayProfile(recentProfile);
				canvas.showMessage("Picture updated");
				// System.out.println(sun.awt.image.ToolkitImage@1cb129);
			}
		}

		if (logedin == false) {
			canvas.showMessage("Please log in");
		}
	}

	// adding friends
	private void addFriend() {
		if (recentProfile.getName().equals("")) {
			canvas.showMessage("Please select a profile to add friend");
		} else if (logedin == true) {
			if (!recentProfile.getName().equals(friendTextField.getText())) {
				if (dataBase.containsProfile(friendTextField.getText())) {

					String relation = " ";
					if (relationshipBox.getSelectedIndex() != -1) {
						relation = (String) relationshipBox.getSelectedItem();
					}

					if (dataBase.getProfile(recentProfile.getName()).addFriend(friendTextField.getText(), relation)) {
						dataBase.getProfile(friendTextField.getText()).addFriend(recentProfile.getName(),
								getRelationOposite(relation));
						recentProfile = dataBase.getProfile(recentProfile.getName());

						canvas.displayProfile(recentProfile);
						canvas.showMessage(friendTextField.getText() + " added as a friend");
						relationshipBox.setSelectedIndex(-1);
					} else {
						canvas.showMessage(
								recentProfile.getName() + " already has " + friendTextField.getText() + " as a friend");
					}
				} else {
					canvas.showMessage("Profile of " + friendTextField.getText() + " doesn't exists");
				}
			}
		}

		if (logedin == false) {
			canvas.showMessage("Please log in");
		}

	}

	private String getRelationOposite(String relation) {
		if (relation.equals("Parent")) {
			return "Child";
		}
		if (relation.equals("Child")) {
			return "Parent";
		}
		if (relation.equals("Wife")) {
			return "Husband";
		}
		if (relation.equals("Husband")) {
			return "Wife";
		}
		return relation;
	}

}
