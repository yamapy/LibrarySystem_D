package beans;

public class User {
	private String mailAdress;
	private String password;
	private int management;

	public String getMailAdress() {
		return mailAdress;
	}

	public void setMailAdress(String mailAdress) {
		this.mailAdress = mailAdress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getManagement() {
		return management;
	}

	public void setManagement(int management) {
		this.management = management;
	}

	@Override
	    public String toString() {
	        return mailAdress+password+management;
	    }
}
