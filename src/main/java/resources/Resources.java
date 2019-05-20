package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

import beans.User;
import dao.UserDAO;

@Path("resources")
public class Resources {
	private final UserDAO userDao = new UserDAO();
	User user = new User();

	/**
	 * 従業員関連のサービス実装。 Servlet/JSPの実装とは異なり、画像についてはバイナリでなくpathベースで扱うものとする。
	 */
	@Path("resources")
	public class EmployeeResource {

	}

	@GET
	@Path("getMailAddress")
	public String getMailAddress() {
		return user.getMailAdress();
	}

	@POST
	@Path("generalLogin")
	@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	public boolean GeneralLogin(@FormParam("mailAdress") String mailAdress, @FormParam("password") String password)
			throws WebApplicationException {
		User DaoResult = new User();
		DaoResult.setMailAdress(userDao.findByParam(mailAdress, password).getMailAdress());
		DaoResult.setPassword(userDao.findByParam(mailAdress, password).getPassword());
		DaoResult.setManagement(userDao.findByParam(mailAdress, password).getManagement());
		if (mailAdress != null && password != null && mailAdress.equals(DaoResult.getMailAdress())
				&& password.equals(DaoResult.getPassword())) {
			user.setMailAdress(DaoResult.getMailAdress());
			user.setPassword(DaoResult.getPassword());
			user.setManagement(DaoResult.getManagement());
			System.out.println(user.getMailAdress() + user.getPassword() + user.getManagement());
			return true;
		} else {
			System.out.println("ng");
			return false;
		}

	}

	@POST
	@Path("mamnagerLogin")
	@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	public boolean ManagerLogin(@FormParam("mailAdress") String mailAdress, @FormParam("password") String password)
			throws WebApplicationException {
		User DaoResult = new User();
		DaoResult.setMailAdress(userDao.findByParam(mailAdress, password).getMailAdress());
		DaoResult.setPassword(userDao.findByParam(mailAdress, password).getPassword());
		DaoResult.setManagement(userDao.findByParam(mailAdress, password).getManagement());
		if (mailAdress != null && password != null && mailAdress.equals(DaoResult.getMailAdress())
				&& password.equals(DaoResult.getPassword()) && DaoResult.getManagement() == 1) {
			user.setMailAdress(DaoResult.getMailAdress());
			user.setPassword(DaoResult.getPassword());
			user.setManagement(DaoResult.getManagement());
			System.out.println(user.getMailAdress() + user.getPassword() + user.getManagement());
			return true;
		} else {
			System.out.println("ng");
			return false;
		}

	}

}
