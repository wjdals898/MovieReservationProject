package com.movie.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.movie.util.DBUtil;

public class UserDAO {
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;

	public String login(String username, String password) {
		conn = DBUtil.dbConnection();
		String sql = "select * from users where username=? and password=?";
		String nickname = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			while(rs.next()) {
				nickname = rs.getString("nickname");
				System.out.println(nickname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		
		return nickname;
	}

}
