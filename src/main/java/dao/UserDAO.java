package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.User;

public class UserDAO {

	private static final String SELECT_BY_MAILADRESS_QUERY = "select USER_T.MAILADDRESS, USER_T.PASSWORD, USER_T.MANAGEMENT from USER_T where USER_T.MAILADDRESS= ? and USER_T.PASSWORD = ?";
	private static final String SELECT_ALL_EMPLOYEE = "";

	public User findByParam(String mailAdrress, String password) {
		User result = new User();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_MAILADRESS_QUERY)) {
			statement.setString(1, mailAdrress);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
					result.setMailAdress(rs.getString("MAILADDRESS"));
					result.setPassword(rs.getString("PASSWORD"));
					result.setManagement(rs.getInt("MANAGEMENT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}
		System.out.println(result.getMailAdress()+result.getPassword()+"ok");

		return result;
	}

//	public List<Employee> getEmployeeName(String id){
//		List<Employee> result = new ArrayList<>();
//		Connection connection = ConnectionProvider.getConnection();
//		if (connection == null) {
//			return result;
//		}
//
//		try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_MAILADRESS_QUERY)) {
//			statement.setString(1, id);
//
//			ResultSet rs = statement.executeQuery();
//
//			wihle (rs.next()) {
//					result.setId(rs.getInt("ID"));
//					result.setName(rs.getString("NAME"));
//					result.setMailAdress(rs.getString("MAILADRESS"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			ConnectionProvider.close(connection);
//		}
//
//		return result;
//	}

}