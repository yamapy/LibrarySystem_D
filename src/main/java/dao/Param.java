package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DAOにクエリーパラムを引き渡すためのオブジェクト。
 */
public class Param {
	private final static String BASE_WHERE_CLAUSE = " WHERE ";

	private String titleParam;
	private String authorParam;
	private String genre;
	private String status;

	public Param(String titleParam, String authorParam, String genre, String status) {
		this.setTitleParam(titleParam);
		this.setAuthorParam(authorParam);
		this.setGenre(genre);
		this.setStatus(status);
	}

	public String getTitleParam() {
		return titleParam;
	}
	public void setTitleParam(String titleParam) {
		this.titleParam = titleParam == null ? "" : "%"+titleParam+"%";
	}

	public String getAuthorParam() {
		return authorParam;
	}
	public void setAuthorParam(String authorParam) {
		this.authorParam = authorParam == null ? "" : "%"+authorParam+"%";
	}

	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}



	/**
	 * 登録されているパラメータの状態からWHERE句を生成する。
	 *
	 * @return SQLのWHERE句
	 */
	public String getWhereClause() {
		StringBuilder whereClause = new StringBuilder();
		if (!titleParam.isEmpty()) {
			if (whereClause.length() == 0) {
				whereClause.append(BASE_WHERE_CLAUSE);
			} else {
				whereClause.append(" AND ");
			}
			whereClause.append("BOOK.TITLE LIKE ?");
		}
		if (!authorParam.isEmpty()) {
			if (whereClause.length() == 0) {
				whereClause.append(BASE_WHERE_CLAUSE);
			} else {
				whereClause.append(" AND ");
			}
			whereClause.append("BOOK.AUTHOR = ?");
		}
		if (!genre.isEmpty()) {
			if (whereClause.length() == 0) {
				whereClause.append(BASE_WHERE_CLAUSE);
			} else {
				whereClause.append(" AND ");
			}
			whereClause.append("BOOK.GENRE = ?");
		}

		if (!status.isEmpty()) {
			if (whereClause.length() == 0) {
				whereClause.append(BASE_WHERE_CLAUSE);
			} else {
				whereClause.append(" AND ");
			}
			whereClause.append("BOOK.STATUS = ?");
		}

		// ORDER BYは最後に指定する
//		whereClause.append(" ORDER BY EMP.ID");

		return whereClause.toString();
	}

	/**
	 * getWhereClauseメソッドで設定されたWHERE句を含むSQLにパラメータをセットする
	 *
	 * @param statement パラメータをセットする対象のPreparedStatement
	 * @throws SQLException パラメータの設定時に何らかの問題があった場合
	 */
	public void setParameter(PreparedStatement statement) throws SQLException {
		int count = 1;
		if (!status.isEmpty()) {
			statement.setString(count++, );
		}
		if (!empId.isEmpty()) {
			statement.setString(count++, empId);
		}
		if (!nameParam.isEmpty()) {
			statement.setString(count++, nameParam);
		}
	}
}
