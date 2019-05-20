package resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

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

	/**
	 * 指定した書籍情報を新規に登録する。
	 *
	 * @param form
	 *            書籍情報を収めたオブジェクト
	 * @return DB上のIDが振られた書籍情報
	 * @throws WebApplicationException
	 *             入力データチェックに失敗した場合に送出される。
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Book create(final FormDataMultiPart form) throws WebApplicationException {
		Book book = new Book();

		book.setId(0);
		book.setTitle(form.getField("title").getValue());
		book.setAuthor(form.getField("author").getValue());
		book.setPublisher(form.getField("publisher").getValue());
		book.setGenre(form.getField("genre").getValue());
		//Date purchaseDate = new Date(System.currentTimeMillis());
		//SimpleDateFormat formatdate = new SimpleDateFormat("YYYY-MM-DD");
		book.setPurchaseDate(form.getField("purchaseDate").getValue());
		book.setBuyer(form.getField("buyer").getValue());

		return dao.create(book);
	}

}



/*@Path("CountLendingBook")
public class LendingBookResources {
	private final BookDAO dao2 = new BookDAO();

	/**
	 * 一覧用に貸出中の書籍件数を取得する。
	 *
	 * @return 貸出中の書籍件数をJSON形式で返す。

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> countLendingBook() {
		return dao2.countLendingBook();
	}
}
*/
