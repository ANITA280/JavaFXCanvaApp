package dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";

	public UserDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection(); Statement stmt = connection.createStatement();) {

			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL,"
					+ "password VARCHAR(8) NOT NULL," + "lastname VARCHAR(10) NOT NULL,"
					+ "firstname VARCHAR(10) NOT NULL," + "profilePhoto TEXT," + "PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
		}
	}

	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setfirstName(rs.getString("firstname"));
					user.setlastName(rs.getString("lastname"));
					user.setlogo(rs.getString("profilePhoto"));

					return user;
				}
				return null;
			}
		}
	}

	@Override
	public User updateUser(String username, String password, String firstName, String lastName, String profilePhoto)
			throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET firstname = ?, lastname = ? , profilePhoto = ?"
				+ " WHERE username = ? AND password = ?";
		String sqlq = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, profilePhoto);
			stmt.setString(4, username);
			stmt.setString(5, password);

			int gotResults = stmt.executeUpdate();

			if (gotResults < 0) {
				System.out.println("No results returned");
			} else {
				User user = new User();
				user = getUser(username, password);

				return user;
			}
		}

		return null;
	}

	@Override
	public User createUser(String username, String password, String firstName, String lastName, String profilePhoto)
			throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?,?,?,?,?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, firstName);
			stmt.setString(4, lastName);
			stmt.setString(5, profilePhoto);
			stmt.executeUpdate();
			return new User(username, password, firstName, lastName, profilePhoto);
		}
	}
}
