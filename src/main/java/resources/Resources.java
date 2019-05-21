package resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.Genre;
import dao.GenreDAO;

@Path("resources")
public class Resources {

	private final GenreDAO genreDAO = new GenreDAO();


	@GET
	@Path("genre")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Genre> findAllGenre() {
		return genreDAO.findAllGenre();
	}
}