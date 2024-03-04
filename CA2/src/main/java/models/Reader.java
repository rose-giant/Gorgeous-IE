package models;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;


import java.io.*;
import java.util.ArrayList;


public class Reader {

    public <classType> ArrayList<classType> readFromFile(String fileAddress, Class<?> classType) throws IOException {
        ArrayList<classType>objs = new ArrayList<>();

        FileReader fr = new FileReader(fileAddress);
        MappingIterator<classType> personIter =
                new CsvMapper().readerWithTypedSchemaFor(classType).readValues(fr);
        objs = (ArrayList<classType>) personIter.readAll();

        return objs;
    }

//    public static void main(String[] args) throws IOException {
//        Reader  csvReader = new Reader();
//        ArrayList<Restaurant> users;
//        users = csvReader.readFromFile("src/main/resources/restaurants.csv", Restaurant.class);
//        System.out.println(users.get(0).name);
//        System.out.println(users.get(0).type);
//        System.out.println(users.get(0).description);
//        System.out.println(users.get(0).endTime);
//
//    }
}


