package resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import beans.Book;
import beans.Genre;
import beans.User;
import dao.BookDAO;
import dao.GenreDAO;
import dao.Param;

@Path("resources")
public class Resources {

	private final BookDAO bookDAO = new BookDAO();
	private  User user;
	private final GenreDAO genreDAO = new GenreDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("book")
	public List<Book> findAll() {
		return bookDAO.findAll();
	}

	@GET
	@Path("isLogin")
	public boolean isLogin(){
		if(user==null){
			return false;
		}else{
			return true;
		}
	}


	@GET
	@Path("genre")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Genre> findAllGenre() {
		return genreDAO.findAllGenre();
	}




	@Path("findByParam")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> findByParam(@QueryParam("titleParam") String titleParam,
			@QueryParam("authorParam") String authorParam,
			@QueryParam("genre") String genre,
			@QueryParam("status") String status) {

		System.out.println("t="+ titleParam );
		Param param = new Param(titleParam, authorParam, genre,status);
		return bookDAO.findByParam(param);
	}




	@POST
	@Path("{login}")
	@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	public boolean Login(@FormParam("id") String id, @FormParam("pass") String pass) throws WebApplicationException {
		String successId = "test";
		String successPass = "1234";
		System.out.println(id + pass);
		if (id != null && pass != null && id.equals(successId) && pass.equals(successPass)) {
			return true;
		} else {
			return false;
		}

	}

}
