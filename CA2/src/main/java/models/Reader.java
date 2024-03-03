package models;

import java.util.ArrayList;


public class Reader {

    ArrayList<Object> objs;
    public <classType> ArrayList<classType> ReadFromFile(String fileName, Class<?> classType){
        objs = new ArrayList<>();

//        fill objs from csv file

        return (ArrayList<classType>) objs;
    }
}
