package beans;

public class User {
	private String mailAddress;
	private String password;
	private int management;

	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
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
	        return mailAddress+password+management;
	    }
}
