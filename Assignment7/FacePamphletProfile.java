
/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what 
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */

import acm.graphics.*;
import java.util.*;

public class FacePamphletProfile implements FacePamphletConstants {

	private String name;

	private String status;

	private GImage image;

	private ArrayList<String> friends;
	/**
	 * Constructor This method takes care of any initialization needed for the
	 * profile.
	 */
	public FacePamphletProfile(String name) {
		this.name = name;
		this.status = "";
		this.image = null;
		this.friends = new ArrayList<>();
	}

	/** This method returns the name associated with the profile. */
	public String getName() {
		// You fill this in. Currently always returns the empty string.
		// return "";
		return this.name;
	}

	/**
	 * This method returns the image associated with the profile. If there is no
	 * image associated with the profile, the method returns null.
	 */
	public GImage getImage() {
		// You fill this in. Currently always returns null.
		// return null;
		return this.image;
	}

	/** This method sets the image associated with the profile. */
	public void setImage(GImage image) {
		// You fill this in
		this.image = image;
	}

	/**
	 * This method returns the status associated with the profile. If there is no
	 * status associated with the profile, the method returns the empty string ("").
	 */
	public String getStatus() {
		// You fill this in. Currently always returns the empty string.
		// return "";
		return this.status;
	}

	/** This method sets the status associated with the profile. */
	public void setStatus(String status) {
		// You fill this in
		this.status = status;
	}

	/**
	 * This method adds the named friend to this profile's list of friends. It
	 * returns true if the friend's name was not already in the list of friends for
	 * this profile (and the name is added to the list). The method returns false if
	 * the given friend name was already in the list of friends for this profile (in
	 * which case, the given friend name is not added to the list of friends a
	 * second time.)
	 */
	public boolean addFriend(String friend) {
		// You fill this in. Currently always returns true.
		if(friends!=null) {
		if (this.friends.contains(friend)) {
			return false;
		}
		}
		this.friends.add(friend);
		return true;
	}

	/**
	 * This method removes the named friend from this profile's list of friends. It
	 * returns true if the friend's name was in the list of friends for this profile
	 * (and the name was removed from the list). The method returns false if the
	 * given friend name was not in the list of friends for this profile (in which
	 * case, the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) {
		if (this.friends.contains(friend)) {
			int index=this.friends.indexOf(friend);
			this.friends.remove(friend);
			return true;
		}

		return false;
	}

	/**
	 * This method returns an iterator over the list of friends associated with the
	 * profile.
	 */
	public Iterator<String> getFriends() {
		if(friends!=null) {
			return friends.iterator();
		}
		return null;
	}
	
	/**
	 * This method returns a string representation of the profile. This string is of
	 * the form: "name (status): list of friends", where name and status are set
	 * accordingly and the list of friends is a comma separated list of the names of
	 * all of the friends in this profile.
	 * 
	 * For example, in a profile with name "Alice" whose status is "coding" and who
	 * has friends Don, Chelsea, and Bob, this method would return the string:
	 * "Alice (coding): Don, Chelsea, Bob"
	 */
	public String toString() {
		
		String result = (new StringTokenizer(name)).nextToken() + " " + "(" + status + "): " ;
		System.out.println(result);
		if(friends!=null) {
			if(friends.size()>1) {
			result+=friends.get(0);
		}
		for (int i = 1; i < friends.size(); i++) {
			result = result + ", " + friends.get(i);
		}
		}
		
		return result;
	}

}
