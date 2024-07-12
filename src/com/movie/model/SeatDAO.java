package com.movie.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.movie.dto.SeatDTO;
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
				+ " where theater_id = ?";
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
	
	public List<SeatDTO> showBySeatNum(String[] seatNumList, int theaterId) {
		List<SeatDTO> seatList = new ArrayList<SeatDTO>();
		conn = DBUtil.dbConnection();
		String sql = "select * from seats where theater_id = ? and seat_num in (?" + ",?".repeat(seatNumList.length-1) + ") and is_soldout=0";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, theaterId);
			for(int i=1;i<=seatNumList.length;i++) {
				pst.setString(i+1, seatNumList[i-1]);
			}
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
