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
	 * SELECT_ALL_QUERY 書籍一覧
	 * INSERT_QUERY 借りる
	 * SELECT_LENDING_QUERY 貸出中の書籍を取得
	 * COUNT_LENDING_QUERY 貸出中の書籍件数を取得
	 * SELECT_BY_ID_QUERY 選択した書籍詳細を取得
	 * INSERT_QUERY_NEW 書籍新規追加
	 */

	/* 書籍一覧 */
	private static final String SELECT_ALL_QUERY = "SELECT \n" +
			"	B2.ID \n" +
			",	B2.TITLE \n" +
			",	B2.GENRE \n" +
			",	B2.AUTHOR \n" +
			",	'貸出可能' STATUS \n" +
			"FROM \n" +
			"	BOOK B2 \n" +
			",	(SELECT \n" +
			"		B.ID \n" +
			"	FROM \n" +
			"		BOOK B \n" +
			"	MINUS \n" +
			"	SELECT  \n" +
			"		B2.ID \n" +
			"	 \n" +
			"	FROM \n" +
			"		BOOK B2 \n" +
			"	,	RENTAL_STATUS R \n" +
			"	WHERE \n" +
			"		B2.ID = R.BOOKID \n" +
			"		)ableB \n" +
			"WHERE \n" +
			"	ableB.ID = B2.ID \n" +
			" \n" +
			"UNION \n" +
			" \n" +
			"SELECT \n" +
			"	B.ID \n" +
			",	B.TITLE \n" +
			",	B.GENRE \n" +
			",	B.AUTHOR \n" +
			",	'貸出中' \n" +
			"FROM \n" +
			"	BOOK B \n" +
			",	RENTAL_STATUS R \n" +
			"WHERE \n" +
			"	B.ID = R.BOOKID  \n"
			;

	/* 借りる */
	private static final String INSERT_QUERY ="INSERT INTO \n" +
			"	RENTAL_STATUS(MAILADDRESS,BOOKID,RENTDATE) \n" +
			"VALUES \n" +
			"	(?,?,?) \n"
			;

	/* 貸出中の書籍を取得 */
	private static final String SELECT_LENDING_QUERY = "select BOOK.ID, BOOK.TITLE, \n" + "EMPLOYEE.NAME, \n"
			+ "RENTAL_STATUS.RENTDATE + 14 RETURN_DATE \n" + "from EMPLOYEE, \n" + "BOOK, \n" + "RENTAL_STATUS \n"
			+ "where \n" + "BOOK.ID = RENTAL_STATUS.BOOKID \n"
			+ "and EMPLOYEE.MAILADDRESS = RENTAL_STATUS.MAILADDRESS \n" + "order by \n" + "RENTAL_STATUS.RENTDATE \n";

	/* private static final String SELECT_LENDING_QUERY_JUST20 = "SELECT a2.* \n"
			+ "FROM (SELECT a1.*, ROWNUM AS \"ADD_ROWNUM\" \n"
			+ "FROM \n"
			+ "( \n"
			+ "select BOOK.TITLE, \n"
			+ "EMPLOYEE.NAME,\n"
			+ "RENTAL_STATUS.RENTDATE + 14 RETURN_DATE \n"
			+ "from EMPLOYEE, \n"
			+ "BOOK, \n"
			+ "RENTAL_STATUS \n"
			+ "where \n"
			+ "BOOK.ID = RENTAL_STATUS.BOOKID \n"
			+ "and EMPLOYEE.MAILADDRESS = RENTAL_STATUS.MAILADDRESS \n"
			+ "order by \n"
			+ "RENTAL_STATUS.RENTDATE \n"
			+ ") a1 \n"
			+ ") a2 \n"
			+ "WHERE a2.\"ADD_ROWNUM\" BETWEEN ? AND ? \n";
	*/

	/* 貸出中の書籍件数を取得 */
	private static final String COUNT_LENDING_QUERY = "select COUNT(*) as TOTAL \n" + " from EMPLOYEE, \n" + "BOOK, \n"
	+ "RENTAL_STATUS \n" + "where \n" + "BOOK.ID = RENTAL_STATUS.BOOKID \n"
	+ "and EMPLOYEE.MAILADDRESS = RENTAL_STATUS.MAILADDRESS \n" + "order by \n" + "RENTAL_STATUS.RENTDATE \n";

	/* 選択した書籍詳細を取得 */
	private static final String SELECT_BY_ID_QUERY = "select BOOK.ID, \n" + "BOOK.TITLE, \n" + "BOOK.AUTHOR, \n"
			+ "BOOK.PUBLISHER, \n" + "BOOK.GENRE, \n" + "BOOK.PUTCHASEDATE, \n" + "BOOK.BUYER, \n" + "EMPLOYEE.NAME, \n"
			+ "EMPLOYEE.MAILADDRESS, \n" + "RENTAL_STATUS.RENTDATE + 14 RETURN_DATE \n" + "from EMPLOYEE, \n"
			+ "BOOK, \n" + "RENTAL_STATUS, \n" + "USER_T \n" + "where BOOK.ID = RENTAL_STATUS.BOOKID(+) \n"
			+ "	and EMPLOYEE.MAILADDRESS(+) = USER_T.MAILADDRESS \n"
			+ "and USER_T.MAILADDRESS(+) = RENTAL_STATUS.MAILADDRESS \n" + " and BOOK.ID = ? \n" + "order by \n"
			+ "BOOK.ID \n";

	/* 書籍新規追加 */
	private static final String INSERT_QUERY_NEW = "INSERT INTO "
			+ "BOOK(TITLE, AUTHOR, PUBLISHER, GENRE, PUTCHASEDATE, BUYER) " + "VALUES(?,?,?,?,?,?)";

	/**
	 * 書籍全件を取得する。
	 *
	 * @return DBに登録されている書籍全件を収めたリスト。途中でエラーが発生した場合は空のリストを返す。
	 */
	public List<Book> findAll() {
		List<Book> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (Statement statement = connection.createStatement();) {
			ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);

			while (rs.next()) {
				result.add(processRow2(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}


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
	 *下記のtryの中のifは受け取るデータが1行だから
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
			if (rs.next()){
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
	 * ID指定の検索を実施する。
	 * 詳細表示！！！！！
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

	public List<Book> findByParam(Param param) {
		List<Book> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		String queryString = "SELECT M.ID, M.TITLE,M.GENRE,M.AUTHOR,M.STATUS  FROM ("+ SELECT_ALL_QUERY +" ) M "+ param.getWhereClause();
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

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)){

			statement.setString(1,mail);
			statement.setInt(2, id);
			statement.setString(3,date);
			ResultSet rs = statement.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		} finally {
			ConnectionProvider.close(connection);
		}
	}

	/** 指定されたBookオブジェクトを新規にDBに登録する。 登録されたオブジェクトにはDB上のIDが上書きされる。
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

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY_NEW, new String[] { "ID" });) {
			// INSERT実行
			setParameterInsert(statement, book);
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
	 * 書籍一覧表示 検索結果行をオブジェクトとして構成する。
	 *
	 * @param rs
	 *            検索結果が収められているResultSet
	 * @return 検索結果行の各データを収めたBookインスタンス
	 * @throws SQLException
	 *             ResultSetの処理中発生した例外
	 */
	private Book processRow2(ResultSet rs) throws SQLException {
		Book result = new Book();
		result.setId(rs.getInt("ID"));
		result.setTitle(rs.getString("TITLE"));
		result.setGenre(rs.getString("GENRE"));
		result.setAuthor(rs.getString("AUTHOR"));
		result.setStatus(rs.getString("STATUS"));

		return result;
	}


	/**
	 * 検索結果からオブジェクトを復元する。
	 * 詳細表示の場合！！！！！
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

	private void setParameterInsert(PreparedStatement statement, Book book) throws SQLException {
		int count = 1;
		statement.setString(count++, book.getTitle());
		statement.setString(count++, book.getAuthor());
		statement.setString(count++, book.getPublisher());
		statement.setString(count++, book.getGenre());
		statement.setString(count++, book.getPurchaseDate());
		statement.setString(count++, book.getBuyer());
	}

	private void setParameter(PreparedStatement statement, Book book) throws SQLException {
		int count = 1;

		statement.setString(count++, book.getTitle());
		statement.setString(count++, book.getGenre());
		statement.setString(count++, book.getAuthor());
		statement.setString(count++, book.getStatus());


	}

}
