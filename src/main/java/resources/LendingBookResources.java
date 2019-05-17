package resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.Book;
import dao.BookDAO;

/**
 * 書籍関連のサービス実装。
 */
@Path("lendingBook")
public class LendingBookResources {
	private final BookDAO dao = new BookDAO();

	/**
	 * 一覧用に貸出中の書籍を取得する。
	 *
	 * @return 貸出中の書籍のリストをJSON形式で返す。
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> findLendingBook() {
		return dao.findLendingBook();
	}
}
