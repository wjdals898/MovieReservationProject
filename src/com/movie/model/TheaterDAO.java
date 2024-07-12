package com.movie.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.movie.dto.MovieDTO;
import com.movie.dto.TheaterDTO;
import com.movie.util.DBUtil;

public class TheaterDAO {
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;

	public List<TheaterDTO> showAll(MovieDTO movie) {
		List<TheaterDTO> theaterList = new ArrayList<TheaterDTO>();
		conn = DBUtil.dbConnection();
		
		String sql = "select * from theaters where movie_id = ? "
				+ "and to_char(screening_date, 'yyyy-mm-dd') >= to_char(sysdate, 'yyyy-mm-dd')";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, movie.getId());
			rs = pst.executeQuery();
			while(rs.next()) {
				theaterList.add(makeTheater(rs, movie));
			}
		} catch (SQLException e) {
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		
		return theaterList;
	}

	public List<TheaterDTO> showByDate(MovieDTO movie, Date selectDate) {
		List<TheaterDTO> theaterList = new ArrayList<TheaterDTO>();
		conn = DBUtil.dbConnection();
		String sql = "select * from theaters "
				+ "where movie_id = ? and to_char(screening_date, 'yyyy-mm-dd') = to_char(?,'yyyy-mm-dd')";
		
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, movie.getId());
			pst.setDate(2, selectDate);
			rs = pst.executeQuery();
			while(rs.next()) {
				theaterList.add(makeTheater(rs, movie));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return theaterList;
	}

	private TheaterDTO makeTheater(ResultSet rs, MovieDTO movie) throws SQLException {
		TheaterDTO theater = new TheaterDTO();
		theater.setId(rs.getInt("id"));
		theater.setMovie(movie);
		theater.setScreeningDate(rs.getDate("screening_date"));
		theater.setScreeningTime(rs.getString("screening_time"));
		theater.setRemainingSeatsCount(rs.getInt("remaining_seats_count"));
		
		return theater;
	}

	public int addTheater(TheaterDTO newTheater) {
		int result = 0;
		conn = DBUtil.dbConnection();
		String sql = "insert into theaters values(theaters_seq.nextval, ?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, newTheater.getMovie().getId());
			pst.setDate(2, newTheater.getScreeningDate());
			pst.setString(3, newTheater.getScreeningTime());
			pst.setInt(4, newTheater.getRemainingSeatsCount());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}

}
