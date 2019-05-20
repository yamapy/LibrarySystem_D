package resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import beans.Employee;
import beans.User;
import dao.UserDAO;

@Path("resources")
public class Resources {
	private final UserDAO userDao = new UserDAO();
	User user = new User();
	User employee = new User();

	/**
	 * 従業員関連のサービス実装。 Servlet/JSPの実装とは異なり、画像についてはバイナリでなくpathベースで扱うものとする。
	 */
	@Path("resources")
	public class EmployeeResource {

	}

	@GET
	@Path("getEmployeeName")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> employeeInfo() {
		return userDao.getEmployeeInfo();
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
		if (nowUser == null || nowUser.getMailAdress().equals("")) {
			return false;
		}
		return true;
	}

	@GET
	@Path("isManagerLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean isManagerLogin(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User nowUser = (User) session.getAttribute("loginUser");
		System.out.println(nowUser.getMailAdress() + " " + nowUser.getPassword());
		if (nowUser == null || nowUser.getMailAdress().equals("") || nowUser.getManagement() != 1) {
			return false;
		}
		return true;
	}

	@GET
	@Path("getLoginMailAdress")
	@Produces(MediaType.APPLICATION_JSON)
	public String loginMailAdress(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();
		User nowUser = (User) session.getAttribute("loginUser");
		System.out.println(nowUser.getMailAdress() + " " + nowUser.getPassword());
		if (nowUser == null || nowUser.getMailAdress().equals("")) {
			return nowUser.getMailAdress();
		}
		return "";
	}

	@POST
	@Path("generalLogin")
	@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	public boolean generalLogin(@Context HttpServletRequest request, @FormParam("id") String mailAdress,
			@FormParam("pass") String password) throws WebApplicationException {

		User DaoResult = new User();
		DaoResult.setMailAdress(userDao.findByParam(mailAdress, password).getMailAdress());
		DaoResult.setPassword(userDao.findByParam(mailAdress, password).getPassword());
		DaoResult.setManagement(userDao.findByParam(mailAdress, password).getManagement());
		if (mailAdress != null && password != null && mailAdress.equals(DaoResult.getMailAdress())
				&& password.equals(DaoResult.getPassword())) {
			user.setMailAdress(DaoResult.getMailAdress());
			user.setPassword(DaoResult.getPassword());
			user.setManagement(DaoResult.getManagement());

			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			User nowUser = (User) session.getAttribute("loginUser");
			System.out.println(nowUser.getMailAdress() + " " + nowUser.getPassword());

			return true;
		} else {
			return false;
		}

	}

	@POST
	@Path("mamnagerLogin")
	@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	public boolean managerLogin(@Context HttpServletRequest request, @FormParam("id") String mailAdress,
			@FormParam("pass") String password) throws WebApplicationException {
		User DaoResult = new User();
		DaoResult.setMailAdress(userDao.findByParam(mailAdress, password).getMailAdress());
		DaoResult.setPassword(userDao.findByParam(mailAdress, password).getPassword());
		DaoResult.setManagement(userDao.findByParam(mailAdress, password).getManagement());
		if (mailAdress != null && password != null && mailAdress.equals(DaoResult.getMailAdress())
				&& password.equals(DaoResult.getPassword()) && DaoResult.getManagement() == 1) {
			user.setMailAdress(DaoResult.getMailAdress());
			user.setPassword(DaoResult.getPassword());
			user.setManagement(DaoResult.getManagement());

			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			User nowUser = (User) session.getAttribute("loginUser");
			System.out.println(nowUser.getMailAdress() + " " + nowUser.getPassword());

			return true;
		} else {
			return false;
		}

	}

	@POST
	@Path("createUser")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createUser(final FormDataMultiPart form) throws WebApplicationException {
		User user = new User();

		user.setMailAdress(form.getField("mailAdress").getValue());
		user.setPassword(form.getField("password").getValue());
		user.setManagement(0);
		if (user.getMailAdress() == null || user.getPassword() == null) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		userDao.create(user);
		if (userDao.create(user).getMailAdress() != null && userDao.create(user).getPassword() != null) {
			return true;
		}
		return false;
	}

}
