package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import objects.*;
import java.io.IOException;
import java.util.*;

import static models.Addresses.*;

public class MizDooni {
    private static MizDooni instance;
    public ResponseHandler responseHandler = new ResponseHandler();
    public ArrayList<User> users = new ArrayList<User>();
    public ArrayList<Review> reviews = new ArrayList<>();
    public ArrayList<Restaurant> restaurants = new ArrayList<>();
    public ArrayList<Reservation> reservations = new ArrayList<>();
    public ArrayList<Table> tables = new ArrayList<>();

    public MizDooni() throws IOException {
        Reader rd = new Reader();
        this.users = rd.readUsersFromFile(USERS_CSV);
        this.restaurants = rd.readRestaurantsFromFile(RESTAURANTS_CSV);
        this.reviews = rd.readReviewsFromFile(REVIEWS_CSV);
        removeRedundantReviews();
        this.reservations = rd.readReservationsFromFile(RESERVATIONS_CSV);
        this.tables = rd.readTablesFromFile(TABLES_CSV);
    }

    public void addTable(Table table) {
        this.tables.add(table);
        String tableString = table.restaurantName+","+table.managerUsername+","+table.tableNumber+","+table.seatsNumber+"\n";
        Writer writer = new Writer();
        try {
            writer.writeReview(TABLES_CSV, tableString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void restartMizdooni() {
        Writer writer = new Writer();
        writer.resetFile(CURRENT_USER_ADDRESS);
        writer.resetFile(CURRENT_RESTAURANT_ADDRESS);
    }

    public ArrayList<Reservation> getUserReservations(String username) {
        ArrayList<Reservation> reservations1 = new ArrayList<>();
        for(Reservation r : reservations) {
            if (Objects.equals(r.username, username)) {

                reservations1.add(r);
            }
        }

        return reservations1;
    }

    public String createHtmlForUserReservations(ArrayList<Reservation> reservations2) {
        String html = "";
        for (Reservation r : reservations2) {
            html += "<tr><td>"+ r.reservationNumber +"</td>\n" +
                    "<td><a href=\"/restaurant/" + r.restaurantName+"\"" + "</a>" + r.restaurantName + "</td>\n"+
                    "<td>"+ r.tableNumber +"</td>\n" +
                    "<td>"+ r.datetime +"</td>"+
                    "<td>\n" +
                    "            <form action=\"/reservations\" method=\"POST\">\n" +
                    "                <button type=\"submit\" name=\"action\" value=\""+ r.reservationNumber +"\">Cancel This</button>\n" +
                    "            </form>\n" +
                    "        </td> </tr>"
            ;
        }

        return html;
    }

    public void addReservation(Reservation reservation) {
        System.out.println("3333333");
        Writer writer = new Writer();
        String reservation1 = reservation.username+","+
                reservation.reservationNumber+","+reservation.restaurantName+","+
                reservation.tableNumber+","+reservation.datetime+"\n";

        System.out.println("in add "+ reservation1);

        try {
            writer.writeReview(RESERVATIONS_CSV, reservation1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelReservation(String reservationNumber) {
        Writer writer = new Writer();
        writer.removeReservationFromFile(reservationNumber, RESERVATIONS_CSV);
    }

    public ArrayList<Restaurant> getRestaurantsByName(String search) {
        ArrayList<Restaurant> restaurants1 = new ArrayList<>();
            for (Restaurant r : restaurants) {
                if (r.name.contains(search)) {
                    restaurants1.add(r);
                }
            }

            return restaurants1;
    }

    public ArrayList<Restaurant> getRestaurantsByType(String search) {
        ArrayList<Restaurant> restaurants1 = new ArrayList<>();
        for (Restaurant r : restaurants) {
            if (Objects.equals(r.type, search)) {
                restaurants1.add(r);
            }
        }

        return restaurants1;
    }

    public ArrayList<Restaurant> getRestaurantsByCity(String search) {
        ArrayList<Restaurant> restaurants1 = new ArrayList<>();
        for (Restaurant r : restaurants) {
            if (Objects.equals(r.city, search)) {
                restaurants1.add(r);
            }
        }

        return restaurants1;
    }

    public ArrayList<Restaurant> sortRestaurantsByScore() {
        for (Restaurant r : restaurants) {
            r.overall = getRestaurantOverallScore(r.name);
        }

        for (int i = 0 ; i < restaurants.size() ; i++) {
            for (int j = 0 ; j < restaurants.size() ; j++) {
                if(restaurants.get(i).overall < restaurants.get(j).overall) {
                    Collections.swap(restaurants, i, j);
                }
            }
        }

        return  restaurants;
    }

    public ArrayList<Restaurant> filterRestaurants(String filter, String search) {

        return switch (filter) {
            case "search_by_type" -> getRestaurantsByType(search);
            case "search_by_name" -> getRestaurantsByName(search);
            case "search_by_city" -> getRestaurantsByCity(search);
            case "sort_by_score" ->  sortRestaurantsByScore();
            default -> restaurants;
        };
    }

    public String createHTMLForTables(Restaurant restaurant) {
        String html = "";

        for (Table t : tables) {
            if(Objects.equals(t.restaurantName, restaurant.name)) {
                html += "<li>"+ "table#"+ String.valueOf(t.tableNumber) +"</li>";
            }
        }

        return html;
    }

    public String createHtmlForTableOptions(Restaurant restaurant) {
        String html = "";
        for (Table t : tables) {
            System.out.println(t.tableNumber);
            if(Objects.equals(t.restaurantName, restaurant.name)) {
                html += "<option value=3>"+ t.tableNumber +"</option>";
            }
        }

        return html;
    }

    public String createHTMLForRestaurantsList(String filter, String search) {
        ArrayList<Restaurant> filteredRestaurants = filterRestaurants(filter, search);
        String html = "";
        for (Restaurant r : filteredRestaurants) {
            r.address = new Address();
            r.responseHandler = new ResponseHandler();

            getRestaurantScores(r.name);
            html += "<tr>\n" +
                    "        <th>" + r.id +"</th>\n" +
                    "        <th>" + "<a href=" +"restaurant/" + r.name+ ">" + r.name + "</a>" + "</th>\n" +
                    "        <th>" + r.city + "</th>\n" +
                    "        <th>" + r.type + "</th>\n" +
                    "        <th>" + r.startTime + " - " + r.endTime + "</th>\n" +
                    "        <th>"+ relatedRestaurantOverall +"</th>\n" +
                    "        <th>"+ relatedRestaurantFood +"</th>\n" +
                    "        <th>" + relatedRestaurantAmbiance + "</th>\n" +
                    "        <th>" + relatedRestaurantService + "</th>\n" +
                    "    </tr>";
        }
        return html;
    }

    public String createHtmlForRestaurantReviews(String name) {
        String html = "";
        for (Review r : reviews) {
            if (Objects.equals(name, r.restaurantName)) {
                html += "<tr>\n" +
                        "        <td>"+ r.username +"</td>\n" +
                        "        <td>"+ r.comment +"</td>\n" +
                        "        <td>"+ r.reviewDate +"</td>\n" +
                        "        <td>"+ r.foodRate +"</td>\n" +
                        "        <td>"+ r.serviceRate +"</td>\n" +
                        "        <td>"+ r.ambianceRate +"</td>\n" +
                        "        <td>"+ r.overall +"</td>\n" +
                        "    </tr>";
            }
        }

        return html;
    }

    public double relatedRestaurantOverall;
    public double relatedRestaurantAmbiance;
    public double relatedRestaurantFood;
    public double relatedRestaurantService;

    public void getRestaurantScores(String restaurantName) {
        double overallSum = 0;
        double ambianceSum = 0;
        double foodSum = 0;
        double serviceSum = 0;
        int num = 0;
        for (Review r : reviews) {
            if(Objects.equals(r.restaurantName, restaurantName)) {
                overallSum += r.overall;
                ambianceSum += r.ambianceRate;
                foodSum += r.foodRate;
                serviceSum += r.serviceRate;
                num++;
            }
        }

        relatedRestaurantOverall = (overallSum) / num;
        relatedRestaurantAmbiance = (ambianceSum) / num;
        relatedRestaurantFood = (foodSum) / num;
        relatedRestaurantService = (serviceSum) / num;
    }

    public double getRestaurantOverallScore(String restaurantName) {
        double overallScore;
        double sum = 0;
        int size = 0;
        for (Review r : reviews) {
            if (Objects.equals(r.restaurantName, restaurantName)) {
                sum += r.overall;
                size ++;
            }
        }

        return sum / size;
    }

    public Restaurant findRestaurantByName(String restaurantName) {
       // System.out.println("req name is " + restaurantName);
        for(Restaurant rest: restaurants) {
            if(Objects.equals(rest.name, restaurantName)) {
                return rest;
            }
        }
        return null;
    }

    public Restaurant findRestaurantByManager(String managerUsername) {
        for(Restaurant rest: restaurants) {
            System.out.println(rest.name);
            if(Objects.equals(rest.managerUsername, managerUsername)) {
                return rest;
            }
        }
        return null;
    }

    public User findUserByUserName(String userName) {
        for(User user: users) {
            if(Objects.equals(user.username, userName)) {
                return user;
            }
        }
        return null;
    }

    public void saveActiveUser(User user) {
        Writer writer = new Writer();
        writer.writeUser(user);
    }

    public void saveReview(Review review) {
        Writer writer = new Writer();
        Date date = new Date();
        String reviewString = review.restaurantName+","+review.username+","+
                date+","+review.comment+","+review.foodRate+","+
                review.serviceRate+","+review.ambianceRate+","+review.overall +"\n";

        try {
            writer.writeReview(REVIEWS_CSV, reviewString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        removeRedundantReviews();
    }

    public void removeRedundantReviews() {
        Set<String> uniqueUsernames = new HashSet<>();
        Set<String> uniqueRestaurant = new HashSet<>();
        Iterator<Review> iterator = reviews.iterator();

        while (iterator.hasNext()) {
            Review review = iterator.next();
            if (!uniqueUsernames.add(review.username) && !uniqueRestaurant.add(review.restaurantName)) {
                iterator.remove();
            }
        }
    }

    public String getActiveUser() {
        Reader reader = new Reader();
        String username = "";
        try {
            username = reader.getActive(CURRENT_USER_ADDRESS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return username;
    }

    public String getCurrentRestaurant() {
        Reader reader = new Reader();
        String restaurantName = "";
        try {
            restaurantName = reader.getActive(CURRENT_RESTAURANT_ADDRESS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("rest name is "+restaurantName);
        return restaurantName;
    }

    public void saveActiveRestaurant(Restaurant restaurant) {
        Writer writer = new Writer();
        writer.writeRestaurant(restaurant);
    }

    public User findUserByUsernameAndPass(String username, String password) {
        User user = findUserByUserName(username);
        if(user != null && Objects.equals(user.password, password)){
            return user;
        }
        return null;
    }
}

