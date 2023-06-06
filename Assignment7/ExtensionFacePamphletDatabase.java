import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import acm.graphics.*;
import java.util.*;

public class ExtensionFacePamphletDatabase implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * database.
	 */
	HashMap<String, ExtensionFacePamphletProfile> profileBase;
	HashMap<String, String> photoPath;

	public ExtensionFacePamphletDatabase() {
		profileBase = new HashMap<String, ExtensionFacePamphletProfile>();
		photoPath=new HashMap<String, String>();
		try {
			FileReader fr = new FileReader("DataBase.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			
			while (line != null) {
				String path="";
				StringTokenizer tokens=new StringTokenizer(line, "$");
				ExtensionFacePamphletProfile profile=new ExtensionFacePamphletProfile(tokens.nextToken(),tokens.nextToken());
				
				path=tokens.nextToken();
				if(!path.equals("null")) {
					//System.out.println(path+"  "+"egaaa");
					profile.setImage(new GImage(path));
				}
				String status=tokens.nextToken();
				if(status.equals("null")) {
					profile.setStatus("");
				}else {
					//System.out.println("modis");
					profile.setStatus(status);}
				
				while (tokens.hasMoreTokens()) {
					profile.addFriend(tokens.nextToken(),tokens.nextToken());
				}
				profileBase.put(profile.getName(), profile);
				photoPath.put(profile.getName(), path);
				line = br.readLine();
			}
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("s");
			e.printStackTrace();
			
		} catch (IOException e) {
			System.out.println("ss");
			e.printStackTrace();
		}
	}
	
	public void saveDataBase() {
		try {
			FileWriter myWriter = new FileWriter("DataBase.txt");
			BufferedWriter writer = new BufferedWriter(myWriter);
			int counter=0;
			for(String key : profileBase.keySet()) {
				String fullProfileString="";
				ExtensionFacePamphletProfile profile=profileBase.get(key);
				fullProfileString=profile.getName()+"$"+profile.getPassword()+"$"+photoPath.get(key)+"$";
				if(profile.getStatus().equals("")) {
					fullProfileString+="null$";
				}else {fullProfileString+=profile.getStatus()+"$";}
				Iterator<String> it = profile.getFriends();
				Iterator<String> itRelation=profile.getFriendsRelation();
				while (it.hasNext()) {
					fullProfileString+=it.next()+"$"+itRelation.next()+"$";
				}
				
				writer.write(fullProfileString);
				counter++;
				if (counter != profileBase.size()) {
					writer.newLine(); 
				}
				
			}

			writer.close();
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method adds the given profile to the database. If the name associated
	 * with the profile is the same as an existing name in the database, the
	 * existing profile is replaced by the new profile passed in.
	 */
	public void addProfile(ExtensionFacePamphletProfile profile) {
		profileBase.put(profile.getName(), profile);
	}
	
	public void addPhoto(ExtensionFacePamphletProfile profile, String photo) {
		photoPath.put(profile.getName(), photo);
	}

	/**
	 * This method returns the profile associated with the given name in the
	 * database. If there is no profile in the database with the given name, the
	 * method returns null.
	 */
	public ExtensionFacePamphletProfile getProfile(String name) {
		if (profileBase.containsKey(name)) {
			return profileBase.get(name);
		}

		return null;
	}

	/**
	 * This method removes the profile associated with the given name from the
	 * database. It also updates the list of friends of all other profiles in the
	 * database to make sure that this name is removed from the list of friends of
	 * any other profile.
	 * 
	 * If there is no profile in the database with the given name, then the database
	 * is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if (profileBase.containsKey(name)) {
			Iterator<String> it = profileBase.get(name).getFriends();
			while (it.hasNext()) {
				profileBase.get(it.next()).removeFriend(name);
			}
			profileBase.remove(name);
			photoPath.remove(name);
		}
	}

	/**
	 * This method returns true if there is a profile in the database that has the
	 * given name. It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		if (profileBase.containsKey(name))return true;
		return false;
	}
	
}
