package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

@Path("resources")
public class Resources {
	/**
	 * 従業員関連のサービス実装。 Servlet/JSPの実装とは異なり、画像についてはバイナリでなくpathベースで扱うものとする。
	 */
	@Path("resources")
	public class EmployeeResource {

	}

	@POST
	@Path("login")
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
	@POST
	@Path("myPage")
	@Consumes("application/x-www-form-urlencoded")
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
}
