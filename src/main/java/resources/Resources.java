package resources;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

import beans.Book;
import dao.BookDAO;

@Path("resources")
public class Resources {
	/**
	 * 従業員関連のサービス実装。 Servlet/JSPの実装とは異なり、画像についてはバイナリでなくpathベースで扱うものとする。
	 */
	Book b = new Book();

	private final BookDAO dao = new BookDAO();

	/**
	 * 一覧用に貸出中の書籍を取得する。
	 *
	 * @return 貸出中の書籍のリストをJSON形式で返す。
	 */

	@GET
	@Path("findLendingBookById")
	//@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public List<Book> findLendingBookById() throws WebApplicationException {
		return dao.findLendingBook();
	}
	@GET
	@Path("returnBookById")
	//@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public boolean returnBookById(@FormParam("id") int id) throws WebApplicationException {
		return dao.returnBookById(id);
	}
	@POST
	@Path("myPage")
	//@Consumes("application/x-www-form-urlencoded")
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
