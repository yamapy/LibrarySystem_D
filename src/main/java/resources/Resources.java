package resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import beans.Book;
import beans.User;
import dao.BookDAO;

@Path("resources")
public class Resources {
	/**
	 * 従業員関連のサービス実装。 Servlet/JSPの実装とは異なり、画像についてはバイナリでなくpathベースで扱うものとする。
	 */
	private final BookDAO bookDAO = new BookDAO();
	private  User user;

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

//	@GET
//	@Path("findByName")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Book findByName(@PathParam("name") String name) {
//		return bookDAO.findByName(name);
//	}


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
