/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {
	private GLabel name;
	
	private GImage image;
	
	private GLabel status;
	
	private GLabel friends;
	
	private GLabel message;
	
	private GRect border;
	
	private GLabel noImage;
	
	private ArrayList<GLabel> friendsList;
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		name=new  GLabel("name");
		name.setFont(PROFILE_NAME_FONT);
		name.setLocation(LEFT_MARGIN, TOP_MARGIN+name.getAscent());
		
		border=new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
		border.setLocation(LEFT_MARGIN, name.getY()+name.getDescent()+IMAGE_MARGIN);
		
		noImage=new  GLabel("No Image");
		noImage.setFont(PROFILE_IMAGE_FONT);
		noImage.setLocation(border.getX()+border.getWidth()/2-noImage.getWidth()/2, border.getY()+(border.getHeight()-noImage.getHeight())/2+noImage.getAscent());
		
		image=new GImage("");
		image.setLocation(border.getLocation());
		
		status=new GLabel("");
		status.setLocation(LEFT_MARGIN, STATUS_MARGIN+image.getY()+image.getHeight()+status.getAscent());
		status.setFont(PROFILE_STATUS_FONT);
		
		friends=new GLabel("Friends:");
		friends.setLocation(getWidth()/2,border.getY());
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		
		friendsList=new ArrayList<>();
		
		message=new GLabel("");
		message.setFont(MESSAGE_FONT);
		message.setLabel("Welcome");
		message.setLocation((getWidth()-message.getWidth())/2, getHeight()-BOTTOM_MESSAGE_MARGIN );
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		message.setLabel(msg);
		message.setLocation((getWidth()-message.getWidth())/2, getHeight()-BOTTOM_MESSAGE_MARGIN );
		add(message);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		if(profile!=null) {
		addPicture(profile);
		
		addFriends(profile);
		
		addFriendList(profile);
	
		addUserName(profile);
		
		addStatus(profile);
	
		showMessage("Displaying " + profile.getName());
		}
	}


	//adding friend list on canvas
	private void addFriendList(FacePamphletProfile profile) {
		double friendDist=friends.getHeight()+2;
		if(profile.getFriends()!=null) {
			Iterator<String> it= profile.getFriends();
		int y=0;
		while(it.hasNext()) {
			y++;
			GLabel friend=new GLabel(it.next());
			friend.setFont(PROFILE_FRIEND_FONT );
			friend.setLocation(getWidth()/2,friends.getY()+y*friendDist);
			add(friend);
		}
		
		}
	}


	//adding user Name on canvas
	private void addUserName(FacePamphletProfile profile) {
		name.setLabel(profile.getName());
		name.setColor(Color.BLUE);
		name.setLocation(LEFT_MARGIN, TOP_MARGIN+name.getAscent());
		add(name);
	}


	//adding status on canvas
	private void addStatus(FacePamphletProfile profile) {
		if(profile.getStatus().isEmpty()) {
			status.setLabel("No current status");
		}else {status.setLabel(new StringTokenizer(profile.getName()).nextToken()+" is "+profile.getStatus());}
		status.setLocation(LEFT_MARGIN, STATUS_MARGIN+border.getY()+border.getHeight()+status.getAscent());
		add(status);
	}


	//adding friends Label on canvas
	private void addFriends(FacePamphletProfile profile) {
		friends.setLocation(getWidth()/2,border.getY());
		add(friends);
	}


	//adding picture on canvas
	private void addPicture(FacePamphletProfile profile) {
		if(profile.getImage()!=null) {
			remove(noImage);
			remove(border);	
			image.setImage(profile.getImage().getImage());
			image.scale(IMAGE_WIDTH/image.getWidth(), IMAGE_HEIGHT/image.getHeight());
			add(image);
		}else {
			add(noImage);
			add(border);
		}
	}
	
	
}
