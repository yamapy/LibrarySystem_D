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
import beans.Rental_Status;
import beans.User;

/**
 * 書籍データを扱うDAO
 */
public class BookDAO {
	/**
	 * クエリ文字列
	 */
	private static final String SELECT_LENDING_QUERY = "select  \n" + "	BOOK.ID \n" + ",	BOOK.TITLE \n"
			+ ",	EMPLOYEE.NAME \n" + ",	RENTAL_STATUS.RENTDATE + 14 RETURN_DATE \n" + " \n" + "from  \n"
			+ "	EMPLOYEE \n" + ",	BOOK \n" + ",	RENTAL_STATUS \n" + ",	USER_T \n" + "			 \n"
			+ "where 1 = 1 \n" + "	and BOOK.ID = RENTAL_STATUS.BOOKID \n"
			+ "	and EMPLOYEE.MAILADDRESS = RENTAL_STATUS.MAILADDRESS \n"
			+ "	and USER_T.MAILADDRESS = EMPLOYEE.MAILADDRESS \n" + "order by  \n" + "	RENTAL_STATUS.RENTDATE \n";
	private static final String SELECT_LENDING_BY_MAILADDRESS_QUERY = "select  \n" + "	BOOK.ID \n"
			+ ",	BOOK.TITLE \n" + ",	EMPLOYEE.NAME \n" + ",	RENTAL_STATUS.RENTDATE + 14 RETURN_DATE \n" + " \n"
			+ "from  \n" + "	EMPLOYEE \n" + ",	BOOK \n" + ",	RENTAL_STATUS \n" + ",	USER_T \n" + "			 \n"
			+ "where 1 = 1 \n" + "	and BOOK.ID = RENTAL_STATUS.BOOKID \n"
			+ "	and EMPLOYEE.MAILADDRESS = RENTAL_STATUS.MAILADDRESS \n"
			+ "	and USER_T.MAILADDRESS = EMPLOYEE.MAILADDRESS \n" + "	and USER_T.MAILADDRESS = ? \n" + "order by  \n"
			+ "	RENTAL_STATUS.RENTDATE \n";

	// private static final String COUNT_LENDING_QUERY = "select COUNT(*) as
	// TOTAL \n" + "from EMPLOYEE, \n" + "BOOK, \n"
	// + "RENTAL_STATUS \n" + "where \n" + "BOOK.ID = RENTAL_STATUS.BOOKID \n"
	// + "and EMPLOYEE.MAILADDRESS = RENTAL_STATUS.MAILADDRESS \n" + "order by
	// \n" + "RENTAL_STATUS.RENTDATE \n";

	private static final String INSERT_BOOK_QUERY = "INSERT INTO "
			+ "BOOK(TITLE, AUTHOR, PUBLISHER, GENRE, PUTCHASEDATE, BUYER) " + "VALUES(?,?,?,?,?,?)";
	private static final String DELETE_QUERY = "DELETE \n" + "FROM \n" + "	RENTAL_STATUS R \n" + "WHERE \n"
			+ "	R.BOOKID = ? \n";
	private static final String DELETE_ALL_QUERY = "DELETE \n" + "FROM \n" + "	RENTAL_STATUS R \n" + "WHERE \n"
			+ "	R.MAILADDRESS = ? \n";
	private static final String SELECT_ALL_QUERY = "SELECT \n" + "	B2.ID \n" + ",	B2.TITLE \n" + ",	B2.GENRE \n"
			+ ",	B2.AUTHOR \n" + ",	'貸出可能' STATUS \n" + "FROM \n" + "	BOOK B2 \n" + ",	(SELECT \n"
			+ "		B.ID \n" + "	FROM \n" + "		BOOK B \n" + "	MINUS \n" + "	SELECT  \n" + "		B2.ID \n"
			+ "	 \n" + "	FROM \n" + "		BOOK B2 \n" + "	,	RENTAL_STATUS R \n" + "	WHERE \n"
			+ "		B2.ID = R.BOOKID \n" + "		)ableB \n" + "WHERE \n" + "	ableB.ID = B2.ID \n" + " \n"
			+ "UNION \n" + " \n" + "SELECT \n" + "	B.ID \n" + ",	B.TITLE \n" + ",	B.GENRE \n" + ",	B.AUTHOR \n"
			+ ",	'貸出中' \n" + "FROM \n" + "	BOOK B \n" + ",	RENTAL_STATUS R \n" + "WHERE \n"
			+ "	B.ID = R.BOOKID  \n";
	private static final String INSERT_RENTAL_STATUS_QUERY = "INSERT INTO \n"
			+ "	RENTAL_STATUS(MAILADDRESS,BOOKID,RENTDATE) \n" + "VALUES \n" + "	(?,?,?) \n";

