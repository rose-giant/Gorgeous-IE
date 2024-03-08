package models;

import objects.Address;
import objects.ResponseHandler;
import objects.Restaurant;
import objects.User;

import java.io.*;

import static models.Addresses.*;

public class Writer {

    public static void writeUser(User user) {
        String fileName = CURRENT_USER_ADDRESS;

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            bufferedWriter.write(user.username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeRestaurant(Restaurant restaurant) {
        String fileName = CURRENT_RESTAURANT_ADDRESS;

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            bufferedWriter.write(restaurant.name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeReview(String filePath, String content) throws IOException {
        StringBuilder fileContents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileContents.insert(0, content);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(fileContents.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeReservationFromFile(String reservationNumber, String filePath) {
        File inputFile = new File(filePath);
        File tempFile = new File(BASE_PATH+"temp.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] attributes = currentLine.split(",");
                if (!attributes[1].equals(reservationNumber)) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }
            writer.close();
            reader.close();

            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.username = "rose";
        user.address = new Address();
        user.responseHandler = new ResponseHandler();
        writeUser(user);

        User user1 = new User();
        user1.username = "j";
        user1.address = new Address();
        user1.responseHandler = new ResponseHandler();
        writeUser(user1);

        Restaurant r = new Restaurant();
        r.name = "charstoon";
        r.address = new Address();
        r.responseHandler = new ResponseHandler();
        writeRestaurant(r);
    }
}
