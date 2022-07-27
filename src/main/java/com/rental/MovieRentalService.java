package com.rental;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
            String myQuery=  "select f.film_id, f.title, f.description, " +
                    "c.name as category, f.release_year, f.length "+
                    "from category c "+
                    "join film_category fc on c.category_id = fc.category_id " +
                    "join film f on fc.film_id = f.film_id "+
                    "where c.name = ?";
            PreparedStatement pstm = c.prepareStatement(myQuery);
            pstm.setString(1, category);
            ResultSet res = pstm.executeQuery();

            while(res.next()){
                int filmId = res.getInt("film_id");
                String title = res.getString("title");
                String description = res.getString("description");
                String movieCategory = res.getString("category");
                int releaseYear =res.getInt("release_year");
                int length =res.getInt("length");
                Movie searchCat = new Movie();
                searchCat.setFilmId(filmId);
                searchCat.setTitle(title);
                searchCat.setDescription(description);
                searchCat.setCategory(movieCategory);
                searchCat.setReleaseYear(releaseYear);
                searchCat.setLength(length);
                searchCategory.add(searchCat);

            }
            res.close();
            pstm.close();
        } catch (SQLException e) {
            System.out.println("ERRORRRRRRR" + e);
            e.printStackTrace();
        }
        return searchCategory;

    }

    public List<Movie> getMostRented(){
        List<Movie> rentedList= new ArrayList<>();
        try {
            Statement stm = c.createStatement();
            ResultSet res = stm.executeQuery("" +
                    "select f.film_id, f.title, f.description,c.name, f.release_year, f.length, count(p.amount)\n" +
                    "from film f\n" +
                    "join film_category fc on f.film_id= fc.film_id\n" +
                    "join category c on fc.category_id = c.category_id\n" +
                    "join inventory inv on f.film_id = inv.film_id\n" +
                    "join rental r on inv.inventory_id = r.inventory_id\n" +
                    "join payment p on r.rental_id = p.rental_id\n" +
                    "group by f.film_id, f.title, f.description, c.name\n" +
                    "order by count(p.amount) desc\n" +
                    "limit 3\n");

            Random randomGenerator = new Random();
            while(res.next()){
                int filmId = res.getInt("film_id");
                String title = res.getString("title");
                String description = res.getString("description");
                String movieCategory = res.getString("name");
                int releaseYear =res.getInt("release_year");
                int length =res.getInt("length");
                Movie rented = new Movie();
                rented.setFilmId(filmId);
                rented.setTitle(title);
                rented.setDescription(description);
                rented.setCategory(movieCategory);
                rented.setReleaseYear(releaseYear);
                rented.setLength(length);
                rented.setImageName(Movie.IMAGES.get(randomGenerator.nextInt(Movie.IMAGES.size())));
                rentedList.add(rented);
            }
            res.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("ERRORRRRRRR" + e);
            e.printStackTrace();
        }
        return rentedList;
    }
}