	private static final String COUNT_LENDING_QUERY = "select COUNT(*) as TOTAL \n" + " from EMPLOYEE, \n" + "BOOK, \n"
			+ "RENTAL_STATUS \n" + "where \n" + "BOOK.ID = RENTAL_STATUS.BOOKID \n"
			+ "and EMPLOYEE.MAILADDRESS = RENTAL_STATUS.MAILADDRESS \n" + "order by \n" + "RENTAL_STATUS.RENTDATE \n";

	private static final String SELECT_BY_ID_QUERY = "select BOOK.ID, \n" + "BOOK.TITLE, \n" + "BOOK.AUTHOR, \n"
			+ "BOOK.PUBLISHER, \n" + "BOOK.GENRE, \n" + "BOOK.PUTCHASEDATE, \n" + "BOOK.BUYER, \n" + "EMPLOYEE.NAME, \n"
			+ "EMPLOYEE.MAILADDRESS, \n" + "RENTAL_STATUS.RENTDATE + 14 RETURN_DATE \n" + "from EMPLOYEE, \n"
			+ "BOOK, \n" + "RENTAL_STATUS, \n" + "USER_T \n" + "where \n" + "and BOOK.ID = RENTAL_STATUS.BOOKID(+) \n"
			+ "	and EMPLOYEE.MAILADDRESS(+) = USER_T.MAILADDRESS \n"
			+ "and USER_T.MAILADDRESS(+) = RENTAL_STATUS.MAILADDRESS \n" + " and BOOK.ID = ? \n" + "order by \n"
			+ "BOOK.ID \n";

	private static final String INSERT_QUERY = "INSERT INTO "
			+ "BOOK(TITLE, AUTHOR, PUBLISHER, GENRE, PUTCHASEDATE, BUYER) " + "VALUES(?,?,?,?,?,?)";

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
	 * 貸出中の書籍が全部で何件かあるを取得する。
	 *
	 * 下記のtryの中のifは受け取るデータが1行だから
	 *
	 * @return DBに登録されている貸出中の書籍全件を収めたリスト。途中でエラーが発生した場合は空のリストを返す。
	 */
	public int findNumber() {
		int result = 1;

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (Statement statement = connection.createStatement();) {
			ResultSet rs = statement.executeQuery(COUNT_LENDING_QUERY);
			if (rs.next()) {
				result = rs.getInt("TOTAL");
				System.out.println(result);
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

	/**
	 * ID指定の検索を実施する。つまり、詳細表示！！！！！
	 *
	 * @param id
	 *            検索対象のID
	 * @return 検索できた場合は検索結果データを収めたPostインスタンス。検索に失敗した場合はnullが返る。
	 */
	public Book findById(int id) {
		Book result = null;

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				result = processRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

	/**
	 * 指定されたBookオブジェクトを新規にDBに登録する。 登録されたオブジェクトにはDB上のIDが上書きされる。
	 * 何らかの理由で登録に失敗した場合、IDがセットされない状態（=0）で返却される。
	 *
	 * @param Book
	 *            登録対象オブジェクト
	 * @return DB上のIDがセットされたオブジェクト
	 */
	public Book create(Book book) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return book;
		}

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[] { "ID" });) {
			// INSERT実行
			setParameter(statement, book);
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
		result.setId(rs.getInt("ID"));
		result.setTitle(rs.getString("TITLE"));
		result.setBorrower(rs.getString("NAME"));
		Date returnDate = rs.getDate("RETURN_DATE");
		if (returnDate != null) {
			result.setReturnDate(returnDate.toString());
		}

		return result;
	}

	/**
	 * 検索結果からオブジェクトを復元する。 詳細表示の場合！！！！！
	 *
	 * @param rs
	 *            検索結果が収められているResultSet。rs.next()がtrueであることが前提。
	 * @return 検索結果を収めたオブジェクト
	 * @throws SQLException
	 *             検索結果取得中に何らかの問題が発生した場合に送出される。
	 */
	private Book processRow(ResultSet rs) throws SQLException {
		Book result = new Book();

		// Book本体の再現
		result.setId(rs.getInt("ID"));
		result.setTitle(rs.getString("TITLE"));
		result.setAuthor(rs.getString("AUTHOR"));
		result.setPublisher(rs.getString("PUBLISHER"));
		result.setGenre(rs.getString("GENRE"));
		result.setPurchaseDate(rs.getString("PUTCHASEDATE"));
		result.setBuyer(rs.getString("BUYER"));
		result.setBorrower(rs.getString("NAME"));
		result.setBorrowerMailaddress(rs.getString("MAILADDRESS"));
		Date returnDate = rs.getDate("RETURN_DATE");
		if (returnDate != null) {
			result.setReturnDate(returnDate.toString());
		}
		return result;
	}

	/**
	 * オブジェクトからSQLにパラメータを展開する。
	 *
	 * @param statement
	 *            パラメータ展開対象のSQL
	 * @param book
	 *            パラメータに対して実際の値を供給するオブジェクト
	 * @throws SQLException
	 *             パラメータ展開時に何らかの問題が発生した場合に送出される。
	 */
	private void setParameter(PreparedStatement statement, Book book) throws SQLException {
		int count = 1;
		statement.setString(count++, book.getTitle());
		statement.setString(count++, book.getAuthor());
		statement.setString(count++, book.getPublisher());
		statement.setString(count++, book.getGenre());
		statement.setString(count++, book.getPurchaseDate());
		statement.setString(count++, book.getBuyer());
	}

	// ------------------------------------------------

	public List<Book> findLendingBook(String mailAddress) {
		List<Book> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (PreparedStatement statement = connection.prepareStatement(SELECT_LENDING_BY_MAILADDRESS_QUERY,
				new String[] { "ID" });) {
			setMailAddressParameter(statement, mailAddress);
			ResultSet rs = statement.executeQuery();

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
	 *         /** 指定されたBookオブジェクトを新規にDBに登録する。 登録されたオブジェクトにはDB上のIDが上書きされる。
	 *         何らかの理由で登録に失敗した場合、IDがセットされない状態（=0）で返却される。
	 *
	 * @param Book
	 *            登録対象オブジェクト
	 * @return DB上のIDがセットされたオブジェクト
	 */
	public Book create(Book book) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return book;
		}

		try (PreparedStatement statement = connection.prepareStatement(INSERT_BOOK_QUERY, new String[] { "ID" });) {
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

	public int returnBookById(int id) {
		Connection connection = ConnectionProvider.getConnection();
		int resultNum;
		if (connection == null) {
			return 0;
		}

		try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY, new String[] { "ID" });) {
			// DELETE実行
			setDeleteParameter(statement, id);
			resultNum = statement.executeUpdate();

			// DELETE
			System.out.println(resultNum + "件更新");
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		} finally {
			ConnectionProvider.close(connection);
		}

		return resultNum;
	}

	public int returnBook(String mailAddress) {
		Connection connection = ConnectionProvider.getConnection();
		int resultNum;
		if (connection == null) {
			return 0;
		}

		try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL_QUERY, new String[] { "ID" });) {
			// DELETE実行
			setMailAddressParameter(statement, mailAddress);
			resultNum = statement.executeUpdate();

			// DELETE
			System.out.println(resultNum + "件更新all");
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		} finally {
			ConnectionProvider.close(connection);
		}

