package beans;

public class User {
	private String mailAddress;
	private String password;
	private int manegement;
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAdress) {
		this.mailAddress = mailAdress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getManegement() {
		return manegement;
	}
	public void setManegement(int manegement) {
		this.manegement = manegement;
	}


}
