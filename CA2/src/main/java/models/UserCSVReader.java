package models;

import objects.Address;
import objects.User;

import java.io.*;
import java.util.ArrayList;

public class UserCSVReader {
    public ArrayList<User> users = new ArrayList<>();
    public String csvFileName = "users.csv";
    public Integer fieldNumber = 6;
    public ArrayList<User> loadUsers() {
        InputStream inputStream = UserCSVReader.class.getClassLoader().getResourceAsStream(csvFileName);
        if (inputStream != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length == fieldNumber) {
                        User obj = new User();

                        obj.username = values[0];
                        obj.password = values[1];
                        obj.role = values[2];
                        obj.email = values[3];
                        obj.address.country = values[4];
                        obj.address.city = values[5];


                        users.add(obj);
                    } else {
                        System.err.println("Invalid line: " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("CSV file not found: " + csvFileName);
        }

        for (User obj : users) {
            System.out.println(obj.username + ", " + obj.password + ", " + obj.role + ", " + obj.email+ ", " + obj.address.city + ", " + obj.address.country);
        }

        return users;
    }
}