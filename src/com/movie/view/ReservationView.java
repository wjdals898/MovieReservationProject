package com.movie.view;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.movie.dto.SeatDTO;
import com.movie.dto.TheaterDTO;

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
