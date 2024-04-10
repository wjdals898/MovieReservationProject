package com.movie.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.movie.MainApplication;
import com.movie.dto.SeatDTO;
import com.movie.dto.UserDTO;
import com.movie.util.DBUtil;

public class ReservationDAO {

	Connection conn;
	CallableStatement cstmt;
	ResultSet rs;

	public List<Integer> createReservation(int theaterId, List<SeatDTO> seatInfo) {
		conn = DBUtil.dbConnection();
		String insertSql = "{call sp_create_reservation(?,?,?)}";
		String procedureSql = "{call sp_seat_reservation(?,?)}";
		int userId = MainApplication.user.getId();
		List<Integer> resultList = new ArrayList<Integer>();

		try {
			conn.setAutoCommit(false);
			cstmt = conn.prepareCall(insertSql);
			cstmt.setInt(1, userId);
			cstmt.setInt(2, theaterId);
			cstmt.registerOutParameter(3, Types.INTEGER);
			int result = cstmt.executeUpdate();
			System.out.println("result = "+result);
			int reservation_id = cstmt.getInt(3);
			System.out.println("예약아이디 = "+reservation_id);
			
			if (result == 1) { // insert 성공
				for (SeatDTO seat : seatInfo) {
					cstmt= conn.prepareCall(procedureSql);
					cstmt.setInt(1, reservation_id);
					cstmt.setInt(2, seat.getId());
					resultList.add(cstmt.executeUpdate());
				}
				System.out.println(resultList.toString());
				if(resultList.size()==seatInfo.size()) {	// 좌석이 모두 예매가능한 경우
					conn.commit();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, cstmt, rs);
		}
		return resultList;
	}

}
