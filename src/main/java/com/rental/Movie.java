package com.rental;

import java.util.Arrays;
import java.util.List;

public class Movie {
    public final static List<String> IMAGES = Arrays.asList("frost.jpg", "duck.webp", "grit.jpeg","friendsCoverFinal3.webp","shock.jpg","submarine.jpeg");

    private int filmId;
    private String title;
    private String description;
    private String category;
    private String imageName;
    private int releaseYear;
    private int length;
    private int rentalDuration;


    public int getFilmId(){
        return filmId;
    }
    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getCategory(){
        return category;
    }

    public int getReleaseYear(){
        return releaseYear;
    }

    public int getLength(){
        return length;
    }

    public int getRentalDuration(){
        return rentalDuration;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setFilmId(int filmId){
        this.filmId = filmId;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setReleaseYear(int releaseYear){
        this.releaseYear = releaseYear;
    }


    public void setLength(int length){
        this.length = length;
    }

    public void setRentalDuration(int rentalDuration){
        this.rentalDuration = rentalDuration;
    }

    @Override
    public String toString(){
        return "Title = " + this.title + "; Id = "+ this.length;
    }

}