		return resultNum;
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
		// System.out.println(rs.getString("ID"));
		result.setId(Integer.parseInt(rs.getString("ID")));
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

	private void setDeleteParameter(PreparedStatement statement, int id) throws SQLException {
		statement.setInt(1, id);
	}

	private void setMailAddressParameter(PreparedStatement statement, String emailAddress) throws SQLException {
		statement.setString(1, emailAddress);
	}

	// ---------------------------------------------------------------

	public List<Book> findAll() {
		List<Book> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (Statement statement = connection.createStatement();) {
			ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);

			while (rs.next()) {
				result.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

	private Book processRow(ResultSet rs) throws SQLException {
		Book result = new Book();
		result.setId(rs.getInt("ID"));
		result.setTitle(rs.getString("TITLE"));
		result.setGenre(rs.getString("GENRE"));
		result.setAuthor(rs.getString("AUTHOR"));
		result.setStatus(rs.getString("STATUS"));

		return result;
	}

	private void setParameter(PreparedStatement statement, Book book) throws SQLException {
		int count = 1;

		statement.setString(count++, book.getTitle());
		statement.setString(count++, book.getGenre());
		statement.setString(count++, book.getAuthor());
		statement.setString(count++, book.getStatus());

	}

	public List<Book> findByParam(Param param) {
		List<Book> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		String queryString = "SELECT M.TITLE,M.GENRE,M.AUTHOR,M.STATUS  FROM (" + SELECT_ALL_QUERY + " ) M "
				+ param.getWhereClause();
		try (PreparedStatement statement = connection.prepareStatement(queryString)) {
			param.setParameter(statement);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				result.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

	public boolean borrowById(int id, String date, String mail) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return false;
		}

		try (PreparedStatement statement = connection.prepareStatement(INSERT_BOOK_QUERY)) {

			statement.setString(1, mail);
			statement.setInt(2, id);
			statement.setString(3, date);
			ResultSet rs = statement.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		} finally {
			ConnectionProvider.close(connection);
		}
	}

	private User getMailAddress(ResultSet rs) throws SQLException {
		User result = new User();
		result.setMailAddress(rs.getString("MAILADDRESS"));

		return result;
	}

	public Rental_Status create(Rental_Status rental_status) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return rental_status;
		}

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[] { "ID" });) {
			// INSERT実行
			setParameter(statement, rental_status, false);
			statement.executeUpdate();

			// INSERTできたらKEYを取得
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			String id = rs.getString(1);
			rental_status.setId(id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return rental_status;
	}

}