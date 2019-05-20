package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Book;

/**
 * 書籍データを扱うDAO
 */
public class BookDAO {
	/**
	 * クエリ文字列
	 */
	private static final String SELECT_LENDING_QUERY = "select BOOK.TITLE, \n" + "EMPLOYEE.NAME, \n"
			+ "RENTAL_STATUS.RENTDATE + 14 RETURN_DATE \n" + "from EMPLOYEE, \n" + "BOOK, \n" + "RENTAL_STATUS \n"
			+ "where \n" + "BOOK.ID = RENTAL_STATUS.BOOKID \n"
			+ "and EMPLOYEE.MAILADDRESS = RENTAL_STATUS.MAILADDRESS \n" + "order by \n" + "RENTAL_STATUS.RENTDATE \n";

	//private static final String COUNT_LENDING_QUERY = "select COUNT(*) as TOTAL \n" + "from EMPLOYEE, \n" + "BOOK, \n"
			//+ "RENTAL_STATUS \n" + "where \n" + "BOOK.ID = RENTAL_STATUS.BOOKID \n"
			//+ "and EMPLOYEE.MAILADDRESS = RENTAL_STATUS.MAILADDRESS \n" + "order by \n" + "RENTAL_STATUS.RENTDATE \n";

	private static final String INSERT_QUERY = "INSERT INTO "
			+"BOOK(TITLE, AUTHOR, PUBLISHER, GENRE, PUTCHASEDATE, BUYER) "
			+"VALUES(?,?,?,?,?,?)";

	/**
	 * 貸出中の書籍全件を取得する。
	 *
	 * @return DBに登録されている貸出中の書籍全件を収めたリスト。途中でエラーが発生した場合は空のリストを返す。
	 */
	public List<Book> findLendingBook() {
		List<Book> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (Statement statement = connection.createStatement();) {
			ResultSet rs = statement.executeQuery(SELECT_LENDING_QUERY);

			while (rs.next()) {
				result.add(processRow1(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

	/**
	 * 貸出中の書籍件数を取得する。
	 *
	 * @return DBに登録されている貸出中の書籍件数を収めたリスト。途中でエラーが発生した場合は空のリストを返す。
	 *
	 *         public int countLendingBook() { int result2;
	 *
	 *         Connection connection = ConnectionProvider.getConnection(); if
	 *         (connection == null) { return result2; }
	 *
	 *         try (Statement statement = connection.createStatement();) {
	 *         ResultSet rs2 = statement.executeQuery(COUNT_LENDING_QUERY);
	 *
	 *         result2.add(rs2); }
	 *
	 *         }catch(
	 *
	 *         SQLException e) { e.printStackTrace(); }finally {
	 *         ConnectionProvider.close(connection); }
	 *
	 *         return result2; }
	 *

	 /**
	 * 指定されたBookオブジェクトを新規にDBに登録する。
	 * 登録されたオブジェクトにはDB上のIDが上書きされる。
	 * 何らかの理由で登録に失敗した場合、IDがセットされない状態（=0）で返却される。
	 *
	 * @param Book 登録対象オブジェクト
	 * @return DB上のIDがセットされたオブジェクト
	 */
	public Book create(Book book) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return book;
		}

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[] { "ID" });) {
			// INSERT実行
			setParameter(statement, book, false);
			statement.executeUpdate();

			// INSERTできたらKEYを取得
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			book.setId(id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return book;
	}

	/**
	 * 貸出中の書籍一覧表示 検索結果行をオブジェクトとして構成する。
	 *
	 * @param rs
	 *            検索結果が収められているResultSet
	 * @return 検索結果行の各データを収めたBookインスタンス
	 * @throws SQLException
	 *             ResultSetの処理中発生した例外
	 */
	private Book processRow1(ResultSet rs) throws SQLException {
		Book result = new Book();
		result.setTitle(rs.getString("TITLE"));
		result.setBorrower(rs.getString("NAME"));
		Date returnDate = rs.getDate("RETURN_DATE");
		if (returnDate != null) {
			result.setReturnDate(returnDate.toString());
		}

		return result;
	}

	/**
	 * 貸出中の書籍の件数表示 検索結果行をオブジェクトとして構成する。
	 *
	 * @param rs2
	 *            検索結果が収められているResultSet
	 * @return 検索結果行の各データを収めたBookインスタンス
	 * @throws SQLException
	 *             ResultSetの処理中発生した例外
	 *
	 *             private Book processRow2(ResultSet rs2) throws SQLException {
	 *             Book result2 = new Book();
	 *             result2.setLendingTotal(rs2.getInt("TOTAL"));
	 *
	 *             return result2; }
	 */

	/**
	 * オブジェクトからSQLにパラメータを展開する。
	 *
	 * @param statement
	 *            パラメータ展開対象のSQL
	 * @param book
	 *            パラメータに対して実際の値を供給するオブジェクト
	 * @param forUpdate
	 *            更新に使われるならtrueを、新規追加に使われるならfalseを指定する。
	 * @throws SQLException
	 *             パラメータ展開時に何らかの問題が発生した場合に送出される。
	 */
	private void setParameter(PreparedStatement statement, Book book, boolean forUpdate) throws SQLException {
		int count = 1;
		statement.setString(count++, book.getTitle());
		statement.setString(count++, book.getAuthor());
		statement.setString(count++, book.getPublisher());
		statement.setString(count++, book.getGenre());
		statement.setString(count++, book.getPurchaseDate());
		statement.setString(count++, book.getBuyer());


		if (forUpdate) {
			statement.setInt(count++, book.getId());
		}
	}


}
