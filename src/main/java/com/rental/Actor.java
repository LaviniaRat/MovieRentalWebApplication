package com.rental;

import java.util.Arrays;
import java.util.List;

public class Actor {
    private String firstName;
    private String lastName;
    private String actorImage;
    public final static List<String> ACTORS = Arrays.asList("actor2.png","actor3.jpeg", "actor4.png", "actor5.jpeg", "actor6.jpeg", "actor7.jpeg");

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    public void setLastName(String lastName){
        this.lastName=lastName;
    }

    public String getActorImage() {
        return actorImage;
    }

    public void setActorImage(String actorImage) {
        this.actorImage = actorImage;
    }
}
