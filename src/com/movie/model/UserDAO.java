package com.movie.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.movie.dto.UserDTO;
import com.movie.util.DBUtil;

public class UserDAO {
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;

	public UserDTO login(String username, String password) {
		conn = DBUtil.dbConnection();
		String sql = "select * from users where username=? and password=?";
		UserDTO user = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			while(rs.next()) {
				user = makeUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		
		return user;
	}

	private UserDTO makeUser(ResultSet rs) throws SQLException {
		UserDTO user = new UserDTO();
		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("username"));
		user.setNickname(rs.getString("nickname"));
		user.setManager(rs.getInt("is_manager")==1?true:false);
		
		return user;
	}

	public int signup(String username, String password, String nickname) {
		conn = DBUtil.dbConnection();
		String sql = "insert into users values(users_seq.nextval, ?, ?, ?, 0)";
		int result = 0;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setString(3, nickname);
			result = pst.executeUpdate();
			
		} catch (SQLException e) {
			
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return result;
	}

}






