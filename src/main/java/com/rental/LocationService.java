package com.rental;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {
    Connection c = null;

    public LocationService() {
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

    public  List<Location> getLocation(){
        List<Location> locationList = new ArrayList<>();
        try {
            Statement stm = c.createStatement();
            ResultSet res = stm.executeQuery("SELECT ad.address, c.city, cnt.country\n" +
                    "from store s\n" +
                    "join address ad on s.address_id = ad.address_id\n" +
                    "join city c on ad.city_id = c.city_id\n" +
                    "join country cnt on c.country_id = cnt.country_id");
            while(res.next()){
                String address = res.getString("address");
                String city = res.getString("city");
                String country = res.getString("country");
                Location location = new Location();
                location.setAddress(address);
                location.setCity(city);
                location.setCountry(country);
                locationList.add(location);
            }
            res.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("ERRORRRRRRR" + e);
            e.printStackTrace();
        }
        System.out.println(locationList);
        return locationList;
    }
}
