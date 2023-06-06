import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

public class ExtensionFacePamphletCanvas extends GCanvas implements FacePamphletConstants, ComponentListener {
	private GLabel name;
	
	private GImage image;
	
	private GLabel status;
	
	private GLabel friends;
	
	private GLabel message;
	
	private GRect border;
	
	private GLabel noImage;
	
	private ArrayList<GLabel> friendsList;
	
	private ExtensionFacePamphletProfile recentProfile;
	
	private int mainWidth;
	
	private int mainHeight;
	
	private Font nameFont;
	
	private Font noImageFont;
	
	private Font statusFont;
	
	private Font friendsFont;
	
	private Font friendsListFont;
	
	private Font messageFont;
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public ExtensionFacePamphletCanvas() {
		
		
		addComponentListener(this);
		
		mainHeight=getHeight();
		mainWidth=getWidth();
		
		recentProfile=new ExtensionFacePamphletProfile("","");
		name=new  GLabel("name");
		name.setFont(PROFILE_NAME_FONT);
		name.setLocation((LEFT_MARGIN*getWidth())/mainWidth, (TOP_MARGIN*getHeight())/mainHeight+name.getAscent());
		nameFont=name.getFont();
		
		border=new GRect((IMAGE_WIDTH*getWidth())/mainWidth, (IMAGE_HEIGHT*getHeight())/mainHeight);
		border.setLocation((LEFT_MARGIN*getWidth())/mainWidth, name.getY()+name.getDescent()+(IMAGE_MARGIN*getHeight())/mainHeight);
		
		noImage=new  GLabel("No Image");
		noImage.setFont(PROFILE_IMAGE_FONT);
		noImage.setLocation(border.getX()+border.getWidth()/2-noImage.getWidth()/2, border.getY()+(border.getHeight()-noImage.getHeight())/2+noImage.getAscent());
		noImageFont=noImage.getFont();
		
		image=new GImage("");
		image.setLocation(border.getLocation());
		
		status=new GLabel("");
		status.setLocation((LEFT_MARGIN*getWidth())/mainWidth, (STATUS_MARGIN*getHeight())/mainHeight+image.getY()+image.getHeight()+status.getAscent());
		status.setFont(PROFILE_STATUS_FONT);
		statusFont=status.getFont();
		
		friends=new GLabel("Friends:");
		friends.setLocation(getWidth()/2,border.getY());
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		friendsFont=friends.getFont();
		
		friendsList=new ArrayList<>();
		GLabel friendsListForFont=new GLabel("");
		friendsListForFont.setFont(PROFILE_FRIEND_FONT);
		friendsListFont=friendsListForFont.getFont();
		
		message=new GLabel("");
		message.setFont(MESSAGE_FONT);
		message.setLabel("Welcome");
		message.setLocation((getWidth()-message.getWidth())/2, getHeight()-(BOTTOM_MESSAGE_MARGIN*getHeight())/mainHeight );
		messageFont=message.getFont();
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		message.setLabel(msg);
		//message.setLocation((getWidth()-message.getWidth())/2, getHeight()-(BOTTOM_MESSAGE_MARGIN*getHeight())/mainHeight );
		message.setFont(new Font(message.getFont()+" ",message.getFont().getStyle(),(messageFont.getSize()*getWidth())/mainWidth));
		message.setLocation((getWidth()-message.getWidth())/2, getHeight()-(BOTTOM_MESSAGE_MARGIN*getHeight())/mainHeight );
		
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
	public void displayProfile(ExtensionFacePamphletProfile profile) {
		
		removeAll();
		recentProfile=profile;
		if(profile!=null && !profile.getName().equals("")) {
			
		addUserName(profile);
		addPicture(profile);
		addStatus(profile);
		addFriends(profile);
		addFriendList(profile);
		showMessage("Displaying " + profile.getName());
		}
	}


	//adding friend list on canvas
	private void addFriendList(ExtensionFacePamphletProfile profile) {
		double friendDist=friends.getHeight()+(2*getHeight())/mainHeight;
		if(profile.getFriends()!=null) {
			Iterator<String> it= profile.getFriends();
			Iterator<String> itRelation=profile.getFriendsRelation();
		int y=0;
		while(it.hasNext()) {
			y++;
			GLabel friend=new GLabel(it.next()+"  "+itRelation.next());
			friend.setFont(PROFILE_FRIEND_FONT );
			//friend.setLocation(getWidth()/2,friends.getY()+y*friendDist);
			friend.setFont(new Font(friend.getFont()+" ",friend.getFont().getStyle(),(friendsListFont.getSize()*getWidth())/mainWidth));
			friend.setLocation(getWidth()/2,friends.getY()+y*friendDist);
			
			add(friend);
		}
		
		}
	}


	//adding user Name on canvas
	private void addUserName(ExtensionFacePamphletProfile profile) {
		name.setLabel(profile.getName());
		name.setColor(Color.BLUE);
		//name.setLocation((LEFT_MARGIN*getWidth())/mainWidth, (TOP_MARGIN*getHeight())/mainHeight+name.getAscent());
		name.setFont(new Font(name.getFont()+" ",name.getFont().getStyle(),(nameFont.getSize()*getWidth())/mainWidth));
		name.setLocation((LEFT_MARGIN*getWidth())/mainWidth, (TOP_MARGIN*getHeight())/mainHeight+name.getAscent());
		
		add(name);
	}


	//adding status on canvas
	private void addStatus(ExtensionFacePamphletProfile profile) {
		if(profile.getStatus().isEmpty()) {
			status.setLabel("No current status");
		}else {status.setLabel(new StringTokenizer(profile.getName()).nextToken()+" is "+profile.getStatus());}
		//status.setLocation((LEFT_MARGIN*getWidth())/mainWidth, (STATUS_MARGIN*getHeight())/mainHeight+border.getY()+border.getHeight()+status.getAscent());
		status.setFont(new Font(status.getFont()+" ",status.getFont().getStyle(),(statusFont.getSize()*getWidth())/mainWidth));
		status.setLocation((LEFT_MARGIN*getWidth())/mainWidth, (STATUS_MARGIN*getHeight())/mainHeight+border.getY()+border.getHeight()+status.getAscent());
		
		add(status);
	}


	//adding friends Label on canvas
	private void addFriends(ExtensionFacePamphletProfile profile) {
		//friends.setLocation(getWidth()/2,border.getY());
		friends.setFont(new Font(friends.getFont()+" ",friends.getFont().getStyle(),(friendsFont.getSize()*getWidth())/mainWidth));
		friends.setLocation(getWidth()/2,border.getY());
		
		add(friends);
	}


	//adding picture on canvas
	private void addPicture(ExtensionFacePamphletProfile profile) {
		if(profile.getImage()!=null) {
			remove(noImage);
			remove(border);	
			image.setLocation((LEFT_MARGIN*getWidth())/mainWidth, name.getY()+name.getDescent()+((IMAGE_MARGIN*getHeight())/mainHeight));
			image.setImage(profile.getImage().getImage());
			border.setBounds((LEFT_MARGIN*getWidth())/mainWidth, name.getY()+name.getDescent()+((IMAGE_MARGIN*getHeight())/mainHeight), (IMAGE_WIDTH*getWidth())/mainWidth, (IMAGE_HEIGHT*getHeight())/mainHeight);
			image.scale((IMAGE_WIDTH*getWidth()/mainWidth)/image.getWidth(), (IMAGE_HEIGHT*getHeight()/mainHeight)/image.getHeight());
			noImage.setLocation(border.getX()+border.getWidth()/2-noImage.getWidth()/2, border.getY()+(border.getHeight()-noImage.getHeight())/2+noImage.getAscent());
			
			add(image);
		}else {
			border.setBounds((LEFT_MARGIN*getWidth())/mainWidth, name.getY()+name.getDescent()+((IMAGE_MARGIN*getHeight())/mainHeight), (IMAGE_WIDTH*getWidth())/mainWidth, (IMAGE_HEIGHT*getHeight())/mainHeight);
			//noImage.setLocation(border.getX()+border.getWidth()/2-noImage.getWidth()/2, border.getY()+(border.getHeight()-noImage.getHeight())/2+noImage.getAscent());
			noImage.setFont(new Font(noImage.getFont()+" ",noImage.getFont().getStyle(),(noImageFont.getSize()*getWidth())/mainWidth));
			noImage.setLocation(border.getX()+border.getWidth()/2-noImage.getWidth()/2, border.getY()+(border.getHeight()-noImage.getHeight())/2+noImage.getAscent());
			
			add(noImage);
			add(border);
		}
	}
	
	private void update() {
		if(mainHeight==0) {
			mainHeight=getHeight();
			mainWidth=getWidth();
		}
		removeAll();
		displayProfile(recentProfile);
	}
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }

}
