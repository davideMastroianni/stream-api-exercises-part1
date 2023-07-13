package com.example.exercises;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.domain.Director;
import com.example.domain.Movie;
import com.example.service.InMemoryMovieService;
import com.example.service.MovieService;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
public class Exercise1 {
	private static final MovieService movieService = InMemoryMovieService.getInstance();

	public static void main(String[] args) {

		Map<String, Integer> movieByDirector = movieService
				.findAllMovies()
				.stream()
				.flatMap(movie -> movie.getDirectors()
					.stream()
					.map(director -> {
						movie.setDirectors(List.of(director));
						return movie;
					}))
				.collect(Collectors.groupingBy(
						movie -> movie.getDirectors().get(0).getName(),
						Collectors.reducing(0, e -> 1, Integer::sum)));

		System.out.println(movieByDirector);
	}
	public static void mainEmanuele(String[] args) {

		Map<String, Long> movieByDirector = movieService
				.findAllMovies()
				.stream()
				.map(Movie::getDirectors)
				.flatMap(Collection::stream)
				.collect(Collectors.groupingBy(Director::getName, Collectors.counting()));

		System.out.println(movieByDirector);
	}

}
