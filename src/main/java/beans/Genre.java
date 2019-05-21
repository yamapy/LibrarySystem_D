package beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Genre {

	private String genre;

	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
}
