package beans;

public class Rental_Status {
	private int id;
	private String mailAddress;
	private int bookId;
	private int rentDate;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getRentDate() {
		return rentDate;
	}
	public void setRentDate(int rentDate) {
		this.rentDate = rentDate;
	}
}