package com.movie.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	// DB 연결
	public static Connection dbConnection() {
		// 1. JDBC Driver load
		// 2. Connection 생성
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "HJM";
		String password = "1234";
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//System.out.println("1. JDBC Driver load 성공");
			conn = DriverManager.getConnection(url,userid, password);
			//System.out.println("2.Connection 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// DB 연결해제
	public static void dbDisconnect(Connection conn, Statement st, ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(conn!=null) conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
