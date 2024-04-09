package com.movie.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.movie.dto.MovieDTO;
import com.movie.util.DBUtil;

public class MovieDAO {

	Connection conn;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;

	public List<MovieDTO> showAll() { // 전체영화조회
		List<MovieDTO> movieList = new ArrayList<MovieDTO>();
		conn = DBUtil.dbConnection();
		String sql = "select * from movies order by id";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				movieList.add(makeMovie(rs));
			}
		} catch (SQLException e) {
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}

		return movieList;
	}

	public List<MovieDTO> showByScreening() {
		List<MovieDTO> movieList = new ArrayList<MovieDTO>();
		conn = DBUtil.dbConnection();
		String sql = "select * from movies where theaters_count>0 order by theaters_count";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				movieList.add(makeMovie(rs));
			}
		} catch (SQLException e) {
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return movieList;
	}
	
	public List<MovieDTO> searchByTitle(String title) {
		List<MovieDTO> movieList = new ArrayList<MovieDTO>();
		conn = DBUtil.dbConnection();
		String[] strList = title.split(" ");
		String sql = makeSql(strList);	// 입력된 내용을 공백으로 분리하여 각각의 단어가 포함되는 영화제목을 검색하는 sql문 만들기
		try {
			pst = conn.prepareStatement(sql);
			for(int i=1;i<=strList.length;i++) {
				pst.setString(i, strList[i-1]);
			}
			rs = pst.executeQuery();
			while(rs.next()) {
				movieList.add(makeMovie(rs));
			}
		} catch (SQLException e) {
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return movieList;
	}

	private String makeSql(String[] strList) {
		String sql = "select * from movies where movie_title like '%'||?||'%'";
		for(int i=1;i<strList.length;i++) {
			sql += " and movie_title like '%'||?||'%'";
		}
		
		return sql;
	}

	public static MovieDTO makeMovie(ResultSet rs) throws SQLException {
		MovieDTO movie = new MovieDTO();
		movie.setId(rs.getInt("id"));
		movie.setMovieTitle(rs.getString("movie_title"));
		movie.setDirector(rs.getString("director"));
		movie.setContent(rs.getString("content"));
		movie.setRunningTime(rs.getString("running_time"));
		movie.setTheaters_count(rs.getInt("theaters_count"));

		return movie;
	}


}
