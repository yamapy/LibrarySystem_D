package resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	 *             入力データチェックに失敗した場合に送出される。 ("")内は、htmlのname=""と同一のものにしないと
	 *             フォームで入力された内容をDAOに送れない！！！！！
	 */
	@POST
	@Path("createBook")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Book create(final FormDataMultiPart form) throws WebApplicationException {
		Book book = new Book();

		book.setId(0);
		book.setTitle(form.getField("title").getValue());
		book.setAuthor(form.getField("author").getValue());
		book.setPublisher(form.getField("publisher").getValue());
		book.setGenre(form.getField("genre").getValue());
		book.setPurchaseDate(form.getField("purchaseDate").getValue());
		book.setBuyer(form.getField("buyer").getValue());

		return dao.create(book);
	}

	/**
	 * ID指定で書籍情報を取得する。
	 * 詳細表示で使用！！！！！
	 *
	 * @param id
	 *            取得対象の書籍のID
	 * @return 取得した書籍情報をJSON形式で返す。データが存在しない場合は空のオブジェクトが返る。
	 */
	@GET
	@Path("{id}") // 自分の関数のパス、数字、その都度変えられるようになっている
	@Produces(MediaType.APPLICATION_JSON)
	public Book findById(@PathParam("id") int id) {
		return dao.findById(id);
	}

	/**
	 * 一覧用に貸出中の書籍件数を取得する。
	 *
	 * @return 貸出中の書籍件数をJSON形式で返す。
	 */
	@GET
	@Path("countLendingBook")
	@Produces(MediaType.APPLICATION_JSON)
	public int findNumber() {
		return dao.findNumber();
	}
}
