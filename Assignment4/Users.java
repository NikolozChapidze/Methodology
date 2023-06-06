import java.util.ArrayList;

public class Users {
	private String users;
	private int usersScores;

	public Users() {
		super();
	}

	public Users(String users, int usersScores) {
		super();
		this.users = users;
		this.usersScores = usersScores;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public int getUsersScores() {
		return usersScores;
	}

	public void setUsersScores(int usersScores) {
		this.usersScores = usersScores;
	}

	@Override
	public String toString() {
		String s=this.users+" : "+this.usersScores;
		return s;
	}
	
}
