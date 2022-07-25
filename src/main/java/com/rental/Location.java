package com.rental;

public class Location {
    private String address;
    private String city;
    private String country;

    public String getAddress(){
        return address;
    }

    public String getCity(){
        return city;
    }

    public String getCountry(){
        return country;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

