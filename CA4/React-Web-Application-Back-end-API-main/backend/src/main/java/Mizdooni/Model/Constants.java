package Mizdooni.Model;

public final class Constants {

    private Constants() {
        // restrict instantiation
    }

    //API_URLS
    public static final String BASE_URL = "http://91.107.137.117:55";
    public static final String GET_RESTAURANTS_URL = "http://91.107.137.117:55/restaurants";
    public static final String GET_USERS_URL = "http://91.107.137.117:55/users";
    public static final String GET_REVIEWS_URL = "http://91.107.137.117:55/reviews";
    public static final String GET_TABLES_URL = "http://91.107.137.117:55/tables";

    //USER
    public static final String MANAGER_ROLE = "manager";
    public static final String CLIENT_ROLE = "client";
    public static final char[] FORBIDDEN_CHARS_FOR_EMAIL = {'`', '~', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '-',
            '=', '{', '[', '}', ']', ';', ':', '|', '/', '\'', '.', '>', ',', '<', ' '};

}