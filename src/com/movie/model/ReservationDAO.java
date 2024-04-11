package com.movie.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.movie.MainApplication;
import com.movie.dto.ReservationDTO;
import com.movie.dto.SeatDTO;
import com.movie.dto.UserDTO;
import com.movie.util.DBUtil;

public class ReservationDAO {

	Connection conn;
	CallableStatement cstmt;
	PreparedStatement pst;
	ResultSet rs;
	int user_id = MainApplication.user.getId();

	public List<Integer> createReservation(int theaterId, List<SeatDTO> seatInfo) {
		conn = DBUtil.dbConnection();
		String insertSql = "{call sp_create_reservation(?,?,?)}";
		String procedureSql = "{call sp_seat_reservation(?,?)}";
		List<Integer> resultList = new ArrayList<Integer>();

		try {
			conn.setAutoCommit(false);
			cstmt = conn.prepareCall(insertSql);
			cstmt.setInt(1, user_id);
			cstmt.setInt(2, theaterId);
			cstmt.registerOutParameter(3, Types.INTEGER);
			int result = cstmt.executeUpdate();
			int reservation_id = cstmt.getInt(3);

			if (result == 1) { // insert 성공
				for (SeatDTO seat : seatInfo) {
					cstmt = conn.prepareCall(procedureSql);
					cstmt.setInt(1, reservation_id);
					cstmt.setInt(2, seat.getId());
					resultList.add(cstmt.executeUpdate());
				}
				if (resultList.size() == seatInfo.size()) { // 좌석이 모두 예매가능한 경우
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

	public List<ReservationDTO> showAll() {
		List<ReservationDTO> reservationList = new ArrayList<ReservationDTO>();
		conn = DBUtil.dbConnection();
		String sql = "select reservations.id, reservations.reservation_date, "
				+ "movies.movie_title, movies.running_time, "
				+ "theaters.screening_date, theaters.screening_time, seats.seat_num "
				+ "from (reservations join theaters on(reservations.theater_id = theaters.id))" + "    , movies"
				+ "    , seats join seats_reservations on(seats.id = seats_reservations.seat_id) "
				+ "where reservations.user_id = ?" 
				+ "    and theaters.movie_id = movies.id "
				+ "    and reservations.id = seats_reservations.reservation_id "
				+ "order by 1, seats.seat_num";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, user_id);
			rs = pst.executeQuery();
			int id = -1;
			List<String> seatList = new ArrayList<String>();
			while (rs.next()) {
				if (id != rs.getInt("id")) {
					seatList.clear();
					reservationList.add(makeReservation(rs));
				}
				seatList.add(rs.getString("seat_num"));
				reservationList.get(reservationList.size()-1).setSeats(seatList.toString());
				id = rs.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, cstmt, rs);
		}

		return reservationList;
	}

	public int cancelReservation(ReservationDTO cancelReservation) {
		int result = 0;
		conn = DBUtil.dbConnection();
		String deleteSql = "{call sp_delete_reservation(?)}";
		try {
			conn.setAutoCommit(false);
			cstmt = conn.prepareCall(deleteSql);
			cstmt.setInt(1, cancelReservation.getId());
			result = cstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, cstmt, rs);
		}
		return result;
	}

	private ReservationDTO makeReservation(ResultSet rs) throws SQLException {
		ReservationDTO reservation = new ReservationDTO();
		reservation.setId(rs.getInt("id"));
		reservation.setMovieTitle(rs.getString("movie_title"));
		reservation.setRunningTime(rs.getString("running_time"));
		reservation.setReservationDate(rs.getDate("reservation_date"));
		reservation.setScreeningDate(rs.getDate("screening_date"));
		reservation.setScreeningTime(rs.getString("screening_time"));
		reservation.setSeats("");

		return reservation;
	}


}
