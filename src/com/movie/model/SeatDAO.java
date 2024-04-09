package com.movie.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.movie.util.DBUtil;

public class SeatDAO {
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;

	public List<SeatDTO> showAll(int theaterId) {
		List<SeatDTO> seatList = new ArrayList<SeatDTO>();
		conn = DBUtil.dbConnection();
		String sql = "select *"
				+ " from seats"
				+ " where theater_id = ? and is_soldout = 0";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, theaterId);
			rs = pst.executeQuery();
			while(rs.next()) {
				seatList.add(makeSeat(rs));
			}
		} catch (SQLException e) {
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return seatList;
	}

	private SeatDTO makeSeat(ResultSet rs) throws SQLException {
		SeatDTO seat = new SeatDTO();
		seat.setId(rs.getInt("id"));
		seat.setSeatNum(rs.getString("seat_num"));
		seat.setSoldout(rs.getInt("is_soldout")==0? false : true);
		
		return seat;
	}

}
