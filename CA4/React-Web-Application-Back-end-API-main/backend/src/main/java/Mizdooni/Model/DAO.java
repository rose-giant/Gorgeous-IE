package Mizdooni.Model;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DAO {

    public static String getRequest(String Url) throws Exception{
        URL url = new URL(Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        //Check if connect is made
        int responseCode = conn.getResponseCode();

        // 200 OK
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                informationString.append(scanner.nextLine());
            }
            scanner.close();
            return informationString.toString();
        }
    }

    //    public ArrayList<User> getFromAPI() throws Exception{
//        String ObjectsJsonString = getRequest();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        return gson.fromJson(ObjectsJsonString, new TypeToken<ArrayList<Object>>() {}.getType());
//    }
}




