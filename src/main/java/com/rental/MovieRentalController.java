package com.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieRentalController {
    @Autowired
    private MovieRentalService movieService;
    @Autowired
    private ActorService actorService;

    @GetMapping("/home")
    public String homePage(Model model){
        List<String> categoryList = movieService.getCategories();
        model.addAttribute("categoryList", categoryList);
        return "home.html";
    }

    @GetMapping("/movies")
    public String viewList(Model model, @RequestParam String category) {
        List<Movie> movieList;
        if (category == null || category.isEmpty()) {
            movieList = movieService.displayList();
        } else {
            movieList = movieService.getMovies(category);
        }
        model.addAttribute("movies", movieList);

        List<String> categoryList = movieService.getCategories();
        model.addAttribute("categoryList", categoryList);
        return "movies.html";
    }

    @PostMapping("/movies")
    public String searchList(@RequestParam String query, Model model) {
        List<Movie> searchList = movieService.searchMovie(query);
        model.addAttribute("movies", searchList);
        return "movies.html";
    }

    @GetMapping("/rented")
    public String rentedPage(Model model){
        List<String> categoryList = movieService.getCategories();
        model.addAttribute("categoryList", categoryList);
        List<Movie> rentedList=movieService.getMostRented();
        model.addAttribute("rentedList",rentedList);
        List<Actor> actorList =actorService.getActor();
        model.addAttribute("actorList", actorList);
        return "rented.html";
    }


}
