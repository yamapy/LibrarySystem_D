package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Employee;
import beans.User;

public class UserDAO {

	private static final String SELECT_BY_MAILADDRESS_QUERY = "select USER_T.MAILADDRESS, USER_T.PASSWORD, USER_T.MANAGEMENT from USER_T where USER_T.MAILADDRESS = ? and USER_T.PASSWORD = ?";
	private static final String SELECT_ALL_EMPLOYEE_QUERY = "select EMPLOYEE.ID,EMPLOYEE.NAME,EMPLOYEE.MAILADDRESS from EMPLOYEE";
	private static final String INSERT_QUERY = "INSERT INTO USER_T ( MAILADDRESS , PASSWORD, MANAGEMENT) VALUES(?,?,?)";

	public User findByParam(String mailAddrress, String password) {
		User result = new User();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_MAILADDRESS_QUERY)) {
			statement.setString(1, mailAddrress);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
					result.setMailAddress(rs.getString("MAILADDRESS"));
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

	public List<Employee> getEmployeeInfo(){
		List<Employee> result = new ArrayList<>();
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_EMPLOYEE_QUERY)) {

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

	public User create(User user) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return user;
		}

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[] { "ID" });) {
			// INSERT実行
//			setParameter(statement, user, false);
			statement.setString(1, user.getMailAddress());
			statement.setString(2, user.getPassword());
			statement.setInt(3, 0);
			statement.executeUpdate();

			// INSERTできたらKEYを取得
//			ResultSet rs = statement.getGeneratedKeys();
//			rs.next();
//			int id = rs.getInt(1);
//			user.setId(id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return user;
	}


	private Employee processRow(ResultSet rs) throws SQLException {
		Employee result = new Employee();

		// Employee本体の再現
		result.setId(rs.getInt("ID"));
		result.setName(rs.getString("NAME"));
		result.setMailAddress(rs.getString("MAILADDRESS"));

		return result;
	}

}
