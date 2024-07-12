package com.movie.view;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;
//import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.movie.MainApplication;
import com.movie.dto.ReservationDTO;
import com.movie.dto.SeatDTO;
import com.movie.dto.TheaterDTO;
import com.movie.util.DBUtil;
import com.movie.util.DateUtil;

public class ReservationView {

	public static void showBeforeReservation(TheaterDTO theater, List<SeatDTO> seatInfo) {
		String startTime = theater.getScreeningTime();
		String endTime = calculateEndTime(startTime, theater.getMovie().getRunningTime());
		String seatNums = seatInfo.stream().map(seat->seat.getSeatNum()).collect(Collectors.toList()).toString();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++");
		System.out.println("|               예매정보 확인                |");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++");
		System.out.println(" 영화제목 | "+theater.getMovie().getMovieTitle());
		System.out.println(" 상영날짜 | "+theater.getScreeningDate()+" "+startTime+"~"+endTime);
		System.out.println(" 좌석번호 | "+seatNums);
		
	}

	public static void showReservationList(List<ReservationDTO> reservationList) {
		String nickname = MainApplication.user.getNickname();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++");
		System.out.println("|                 예매내역                 |");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++");
		System.out.println("["+nickname+"]님");
		reservationList.stream()
		.forEach((reservation)->{
			String startTime = reservation.getScreeningTime();
			String endTime = calculateEndTime(startTime, reservation.getRunningTime());
			System.out.println("-----------------------------------------");
			print(reservation, startTime, endTime);
		});
		
	}

	public static void showCancelableList(List<ReservationDTO> reservationList) {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++");
		System.out.println("|             취소가능한 예매내역             |");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++");
		reservationList.stream()
		.forEach((reservation)->{
			Date now = DateUtil.getSQLDate((new Date(System.currentTimeMillis())).toString());
			Date screening = DateUtil.getSQLDate(reservation.getScreeningDate().toString());
			Calendar cal = Calendar.getInstance();
			String startTime = reservation.getScreeningTime();
			String endTime = calculateEndTime(startTime, reservation.getRunningTime());
			
			if(screening.before(now)) {	// 상영날짜 지남
				return;
			} else if(screening.toString().equals(now.toString())) {	// 당일인 경우 - 상영시간 비교
				if(Integer.parseInt(startTime.substring(0, 1))<cal.get(Calendar.HOUR_OF_DAY)
						&& Integer.parseInt(startTime.substring(3,4)) <cal.get(Calendar.MINUTE)) {
					return;
				}
			}
			print(reservation, startTime, endTime);
			System.out.println("-----------------------------------------");
		});
		
	}
	
	public static void print(ReservationDTO reservation, String startTime, String endTime) {
		System.out.println(" 예매번호 | "+reservation.getId());
		System.out.println(" 영화제목 | "+reservation.getMovieTitle());
		System.out.println(" 예약날짜 | "+reservation.getReservationDate());
		System.out.println(" 상영날짜 | "+reservation.getScreeningDate()+" "+startTime+"~"+endTime);
		System.out.println(" 좌석번호 | "+reservation.getSeats().toString());
	}
	
	public static String calculateEndTime(String startTime, String runningTime) {
		String[] endTime = new String[2];
		String[] splitStart = startTime.split(":");
		String regExp = "([0-9]+)시간([0-9]+)분";
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(runningTime);
		if(matcher.find()) {
			int hour = Integer.parseInt(matcher.group(1))+Integer.parseInt(splitStart[0]);
			int minute = Integer.parseInt(matcher.group(2))+Integer.parseInt(splitStart[1]); 
			if(minute != 0) {
				endTime[0] = "0"+(hour+minute/60);
				endTime[1] = "0"+(minute%60);
			} else {
				endTime[0] = "0"+hour;
				endTime[1] = "00";
			}
		}
		return endTime[0].substring(endTime[0].length()-2)+":"+endTime[1].substring(endTime[1].length()-2);
	}

}
