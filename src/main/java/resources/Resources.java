package resources;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import beans.Book;
import dao.BookDAO;

@Path("/resources")
public class Resources {
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
	//@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public List<Book> findLendingBookById() throws WebApplicationException {
//		return null;
		return dao.findLendingBook(mailAddress);
	}


	/*
	@GET
	@Path("returnBookById")
	public boolean returnBookById(@FormParam("id") int id) throws WebApplicationException {
		//return dao.returnBookById(id);
		return false;
	}
	*/
	@GET
    @Path("returnBook")
    public int returnBook()  throws WebApplicationException{

        return dao.returnBook(mailAddress);
    }
	@GET
    @Path("returnBook/{id}")
    public int returnBookById(@PathParam("id") int id)  throws WebApplicationException{
		System.out.println(id);
//        return false;
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
	@POST
	@Path("login")
	//@Consumes("application/x-www-form-urlencoded")
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
	@PUT
	@Path("returnBookById")
	//@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public boolean returnBookById(@FormParam("id") int id) throws WebApplicationException {
		//return dao.returnBookById(id);
		return false;
	}
	*/


    @GET
    @Path("matrix")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMatrixParam(@MatrixParam("name") final String name,
                                 @MatrixParam("year") final String year) {

        return "[MatrixParam] Name : " + name + " Year : " + year;
    }


}
