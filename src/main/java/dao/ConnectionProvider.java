package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBへのコネクションオブジェクトを管理するためのヘルパークラス。
 */
public class ConnectionProvider {
    /**
     * データベース接続情報
     */

	//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

	private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@ec2-54-248-14-40.ap-northeast-1.compute.amazonaws.com:1521:XE";
    private static final String USER_ID = "TEAMD";
    private static final String PASSWORD = "TEAMD";

    /**
     * コネクションオブジェクトを取得する。
     * コネクションオブジェクトを利用し終えたら必ずcloseメソッドで終了処理をすること。
     * @return コネクションオブジェクト。何らかの問題があった場合はnullを返す。
     */
    public static Connection getConnection() {
		Connection connection = null;

		try {
			Class.forName(DRIVER_NAME);
			connection = DriverManager.getConnection(URL, USER_ID, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
    }

    /**
     * コネクションオブジェクトをクローズする。
     * @param connection クローズ対象のコネクションオブジェクト。
     */
    public static void close(Connection connection) {
    	try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
