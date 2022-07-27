package com.rental;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ActorService {
    Connection c = null;

    public ActorService() {
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

    public List<Actor> getActor(){
        List<Actor> actorList = new ArrayList<>();
        try {
            Statement stm = c.createStatement();
            ResultSet res = stm.executeQuery("select a.first_name, a.last_name,count(p.amount)\n" +
                    "from actor a\n" +
                    "join film_actor fa on a.actor_id= fa.actor_id\n" +
                    "join film f on fa.film_id=f.film_id\n" +
                    "join inventory inv on f.film_id = inv.film_id\n" +
                    "join rental r on inv.inventory_id = r.inventory_id\n" +
                    "join payment p on r.rental_id = p.rental_id\n" +
                    "group by a.first_name, a.last_name\n" +
                    "order by count(p.amount) desc\n" +
                    "limit 3");

            Random random = new Random();
            while(res.next()){
                String firstName = res.getString("first_name");
                String lastName= res.getString("last_name");
                Actor actor = new Actor();
                actor.setFirstName(firstName);
                actor.setLastName(lastName);
                actor.setActorImage(Actor.ACTORS.get(random.nextInt(Actor.ACTORS.size())));
                actorList.add(actor);
            }
            res.close();
            stm.close();
        } catch (SQLException e) {
            System.out.println("ERRORRRRRRR" + e);
            e.printStackTrace();
        }
        System.out.println(actorList);
        return actorList;
    }
}
