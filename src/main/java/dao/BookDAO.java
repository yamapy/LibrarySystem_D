package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Book;

public class BookDAO {
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
	private static final String INSERT_QUERY ="INSERT INTO \n" +
			"	RENTAL_STATUS(MAILADDRESS,BOOKID,RENTDATE) \n" +
			"VALUES \n" +
			"	(?,?,?) \n"
			;

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

		String queryString = "SELECT M.TITLE,M.GENRE,M.AUTHOR,M.STATUS  FROM ("+ SELECT_ALL_QUERY +" ) M "+ param.getWhereClause();
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




//
//	public Rental_Status create(Rental_Status rental_status) {
//		Connection connection = ConnectionProvider.getConnection();
//		if (connection == null) {
//			return rental_status;
//		}
//
//		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[] { "ID" });) {
//			// INSERT実行
//			setParameter(statement, rental_status, false);
//			statement.executeUpdate();
//
//			// INSERTできたらKEYを取得
//			ResultSet rs = statement.getGeneratedKeys();
//			rs.next();
//			String id = rs.getString(1);
//			rental_status.setId(id);
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		} finally {
//			ConnectionProvider.close(connection);
//		}
//
//		return rental_status;
//	}
//
//	public Rental_Status update(Rental_Status rental_status) {
//		Connection connection = ConnectionProvider.getConnection();
//		if (connection == null) {
//			return rental_status;
//		}
//
//		try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
//			setParameter(statement, rental_status, true);
//			statement.executeUpdate();
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		} finally {
//			ConnectionProvider.close(connection);
//		}
//
//		return rental_status;
//	}
//

}
