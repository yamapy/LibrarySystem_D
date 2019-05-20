package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.User;

public class UserDAO {

	private static final String SELECT_BY_MAILADRESS_QUERY = "select USER_T.MAILADDRESS, USER_T.PASSWORD, USER_T.MANAGEMENT from USER_T where USER_T.MAILADDRESS=? and USER_T.PASSWORD =?";

	@SuppressWarnings({ "null"})
	public User findByParam(String mailAdrress, String password) {
		User result = null;

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_MAILADRESS_QUERY)) {
			statement.setString(1, mailAdrress);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
					result.setMailAdress(rs.getString("MAILADRESS"));
					result.setPassword(rs.getString("PASSWORD"));
					result.setManagement(rs.getInt("MANAGEMENT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

}