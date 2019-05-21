package resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import beans.Book;
import beans.Employee;
import beans.User;
import dao.BookDAO;
import dao.UserDAO;

@Path("/resources")
public class Resources {
	private final UserDAO userDao = new UserDAO();
	User user = new User();
	User employee = new User();

	/**
	 * 従業員関連のサービス実装。 Servlet/JSPの実装とは異なり、画像についてはバイナリでなくpathベースで扱うものとする。
	 */
	Book b = new Book();

	private final BookDAO dao = new BookDAO();

	String mailAddress = "t-kamehashi@virtualex.co.jp";

	/**
	 * 一覧用に貸出中の書籍を取得する。
	 *
	 * @return 貸出中の書籍のリストをJSON形式で返す。
	 */

	@GET
	@Path("/findLendingBookById")
	// @Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	public List<Book> findLendingBookById() throws WebApplicationException {
		// return null;
		return dao.findLendingBook(mailAddress);
	}

	/*
	 * @GET
	 *
	 * @Path("returnBookById") public boolean returnBookById(@FormParam("id")
	 * int id) throws WebApplicationException { //return dao.returnBookById(id);
	 * return false; }
	 */
	@GET
	@Path("returnBook")
	public int returnBook() throws WebApplicationException {

		return dao.returnBook(mailAddress);
	}

	@GET
	@Path("returnBook/{id}")
	public int returnBookById(@PathParam("id") int id) throws WebApplicationException {
		System.out.println(id);
		// return false;
		return dao.returnBookById(id);
	}

	@GET
	@Path("getEmployeeName")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> employeeInfo() {
		return userDao.getEmployeeInfo();
	}

	@GET
	@Path("getUserInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> userInfo() {
		return userDao.getUserInfo();
	}

	@GET
	@Path("isLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean isGeneralLogin(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		// session.setAttribute("isGeneralLogin", user);
		// if(session.getAttribute("isGeneralLogin") != null){
		User nowUser = (User) session.getAttribute("loginUser");
		// }
		if (session != null && nowUser != null && !nowUser.getMailAddress().equals("")) {
			System.out.println(session.getAttribute("loginUser"));
			return true;
		} else {
			System.out.println("Resource false");
			return false;
		}
	}

	@GET
	@Path("isManagerLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean isManagerLogin(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User nowUser = (User) session.getAttribute("loginUser");
		if (session != null && nowUser != null && !nowUser.getMailAddress().equals("")
				&& nowUser.getManagement() == 1) {
			System.out.println(nowUser.getMailAddress() + " " + nowUser.getPassword());
			return true;
		} else {
			System.out.println("Resource false");
			return false;
		}
	}

	@GET
	@Path("getLoginMailAddress")
	@Produces(MediaType.APPLICATION_JSON)
	public String loginMailAddress(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User nowUser = (User) session.getAttribute("loginUser");
		if ((session != null) && (nowUser.getMailAddress() != null || !nowUser.getMailAddress().equals(""))) {
			System.out.println(nowUser.getMailAddress());
			return nowUser.getMailAddress();
		} else {
			System.out.println("Resource false");
			return "";
		}
	}

	@GET
	@Path("logout")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean logout(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User nowUser = (User) session.getAttribute("loginUser");
		if (nowUser == null || nowUser.getMailAddress() == null || nowUser.getMailAddress() == "") {
			System.out.println("Resource false");
			return false;
		} else {
			session.removeAttribute("loginUser");
			System.out.println("logout");
			return true;
		}
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

	@POST
	@Path("myPage")
	// @Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	public boolean myPage(@FormParam("id") String id, @FormParam("pass") String pass) throws WebApplicationException {
		String successId = "aaaa";
		String successPass = "aaaa";
		System.out.println(id + pass);
		if (id != null && pass != null && id.equals(successId) && pass.equals(successPass)) {
			return true;
		} else {
			return false;
		}
	}

	@POST
	@Path("login")
	// @Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	public boolean login(@FormParam("id") String id, @FormParam("pass") String pass) throws WebApplicationException {
		String successId = "aaaa";
		String successPass = "aaaa";
		System.out.println(id + pass);
		if (id != null && pass != null && id.equals(successId) && pass.equals(successPass)) {
			return true;
		} else {
			return false;
		}
	}
	/*
	 * @PUT
	 *
	 * @Path("returnBookById") //@Consumes("application/x-www-form-urlencoded")
	 * // @Produces(MediaType.APPLICATION_JSON)
	 * //@Produces(MediaType.APPLICATION_JSON) public boolean
	 * returnBookById(@FormParam("id") int id) throws WebApplicationException {
	 * //return dao.returnBookById(id); return false; }
	 */

	@GET
	@Path("matrix")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMatrixParam(@MatrixParam("name") final String name, @MatrixParam("year") final String year) {

		return "[MatrixParam] Name : " + name + " Year : " + year;
	}

	@POST
	@Path("createUser")
	// @Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createUser(final FormDataMultiPart form) throws WebApplicationException {
		User user = new User();

		user.setMailAddress(form.getField("mailAddress").getValue());
		user.setPassword(form.getField("password").getValue());
		user.setManagement(0);
		if (user.getMailAddress() == null || user.getPassword() == null) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		User userResult = userDao.create(user);
		if (userResult.getMailAddress() != null && userResult.getPassword() != null) {
			return true;
		} else {
			return false;
		}
	}

}
