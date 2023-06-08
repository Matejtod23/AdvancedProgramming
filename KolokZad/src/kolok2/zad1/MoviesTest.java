package kolok2.zad1;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class Movie{
    String naslov;
    int[] ratings;

    public Movie(String naslov, int[] ratings) {
        this.naslov = naslov;
        this.ratings = ratings;
    }

    public String getNaslov() {
        return naslov;
    }

    public int[] getRatings() {
        return ratings;
    }

    public double average(){
        return Arrays.stream(ratings).average().orElse(Double.NaN);
    }
    public int maxRating(){
        return Arrays.stream(ratings).max().getAsInt();
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings", naslov, this.average(), ratings.length);
    }
}


class MoviesList{
    TreeMap<String, Movie> movies;

    public MoviesList() {
        movies = new TreeMap<>();
    }


    public void addMovie(String title, int[] ratings) {
        movies.put(title, new Movie(title, ratings));
    }


    public List<Movie> top10ByAvgRating() {
        Comparator comparator = Comparator.comparing(Movie::average);
        return (List<Movie>) movies.values().stream().sorted(comparator.reversed()).limit(10).collect(Collectors.toList());
    }

    public int maxRatingOfAll(){
        return movies.values().stream().mapToInt(movie -> movie.maxRating()).max().getAsInt();
    }
    public List<Movie> top10ByRatingCoef() {
            Comparator<Movie> comparator = ((o1, o2) -> {
                double first = o1.average() * o1.ratings.length / maxRatingOfAll();
                double second = o2.average() * o2.ratings.length / maxRatingOfAll();

                return Double.compare(first, second);
            });

            return movies.values().stream().sorted(comparator.reversed()).limit(10).collect(Collectors.toList());
        }
}

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

// vashiot kod ovde
