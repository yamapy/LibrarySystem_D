package resources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Book;
import beans.Genre;
import beans.User;
import dao.BookDAO;
import dao.GenreDAO;
import dao.Param;
import dao.UserDAO;

@Path("resources")
public class Resources {

	private final BookDAO bookDAO = new BookDAO();
	private final UserDAO userDao = new UserDAO();
	User user = new User();
	private final GenreDAO genreDAO = new GenreDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("book")
	public List<Book> findAll() {
		return bookDAO.findAll();
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

	public boolean borrow(@FormParam("id") int id, @Context HttpServletRequest request) throws WebApplicationException{

		System.out.println(id);

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatter.format(date);
		HttpSession session = request.getSession();
		User nowUser = (User) session.getAttribute("loginUser");
		session.getAttribute("mail");


		String mail = nowUser.getMailAddress();

		return bookDAO.borrowById(id,today,mail) ;

	}



	@GET
	@Path("isLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean isGeneralLogin(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		// session.setAttribute("isGeneralLogin", user);
		// if(session.getAttribute("isGeneralLogin") != null){
		User nowUser = (User) session.getAttribute("loginUser");
		System.out.println(session.getAttribute("loginUser"));
		// }
		if (nowUser == null || nowUser.getMailAddress().equals("")) {
			return false;
		}
		return true;
	}


	@POST
	@Path("generalLogin")
	@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	public boolean generalLogin(@Context HttpServletRequest request, @FormParam("id") String mailAddress,
			@FormParam("pass") String password) throws WebApplicationException {

		User DaoResult = new User();
		DaoResult.setMailAddress(userDao.findByParam(mailAddress, password).getMailAddress());
		DaoResult.setPassword(userDao.findByParam(mailAddress, password).getPassword());
		DaoResult.setManagement(userDao.findByParam(mailAddress, password).getManagement());
		if (mailAddress != null && password != null && mailAddress.equals(DaoResult.getMailAddress())
				&& password.equals(DaoResult.getPassword())) {
			user.setMailAddress(DaoResult.getMailAddress());
			user.setPassword(DaoResult.getPassword());
			user.setManagement(DaoResult.getManagement());

			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			User nowUser = (User) session.getAttribute("loginUser");
			System.out.println(nowUser.getMailAddress() + " " + nowUser.getPassword());

			return true;
		} else {
			return false;
		}

	}

	@GET
	@Path("getLoginMailAddress")
	@Produces(MediaType.APPLICATION_JSON)
	public String loginMailAddress(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User nowUser = (User) session.getAttribute("loginUser");
		session.setAttribute("mail", nowUser.getMailAddress());
		System.out.println(nowUser.getMailAddress() + " " + nowUser.getPassword());
		if (nowUser.getMailAddress() != null || !nowUser.getMailAddress().equals("")) {
			return nowUser.getMailAddress();
		}
		return "";
	}

	@POST
	@Path("mamnagerLogin")
	@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	public boolean managerLogin(@Context HttpServletRequest request, @FormParam("id") String mailAddress,
			@FormParam("pass") String password) throws WebApplicationException {
		User DaoResult = new User();
		DaoResult.setMailAddress(userDao.findByParam(mailAddress, password).getMailAddress());
		DaoResult.setPassword(userDao.findByParam(mailAddress, password).getPassword());
		DaoResult.setManagement(userDao.findByParam(mailAddress, password).getManagement());
		if (mailAddress != null && password != null && mailAddress.equals(DaoResult.getMailAddress())
				&& password.equals(DaoResult.getPassword()) && DaoResult.getManagement() == 1) {
			user.setMailAddress(DaoResult.getMailAddress());
			user.setPassword(DaoResult.getPassword());
			user.setManagement(DaoResult.getManagement());

			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			User nowUser = (User) session.getAttribute("loginUser");
			System.out.println(nowUser.getMailAddress() + " " + nowUser.getPassword());

			return true;
		} else {
			return false;
		}

	}




}
