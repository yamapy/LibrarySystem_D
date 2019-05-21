package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Genre;



public class GenreDAO {

	private static final String SELECT_ALL_QUERY = "SELECT DISTINCT \n" +
			"	BOOK.GENRE \n" +
			"FROM \n" +
			"	BOOK \n" ;

	public List<Genre> findAllGenre() {
		List<Genre> result = new ArrayList<>();

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


	private Genre processRow(ResultSet rs) throws SQLException {
		Genre result = new Genre();
		result.setGenre(rs.getString("GENRE"));
		return result;
	}


}