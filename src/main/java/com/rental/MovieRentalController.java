package com.rental;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MovieRentalController {
    private final MovieRentalService movieService;

    public MovieRentalController(MovieRentalService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/home")
    public String homePage(Model model){
        List<String> categoryList = movieService.getCategories();
        model.addAttribute("categoryList", categoryList);
        return "home.html";
    }


    @GetMapping("/movies")
    public String viewList(Model model) {
        List<Movie> movieList = movieService.displayList();
        model.addAttribute("movies", movieList);
        return "movies.html";
    }

    @PostMapping("/movie")
    public void addMovie(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int releaseYear,
            @RequestParam int length,
            @RequestParam int rentalDuration,
            Model model
    ){
        Movie p = new Movie();
        p.setTitle(title);
        p.setDescription(description);
        p.setLength(length);
        p.setReleaseYear(releaseYear);
        p.setRentalDuration(rentalDuration);
        movieService.addMovie(p);

    }

    @PostMapping("/movies")
    public String searchList(@RequestParam String query, Model model) {
        List<Movie> searchList = movieService.searchMovie(query);
        model.addAttribute("movies", searchList);
        return "movies.html";
    }


}
