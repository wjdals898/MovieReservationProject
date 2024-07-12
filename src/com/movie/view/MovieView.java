package com.movie.view;

import java.util.List;

import com.movie.dto.MovieDTO;

public class MovieView {

	public static void printAll(List<MovieDTO> movieList, String title) {
		System.out.println("-----------------------------------------");
		System.out.println("         "+title);
		System.out.println("-----------------------------------------");
		System.out.println(" 번호\t제목\t감독\t상영시간\t소개\t상영여부");
		System.out.println("-----------------------------------------");
		int i = 1;
		for(MovieDTO movie:movieList) {
			String screening = movie.getTheaters_count()>0 ? "상영중" : "상영종료";
			System.out.printf("<%d>\t%s\t%s\t%s\t%s\t%s\n"
					,i++, movie.getMovieTitle(), movie.getDirector(), movie.getRunningTime(), movie.getContent(), screening);
		}
		
	}

	public static void printScreening(List<MovieDTO> movieList) {
		System.out.println("-----------------------------------------");
		System.out.println("         상영중인 영화 리스트");
		System.out.println("-----------------------------------------");
		System.out.println(" 번호\t제목\t감독\t상영시간\t소개\t상영관수");
		System.out.println("-----------------------------------------");
		int i = 1;
		for(MovieDTO movie:movieList) {
			System.out.printf("<%d>\t%s\t%s\t%s\t%s\t%s\n"
					,i++, movie.getMovieTitle(), movie.getDirector(), movie.getRunningTime(), movie.getContent(), movie.getTheaters_count());
		}
		
	}

}
