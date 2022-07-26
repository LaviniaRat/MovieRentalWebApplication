package com.rental;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieRentalService {
    Connection c = null;

    public MovieRentalService() {
        connectTodb("dvdrental", "postgres", "java");
    }

    public Connection connectTodb(String dbname, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, password);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": " +e.getMessage());
        }

        return c;
    }

    public List<Movie> displayList(){
        Statement stm = null;
        List<Movie> movieList = new ArrayList<>();
        try {
            stm = c.createStatement();
            ResultSet res = stm.executeQuery("select f.film_id, f.title, f.description,c.name,f.length, f.release_year, f.rental_duration\n" +
                    "from film f\n" +
                    "join film_category fg on f.film_id = fg.film_id\n" +
                    "join category c on fg.category_id = c.category_id");
            while(res.next()){
                int filmId = res.getInt("film_id");
                String title = res.getString("title");
                String description = res.getString("description");
                String category = res.getString("name");
                int releaseYear =res.getInt("release_year");
                int length =res.getInt("length");
                int rentalDuration =res.getInt("rental_duration");
                Movie movie = new Movie();
                movie.setFilmId(filmId);
                movie.setTitle(title);
                movie.setDescription(description);
                movie.setCategory(category);
                movie.setReleaseYear(releaseYear);
                movie.setLength(length);
                movie.setRentalDuration(rentalDuration);
                movieList.add(movie);
            }
            res.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("ERRORRRRRRR" + e);
            e.printStackTrace();
        }
        return movieList;
    }

    public void addMovie(Movie movie) {

    }


    public List<Movie> searchMovie(String word){
        List<Movie> searchList = new ArrayList<>();
        try {
            Statement stm = c.createStatement();
            ResultSet res = stm.executeQuery("select f.film_id, f.title, f.description,c.name,f.length, f.release_year, f.rental_duration\n" +
                            "from film f\n" +
                            "join film_category fg on f.film_id = fg.film_id\n" +
                            "join category c on fg.category_id = c.category_id\n"+
                    "group by f.film_id, c.name\n"+
                    "having f.title like '%" +word +"%'");
            while(res.next()){
                int filmId = res.getInt("film_id");
                String title = res.getString("title");
                String description = res.getString("description");
                String category = res.getString("name");
                int releaseYear =res.getInt("release_year");
                int length =res.getInt("length");
                int rentalDuration =res.getInt("rental_duration");
                Movie searchmovie = new Movie();
                searchmovie.setFilmId(filmId);
                searchmovie.setTitle(title);
                searchmovie.setDescription(description);
                searchmovie.setCategory(category);
                searchmovie.setReleaseYear(releaseYear);
                searchmovie.setLength(length);
                searchmovie.setRentalDuration(rentalDuration);
                searchList.add(searchmovie);
            }
            res.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("ERRORRRRRRR" + e);
            e.printStackTrace();
        }
        return searchList;
    }

    public List<String> getCategories(){
        List<String> categoryList = new ArrayList<>();
        try {
            Statement stm = c.createStatement();
            ResultSet res = stm.executeQuery("select name from category");
            while(res.next()){
                String category = res.getString("name");
                categoryList.add(category);
            }
            res.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("ERRORRRRRRR" + e);
            e.printStackTrace();
        }
        System.out.println(categoryList);
        return categoryList;
    }

    public List<Movie> getMovies(String category){
        List<Movie> searchCategory= new ArrayList<>();
        try {
            Statement stm = c.createStatement();
            ResultSet res = stm.executeQuery("" +
                    "select f.film_id, f.title, f.description,c.name\n"+
                    "from category c\n"+
                    "join film_category fc on c.category_id = fc.category_id\n" +
                    "join film f on fc.film_id = f.film_id\n"+
                    "where c.name = '" + category +"'");

            while(res.next()){
                int filmId = res.getInt("film_id");
                String title = res.getString("title");
                String description = res.getString("description");
                String movieCategory = res.getString("name");
                Movie searchCat = new Movie();
                searchCat.setFilmId(filmId);
                searchCat.setTitle(title);
                searchCat.setDescription(description);
                searchCat.setCategory(movieCategory);
                searchCategory.add(searchCat);
            }
            res.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("ERRORRRRRRR" + e);
            e.printStackTrace();
        }
        return searchCategory;
    }
}

