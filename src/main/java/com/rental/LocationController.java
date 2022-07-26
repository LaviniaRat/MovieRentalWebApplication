package com.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LocationController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private MovieRentalService movieService;



    @GetMapping("/about")
    public String locationPage(Model model) {
        List<Location> locationList = locationService.getLocation();
        model.addAttribute("locationList", locationList);
        List<String> categoryList = movieService.getCategories();
        model.addAttribute("categoryList", categoryList);
        return "about.html";
    }

}
