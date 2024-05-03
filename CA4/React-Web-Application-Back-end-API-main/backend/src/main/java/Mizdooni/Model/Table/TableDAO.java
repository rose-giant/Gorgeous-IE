package Mizdooni.Model.Table;

import Mizdooni.Model.Constants;
import Mizdooni.Model.DAO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class TableDAO extends DAO {
    public ArrayList<Table> getFromAPI() throws Exception{
        String TablesJsonString = getRequest(Constants.GET_TABLES_URL);
        ObjectMapper om = new ObjectMapper();
        return om.readValue(TablesJsonString, new TypeReference<ArrayList<Table>>(){});
    }
}
